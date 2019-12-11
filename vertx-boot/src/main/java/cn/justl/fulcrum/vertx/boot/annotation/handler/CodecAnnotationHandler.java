package cn.justl.fulcrum.vertx.boot.annotation.handler;

import cn.justl.fulcrum.vertx.boot.ClassHelper;
import cn.justl.fulcrum.vertx.boot.VerticleHolder;
import cn.justl.fulcrum.vertx.boot.annotation.CodeC;
import cn.justl.fulcrum.vertx.boot.annotation.Verticle;
import cn.justl.fulcrum.vertx.boot.codec.Codec;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.definition.VerticleDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.AnnotationScannerException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleCloseException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleCreationException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Date : 2019/12/11
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class CodecAnnotationHandler implements AnnotationHandler, VerticleParsable {

    @Override
    public Set<VerticleDefinition> scan(Context context, String packageName) throws AnnotationScannerException {
        for (Class clazz : ClassHelper.scan(packageName, clazz -> clazz.getDeclaredAnnotation(CodeC.class) != null)) {
            context.getVertx().eventBus().registerDefaultCodec(clazz, Codec.getCodec(clazz));
        }
        return null;
    }

    @Override
    public <T> VerticleHolder<T> create(Context context, VerticleDefinition<T> verticleDefinition)
        throws VerticleCreationException {
        return null;
    }

    @Override
    public <T> void close(Context context, VerticleDefinition<T> verticleDefinition,
        VerticleHolder<T> verticleHolder) throws VerticleCloseException {

    }

    @Override
    public boolean isTargetVerticle(Class clazz) {
        return false;
    }

    @Override
    public VerticleDefinition parseVerticle(Class clazz) throws AnnotationScannerException {
        return null;
    }
}
