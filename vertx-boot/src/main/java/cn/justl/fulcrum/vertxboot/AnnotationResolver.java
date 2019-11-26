package cn.justl.fulcrum.vertxboot;

import cn.justl.fulcrum.vertxboot.annotation.HttpVerticle;
import cn.justl.fulcrum.vertxboot.annotation.Verticle;
import cn.justl.fulcrum.vertxboot.annotation.VerticleScan;
import cn.justl.fulcrum.vertxboot.annotationhandler.AnnotationHandler;
import cn.justl.fulcrum.vertxboot.annotationhandler.HttpVerticleAnnotationHandler;
import cn.justl.fulcrum.vertxboot.annotationhandler.VerticleAnnotationHandler;
import cn.justl.fulcrum.vertxboot.annotationhandler.VerticleScanAnnotationHandler;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationHandleException;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationHandlerNotFoundException;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationNotFoundException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class AnnotationResolver {

    private static Logger logger = LoggerFactory.getLogger(AnnotationResolver.class);

    private static Map<Class, AnnotationHandler> resolvers = new HashMap();

    static {
        register(VerticleScan.class, new VerticleScanAnnotationHandler());
        register(Verticle.class, new VerticleAnnotationHandler());
        register(HttpVerticle.class, new HttpVerticleAnnotationHandler());
    }


    public static void resolve(Class clazz, Class<? extends Annotation>... annotations)
        throws AnnotationHandlerNotFoundException {
        for (Class annotation : annotations) {
            AnnotationHandler handler;
            if ((handler = resolvers.get(annotation)) != null) {
                try {
                    handler.resolve(clazz);
                } catch (AnnotationHandleException e) {}
            } else {
                logger.error("Can't find AnnotationHandler for {}",
                    annotation.getSimpleName());
                throw new AnnotationHandlerNotFoundException(
                    "Can't find AnnotationHandler for " + annotation.getSimpleName());
            }
        }
    }

    private static synchronized void register(Class clazz, AnnotationHandler handler) {
        resolvers.put(clazz, handler);
    }
}
