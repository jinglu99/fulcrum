package cn.justl.fulcrum.vertxboot.context;

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

    private Set<Class> verticleList = new HashSet<>();
    private Map<String, Class> verticleClasses = new HashMap<>();
    private Map<String, Object> verticleObjs = new HashMap<>();

    @Override
    public Vertx getVertx() {
        return vertx;
    }

    @Override
    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public List<Class> listVerticles() {
        return new ArrayList<>(verticleList);
    }

    @Override
    public synchronized void addVerticle(Class verticle) {
        verticleList.add(verticle);
    }

    @Override
    public synchronized void registerVerticleClass(String name, Class verticleClazz) {
        verticleClasses.put(name, verticleClazz);
    }

    @Override
    public Class getVerticleClass(String name) {
        return verticleClasses.get(name);
    }

    @Override
    public Object getVerticle(String name) {
        return verticleObjs.get(name);
    }

    @Override
    public synchronized void registerVerticle(String name, Object verticle) {
        verticleObjs.put(name, verticle);
    }
}
