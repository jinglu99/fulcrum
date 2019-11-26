package cn.justl.fulcrum.vertxboot;

import cn.justl.fulcrum.vertxboot.annotation.VerticleScan;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationHandlerNotFoundException;
import cn.justl.fulcrum.vertxboot.excetions.VertxBootInitializeFailedException;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @Date : 2019/11/23
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VertxBootStrap extends AbstractVerticle {

    private static volatile boolean isRun = false;

    public static Future run(Vertx vertx, Class clazz) {
        return Future.future(promise -> {
            if (isRun) {
                promise.fail(new VertxBootInitializeFailedException(
                    "VertxBootStrap.run() only can be called one time!"));
                return;
            }

            try {
                doRun(vertx, clazz);
                promise.complete();
            } catch (AnnotationHandlerNotFoundException e) {
                promise.fail(e);
            }
        });
    }

    public static void doRun(Vertx vertx, Class clazz) throws AnnotationHandlerNotFoundException {
        printLogo();

        scanVerticle(clazz);
    }

    private static void scanVerticle(Class clazz) throws AnnotationHandlerNotFoundException {
        AnnotationResolver.resolve(clazz, VerticleScan.class);
    }

    private static void instantiateVerticle() {

    }

    private static void printLogo() {
        InputStream in = ClassHelper.getClassLoader().getResourceAsStream(Constants.LOGO_PATH);
        System.out.println(new BufferedReader(new InputStreamReader(in))
            .lines().collect(Collectors.joining(System.lineSeparator())));
    }
}
