package cn.justl.fulcrum.vertx.boot.annotation.handler;

import cn.justl.fulcrum.vertx.boot.bean.BeanHolder;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.BeanDefinitionParseException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanInitializeException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanCloseException;
import cn.justl.fulcrum.vertx.boot.excetions.BeanCreationException;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface AnnotationHandler {

    BeanDefinition parseBeanDefinition(Context context, Class clazz)
        throws BeanDefinitionParseException;

    BeanHolder creatBean(Context context, BeanDefinition beanDefinition) throws BeanCreationException;

    BeanHolder initBean(Context context, BeanDefinition beanDefinition, BeanHolder beanHolder) throws BeanInitializeException;

    void close(Context context, BeanDefinition beanDefinition, BeanHolder beanHolder) throws BeanCloseException;

    boolean isTargetBean(Class clazz);

}
