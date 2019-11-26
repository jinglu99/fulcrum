package cn.justl.fulcrum.vertxboot.annotationhandler;

import cn.justl.fulcrum.vertxboot.annotation.VerticleScan;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationNotFoundException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public abstract class AbstractAnnotationHandler<T> implements AnnotationHandler {
    private Logger logger = LoggerFactory.getLogger(AbstractAnnotationHandler.class);



}
