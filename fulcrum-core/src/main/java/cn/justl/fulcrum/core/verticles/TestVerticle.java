package cn.justl.fulcrum.core.verticles;

import cn.justl.fulcrum.vertx.boot.annotation.PostStart;
import cn.justl.fulcrum.vertx.boot.annotation.PreStart;
import cn.justl.fulcrum.vertx.boot.annotation.Start;
import cn.justl.fulcrum.vertx.boot.annotation.VertX;
import cn.justl.fulcrum.vertx.boot.annotation.Verticle;
import io.vertx.core.Vertx;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Verticle("test")
public class TestVerticle {

    @VertX
    private Vertx vertx;

    @PreStart
    private void preStart() {
        vertx.executeBlocking(promise -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("prestart called");
            promise.complete();
        }, false, res->{

        });
    }


    @Start
    private void start() {
        vertx.executeBlocking(promise -> {
            System.out.println("start called");
            promise.complete();
        }, false, res->{

        });
    }

    @PostStart
    private void postStart() {
        vertx.executeBlocking(promise -> {
            System.out.println("prestart called");
            promise.complete();
        }, false, res->{
        });
    }
}
