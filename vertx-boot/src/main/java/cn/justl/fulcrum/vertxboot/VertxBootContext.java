package cn.justl.fulcrum.vertxboot;

import cn.justl.fulcrum.vertxboot.context.Context;
import cn.justl.fulcrum.vertxboot.context.DefaultVertxBootContext;
import io.vertx.core.Vertx;
import java.util.List;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VertxBootContext implements Context {

    private static final VertxBootContext context = new VertxBootContext();
    private Context delegate = new DefaultVertxBootContext();

    private VertxBootContext(){}

    public static Context getInstance() {
        return context;
    }

    @Override
    public Vertx getVertx() {
        return delegate.getVertx();
    }

    @Override
    public void setVertx(Vertx vertx) {
        delegate.setVertx(vertx);
    }

    @Override
    public List<Class> listVerticles() {
        return delegate.listVerticles();
    }

    @Override
    public void addVerticle(Class verticle) {
        delegate.addVerticle(verticle);
    }

    @Override
    public Class getVerticleClass(String name) {
        return delegate.getVerticleClass(name);
    }

    @Override
    public void registerVerticle(String name, Object verticle) {
        delegate.registerVerticle(name, verticle);
    }

    @Override
    public Object getVerticle(String name) {
        return delegate.getVerticle(name);
    }

    @Override
    public void registerVerticleClass(String name, Class verticle) {
        delegate.registerVerticleClass(name, verticle);
    }
}
