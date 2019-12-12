package cn.justl.fulcrum.vertx.boot.context;

import io.vertx.core.Vertx;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface VertxContext {
    /**
     * get the {@link Vertx} Object.
     */
    Vertx getVertx();

    /**
     * set a {@link Vertx} Object.
     */
    void setVertx(Vertx vertx);
}
