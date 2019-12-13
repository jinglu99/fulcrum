package cn.justl.fulcrum.vertx.boot.annotation.handler;

import cn.justl.fulcrum.vertx.boot.helper.ClassHelper;
import cn.justl.fulcrum.vertx.boot.annotation.DependOn;
import cn.justl.fulcrum.vertx.boot.annotation.PostStart;
import cn.justl.fulcrum.vertx.boot.annotation.PreStart;
import cn.justl.fulcrum.vertx.boot.annotation.VertX;
import cn.justl.fulcrum.vertx.boot.context.BootStrapContext;
import cn.justl.fulcrum.vertx.boot.excetions.*;
import cn.justl.fulcrum.vertx.boot.VerticleHolder;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public abstract class AbstractAnnotationHandler implements AnnotationHandler, VerticleParsable {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAnnotationHandler.class);


    @Override
    public Set<BeanDefinition> scan(BootStrapContext context, String packageName) throws AnnotationScannerException {
        Set<BeanDefinition> definitions = new HashSet<>();
        for (Class clazz : ClassHelper.scan(packageName, clazz -> isTargetBean(clazz))) {
            definitions.add(setDependent(parseVerticle(clazz)));
        }
        return definitions;
    }


    @Override
    public <T> VerticleHolder<T> create(BootStrapContext context, BeanDefinition<T> verticleDefinition)
        throws BeanCreationException {
        try {
            VerticleHolder holder = new VerticleHolder();
            holder.setId(verticleDefinition.getId());
            holder.setVerticle(verticleDefinition.getClazz().newInstance());
            holder.setVerticleDefinition(verticleDefinition);
            holder.setTrueVerticle(createVerticle(context, verticleDefinition, holder));
            return holder;
        } catch (Throwable e) {
            logger.error("Failed to instantiate Verticle");
            throw new BeanCreationException(
                "Failed to instantiate Verticle: " + verticleDefinition.getClazz().getName(), e);
        }
    }


    @Override
    public <T> void close(BootStrapContext context, BeanDefinition<T> verticleDefinition,
        VerticleHolder<T> verticleHolder) throws BeanCloseException {
        try {
            AtomicReference<Throwable> throwable = new AtomicReference<>();
            context.getVertx().undeploy(verticleHolder.getTrueVerticleId(), res -> {
                if (res.failed()) {
                    throwable.set(res.cause());
                }
            });

            if (throwable.get() != null) {
                throw throwable.get();
            }
        } catch (Throwable throwable) {
            logger.error("Fail to close the verticle: " + verticleHolder.getId(), throwable);
            throw new BeanCloseException(
                "Fail to close the verticle: " + verticleHolder.getId(), throwable);
        }
    }

    protected Verticle createVerticle(BootStrapContext cxt, BeanDefinition verticleDefinition,
        VerticleHolder verticleHolder) {
        return new AbstractVerticle() {
            @Override
            public void start() throws Exception {
                injectVertx(cxt, verticleDefinition, verticleHolder);

                callPreStart(verticleDefinition, verticleHolder);

                doStart(cxt, verticleDefinition, verticleHolder);

                callPostStart(verticleDefinition, verticleHolder);
            }

            @Override
            public void stop() throws Exception {
                doClose(cxt, verticleDefinition, verticleHolder);
            }
        };
    }


    abstract <T> void doStart(BootStrapContext context, BeanDefinition<T> verticleDefinition,
        VerticleHolder<T> verticleHolder) throws BeanStartException;

    abstract void doClose(BootStrapContext context, BeanDefinition verticleDefinition,
        VerticleHolder verticleHolder);


    protected <T> void injectVertx(BootStrapContext context, BeanDefinition<T> verticleDefinition,
        VerticleHolder<T> verticleHolder) throws IllegalAccessException {
        Field[] fields = null;
        if ((fields = verticleDefinition.getClazz().getDeclaredFields()) == null) {
            return;
        }

        for (Field field : fields) {
            if (field.getAnnotation(VertX.class) == null) {
                continue;
            }
            field.setAccessible(true);
            field.set(verticleHolder.getVerticle(), context.getVertx());
        }
    }

    protected <T> void callPreStart(BeanDefinition<T> verticleDefinition,
        VerticleHolder<T> verticleHolder)
        throws InvocationTargetException, IllegalAccessException, BeanInitializeException {
        Method[] methods = null;
        if ((methods = verticleDefinition.getClazz().getDeclaredMethods()) == null) {
            return;
        }

        List<Method> preStartMethods = Arrays.asList(methods)
            .stream()
            .filter(method -> method.getAnnotation(PreStart.class) != null)
            .collect(Collectors.toList());

        if (preStartMethods.size() == 0) {
            return;
        }
        if (preStartMethods.size() > 1) {
            throw new BeanInitializeException(
                "More than one PreStart declared in " + verticleDefinition.getClazz().getName());
        }
        preStartMethods.get(0).setAccessible(true);
        preStartMethods.get(0).invoke(verticleHolder.getVerticle());
    }

    protected <T> void callPostStart(BeanDefinition<T> verticleDefinition,
        VerticleHolder<T> verticleHolder)
        throws BeanInitializeException, InvocationTargetException, IllegalAccessException {
        Method[] methods = null;
        if ((methods = verticleDefinition.getClazz().getDeclaredMethods()) == null) {
            return;
        }

        List<Method> postStartMethods = Arrays.asList(methods)
            .stream()
            .filter(method -> method.getAnnotation(PostStart.class) != null)
            .collect(Collectors.toList());

        if (postStartMethods.size() == 0) {
            return;
        }
        if (postStartMethods.size() > 1) {
            throw new BeanInitializeException(
                "More than one PostStart declared in " + verticleDefinition.getClazz().getName());
        }

        postStartMethods.get(0).setAccessible(true);
        postStartMethods.get(0).invoke(verticleHolder.getVerticle());
    }

    protected BeanDefinition setDependent(BeanDefinition definition) {
        DependOn dependOn = (DependOn) definition.getClazz().getAnnotation(DependOn.class);
        if (dependOn == null || dependOn.value().length == 0) {
            return definition;
        }
        definition.setDependOn(dependOn.value());
        return definition;
    }

}
