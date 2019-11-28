package cn.justl.fulcrum.core;

import cn.justl.fulcrum.vertx.boot.VertxBootStrap;
import cn.justl.fulcrum.vertx.boot.annotation.VerticleScan;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * @Date : 2019/11/23
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@VerticleScan
public class BootstrapVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        VertxBootStrap.run(vertx, BootstrapVerticle.class)
            .otherwise(res->{
                res.printStackTrace();
                return null;
            });
        startPromise.complete();
    }
}
