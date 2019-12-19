package cn.justl.fulcrum.vertx.boot.annotation.handler;

import cn.justl.fulcrum.vertx.boot.bean.BeanHolder;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.BeanDefinitionParseException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanInitializeException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanCloseException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanCreationException;
import io.vertx.core.Future;


/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface AnnotationHandler {

    Future<BeanDefinition> parseBeanDefinition(Context context, Class clazz);

    Future<BeanHolder> createBean(Context context, BeanDefinition beanDefinition);

    Future<BeanHolder> initBean(Context context, BeanDefinition beanDefinition, BeanHolder beanHolder);

    Future<Void> close(Context context, BeanDefinition beanDefinition, BeanHolder beanHolder);

    boolean isTargetBean(Class clazz);

}
