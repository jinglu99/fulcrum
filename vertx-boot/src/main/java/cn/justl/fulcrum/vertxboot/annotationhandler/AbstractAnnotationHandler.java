package cn.justl.fulcrum.vertxboot.annotationhandler;

import cn.justl.fulcrum.vertxboot.context.VertxBootContext;
import cn.justl.fulcrum.vertxboot.annotation.PostStart;
import cn.justl.fulcrum.vertxboot.annotation.PreStart;
import cn.justl.fulcrum.vertxboot.annotation.VertX;
import cn.justl.fulcrum.vertxboot.excetions.VerticleInitializeException;
import cn.justl.fulcrum.vertxboot.excetions.VerticleInstantiateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public abstract class AbstractAnnotationHandler implements AnnotationHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAnnotationHandler.class);


    public <T> T instantiate(Class<T> clazz) throws VerticleInstantiateException {
        try {
            return clazz.newInstance();
        } catch (Throwable e) {
            throw new VerticleInstantiateException("Failed to instantiate Verticle: " + clazz.getName(), e);
        }
    }

    public <T> void injectVertx(Class<T> clazz, T obj) throws IllegalAccessException {
        Field[] fields = null;
        if ((fields = clazz.getDeclaredFields()) == null) return;

        for (Field field : fields) {
            if (field.getAnnotation(VertX.class) == null) continue;
            field.setAccessible(true);
            field.set(obj, VertxBootContext.getInstance().getVertx());
        }
    }

    public <T> void callPreStart(Class<T> clazz, T obj) throws InvocationTargetException, IllegalAccessException, VerticleInitializeException {
        Method[] methods = null;
        if ((methods = clazz.getDeclaredMethods()) == null) return;


        List<Method> preStartMethods = Arrays.asList(methods)
                .stream()
                .filter(method -> method.getAnnotation(PreStart.class) != null)
                .collect(Collectors.toList());

        if (preStartMethods.size() == 0) return;
        if (preStartMethods.size() > 1) {
            throw new VerticleInitializeException("More than one PreStart declared in " + clazz.getName());
        }
        preStartMethods.get(0).setAccessible(true);
        preStartMethods.get(0).invoke(obj);
    }

    public <T> void callPostStart(Class<T> clazz, T obj) throws VerticleInitializeException, InvocationTargetException, IllegalAccessException {
        Method[] methods = null;
        if ((methods = clazz.getDeclaredMethods()) == null) return;


        List<Method> postStartMethods = Arrays.asList(methods)
                .stream()
                .filter(method -> method.getAnnotation(PostStart.class) != null)
                .collect(Collectors.toList());

        if (postStartMethods.size() == 0) return;
        if (postStartMethods.size() > 1) {
            throw new VerticleInitializeException("More than one PostStart declared in " + clazz.getName());
        }

        postStartMethods.get(0).setAccessible(true);
        postStartMethods.get(0).invoke(obj);
    }

}
