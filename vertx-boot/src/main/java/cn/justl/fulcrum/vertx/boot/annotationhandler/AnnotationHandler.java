package cn.justl.fulcrum.vertx.boot.annotationhandler;

import cn.justl.fulcrum.vertx.boot.VerticleHolder;
import cn.justl.fulcrum.vertx.boot.definition.VerticleDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.AnnotationScannerException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleInitializeException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleInstantiateException;

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
