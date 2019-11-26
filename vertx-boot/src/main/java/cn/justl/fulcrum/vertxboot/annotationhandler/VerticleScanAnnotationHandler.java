package cn.justl.fulcrum.vertxboot.annotationhandler;

import cn.justl.fulcrum.vertxboot.AnnotationResolver;
import cn.justl.fulcrum.vertxboot.ClassHelper;
import cn.justl.fulcrum.vertxboot.VertxBootContext;
import cn.justl.fulcrum.vertxboot.annotation.HttpVerticle;
import cn.justl.fulcrum.vertxboot.annotation.Verticle;
import cn.justl.fulcrum.vertxboot.annotation.VerticleScan;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationHandleException;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationHandlerNotFoundException;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationNotFoundException;
import java.lang.annotation.Annotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VerticleScanAnnotationHandler extends AbstractAnnotationHandler {

    private final static Logger logger = LoggerFactory
        .getLogger(VerticleScanAnnotationHandler.class);


    @Override
    public void resolve(Class clazz) throws AnnotationNotFoundException {
        VerticleScan verticleScan;
        if ((verticleScan = (VerticleScan) clazz.getAnnotation(VerticleScan.class)) == null) {
            logger.warn("Can't find annotation {} in class {}", VerticleScan.class.getSimpleName(),
                clazz.getSimpleName());
            throw new AnnotationNotFoundException(String
                .format("Can't find annotation %s in class %s", VerticleScan.class.getSimpleName(),
                    clazz.getSimpleName()));
        }

        logger.info("Start resolving VerticleScan annotation for {}", clazz.getSimpleName());
        String[] packages = verticleScan.value();
        if (packages.length == 0) {
            packages = new String[]{clazz.getPackage().getName()};
        }

        scanVerticle(packages);

        resolveVerticles();
    }

    @Override
    public void resolve(Object obj) {

    }

    public void scanVerticle(String[] packages) {
        for (String packagePath : packages) {
            ClassHelper.scan(packagePath,
                clazz ->
                    clazz.getAnnotation(Verticle.class) != null
                        || clazz.getAnnotation(HttpVerticle.class) != null
            ).stream().forEach(clazz -> {
                logger.info("Find verticle {}", clazz.getName());
                VertxBootContext.getInstance().addVerticle(clazz);
            });
        }
    }

    public void resolveVerticles() {
        VertxBootContext
            .getInstance()
            .listVerticles()
            .stream()
            .forEach(clazz -> {
                try {
                    if (clazz.getAnnotation(HttpVerticle.class) != null) {
                        AnnotationResolver.resolve(clazz, HttpVerticle.class);
                    } else if (clazz.getAnnotation(Verticle.class) != null) {
                        AnnotationResolver.resolve(clazz, Verticle.class);
                    }
                } catch (AnnotationHandlerNotFoundException e) {
                    e.printStackTrace();
                }
            });
    }
}
