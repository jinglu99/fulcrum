package cn.justl.fulcrum.vertx.boot.annotation.handler;

import cn.justl.fulcrum.vertx.boot.helper.ClassHelper;
import cn.justl.fulcrum.vertx.boot.VerticleHolder;
import cn.justl.fulcrum.vertx.boot.annotation.CodeC;
import cn.justl.fulcrum.vertx.boot.codec.Codec;
import cn.justl.fulcrum.vertx.boot.context.BootStrapContext;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.AnnotationScannerException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleCloseException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleCreationException;
import java.util.Set;

/**
 * @Date : 2019/12/11
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class CodecAnnotationHandler implements AnnotationHandler, VerticleParsable {

    @Override
    public Set<BeanDefinition> scan(BootStrapContext context, String packageName) throws AnnotationScannerException {
        for (Class clazz : ClassHelper.scan(packageName, clazz -> clazz.getDeclaredAnnotation(CodeC.class) != null)) {
            context.getVertx().eventBus().registerDefaultCodec(clazz, Codec.getCodec(clazz));
        }
        return null;
    }

    @Override
    public <T> VerticleHolder<T> create(BootStrapContext context, BeanDefinition<T> verticleDefinition)
        throws VerticleCreationException {
        return null;
    }

    @Override
    public <T> void close(BootStrapContext context, BeanDefinition<T> verticleDefinition,
        VerticleHolder<T> verticleHolder) throws VerticleCloseException {
    }

    @Override
    public boolean isTargetVerticle(Class clazz) {
        return false;
    }

    @Override
    public BeanDefinition parseVerticle(Class clazz) throws AnnotationScannerException {
        return null;
    }
}
