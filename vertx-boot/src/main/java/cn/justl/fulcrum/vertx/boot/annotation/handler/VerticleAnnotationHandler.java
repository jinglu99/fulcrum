package cn.justl.fulcrum.vertx.boot.annotation.handler;

import cn.justl.fulcrum.vertx.boot.annotation.Verticle;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.excetions.AnnotationScannerException;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleInitializeException;
import cn.justl.fulcrum.vertx.boot.VerticleHolder;
import cn.justl.fulcrum.vertx.boot.annotation.Start;
import cn.justl.fulcrum.vertx.boot.definition.DefaultVerticleDefinition;
import cn.justl.fulcrum.vertx.boot.definition.VerticleDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleStartException;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VerticleAnnotationHandler extends AbstractAnnotationHandler {
    private static final Logger logger = LoggerFactory.getLogger(VerticleAnnotationHandler.class);


    @Override
    public boolean isTargetVerticle(Class clazz) {
        return clazz.getDeclaredAnnotation(Verticle.class) != null;
    }

    @Override
    VerticleDefinition parseVerticle(Class clazz) throws AnnotationScannerException {
        Verticle verticle = (Verticle) clazz.getAnnotation(Verticle.class);
        VerticleDefinition definition = new DefaultVerticleDefinition();

        String id = verticle.value().equals("") ? clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1) : verticle.value();
        definition.setId(id);
        definition.setClazz(clazz);

        return definition;
    }

    @Override
    <T> void doStart(Context context, VerticleDefinition<T> verticleDefinition, VerticleHolder<T> verticleHolder) throws VerticleStartException {
        try {
            callStart(context, verticleDefinition.getClazz(), verticleHolder.getVerticle());
        } catch (Throwable e) {
            throw new VerticleStartException("Failed to execute start option", e);
        }
    }

    @Override
    void doClose(Context context, VerticleDefinition verticleDefinition, VerticleHolder verticleHolder) {

    }


    private <T> void callStart(Context context, Class<T> clazz, T obj) throws InvocationTargetException, IllegalAccessException, VerticleInitializeException {
        Method[] methods = null;
        if ((methods = clazz.getDeclaredMethods()) == null) return;


        List<Method> preStartMethods = Arrays.asList(methods)
                .stream()
                .filter(method -> method.getAnnotation(Start.class) != null)
                .collect(Collectors.toList());

        if (preStartMethods.size() == 0) return;
        if (preStartMethods.size() > 1) {
            throw new VerticleInitializeException("More than one PreStart declared in " + clazz.getName());
        }

        Method target = preStartMethods.get(0);
        Parameter[] parameters = target.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getType().isAssignableFrom(Vertx.class)) {
                args[i] = context.getVertx();
            }
        }
        target.setAccessible(true);
        target.invoke(obj, args);
    }


}
