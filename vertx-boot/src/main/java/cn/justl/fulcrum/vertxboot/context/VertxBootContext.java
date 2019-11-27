package cn.justl.fulcrum.vertxboot.context;

import cn.justl.fulcrum.vertxboot.VerticleHolder;
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
    public List<Class> listVerticleClasses() {
        return delegate.listVerticleClasses();
    }

    @Override
    public void registerVerticleClass(Class verticle) {
        delegate.registerVerticleClass(verticle);
    }

    @Override
    public VerticleHolder getVerticle(String name) {
        return delegate.getVerticle(name);
    }

    @Override
    public void registerVerticle(String name, VerticleHolder verticle) {
        delegate.registerVerticle(name, verticle);
    }
}
