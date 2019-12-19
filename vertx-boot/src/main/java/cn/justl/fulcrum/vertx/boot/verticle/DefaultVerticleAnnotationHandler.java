package cn.justl.fulcrum.vertx.boot.verticle;

import cn.justl.fulcrum.vertx.boot.annotation.*;
import cn.justl.fulcrum.vertx.boot.annotation.Verticle;
import cn.justl.fulcrum.vertx.boot.annotation.handler.BootBeanAnnotationHandler;
import cn.justl.fulcrum.vertx.boot.annotation.handler.DefaultBootBeanAnnotationHandler;
import cn.justl.fulcrum.vertx.boot.bean.BeanHolder;
import cn.justl.fulcrum.vertx.boot.bean.BeanHolderImpl;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.definition.DefaultBeanDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.*;
import cn.justl.fulcrum.vertx.boot.helper.AnnotationHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import cn.justl.fulcrum.vertx.boot.verticle.VerticleAnnotationHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/12/19
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultVerticleAnnotationHandler implements BootBeanAnnotationHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultBootBeanAnnotationHandler.class);

    private static final ServiceLoader<cn.justl.fulcrum.vertx.boot.verticle.VerticleAnnotationHandler> services = ServiceLoader.load(VerticleAnnotationHandler.class);

//    @Override
//    public BeanDefinition parseBeanDefinition(Context context, Class clazz)
//        throws BeanDefinitionParseException {
//        Verticle verticle = (Verticle) AnnotationHelper.getAnnotation(clazz, Verticle.class);
//        BeanDefinition definition = new DefaultBeanDefinition();
//
//        String id =
//            verticle.value().equals("") ? clazz.getSimpleName().substring(0, 1).toLowerCase()
//                + clazz.getSimpleName().substring(1) : verticle.value();
//        definition.setId(id);
//        definition.setClazz(clazz);
//        return definition;
//    }
//
//    @Override
//    public BeanHolder createBean(Context context, BeanDefinition beanDefinition)
//        throws BeanCreationException {
//        try {
//            VerticleBeanHolder holder = new DefaultVerticleBeanHolder();
//            holder.setId(beanDefinition.getId());
//            holder.setInstance(beanDefinition.getClazz().newInstance());
//            holder.setVerticle(createVerticle(context, beanDefinition, holder));
//            return holder;
//        } catch (Throwable e) {
//            logger.error("Failed to instantiate Verticle");
//            throw new BeanCreationException(
//                "Failed to instantiate Verticle: " + beanDefinition.getClazz().getName(), e);
//        }
//    }
//
//    @Override
//    public BeanHolder initBean(Context context, BeanDefinition beanDefinition,
//        BeanHolder beanHolder) throws BeanInitializeException {
//
//        context.getVertx().executeBlocking(p -> {
//            try {
//                CountDownLatch latch = new CountDownLatch(orderLevels.get(0).size());
//                for (BeanDefinition definition : orderLevels.get(0)) {
//                    getVertx()
//                        .deployVerticle(getVerticleHolder(definition.getId()).getTrueVerticle(),
//                            res -> {
//                                if (res.succeeded()) {
//                                    getVerticleHolder(definition.getId())
//                                        .setTrueVerticleId(res.result());
//                                    latch.countDown();
//                                } else {
//                                    p.fail(res.cause());
//                                    promise.fail(res.cause());
//                                }
//                            });
//                }
//                latch.await();
//                p.complete();
//            } catch (Exception e) {
//
//            }
//        }, res -> {
//            if (res.succeeded()) {
//                if (orderLevels.size() == 1) {
//                    promise.complete();
//                } else {
//                    initializeVerticles(promise, orderLevels.subList(1, orderLevels.size()));
//                }
//            }
//        });
//
//        return null;
//    }
//
//    @Override
//    public void close(Context context, BeanDefinition beanDefinition, BeanHolder beanHolder)
//        throws BeanCloseException {
//
//    }

    @Override
    public Future<BeanDefinition> parseBeanDefinition(Context context, Class clazz) {
        return null;
    }

    @Override
    public Future<BeanHolder> createBean(Context context, BeanDefinition beanDefinition) {
        return null;
    }

    @Override
    public Future<BeanHolder> initBean(Context context, BeanDefinition beanDefinition, BeanHolder beanHolder) {
        return null;
    }

    @Override
    public Future<Void> close(Context context, BeanDefinition beanDefinition, BeanHolder beanHolder) {
        return null;
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
            callStart(context, verticleDefinition.getClazz(), verticleHolder.getVerticle());
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
        preStartMethods.get(0).invoke(verticleHolder.getVerticle());
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
        postStartMethods.get(0).invoke(verticleHolder.getVerticle());
    }

}
