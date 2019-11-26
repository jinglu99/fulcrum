package cn.justl.fulcrum.vertxboot.context;

import cn.justl.fulcrum.vertxboot.VerticleHolder;
import cn.justl.fulcrum.vertxboot.context.Context;
import io.vertx.core.Vertx;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultVertxBootContext implements Context {

    private Vertx vertx;

    private Set<Class> verticleClasses = new HashSet<>();
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
    public List<Class> listVerticleClasses() {
        return new ArrayList<>(verticleClasses);
    }

    @Override
    public synchronized void registerVerticleClass(Class verticle) {
        verticleClasses.add(verticle);
    }

    @Override
    public VerticleHolder getVerticle(String name) {
        return verticles.get(name);
    }

    @Override
    public synchronized void registerVerticle(String name, VerticleHolder holder) {
        verticles.put(name, holder);
    }


}
