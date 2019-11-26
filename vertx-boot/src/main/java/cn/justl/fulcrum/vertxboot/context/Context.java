package cn.justl.fulcrum.vertxboot.context;

import cn.justl.fulcrum.vertxboot.VerticleHolder;
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

    List<Class> listVerticleClasses();

    void registerVerticleClass(Class verticle);

    VerticleHolder getVerticle(String name);

    void registerVerticle(String name, VerticleHolder verticle);

}
