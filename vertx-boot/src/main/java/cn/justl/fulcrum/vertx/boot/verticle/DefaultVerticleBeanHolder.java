package cn.justl.fulcrum.vertx.boot.verticle;

import cn.justl.fulcrum.vertx.boot.bean.BeanHolder;
import cn.justl.fulcrum.vertx.boot.bean.BeanHolderImpl;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.definition.DefaultBeanDefinition;

/**
 * @Date : 2019-12-19
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultVerticleBeanHolder implements VerticleBeanHolder {

    private BeanHolder holder = new BeanHolderImpl();

    private Object verticle;

    @Override
    public void setVerticle(Object obj) {
        this.verticle = obj;
    }

    @Override
    public Object getVerticle() {
        return this.verticle;
    }



    ////////////////////////BeanDefinition implements//////////////

    @Override
    public void setId(String id) {
        holder.setId(id);
    }

    @Override
    public String getId() {
        return holder.getId();
    }

    @Override
    public Object getInstance() {
        return holder.getInstance();
    }

    @Override
    public void setInstance(Object instance) {
        holder.setInstance(instance);
    }
}
