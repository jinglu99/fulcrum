package cn.justl.fulcrum.vertxboot.context;

import io.vertx.core.Vertx;
import java.util.List;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface Context {
    /**
     * get the {@link Vertx} Object
     * @return
     */
    Vertx getVertx();

    /**
     * set a {@link Vertx} Object
     * @param vertx
     */
    void setVertx(Vertx vertx);

    List<Class> listVerticles();

    void addVerticle(Class verticle);

    void registerVerticleClass(String name, Class verticleClazz);

    Class getVerticleClass(String name);

    Object getVerticle(String name);

    void registerVerticle(String name, Object verticle);

}
