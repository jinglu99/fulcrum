package cn.justl.fulcrum.vertxboot.annotationhandler;

import cn.justl.fulcrum.vertxboot.excetions.AnnotationHandleException;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class HttpVerticleAnnotationHandler extends AbstractAnnotationHandler {
    private static final Logger logger = LoggerFactory.getLogger(HttpVerticleAnnotationHandler.class);

    @Override
    public void resolve(Class clazz) throws AnnotationHandleException {
        logger.info("Start resolving HttpVerticle annotation for {}", clazz.getName());
    }

    @Override
    public void resolve(Object obj) throws AnnotationHandleException {

    }
}
