package cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap;

import cn.justl.fulcrum.vertx.boot.verticle.annotations.Start;
import cn.justl.fulcrum.vertx.boot.verticle.annotations.Verticle;

/**
 * @Date : 2019/11/28
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Verticle("testVerticle1")
public class TestVerticle1 {

    @Start
    public void start() throws InterruptedException {
        System.out.println("testVerticle1 start...");
        Monitor.initSequence.add(this.getClass());
    }

    public int test() {
        return 0;
    }
}
