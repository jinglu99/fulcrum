package cn.justl.fulcrum.core;

import cn.justl.fulcrum.vertx.boot.VertxBootStrap;
import cn.justl.fulcrum.vertx.boot.annotation.VertxScan;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;

/**
 * @Date : 2019/11/23
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@VertxScan
public class BootstrapVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        VertxBootStrap.run(vertx, BootstrapVerticle.class)
            .compose(res-> {
                startPromise.complete();
                return Future.succeededFuture();
            }).otherwise(throwable->{
                startPromise.fail(throwable);
                return Future.failedFuture(throwable);
            });
    }
}
