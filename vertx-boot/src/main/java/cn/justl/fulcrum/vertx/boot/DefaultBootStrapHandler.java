package cn.justl.fulcrum.vertx.boot;

import cn.justl.fulcrum.vertx.boot.annotationhandler.AnnotationHandler;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.context.DefaultVertxBootContext;
import cn.justl.fulcrum.vertx.boot.definition.VerticleDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.*;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Date : 2019-11-28
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultBootStrapHandler implements BootStrapHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultBootStrapHandler.class);

    private final ServiceLoader<AnnotationHandler> annotationHandlers = ServiceLoader
            .load(AnnotationHandler.class);

    private final Set<VerticleDefinition> instantiatingDefinitions = new HashSet<>();

    private final Set<VerticleDefinition> instantiatedDefinitions = new HashSet<>();

    private final Set<VerticleHolder> initializingVerticles = new HashSet<>();

    private final Set<VerticleHolder> initializedVerticles = new HashSet<>();

    private final Context context = new DefaultVertxBootContext();

    @Override
    public void setVertx(Vertx vertx) {
        this.context.setVertx(vertx);
    }

    @Override
    public Vertx getVertx() {
        return context.getVertx();
    }

    @Override
    public void scanVerticles(String[] packages) throws VertxBootException {
        if (packages == null || packages.length == 0) {
            throw new VertxBootException("No package given when scan verticles");
        }

        annotationHandlers.forEach(handler -> {
            for (String packagePath : packages) {
                try {
                    handler.scan(packagePath)
                            .forEach(definition -> {
                                context.registerVerticleDefinition(definition);
                            });
                } catch (AnnotationScannerException e) {
                    logger.error("Something wrong when scan package " + packagePath, e);
                }
            }
        });
    }

    @Override
    public void instantiateVerticles() throws VertxBootException {
        try {
            for (VerticleDefinition definition : context
                    .listVerticleDefinitions()) {
                instantiateVerticle(definition);
            }
        } catch (VerticleInstantiateException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new VertxBootException("Something wrong when instatiate verticles", throwable);
        }

    }

    @Override
    public void initializeVerticles() throws VertxBootException {
        try {
            for (VerticleHolder verticleHolder : context
                    .listVerticleHolders()) {
                initializeVerticle(verticleHolder);
            }
        } catch (VerticleInitializeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new VertxBootException("Something wrong when instatiate verticles", throwable);
        }

    }


    @Override
    public void close() throws VertxBootException {
        try {
            for (VerticleHolder holder : context.listVerticleHolders()) {
                Iterator iterator = annotationHandlers.iterator();
                while (iterator.hasNext()) {
                    AnnotationHandler handler = (AnnotationHandler) iterator.next();
                    if (handler.satisfied(holder.getVerticleDefinition().getClazz())) {
                        handler.close(context, holder.getVerticleDefinition(), holder);
                        context.unregisterVerticle(holder.getId());
                        context.unregisterVerticleDefinition(holder.getId());
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
    public Context getContext() {
        return context;
    }

    private void instantiateVerticle(VerticleDefinition verticleDefinition)
            throws VerticleInstantiateException {
        if (instantiatingDefinitions.contains(verticleDefinition)) {
            logger.error("There is Circular dependency occurred in {}",
                    verticleDefinition.getClazz().getName());
            throw new VerticleInstantiateException(
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
                VerticleDefinition definition = context
                        .getVerticleDefinition(dependOn);
                if (definition == null) {
                    logger.warn("There is no Verticle called {} registered in Context", dependOn);
                    continue;
                }

                if (context.getVerticleHolder(dependOn) != null) {
                    continue;
                }

                instantiateVerticle(definition);
            }
        }

        Iterator iterator = annotationHandlers.iterator();
        while (iterator.hasNext()) {
            AnnotationHandler handler = (AnnotationHandler) iterator.next();
            if (handler.satisfied(verticleDefinition.getClazz())) {
                VerticleHolder holder = handler.instantiate(context, verticleDefinition);
                context.registerVerticle(holder);
                logger.info("register verticle {}", holder.getId());
                break;
            }
        }

        instantiatedDefinitions.add(verticleDefinition);
        instantiatingDefinitions.remove(verticleDefinition);
    }

    private void initializeVerticle(VerticleHolder verticleHolder)
            throws VerticleInitializeException {
        if (initializingVerticles.contains(verticleHolder)) {
            logger.error("There is Circular dependency occurred in {}" + verticleHolder
                    .getVerticleDefinition().getClazz().getName());
            throw new VerticleInitializeException(
                    "There is Circular dependency occurred in {}" + verticleHolder
                            .getVerticleDefinition().getClazz().getName());
        }

        if (initializedVerticles.contains(verticleHolder)) {
            logger.info("Verticle {} has been initialized.", verticleHolder.getId());
            return;
        }

        initializingVerticles.add(verticleHolder);

        if (verticleHolder.getVerticleDefinition().getDependOn() != null) {
            logger.info("Verticle init {} is depend on {}", verticleHolder.getId(),
                    verticleHolder.getVerticleDefinition().getDependOn());
            for (String dependOn : verticleHolder.getVerticleDefinition().getDependOn()) {
                VerticleHolder holder = context.getVerticleHolder(dependOn);
                if (holder == null) {
                    continue;
                }

                if (context.getVerticleHolder(dependOn) != null) {
                    continue;
                }

                initializeVerticle(holder);
            }
        }

        Iterator iterator = annotationHandlers.iterator();
        while (iterator.hasNext()) {
            AnnotationHandler handler = (AnnotationHandler) iterator.next();
            if (handler.satisfied(verticleHolder.getVerticleDefinition().getClazz())) {
                VerticleHolder holder = handler
                        .initialize(context, verticleHolder.getVerticleDefinition(), verticleHolder);
                logger.info("Initialize verticle {}", holder.getId());
                break;
            }
        }

        initializedVerticles.add(verticleHolder);
        initializingVerticles.remove(verticleHolder);
    }


}
