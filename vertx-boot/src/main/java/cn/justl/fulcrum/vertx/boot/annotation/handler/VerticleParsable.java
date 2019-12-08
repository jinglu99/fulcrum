package cn.justl.fulcrum.vertx.boot.annotation.handler;

import cn.justl.fulcrum.vertx.boot.definition.VerticleDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.AnnotationScannerException;

/**
 * @Date : 2019/12/6
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface VerticleParsable {
    VerticleDefinition parseVerticle(Class clazz) throws AnnotationScannerException;
}