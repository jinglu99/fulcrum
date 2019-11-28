package cn.justl.fulcrum.vertx.boot.annotationhandler;

import cn.justl.fulcrum.vertx.boot.VerticleHolder;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.definition.VerticleDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.AnnotationScannerException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleCloseException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleInitializeException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleInstantiateException;
import io.vertx.core.Closeable;
import io.vertx.core.Vertx;

import java.util.Set;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface AnnotationHandler {

    Set<VerticleDefinition> scan(String packageName) throws AnnotationScannerException;

    <T> VerticleHolder<T> instantiate(Context context, VerticleDefinition<T> verticleHolder) throws VerticleInstantiateException;

    <T> VerticleHolder<T> initialize(Context context, VerticleDefinition<T> verticleDefinition, VerticleHolder<T> verticleHolder) throws VerticleInitializeException;

    <T> void close(Context context, VerticleDefinition<T> verticleDefinition, VerticleHolder<T> verticleHolder) throws VerticleCloseException;

    boolean satisfied(Class clazz);

}
