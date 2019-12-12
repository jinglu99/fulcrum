package cn.justl.fulcrum.vertx.boot.context;

import cn.justl.fulcrum.vertx.boot.AbstractBootStrapContext;
import cn.justl.fulcrum.vertx.boot.VerticleHolder;
import cn.justl.fulcrum.vertx.boot.annotation.handler.AnnotationHandler;
import cn.justl.fulcrum.vertx.boot.annotation.handler.VerticleParsable;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.*;
import io.vertx.core.Promise;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * @Date : 2019-11-28
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultBootStrapContext extends AbstractBootStrapContext {

    private static final Logger logger = LoggerFactory.getLogger(DefaultBootStrapContext.class);

    private final ServiceLoader<AnnotationHandler> annotationHandlers = ServiceLoader
        .load(AnnotationHandler.class);

    private final Set<BeanDefinition> instantiatingDefinitions = new HashSet<>();

    private final Set<BeanDefinition> instantiatedDefinitions = new HashSet<>();

    private final List<List<BeanDefinition>> deployOrderLevel = new ArrayList<>();

    private final Promise initPromise;

    public DefaultBootStrapContext(Promise initPromise) {
        this.initPromise = initPromise;
    }

    @Override
    public void scanVerticles(String[] packages) throws VertxBootException {
        if (packages == null || packages.length == 0) {
            throw new VertxBootException("No package given when scan verticles");
        }

        annotationHandlers.forEach(handler -> {
            for (String packagePath : packages) {
                try {
                    handler.scan(this, packagePath)
                        .forEach(definition -> {
                            registerBeanDefinition(definition);
                        });
                } catch (AnnotationScannerException e) {
                    logger.error("Something wrong when scan package " + packagePath, e);
                }
            }
        });
    }

    @Override
    public void scanVerticles(Class[] classes) throws VertxBootException {
        if (classes == null || classes.length == 0) {
            logger.warn("No verticles provided!");
        }

        for (Class clazz : classes) {
            for (AnnotationHandler handler : annotationHandlers) {
                if (handler.isTargetVerticle(clazz)) {
                    registerBeanDefinition(((VerticleParsable) handler).parseVerticle(clazz));
                    break;
                }
            }
        }
    }

    @Override
    public void instantiateVerticles() throws VertxBootException {
        try {
            for (BeanDefinition definition : listBeanDefinitions()) {
                instantiateVerticle(definition);
            }
        } catch (VerticleCreationException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new VertxBootException("Something wrong when instatiate verticles", throwable);
        }

    }

    @Override
    public void initializeVerticles() throws VertxBootException {
        try {
            orderDeploySequence();

            initializeVerticles(initPromise, deployOrderLevel);
        } catch (
            Throwable throwable) {
            throw new VertxBootException("Something wrong when instatiate verticles", throwable);
        }

    }

    private void initializeVerticles(Promise promise, List<List<BeanDefinition>> orderLevels) {
        if (orderLevels == null || orderLevels.size() == 0) {
            promise.complete();
            return;
        }

        getVertx().executeBlocking(p -> {
            try {
                CountDownLatch latch = new CountDownLatch(orderLevels.get(0).size());
                for (BeanDefinition definition : orderLevels.get(0)) {
                    getVertx()
                        .deployVerticle(getVerticleHolder(definition.getId()).getTrueVerticle(),
                            res -> {
                                if (res.succeeded()) {
                                    getVerticleHolder(definition.getId())
                                        .setTrueVerticleId(res.result());
                                    latch.countDown();
                                } else {
                                    p.fail(res.cause());
                                    promise.fail(res.cause());
                                }
                            });
                }
                latch.await();
                p.complete();
            } catch (Exception e) {

            }
        }, res -> {
            if (res.succeeded()) {
                if (orderLevels.size() == 1) {
                    promise.complete();
                } else {
                    initializeVerticles(promise, orderLevels.subList(1, orderLevels.size()));
                }
            }
        });
    }


    @Override
    public void close() throws VertxBootException {
        try {
            for (VerticleHolder holder : listVerticleHolders()) {
                Iterator iterator = annotationHandlers.iterator();
                while (iterator.hasNext()) {
                    AnnotationHandler handler = (AnnotationHandler) iterator.next();
                    if (handler.isTargetVerticle(holder.getVerticleDefinition().getClazz())) {
                        handler.close(this, holder.getVerticleDefinition(), holder);
                        unregisterVerticle(holder.getId());
                        unregisterBeanDefinition(holder.getId());
                        logger.info("unregister verticle {}", holder.getId());
                        break;
                    }
                }
            }
        } catch (VerticleCloseException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new VertxBootException("Something wrong happens when close verticles", throwable);
        }
    }

    @Override
    public BootStrapContext getContext() {
        return this;
    }

    private void instantiateVerticle(BeanDefinition verticleDefinition)
        throws VerticleCreationException {
        if (instantiatingDefinitions.contains(verticleDefinition)) {
            logger.error("There is Circular dependency occurred in {}",
                verticleDefinition.getClazz().getName());
            throw new VerticleCreationException(
                "There is Circular dependency occurred in " + verticleDefinition.getClazz()
                    .getName());
        }

        if (instantiatedDefinitions.contains(verticleDefinition)) {
            logger.info("Verticle {} has been instantiated.", verticleDefinition.getId());
            return;
        }

        instantiatingDefinitions.add(verticleDefinition);

        if (verticleDefinition.getDependOn() != null) {
            logger.info("Verticle {} is depend on {}", verticleDefinition.getId(),
                verticleDefinition.getDependOn());
            for (String dependOn : verticleDefinition.getDependOn()) {
                BeanDefinition definition = getBeanDefinition(dependOn);
                if (definition == null) {
                    logger.warn("There is no Verticle called {} registered in Context", dependOn);
                    continue;
                }

                if (getVerticleHolder(dependOn) != null) {
                    continue;
                }

                instantiateVerticle(definition);
            }
        }

        Iterator iterator = annotationHandlers.iterator();
        while (iterator.hasNext()) {
            AnnotationHandler handler = (AnnotationHandler) iterator.next();
            if (handler.isTargetVerticle(verticleDefinition.getClazz())) {
                VerticleHolder holder = handler.create(this, verticleDefinition);
                registerVerticle(holder);
                logger.info("register verticle {}", holder.getId());
                break;
            }
        }

        instantiatedDefinitions.add(verticleDefinition);
        instantiatingDefinitions.remove(verticleDefinition);
    }

    private void orderDeploySequence() {
        for (BeanDefinition definition : listBeanDefinitions()) {
            orderDeployVerticle(definition);
        }
    }

    private int orderDeployVerticle(BeanDefinition definition) {
        if (definition.getDeployLevel() > -1) {
            return definition.getDeployLevel();
        }

        if (definition.getDependOn() == null || definition.getDependOn().length == 0) {
            return addVerticleToOrderLevel(0, definition);
        }

        List<BeanDefinition> dependOnVerticles = Stream.of(definition.getDependOn())
            .map(id -> getBeanDefinition(id))
            .filter(verticle -> verticle != null)
            .collect(Collectors.toList());

        int maxLevel = 0;
        for (BeanDefinition depend : dependOnVerticles) {
            int level = orderDeployVerticle(depend);
            if (maxLevel < level) {
                maxLevel = level;
            }
        }

        return addVerticleToOrderLevel(maxLevel + 1, definition);
    }

    private int addVerticleToOrderLevel(int level, BeanDefinition definition) {
        List<BeanDefinition> levelList = null;
        if (deployOrderLevel.size() <= level || (levelList = deployOrderLevel.get(level)) == null) {
            levelList = new ArrayList<>();
            deployOrderLevel.add(level, levelList);
        }

        definition.setDeployLevel(level);
        levelList.add(definition);
        return level;
    }
}
