package cn.justl.fulcrum.vertx.boot;

import cn.justl.fulcrum.vertx.boot.annotation.VerticleScan;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.context.DefaultBootStrapContext;
import cn.justl.fulcrum.vertx.boot.excetions.VertxBootException;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @Date : 2019/11/23
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VertxBootStrap {

    private static final Logger logger = LoggerFactory.getLogger(VertxBootStrap.class);

    private static BootStrapHandler handler;

    public static Future<Void> run(Vertx vertx, Class clazz) {
        return Future.future(promise -> {
            try {
                if (handler != null) {
                    logger.warn(
                        "VertxBootStrap.run() has been called, a new context will be created and the old one will be closed!");
                    handler.close();
                }
                handler = new DefaultBootStrapContext(promise);

                logger.info("start initializing VertX-Boot...");
                doRunVerticleScan(vertx, clazz);
                logger.info("Initializing VertX-Boot successfully");
            } catch (Throwable e) {
                logger.error("VertX-Boot initializing failed", e);
                promise.fail(e);
            }
        });
    }

    public static Future<Void> run(Vertx vertx, String packages) {
        return Future.future(promise -> {
            try {
                if (handler != null) {
                    logger.warn(
                        "VertxBootStrap.run() has been called, a new context will be created and the old one will be closed!");
                    handler.close();
                }
                handler = new DefaultBootStrapContext(promise);

                logger.info("start initializing VertX-Boot...");
                doRunPackages(vertx, packages.split(","));
                logger.info("Initializing VertX-Boot successfully");
            } catch (Throwable e) {
                logger.error("VertX-Boot initializing failed", e);
                promise.fail(e);
            }
        });
    }

    public static Future<Void> close() {
        return Future.future(promise -> {
            try {
                logger.info("Closing VertX-Boot...");
                handler.close();
                handler = null;
                logger.info("Closing VertX-Boot successfully.");
                promise.complete();
            } catch (VertxBootException e) {
                logger.error("VertX-Boot closing failed", e);
                promise.fail(e);
            }
        });
    }

    private static void doRunVerticleScan(Vertx vertx, Class clazz) throws VertxBootException {
        VerticleScan verticleScan = (VerticleScan) clazz.getAnnotation(VerticleScan.class);

        if (verticleScan == null) {
            throw new VertxBootException(
                "VerticleScan annotation not found or no package declared in VerticleScan in class "
                    + clazz.getName());
        }

        String[] packages =
            verticleScan.value().length == 0 ? new String[]{clazz.getPackage().getName()}
                : verticleScan.value();

        doRunPackages(vertx, packages);
    }

    private static void doRunPackages(Vertx vertx, String[] packages) throws VertxBootException {
        printLogo();

        handler.setVertx(vertx);

        handler.scanVerticles(packages);

        handler.instantiateVerticles();

        handler.initializeVerticles();
    }

    public static Context getContext() {
        if (handler == null) {
            return null;
        } else {
            return handler.getContext();
        }
    }

    private static void printLogo() {
        InputStream in = ClassHelper.getClassLoader().getResourceAsStream(Constants.LOGO_PATH);
        System.out.println(new BufferedReader(new InputStreamReader(in))
            .lines().collect(Collectors.joining(System.lineSeparator())));
    }
}
