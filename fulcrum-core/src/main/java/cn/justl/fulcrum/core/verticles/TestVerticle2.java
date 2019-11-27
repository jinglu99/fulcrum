package cn.justl.fulcrum.core.verticles;

import cn.justl.fulcrum.vertxboot.annotation.DependOn;
import cn.justl.fulcrum.vertxboot.annotation.Start;
import cn.justl.fulcrum.vertxboot.annotation.Verticle;

/**
 * @Date : 2019-11-28
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Verticle("test2")
@DependOn({"test"})
public class TestVerticle2 {
    @Start
    public void start() {
        System.out.println("This is test 2");
    }
}
