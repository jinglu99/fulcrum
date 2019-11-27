package cn.justl.fulcrum.vertxboot.annotationhandler;

import cn.justl.fulcrum.vertxboot.ClassHelper;
import cn.justl.fulcrum.vertxboot.VerticleHolder;
import cn.justl.fulcrum.vertxboot.annotation.*;
import cn.justl.fulcrum.vertxboot.context.VertxBootContext;
import cn.justl.fulcrum.vertxboot.definition.DefaultVerticleDefinition;
import cn.justl.fulcrum.vertxboot.definition.VerticleDefinition;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationScannerException;
import cn.justl.fulcrum.vertxboot.excetions.VerticleInitializeException;
import cn.justl.fulcrum.vertxboot.excetions.VerticleInstantiateException;
import cn.justl.fulcrum.vertxboot.excetions.VerticleStartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public abstract class AbstractAnnotationHandler implements AnnotationHandler {
    private static final Logger logger = LoggerFactory.getLogger(AbstractAnnotationHandler.class);


    @Override
    public Set<VerticleDefinition> scan(String packageName) throws AnnotationScannerException {
        Set<VerticleDefinition> definitions = new HashSet<>();
        for (Class clazz : ClassHelper.scan(packageName, clazz -> isTargetVerticle(clazz))) {
            definitions.add(setDependent(parseVerticle(clazz)));
        }
        return definitions;
    }

    abstract boolean isTargetVerticle(Class clazz);

    abstract VerticleDefinition parseVerticle(Class clazz) throws AnnotationScannerException;

    @Override
    public <T> VerticleHolder<T> instantiate(VerticleDefinition<T> verticleDefinition) throws VerticleInstantiateException {
        try {
            VerticleHolder holder = new VerticleHolder(verticleDefinition.getId(), verticleDefinition.getClazz().newInstance());
            holder.setVerticleDefinition(verticleDefinition);
            return holder;
        } catch (Throwable e) {
            logger.error("Failed to instantiate Verticle ");
            throw new VerticleInstantiateException("Failed to instantiate Verticle: " + verticleDefinition.getClazz().getName(), e);
        }

    }


    @Override
    public <T> VerticleHolder<T> initialize(VerticleDefinition<T> verticleDefinition, VerticleHolder<T> verticleHolder) throws VerticleInitializeException {
        try {
            injectVertx(verticleDefinition, verticleHolder);

            callPreStart(verticleDefinition, verticleHolder);

            doStart(verticleDefinition, verticleHolder);

            callPostStart(verticleDefinition, verticleHolder);

            return verticleHolder;
        } catch (VerticleInitializeException e) {
            throw e;
        } catch (Throwable e) {
            logger.info("Failed to initialize Verticle " + verticleDefinition.getClazz().getName(), e);
            throw new VerticleInitializeException("Failed to initialize Verticle " + verticleDefinition.getClazz().getName(), e);
        }
    }


    abstract <T> void doStart(VerticleDefinition<T> verticleDefinition, VerticleHolder<T> verticleHolder) throws VerticleStartException;


    protected <T> void injectVertx(VerticleDefinition<T> verticleDefinition, VerticleHolder<T> verticleHolder) throws IllegalAccessException {
        Field[] fields = null;
        if ((fields = verticleDefinition.getClazz().getDeclaredFields()) == null) return;

        for (Field field : fields) {
            if (field.getAnnotation(VertX.class) == null) continue;
            field.setAccessible(true);
            field.set(verticleHolder.getVerticle(), VertxBootContext.getInstance().getVertx());
        }
    }

    protected <T> void callPreStart(VerticleDefinition<T> verticleDefinition, VerticleHolder<T> verticleHolder) throws InvocationTargetException, IllegalAccessException, VerticleInitializeException {
        Method[] methods = null;
        if ((methods = verticleDefinition.getClazz().getDeclaredMethods()) == null) return;


        List<Method> preStartMethods = Arrays.asList(methods)
                .stream()
                .filter(method -> method.getAnnotation(PreStart.class) != null)
                .collect(Collectors.toList());

        if (preStartMethods.size() == 0) return;
        if (preStartMethods.size() > 1) {
            throw new VerticleInitializeException("More than one PreStart declared in " + verticleDefinition.getClazz().getName());
        }
        preStartMethods.get(0).setAccessible(true);
        preStartMethods.get(0).invoke(verticleHolder.getVerticle());
    }

    protected <T> void callPostStart(VerticleDefinition<T> verticleDefinition, VerticleHolder<T> verticleHolder) throws VerticleInitializeException, InvocationTargetException, IllegalAccessException {
        Method[] methods = null;
        if ((methods = verticleDefinition.getClazz().getDeclaredMethods()) == null) return;


        List<Method> postStartMethods = Arrays.asList(methods)
                .stream()
                .filter(method -> method.getAnnotation(PostStart.class) != null)
                .collect(Collectors.toList());

        if (postStartMethods.size() == 0) return;
        if (postStartMethods.size() > 1) {
            throw new VerticleInitializeException("More than one PostStart declared in " + verticleDefinition.getClazz().getName());
        }

        postStartMethods.get(0).setAccessible(true);
        postStartMethods.get(0).invoke(verticleHolder.getVerticle());
    }

    protected VerticleDefinition setDependent(VerticleDefinition definition) {
        DependOn dependOn = (DependOn) definition.getClazz().getAnnotation(DependOn.class);
        if (dependOn == null || dependOn.value().length == 0) return definition;
        definition.setDependOn(dependOn.value());
        return definition;
    }
}
