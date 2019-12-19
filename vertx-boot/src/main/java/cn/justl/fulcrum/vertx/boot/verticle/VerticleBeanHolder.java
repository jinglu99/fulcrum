package cn.justl.fulcrum.vertx.boot.verticle;

import cn.justl.fulcrum.vertx.boot.bean.BeanHolder;

/**
 * @Date : 2019-12-19
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface VerticleBeanHolder extends BeanHolder {

    /**
     * Set verticle.
     * @param obj
     */
    void setVerticle(Object obj);

    /**
     * Get verticle.
     * @return
     */
    Object getVerticle();



}
