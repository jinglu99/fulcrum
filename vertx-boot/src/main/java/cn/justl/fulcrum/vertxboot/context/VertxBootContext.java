package cn.justl.fulcrum.vertxboot.context;

import cn.justl.fulcrum.vertxboot.VerticleHolder;
import cn.justl.fulcrum.vertxboot.definition.VerticleDefinition;
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
    public VerticleDefinition getVerticleDefinition(String id) {
        return delegate.getVerticleDefinition(id);
    }

    @Override
    public List<VerticleDefinition> listVerticleDefinitions() {
        return delegate.listVerticleDefinitions();
    }

    @Override
    public void registerVerticleDefinition(VerticleDefinition verticleDefinition) {
        delegate.registerVerticleDefinition(verticleDefinition);
    }

    @Override
    public VerticleHolder getVerticleHolder(String id) {
        return delegate.getVerticleHolder(id);
    }

    @Override
    public List<VerticleHolder> listVerticleHolders() {
        return delegate.listVerticleHolders();
    }

    @Override
    public void registerVerticle(VerticleHolder verticleHolder) {
        delegate.registerVerticle(verticleHolder);
    }
}
