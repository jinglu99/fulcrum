package cn.justl.fulcrum.vertx.boot;

import cn.justl.fulcrum.vertx.boot.annotation.VerticleScan;
import cn.justl.fulcrum.vertx.boot.context.BootStrapContext;
import cn.justl.fulcrum.vertx.boot.definition.BeanDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleCreationException;
import cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap.Monitor;
import cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap.TestVerticle1;
import cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap.TestVerticle2;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date : 2019/11/28
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@ExtendWith(VertxExtension.class)
@DisplayName("Test for VertxBootStrap")
@VerticleScan("cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap")
public class VertxBootStrapTest {

    @Test
    public void basic_VerticleScan_run_test(Vertx vertx, VertxTestContext testContext) {
        VertxBootStrap.run(vertx, this.getClass())
            .compose(res -> {
                testContext.verify(() -> {
                    BootStrapContext context = VertxBootStrap.getContext();
                    assertNotNull(context.getVertx());
                    assertTrue(context.listBeanDefinitions().size() > 0);
                    assertTrue(context.listVerticleHolders().size() > 0);
                }).completeNow();
                return Future.succeededFuture();
            }).otherwise(throwable -> {
            testContext.failNow(throwable);
            return Future.failedFuture(throwable);
        });

    }

    @Test
    void basic_package_run_test(Vertx vertx, VertxTestContext testContext) {
        VertxBootStrap
            .run(vertx, "cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap")
            .compose(res -> {
                testContext.verify(() -> {
                    BootStrapContext context = VertxBootStrap.getContext();
                    assertNotNull(context.getVertx());
                    assertTrue(context.listBeanDefinitions().size() > 0);
                    assertTrue(context.listVerticleHolders().size() > 0);
                }).completeNow();
                return Future.succeededFuture();
            }).otherwise(throwable -> {
            testContext.failNow(throwable);
            return Future.failedFuture(throwable);
        });

    }

    @Test
    public void circularDependencyTest(Vertx vertx, VertxTestContext testContext) {
        VertxBootStrap
            .run(vertx, "cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap2")
            .compose(res -> {
                testContext.failNow(null);
                return Future.succeededFuture();
            }).otherwise(throwable -> {
            testContext.verify(() -> {
                assertThrows(VerticleCreationException.class, () -> {
                    throw throwable;
                });
            }).completeNow();
            return Future.succeededFuture();
        });
    }

    @Test
    public void verticleTest(Vertx vertx, VertxTestContext testContext) {
        VertxBootStrap.run(vertx, this.getClass())
            .compose(res -> {
                testContext.verify(() -> {
                    BootStrapContext context = VertxBootStrap.getContext();
                    BeanDefinition definition = context.getBeanDefinition("testVerticle1");
                    assertNotNull(definition);
                    assertEquals(TestVerticle1.class, definition.getClazz());
                    VerticleHolder verticleHolder = context.getVerticleHolder("testVerticle1");
                    assertNotNull(verticleHolder);
                    assertNotNull(verticleHolder.getVerticle());
                    assertEquals(definition, verticleHolder.getVerticleDefinition());
                }).completeNow();
                return Future.succeededFuture();
            }).otherwise(throwable -> {
                testContext.failNow(throwable);
                return Future.failedFuture(throwable);
        });
    }

    @Test
    @Timeout(timeUnit = TimeUnit.SECONDS, value = 20)
    public void dependOnTest(Vertx vertx, VertxTestContext testContext)
        throws InterruptedException {
        VertxBootStrap.run(vertx, this.getClass())
            .compose(res -> {
                testContext.verify(() -> {
                    int test1Order = Monitor.initSequence.indexOf(TestVerticle1.class);
                    int test2Order = Monitor.initSequence.indexOf(TestVerticle2.class);
                    assertTrue(test1Order < test2Order);
                    testContext.completeNow();
                });
                return Future.succeededFuture();
            }).otherwise(throwable -> {
            testContext.failNow(throwable);
            return Future.failedFuture(throwable);
        });


    }




}
