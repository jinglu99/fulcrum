package cn.justl.fulcrum.core.manager;

import cn.justl.fulcrum.common.modal.QueryRequest;
import cn.justl.fulcrum.core.db.DBTest;
import cn.justl.fulcrum.core.managers.ResourceManager;
import cn.justl.fulcrum.core.verticles.QueryService;
import cn.justl.fulcrum.vertx.boot.VertxBootStrap;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @Date : 2019-12-11
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@ExtendWith(VertxExtension.class)
public class ResourceManagerTest extends DBTest {

    @BeforeEach
    public void init() throws SQLException, IOException, ClassNotFoundException {
        initDB();
        prepareData("/db/QueryService.sql");
    }

    @Test
    public void test(Vertx vertx, VertxTestContext context) {
        VertxBootStrap.runWithVerticles(vertx, ResourceManager.class)
                .compose(res -> {
                    vertx.eventBus()
                            .request("test", "123", r -> {
                                context.completeNow();
                                System.out.println(r);
                            });
                    return Future.succeededFuture();
                }).otherwise(throwable -> {
            context.failNow(throwable);
            return Future.failedFuture(throwable);
        });

    }
}
