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
     * @return
     */
    Vertx getVertx();

    /**
     * set a {@link Vertx} Object.
     * @param vertx
     */
    void setVertx(Vertx vertx);

    /**
     * Get a {@link VerticleDefinition} by given ID.
     * @param id
     * @return
     */
    VerticleDefinition getVerticleDefinition(String id);

    /**
     * List all {@link VerticleDefinition} registered in Context.
     * @return
     */
    List<VerticleDefinition> listVerticleDefinitions();

    /**
     * register a {@link VerticleDefinition} in Context.
     * @param verticleDefinition
     */
    void registerVerticleDefinition(VerticleDefinition verticleDefinition);

    /**
     * Get a {@link VerticleHolder} by given ID.
     * @param id
     * @return
     */
    VerticleHolder getVerticleHolder(String id);


    /**
     * List all {@link VerticleHolder} registerd in Context.
     * @return
     */
    List<VerticleHolder> listVerticleHolders();


    /**
     * Register a {@link VerticleHolder} in Context.
     * @param verticleHolder
     */
    void registerVerticle(VerticleHolder verticleHolder);

}
