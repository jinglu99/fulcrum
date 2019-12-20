package cn.justl.fulcrum.vertx.boot.verticle;

import cn.justl.fulcrum.vertx.boot.bean.BeanHolder;
import io.vertx.core.Verticle;

/**
 * @Date : 2019-12-19
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface VerticleBeanHolder extends BeanHolder {


    /**
     * Set verticle ID.
     * @param id
     */
    void setVerticleId(String id);

    /**
     * Get verticle ID.
     * @return
     */
    String getVerticleId();

    /**
     * Set verticle.
     * @param obj
     */
    void setVerticle(Verticle obj);

    /**
     * Get verticle.
     * @return
     */
    Verticle getVerticle();



}
