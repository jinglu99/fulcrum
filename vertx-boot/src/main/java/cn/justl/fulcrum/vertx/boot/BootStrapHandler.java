package cn.justl.fulcrum.vertx.boot;

import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.excetions.VertxBootException;
import io.vertx.core.Vertx;

/**
 * @Date : 2019-11-28
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface BootStrapHandler {

    void setVertx(Vertx vertx);

    Vertx getVertx();

    void scanVerticles(String[] packages) throws VertxBootException;

    void instantiateVerticles() throws VertxBootException;

    void initializeVerticles() throws VertxBootException;

    void close() throws VertxBootException;

    Context getContext();

}
