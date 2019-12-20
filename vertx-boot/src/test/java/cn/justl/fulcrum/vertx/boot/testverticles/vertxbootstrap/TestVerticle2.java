package cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap;

import cn.justl.fulcrum.vertx.boot.annotation.DependOn;
import cn.justl.fulcrum.vertx.boot.verticle.annotations.Start;
import cn.justl.fulcrum.vertx.boot.verticle.annotations.Verticle;

/**
 * @Date : 2019/11/29
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Verticle("testVerticle2")
@DependOn("testVerticle1")
public class TestVerticle2 {
    @Start
    public void start() {
        System.out.println("testVerticle2 start...");
        Monitor.initSequence.add(this.getClass());
    }
}
