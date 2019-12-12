package cn.justl.fulcrum.vertx.boot.context;

import cn.justl.fulcrum.vertx.boot.VerticleHolder;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.properties.FulcrumProperties;
import io.vertx.core.Vertx;
import java.util.List;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface Context {

//    /**
//     * get the {@link Vertx} Object.
//     */
//    Vertx getVertx();
//
//    /**
//     * set a {@link Vertx} Object.
//     */
//    void setVertx(Vertx vertx);
//
//    /**
//     * Get a {@link BeanDefinition} by given ID.
//     */
//    BeanDefinition getVerticleDefinition(String id);
//
//    /**
//     * List all {@link BeanDefinition} registered in Context.
//     */
//    List<BeanDefinition> listVerticleDefinitions();
//
//    /**
//     * register a {@link BeanDefinition} in Context.
//     */
//    void registerVerticleDefinition(BeanDefinition verticleDefinition);
//
//    void unregisterVerticleDefinition(String id);

    /**
     * Get a {@link VerticleHolder} by given ID.
     */
    VerticleHolder getVerticleHolder(String id);

    /**
     * List all {@link VerticleHolder} registerd in Context.
     */
    List<VerticleHolder> listVerticleHolders();

    /**
     * Register a {@link VerticleHolder} in Context.
     */
    void registerVerticle(VerticleHolder verticleHolder);

    void unregisterVerticle(String id);

    FulcrumProperties getProperties();

}
