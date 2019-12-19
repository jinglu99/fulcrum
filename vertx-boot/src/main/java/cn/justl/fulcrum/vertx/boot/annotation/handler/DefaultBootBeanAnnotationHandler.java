package cn.justl.fulcrum.vertx.boot.annotation.handler;

import cn.justl.fulcrum.vertx.boot.annotation.BootBean;
import cn.justl.fulcrum.vertx.boot.bean.BeanHolder;
import cn.justl.fulcrum.vertx.boot.bean.BeanHolderImpl;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.definition.DefaultBeanDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.BeanCloseException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanCreationException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanDefinitionParseException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanInitializeException;
import cn.justl.fulcrum.vertx.boot.helper.AnnotationHelper;

import java.util.Iterator;
import java.util.ServiceLoader;

import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/12/16
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultBootBeanAnnotationHandler implements AnnotationHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultBootBeanAnnotationHandler.class);

    private static final ServiceLoader<BootBeanAnnotationHandler> services = ServiceLoader.load(BootBeanAnnotationHandler.class);

    @Override
    public Future<BeanDefinition> parseBeanDefinition(Context context, Class clazz) {
        return Future.future(promise -> {

            try {
                Iterator<BootBeanAnnotationHandler> iterator = services.iterator();
                while (iterator.hasNext()) {
                    BootBeanAnnotationHandler handler = iterator.next();
                    if (handler.isTargetBean(clazz)) {
                        handler.parseBeanDefinition(context, clazz)
                                .compose(definition -> {
                                    promise.complete(definition);
                                    return Future.succeededFuture();
                                })
                                .otherwise(throwable -> {
                                    promise.fail(throwable);
                                    return Future.failedFuture(throwable);
                                });
                        return;
                    }
                }
                BeanDefinition definition = new DefaultBeanDefinition();
                definition.setId(clazz.getSimpleName().substring(0, 1).toLowerCase()
                        + clazz.getSimpleName().substring(1));
                definition.setClazz(clazz);
                promise.complete(definition);
            } catch (Exception e) {
                logger.error("Failed to parse bean definition of " + clazz.getName(), e);
                promise.fail(new BeanCreationException("Failed to parse bean definition of " + clazz.getName(), e));
            }
        });

    }

    @Override
    public Future<BeanHolder> createBean(Context context, BeanDefinition beanDefinition) {
        return Future.future(promise -> {
            try {
                Iterator<BootBeanAnnotationHandler> iterator = services.iterator();
                while (iterator.hasNext()) {
                    BootBeanAnnotationHandler handler = iterator.next();
                    if (handler.isTargetBean(beanDefinition.getClazz())) {
                        handler.createBean(context, beanDefinition)
                                .compose(holder -> {
                                    promise.complete(holder);
                                    return Future.succeededFuture();
                                })
                                .otherwise(throwable -> {
                                    promise.fail(throwable);
                                    return Future.failedFuture(throwable);
                                });
                        return;
                    }
                }

                Object bean = beanDefinition.getClazz().newInstance();
                BeanHolder holder = new BeanHolderImpl();
                holder.setId(beanDefinition.getId());
                holder.setInstance(bean);
                promise.complete(holder);
            } catch (Exception e) {
                logger.error("Failed to create bean instance of bean " + beanDefinition.getId(), e);
                promise.fail(new BeanCreationException("Failed to create bean instance of bean " + beanDefinition.getId(), e));
            }
        });

    }

    @Override
    public Future<BeanHolder> initBean(Context context, BeanDefinition beanDefinition,
                               BeanHolder beanHolder) {

        return Future.future(promise->{
            Iterator<BootBeanAnnotationHandler> iterator = services.iterator();
            while (iterator.hasNext()) {
                BootBeanAnnotationHandler handler = iterator.next();
                if (handler.isTargetBean(beanDefinition.getClazz())) {
                    handler.initBean(context, beanDefinition, beanHolder)
                            .compose(holder -> {
                                promise.complete(holder);
                                return Future.succeededFuture();
                            })
                            .otherwise(throwable -> {
                                promise.fail(throwable);
                                return Future.failedFuture(throwable);
                            });
                    return;
                }
            }

            promise.complete(beanHolder);
        });
    }

    @Override
    public Future<Void> close(Context context, BeanDefinition beanDefinition, BeanHolder beanHolder) {
        return Future.future(promise->{
            Iterator<BootBeanAnnotationHandler> iterator = services.iterator();
            while (iterator.hasNext()) {
                BootBeanAnnotationHandler handler = iterator.next();
                if (handler.isTargetBean(beanDefinition.getClazz())) {
                    handler.close(context, beanDefinition, beanHolder)
                            .compose(aVoid -> {
                                promise.complete(aVoid);
                                return Future.succeededFuture();
                            })
                            .otherwise(throwable -> {
                                promise.fail(throwable);
                                return Future.failedFuture(throwable);
                            });
                    return;
                }
            }

            promise.complete();
        });
    }

    @Override
    public boolean isTargetBean(Class clazz) {
        return AnnotationHelper.hasAnnotation(clazz, BootBean.class);
    }
}
