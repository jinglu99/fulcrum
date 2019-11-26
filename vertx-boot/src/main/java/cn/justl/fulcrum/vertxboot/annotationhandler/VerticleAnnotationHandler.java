package cn.justl.fulcrum.vertxboot.annotationhandler;

import cn.justl.fulcrum.vertxboot.excetions.AnnotationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VerticleAnnotationHandler extends AbstractAnnotationHandler {
    private static final Logger logger = LoggerFactory.getLogger(VerticleAnnotationHandler.class);

    public void resolve(Class clazz) throws AnnotationNotFoundException  {

    }

    @Override
    public void resolve(Object obj) throws AnnotationNotFoundException {

    }
}
