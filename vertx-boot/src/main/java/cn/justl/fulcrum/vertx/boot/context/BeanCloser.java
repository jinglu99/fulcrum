package cn.justl.fulcrum.vertx.boot.context;

import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;

/**
 * @Date : 2019/12/13
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface BeanCloser {
    void closeBeans();
}
