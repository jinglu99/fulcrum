package cn.justl.fulcrum.vertx.boot.context;

import cn.justl.fulcrum.vertx.boot.definition.BeanDefinitionRegister;
import cn.justl.fulcrum.vertx.boot.excetions.VertxBootException;
import cn.justl.fulcrum.vertx.boot.excetions.VertxBootInitializeException;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface BootStrapContext extends BeanDefinitionRegister,
    BeanCreator, BeanInitializer, BeanCloser, VertxContext {

    void init() throws VertxBootInitializeException;

    void close() throws VertxBootException;
}
