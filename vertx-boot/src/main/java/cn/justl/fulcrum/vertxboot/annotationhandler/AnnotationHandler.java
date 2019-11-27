package cn.justl.fulcrum.vertxboot.annotationhandler;

import cn.justl.fulcrum.vertxboot.VerticleHolder;
import cn.justl.fulcrum.vertxboot.definition.VerticleDefinition;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationScannerException;
import cn.justl.fulcrum.vertxboot.excetions.VerticleInitializeException;
import cn.justl.fulcrum.vertxboot.excetions.VerticleInstantiateException;

import java.util.Set;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface AnnotationHandler {

    Set<VerticleDefinition> scan(String packageName) throws AnnotationScannerException;

    <T> VerticleHolder<T> instantiate(VerticleDefinition<T> verticleHolder) throws VerticleInstantiateException;

    <T> VerticleHolder<T> initialize(VerticleDefinition<T> verticleDefinition, VerticleHolder<T> verticleHolder) throws VerticleInitializeException;

    boolean satisfied(Class clazz);

}
