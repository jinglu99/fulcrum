package cn.justl.fulcrum.core.verticles;

import cn.justl.fulcrum.vertx.boot.annotation.HttpRouter;
import cn.justl.fulcrum.vertx.boot.annotation.PreStart;
import cn.justl.fulcrum.vertx.boot.annotation.Start;
import cn.justl.fulcrum.vertx.boot.annotation.Verticle;
import cn.justl.fulcrum.vertx.boot.annotation.WebVerticle;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

/**
 * @Date : 2019-11-28
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@WebVerticle("test2")
public class TestVerticle2 {
    @PreStart
    public void start() {
        System.out.println("This is test 2");
    }

    @HttpRouter("/")
    public void test(RoutingContext context) {
        context.response().end("ok");
    }
}
