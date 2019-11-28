package cn.justl.fulcrum.vertx.boot;

import cn.justl.fulcrum.vertx.boot.annotation.VerticleScan;
import cn.justl.fulcrum.vertx.boot.context.Context;
import cn.justl.fulcrum.vertx.boot.context.VertxBootContext;
import cn.justl.fulcrum.vertx.boot.definition.VerticleDefinition;
import cn.justl.fulcrum.vertx.boot.excetions.VerticleInstantiateException;
import cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap.TestVerticle1;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date : 2019/11/28
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for VertxBootStrap")
@VerticleScan("cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap")
public class VertxBootStrapTest {

    private Vertx vertx;


    @BeforeEach
    public void createVertx() {
        vertx = Vertx.vertx();
    }

    @Test
    public void basic_VerticleScan_run_test() {
        Future future = VertxBootStrap.run(vertx, this.getClass());
        assertTrue(future.succeeded());
        Context context = VertxBootContext.getInstance();
        assertNotNull(context.getVertx());
        assertTrue(context.listVerticleDefinitions().size() > 0);
        assertTrue(context.listVerticleHolders().size() > 0);
    }

    @Test
    void basic_package_run_test() {
        Future future = VertxBootStrap
            .run(vertx, "cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap");
        assertTrue(future.succeeded());
        Context context = VertxBootContext.getInstance();
        assertNotNull(context.getVertx());
        assertTrue(context.listVerticleDefinitions().size() > 0);
        assertTrue(context.listVerticleHolders().size() > 0);
    }

    @Test
    public void circularDependencyTest() {
        Future future = VertxBootStrap
            .run(vertx, "cn.justl.fulcrum.vertx.boot.testverticles.vertxbootstrap2");
        assertTrue(future.failed());
        assertThrows(VerticleInstantiateException.class, () -> {
            throw future.cause();
        });
    }

    @Test
    public void verticleTest() {
        Future future = VertxBootStrap.run(vertx, this.getClass());
        assertTrue(future.succeeded());

        Context context = VertxBootContext.getInstance();
        VerticleDefinition definition = context.getVerticleDefinition("testVerticle1");
        assertNotNull(definition);
        assertEquals(TestVerticle1.class, definition.getClazz());
        VerticleHolder verticleHolder = context.getVerticleHolder("testVerticle1");
        assertNotNull(verticleHolder);
        assertNotNull(verticleHolder.getVerticle());
        assertEquals(definition, verticleHolder.getVerticleDefinition());
    }


}
