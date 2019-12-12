package cn.justl.fulcrum.vertx.boot;

import cn.justl.fulcrum.vertx.boot.testverticles.codec.test1.TestPOJO;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for Codec")
@ExtendWith(VertxExtension.class)
public class CodeCTest {

//    @Test
//    public void codcTestForRunWithVerticle(Vertx vertx, VertxTestContext testContext) {
//        VertxBootStrap.runWithVerticles(vertx, TestPOJO.class)
//            .compose(res->{
//                testContext.verify(()->{
//
//                })
//            })
//    }

}
