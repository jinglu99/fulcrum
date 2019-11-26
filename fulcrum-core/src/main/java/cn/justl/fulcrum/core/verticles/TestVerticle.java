package cn.justl.fulcrum.core.verticles;

import cn.justl.fulcrum.vertxboot.annotation.Start;
import cn.justl.fulcrum.vertxboot.annotation.Verticle;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Verticle
public class TestVerticle {

    @Start
    public void start() {
        System.out.println("test");
    }
}
