package cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap;

import cn.justl.fulcrum.vertx.boot.annotation.Verticle;

/**
 * @Date : 2019/11/28
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Verticle("testVerticle1")
public class TestVerticle1 {
    public int test() {
        return 0;
    }
}
