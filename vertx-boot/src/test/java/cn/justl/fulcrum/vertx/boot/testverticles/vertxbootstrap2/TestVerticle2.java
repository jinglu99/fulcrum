package cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap2;

import cn.justl.fulcrum.vertx.boot.annotation.DependOn;
import cn.justl.fulcrum.vertx.boot.annotation.Verticle;

/**
 * @Date : 2019/11/28
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Verticle("test2")
@DependOn("test1")
public class TestVerticle2 {

}
