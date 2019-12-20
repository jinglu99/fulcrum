package cn.justl.fulcrum.vertx.boot;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @Date : 2019/12/20
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */

@ExtendWith(VertxExtension.class)
public class VertxTest {

    @Test
    public void test1(Vertx vertx) {
        Future.future(promise -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("b");
            promise.complete();
        });
        System.out.println("a");
        while (true) {
            ;
        }
    }


}
