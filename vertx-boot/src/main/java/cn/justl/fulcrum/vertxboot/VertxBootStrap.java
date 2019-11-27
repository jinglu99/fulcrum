package cn.justl.fulcrum.vertxboot;

import cn.justl.fulcrum.vertxboot.annotation.VerticleScan;
import cn.justl.fulcrum.vertxboot.annotationhandler.AnnotationHandler;
import cn.justl.fulcrum.vertxboot.context.VertxBootContext;
import cn.justl.fulcrum.vertxboot.excetions.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * @Date : 2019/11/23
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VertxBootStrap extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(VertxBootStrap.class);

    private static volatile boolean isRun = false;

    private static final ServiceLoader<AnnotationHandler> annotationHandlers = ServiceLoader.load(AnnotationHandler.class);

    public static Future<Void> run(Vertx vertx, Class clazz) {
        return Future.future(promise -> {
            if (isRun) {
                promise.fail(new VertxBootInitializeException(
                        "VertxBootStrap.run() only can be called one time!"));
                return;
            }

            try {
                doRun(vertx, clazz);
                promise.complete();
            } catch (Throwable e) {
                promise.fail(e);
            }
        });
    }

    public static void doRun(Vertx vertx, Class clazz) throws VertxBootException {
        printLogo();

        VertxBootContext.getInstance().setVertx(vertx);

        scanVerticle(clazz);

        instantiateVerticle();
    }

    private static void scanVerticle(Class clazz) throws VertxBootException {
        VerticleScan verticleScan = (VerticleScan) clazz.getAnnotation(VerticleScan.class);

        if (verticleScan == null) {
            throw new VertxBootException("VerticleScan annotation not found or no package declared in VerticleScan in class " + clazz.getName());
        }

        String[] packages = verticleScan.value().length == 0 ? new String[] {clazz.getPackage().getName()} : verticleScan.value();

        annotationHandlers.forEach(handler -> {
            for (String packagePath : packages) {
                try {
                    handler.scan(packagePath)
                            .forEach(verticle -> {
                                VertxBootContext.getInstance().registerVerticleClass(verticle);
                            });
                } catch (AnnotationScannerException e) {
                    logger.error("Something wrong when scan package " + packagePath, e);
                }
            }
        });
    }

    private static void instantiateVerticle() throws VertxBootException {
            try {
                for (Class clazz : VertxBootContext.getInstance().listVerticleClasses()) {

                    Iterator iterator = annotationHandlers.iterator();
                    while (iterator.hasNext()) {
                        AnnotationHandler handler = (AnnotationHandler) iterator.next();
                        if (handler.satisfied(clazz)) {
                            VerticleHolder holder = handler.initialize(clazz);
                            VertxBootContext.getInstance().registerVerticle(holder.getName(), holder);
                            logger.info("register verticle {}", holder.getName());
                        }
                    }
                }
            } catch (VerticleInitializeException e) {
                throw e;
            } catch (Throwable throwable) {
                throw new VertxBootException("Something wrong when instatiate verticles", throwable);
            }

    }

    private static void printLogo() {
        InputStream in = ClassHelper.getClassLoader().getResourceAsStream(Constants.LOGO_PATH);
        System.out.println(new BufferedReader(new InputStreamReader(in))
                .lines().collect(Collectors.joining(System.lineSeparator())));
    }
}
