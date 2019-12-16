package cn.justl.fulcrum.vertx.boot.bean;

/**
 * @Date : 2019/12/13
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface BeanHolder<T> {

    String getId();

    void setId(String id);

    T getInstance();

    void setInstance(T instance);
}
