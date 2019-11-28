package cn.justl.fulcrum.vertx.boot.context;

import cn.justl.fulcrum.vertx.boot.VerticleHolder;
import cn.justl.fulcrum.vertx.boot.definition.VerticleDefinition;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultVertxBootContext implements Context {
    private static final Logger logger = LoggerFactory.getLogger(DefaultVertxBootContext.class);

    private Vertx vertx;

    private Map<String, VerticleDefinition> definitionMap = new HashMap<>();
    private Map<String, VerticleHolder> verticles = new HashMap<>();

    @Override
    public Vertx getVertx() {
        return vertx;
    }

    @Override
    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }


    @Override
    public VerticleDefinition getVerticleDefinition(String id) {
        return definitionMap.get(id);
    }

    @Override
    public List<VerticleDefinition> listVerticleDefinitions() {
        return new ArrayList<>(definitionMap.values());
    }

    @Override
    public synchronized void registerVerticleDefinition(VerticleDefinition verticleDefinition) {
        if (definitionMap.containsKey(verticleDefinition.getId())) {
            logger.warn("VerticleDefinition {}:{} has been registered, it will by replaced by {}",
                    verticleDefinition.getId(), getVerticleDefinition(verticleDefinition.getId()).getClazz().getName(),
                    verticleDefinition.getClazz().getName());
        }
        definitionMap.put(verticleDefinition.getId(), verticleDefinition);
    }

    @Override
    public VerticleHolder getVerticleHolder(String id) {
        return verticles.get(id);
    }

    @Override
    public List<VerticleHolder> listVerticleHolders() {
        return new ArrayList<>(verticles.values());
    }

    @Override
    public synchronized void registerVerticle(VerticleHolder verticleHolder) {
        if (verticles.containsKey(verticleHolder.getId())) {
            logger.warn("Verticle {}:{} has been registered, it will by replaced by {}",
                    verticleHolder.getId(), getVerticleHolder(verticleHolder.getId()).getVerticle(),
                    verticleHolder.getVerticle());
        }
        verticles.put(verticleHolder.getId(), verticleHolder);
    }
}
