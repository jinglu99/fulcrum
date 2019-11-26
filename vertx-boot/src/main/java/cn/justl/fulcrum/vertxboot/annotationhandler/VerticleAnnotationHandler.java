package cn.justl.fulcrum.vertxboot.annotationhandler;

import cn.justl.fulcrum.vertxboot.ClassHelper;
import cn.justl.fulcrum.vertxboot.VerticleHolder;
import cn.justl.fulcrum.vertxboot.VertxBootContext;
import cn.justl.fulcrum.vertxboot.annotation.PreStart;
import cn.justl.fulcrum.vertxboot.annotation.Start;
import cn.justl.fulcrum.vertxboot.annotation.Verticle;
import cn.justl.fulcrum.vertxboot.excetions.AnnotationScannerException;
import cn.justl.fulcrum.vertxboot.excetions.VerticleInitializeException;
import io.vertx.core.Vertx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VerticleAnnotationHandler extends AbstractAnnotationHandler {
    @Override
    public Set<Class> scan(String packageName) throws AnnotationScannerException {
        return ClassHelper.scan(packageName, clazz ->
                clazz.getDeclaredAnnotation(Verticle.class) != null
        );
    }

    @Override
    public <T> VerticleHolder<T> initialize(Class<T> clazz) throws VerticleInitializeException {
        try {
            Verticle verticle = clazz.getDeclaredAnnotation(Verticle.class);

            String name = verticle.value().equals("") ? clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1) : verticle.value();

            T obj = instantiate(clazz);

            injectVertx(clazz, obj);

            callPreStart(clazz, obj);

            callStart(clazz, obj);

            callPostStart(clazz, obj);

            return new VerticleHolder<>(name, obj);
        } catch (VerticleInitializeException e) {
            throw e;
        } catch (Throwable e) {
            throw new VerticleInitializeException("Failed to initialize Verticle " + clazz.getName(), e);
        }
    }

    @Override
    public boolean satisfied(Class clazz) {
        return clazz.getDeclaredAnnotation(Verticle.class) != null;
    }


    public <T> void callStart(Class<T> clazz, T obj) throws InvocationTargetException, IllegalAccessException, VerticleInitializeException {
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
                args[i] = VertxBootContext.getInstance().getVertx();
            }
        }

        preStartMethods.get(0).invoke(obj, parameters);
    }


}
