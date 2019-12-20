package cn.justl.fulcrum.core.service;

import cn.justl.fulcrum.common.modal.QueryRequest;
import cn.justl.fulcrum.core.data.FulcrumResource;
import cn.justl.fulcrum.core.db.DBTest;
import cn.justl.fulcrum.core.verticles.QueryService;
import cn.justl.fulcrum.vertx.boot.InitProps;
import cn.justl.fulcrum.vertx.boot.VertxBootStrap;
import cn.justl.fulcrum.vertx.boot.annotation.VertxScan;
import cn.justl.fulcrum.vertx.boot.codec.Codec;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.*;
import java.sql.*;

/**
 * @Date : 2019/12/6
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for QueryService")
@ExtendWith(VertxExtension.class)
@VertxScan
public class QueryServiceTest extends DBTest {

    private static final String Test_Path = "/";

    @BeforeEach
    public void init() throws SQLException, IOException, ClassNotFoundException {
        initDB();
        prepareData("/db/QueryService.sql");
    }

    @Test
    public void dbTest() throws SQLException, ClassNotFoundException {
        System.out.println(executeSql("select * from fulcrum_resource"));
    }

    @Test
    public void initBootTest(Vertx vertx, VertxTestContext context) {
        VertxBootStrap.run(vertx, this.getClass())
            .compose(res->{
                context.verify(()->{
                    context.completeNow();
                });
                return Future.succeededFuture();
            }).otherwise(throwable -> {
                context.failNow(throwable);
                return Future.failedFuture(throwable);
        });
    }


    @Test
    public void test(Vertx vertx, VertxTestContext testContext) {
        VertxBootStrap.runWithBeans(vertx, new InitProps() {{
            setProperties("test1.properties");
        }}, QueryService.class, QueryRequest.class, FulcrumResource.class)
            .compose(res -> {
                DeliveryOptions options = new DeliveryOptions().setCodecName(
                    Codec.getCodecName(QueryRequest.class));
                vertx.eventBus()
                    .request(QueryService.QUERY_SERVICE,
                        new QueryRequest() {{
                            setResource("test");
                        }}, options, r -> {
                            if (r.succeeded()) {
                                System.out.println(r.result().body());
                            }
                            testContext.completeNow();
                        });
                return Future.succeededFuture();
            }).otherwise(throwable -> {
            testContext.failNow(throwable);
            return Future.failedFuture(throwable);
        });
    }
}
