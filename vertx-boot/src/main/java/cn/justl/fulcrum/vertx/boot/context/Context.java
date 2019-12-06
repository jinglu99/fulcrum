package cn.justl.fulcrum.vertx.boot.context;

import cn.justl.fulcrum.vertx.boot.VerticleHolder;
import cn.justl.fulcrum.vertx.boot.definition.VerticleDefinition;
import io.vertx.core.Vertx;
import java.util.List;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface Context {

    /**
     * get the {@link Vertx} Object.
     */
    Vertx getVertx();

    /**
     * set a {@link Vertx} Object.
     */
    void setVertx(Vertx vertx);

    /**
     * Get a {@link VerticleDefinition} by given ID.
     */
    VerticleDefinition getVerticleDefinition(String id);

    /**
     * List all {@link VerticleDefinition} registered in Context.
     */
    List<VerticleDefinition> listVerticleDefinitions();

    /**
     * register a {@link VerticleDefinition} in Context.
     */
    void registerVerticleDefinition(VerticleDefinition verticleDefinition);

    void unregisterVerticleDefinition(String id);

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

}
