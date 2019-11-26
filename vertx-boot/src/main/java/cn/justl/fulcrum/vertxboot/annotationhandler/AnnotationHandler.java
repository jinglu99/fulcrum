package cn.justl.fulcrum.vertxboot.annotationhandler;

import cn.justl.fulcrum.vertxboot.VerticleHolder;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationScannerException;
import cn.justl.fulcrum.vertxboot.excetions.VerticleInitializeException;

import java.util.Set;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface AnnotationHandler {

    Set<Class> scan(String packageName) throws AnnotationScannerException;

    <T> VerticleHolder<T> initialize(Class<T> clazz) throws VerticleInitializeException;

    boolean satisfied(Class clazz);

}
