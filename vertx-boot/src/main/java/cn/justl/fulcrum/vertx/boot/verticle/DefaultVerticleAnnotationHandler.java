package cn.justl.fulcrum.vertx.boot.verticle;

import cn.justl.fulcrum.vertx.boot.annotation.VertX;
import cn.justl.fulcrum.vertx.boot.annotation.handler.BootBeanAnnotationHandler;
import cn.justl.fulcrum.vertx.boot.annotation.handler.DefaultBootBeanAnnotationHandler;
import cn.justl.fulcrum.vertx.boot.bean.BeanHolder;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.definition.DefaultBeanDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.BeanCreationException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanDefinitionParseException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanInitializeException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanStartException;
import cn.justl.fulcrum.vertx.boot.helper.AnnotationHelper;
import cn.justl.fulcrum.vertx.boot.verticle.annotations.PostStart;
import cn.justl.fulcrum.vertx.boot.verticle.annotations.PreStart;
import cn.justl.fulcrum.vertx.boot.verticle.annotations.Start;
import cn.justl.fulcrum.vertx.boot.verticle.annotations.Verticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/12/19
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultVerticleAnnotationHandler implements BootBeanAnnotationHandler {

    private static final Logger logger = LoggerFactory
        .getLogger(DefaultBootBeanAnnotationHandler.class);

    private static final ServiceLoader<cn.justl.fulcrum.vertx.boot.verticle.VerticleAnnotationHandler> services = ServiceLoader
        .load(VerticleAnnotationHandler.class);

    @Override
    public Future<BeanDefinition> parseBeanDefinition(Context context, Class clazz) {
        return Future.future(promise -> {
            try {
                Verticle verticle = (Verticle) AnnotationHelper
                    .getAnnotation(clazz, Verticle.class);
                BeanDefinition definition = new DefaultBeanDefinition();

                String id =
                    verticle.value().equals("") ?
                        clazz.getSimpleName().substring(0, 1).toLowerCase()
                            + clazz.getSimpleName().substring(1) : verticle.value();
                definition.setId(id);
                definition.setClazz(clazz);
                promise.complete(definition);
            } catch (Exception e) {
                logger
                    .error("Something wrong when parse beanDefinition " + clazz.getName() + ".", e);
                promise.fail(new BeanDefinitionParseException(
                    "Something wrong when parse beanDefinition " + clazz.getName()));
            }
        });
    }

    @Override
    public Future<BeanHolder> createBean(Context context, BeanDefinition beanDefinition) {
        return Future.future(promise -> {
            try {
                VerticleBeanHolder holder = new DefaultVerticleBeanHolder();
                holder.setId(beanDefinition.getId());
                holder.setInstance(beanDefinition.getClazz().newInstance());
                holder.setVerticle(createVerticle(context, beanDefinition, holder));
                promise.complete(holder);
            } catch (Throwable e) {
                logger
                    .error("Failed to instantiate Verticle: " + beanDefinition.getClazz().getName(),
                        e);
                promise.fail(new BeanCreationException(
                    "Failed to instantiate Verticle: " + beanDefinition.getClazz().getName(), e));
            }
        });
    }

    @Override
    public Future<BeanHolder> initBean(Context context, BeanDefinition beanDefinition,
        BeanHolder beanHolder) {
        return Future.future(promise -> {
            try {
                VerticleBeanHolder verticleBeanHolder = (VerticleBeanHolder) beanHolder;
                context.getVertx()
                    .deployVerticle(
                        verticleBeanHolder.getVerticle(),
                        res -> {
                            if (res.succeeded()) {
                                verticleBeanHolder.setVerticleId(res.result());
                                promise.complete(beanHolder);
                            } else {
                                promise.fail(res.cause());
                            }
                        });
            } catch (Exception e) {
                logger.error("Fail to initialize bean " + beanDefinition.getId(), e);
                promise.fail(
                    new BeanInitializeException("Fail to initialize bean " + beanDefinition.getId(),
                        e));
            }
        });
    }

    @Override
    public Future<Void> close(Context context, BeanDefinition beanDefinition,
        BeanHolder beanHolder) {
        return Future.succeededFuture();
    }

    @Override
    public boolean isTargetBean(Class clazz) {
        return AnnotationHelper.hasAnnotation(clazz, Verticle.class);
    }


    protected io.vertx.core.Verticle createVerticle(Context cxt, BeanDefinition verticleDefinition,
        VerticleBeanHolder holder) {
        return new AbstractVerticle() {
            @Override
            public void start() throws Exception {
                injectVertx(cxt, verticleDefinition, holder);

                callPreStart(verticleDefinition, holder);

                doStart(cxt, verticleDefinition, holder);

                callPostStart(verticleDefinition, holder);
            }

            @Override
            public void stop() throws Exception {
            }
        };
    }

    <T> void doStart(Context context, BeanDefinition<T> verticleDefinition,
        VerticleBeanHolder verticleHolder) throws BeanStartException {
        try {
            callStart(context, verticleDefinition.getClazz(), verticleHolder.getInstance());
        } catch (Throwable e) {
            throw new BeanStartException("Failed to execute start option", e);
        }
    }


    private <T> void callStart(Context context, Class<T> clazz, Object obj)
        throws InvocationTargetException, IllegalAccessException, BeanInitializeException {
        Method[] methods = null;
        if ((methods = clazz.getDeclaredMethods()) == null) {
            return;
        }

        List<Method> preStartMethods = Arrays.asList(methods)
            .stream()
            .filter(method -> method.getAnnotation(Start.class) != null)
            .collect(Collectors.toList());

        if (preStartMethods.size() == 0) {
            return;
        }
        if (preStartMethods.size() > 1) {
            throw new BeanInitializeException(
                "More than one PreStart declared in " + clazz.getName());
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


    protected <T> void injectVertx(Context context, BeanDefinition<T> verticleDefinition,
        VerticleBeanHolder verticleHolder) throws IllegalAccessException {
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
        VerticleBeanHolder verticleHolder)
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
        preStartMethods.get(0).invoke(verticleHolder.getInstance());
    }

    protected <T> void callPostStart(BeanDefinition<T> verticleDefinition,
        VerticleBeanHolder verticleHolder)
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
        postStartMethods.get(0).invoke(verticleHolder.getInstance());
    }

}
