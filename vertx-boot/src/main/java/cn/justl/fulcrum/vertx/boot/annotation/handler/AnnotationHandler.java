package cn.justl.fulcrum.vertx.boot.annotation.handler;

import cn.justl.fulcrum.vertx.boot.VerticleHolder;
import cn.justl.fulcrum.vertx.boot.context.BootStrapContext;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.AnnotationScannerException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleCloseException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleCreationException;

import java.util.Set;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface AnnotationHandler {

    Set<BeanDefinition> scan(BootStrapContext context, String packageName) throws AnnotationScannerException;

    <T> VerticleHolder<T> create(BootStrapContext context, BeanDefinition<T> verticleDefinition) throws VerticleCreationException;

    <T> void close(BootStrapContext context, BeanDefinition<T> verticleDefinition, VerticleHolder<T> verticleHolder) throws VerticleCloseException;

    boolean isTargetVerticle(Class clazz);

}
