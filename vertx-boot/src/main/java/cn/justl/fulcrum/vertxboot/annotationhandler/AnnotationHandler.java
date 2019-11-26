package cn.justl.fulcrum.vertxboot.annotationhandler;

import cn.justl.fulcrum.vertxboot.excetions.AnnotationHandleException;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationNotFoundException;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface  AnnotationHandler {
    void resolve(Class clazz) throws AnnotationHandleException;

    void resolve(Object obj) throws AnnotationHandleException;
}
