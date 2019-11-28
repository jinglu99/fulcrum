package cn.justl.fulcrum.vertx.boot;

import cn.justl.fulcrum.vertx.boot.annotation.VerticleScan;
import cn.justl.fulcrum.vertx.boot.excetions.AnnotationScannerException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleInitializeException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleInstantiateException;
import cn.justl.fulcrum.vertx.boot.excetions.VertxBootException;
import cn.justl.fulcrum.vertx.boot.excetions.VertxBootInitializeException;
import cn.justl.fulcrum.vertx.boot.annotationhandler.AnnotationHandler;
import cn.justl.fulcrum.vertx.boot.context.VertxBootContext;
import cn.justl.fulcrum.vertx.boot.definition.VerticleDefinition;
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
 * @Date : 2019/11/23
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VertxBootStrap {

    private static final Logger logger = LoggerFactory.getLogger(VertxBootStrap.class);

    private static volatile boolean isRun = false;

    private static final ServiceLoader<AnnotationHandler> annotationHandlers = ServiceLoader
        .load(AnnotationHandler.class);

    private static final Set<VerticleDefinition> instantiatingDefinitions = new HashSet<>();

    private static final Set<VerticleDefinition> instantiatedDefinitions = new HashSet<>();

    private static final Set<VerticleHolder> initializingVerticles = new HashSet<>();

    private static final Set<VerticleHolder> initializedVerticles = new HashSet<>();

    public static Future<Void> run(Vertx vertx, Class clazz) {
        return Future.future(promise -> {
            if (isRun) {
                logger.error("VertxBootStrap.run() is called more than one time!");
                promise.fail(new VertxBootInitializeException(
                    "VertxBootStrap.run() only can be called one time!"));
                return;
            }

            try {
                logger.info("start initializing VertX-Boot...");
                doRun(vertx, clazz);
                logger.info("Initializing VertX-Boot successfully");
                promise.complete();
            } catch (Throwable e) {
                logger.error("VertX-Boot initializing failed", e);
                promise.fail(e);
            }
        });
    }

    public static Future<Void> run(Vertx vertx, String packages) {
        return Future.future(promise -> {
            if (isRun) {
                logger.error("VertxBootStrap.run() is called more than one time!");
                promise.fail(new VertxBootInitializeException(
                    "VertxBootStrap.run() only can be called one time!"));
                return;
            }

            try {
                logger.info("start initializing VertX-Boot...");
                doRun(vertx, packages.split(","));
                logger.info("Initializing VertX-Boot successfully");
                promise.complete();
            } catch (Throwable e) {
                logger.error("VertX-Boot initializing failed", e);
                promise.fail(e);
            }
        });
    }

    private static void doRun(Vertx vertx, Class clazz) throws VertxBootException {
        VerticleScan verticleScan = (VerticleScan) clazz.getAnnotation(VerticleScan.class);

        if (verticleScan == null) {
            throw new VertxBootException(
                "VerticleScan annotation not found or no package declared in VerticleScan in class "
                    + clazz.getName());
        }

        String[] packages =
            verticleScan.value().length == 0 ? new String[]{clazz.getPackage().getName()}
                : verticleScan.value();

        doRun(vertx, packages);
    }

    private static void doRun(Vertx vertx, String[] packages) throws VertxBootException {
        printLogo();

        VertxBootContext.getInstance().setVertx(vertx);

        scanVerticle(packages);

        instantiateVerticles();

        initializeVerticles();
    }

    private static void scanVerticle(String[] packages) throws VertxBootException {
        if (packages == null || packages.length == 0) {
            throw new VertxBootException("No package given when scan verticles");
        }

        annotationHandlers.forEach(handler -> {
            for (String packagePath : packages) {
                try {
                    handler.scan(packagePath)
                        .forEach(definition -> {
                            VertxBootContext.getInstance().registerVerticleDefinition(definition);
                        });
                } catch (AnnotationScannerException e) {
                    logger.error("Something wrong when scan package " + packagePath, e);
                }
            }
        });
    }

    private static void instantiateVerticles() throws VertxBootException {
        try {
            for (VerticleDefinition definition : VertxBootContext.getInstance()
                .listVerticleDefinitions()) {
                instantiateVerticle(definition);
            }
        } catch (VerticleInstantiateException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new VertxBootException("Something wrong when instatiate verticles", throwable);
        }

    }

    private static void instantiateVerticle(VerticleDefinition verticleDefinition)
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
                VerticleDefinition definition = VertxBootContext.getInstance()
                    .getVerticleDefinition(dependOn);
                if (definition == null) {
                    logger.warn("There is no Verticle called {} registered in Context", dependOn);
                    continue;
                }

                if (VertxBootContext.getInstance().getVerticleHolder(dependOn) != null) {
                    continue;
                }

                instantiateVerticle(definition);
            }
        }

        Iterator iterator = annotationHandlers.iterator();
        while (iterator.hasNext()) {
            AnnotationHandler handler = (AnnotationHandler) iterator.next();
            if (handler.satisfied(verticleDefinition.getClazz())) {
                VerticleHolder holder = handler.instantiate(verticleDefinition);
                VertxBootContext.getInstance().registerVerticle(holder);
                logger.info("register verticle {}", holder.getId());
                break;
            }
        }

        instantiatedDefinitions.add(verticleDefinition);
        instantiatingDefinitions.remove(verticleDefinition);
    }

    private static void initializeVerticles() throws VertxBootException {
        try {
            for (VerticleHolder verticleHolder : VertxBootContext.getInstance()
                .listVerticleHolders()) {
                initializeVerticle(verticleHolder);
            }
        } catch (VerticleInitializeException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new VertxBootException("Something wrong when instatiate verticles", throwable);
        }

    }

    private static void initializeVerticle(VerticleHolder verticleHolder)
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
                VerticleHolder holder = VertxBootContext.getInstance().getVerticleHolder(dependOn);
                if (holder == null) {
                    continue;
                }

                if (VertxBootContext.getInstance().getVerticleHolder(dependOn) != null) {
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
                    .initialize(verticleHolder.getVerticleDefinition(), verticleHolder);
                logger.info("Initialize verticle {}", holder.getId());
                break;
            }
        }

        initializedVerticles.add(verticleHolder);
        initializingVerticles.remove(verticleHolder);
    }

    private static void printLogo() {
        InputStream in = ClassHelper.getClassLoader().getResourceAsStream(Constants.LOGO_PATH);
        System.out.println(new BufferedReader(new InputStreamReader(in))
            .lines().collect(Collectors.joining(System.lineSeparator())));
    }
}
