package cn.justl.fulcrum.core.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cn.justl.fulcrum.common.modal.QueryRequest;
import cn.justl.fulcrum.common.modal.codec.QueryRequestMessageCodec;
import cn.justl.fulcrum.core.db.DBTest;
import cn.justl.fulcrum.core.verticles.QueryService;
import cn.justl.fulcrum.vertx.boot.VertxBootStrap;
import cn.justl.fulcrum.vertx.boot.annotation.VerticleScan;
import cn.justl.fulcrum.vertx.boot.context.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Stream;

/**
 * @Date : 2019/12/6
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for QueryService")
@ExtendWith(VertxExtension.class)
@VerticleScan
public class QueryServiceTest extends DBTest {
    private static final String  Test_Path = "/";

    @BeforeEach
    public void init() throws SQLException, IOException, ClassNotFoundException {
        initDB();
//        prepareData("/db/QueryService.sql");
    }

    @Test
    public void dbTest() throws SQLException, ClassNotFoundException {
        System.out.println(executeSql("select * from fulcrum_info"));
    }



    @Test
    public void test(Vertx vertx, VertxTestContext testContext) {
        VertxBootStrap.runWithVerticles(vertx, QueryService.class)
            .compose(res -> {
                DeliveryOptions options = new DeliveryOptions().setCodecName("QueryRequestMessageCodec");
                vertx.eventBus()
                    .request(QueryService.QUERY_SERVICE,
                        new QueryRequest() {{
                            setResource("test");
                        }}, options, r -> {
                            System.out.println(r);
                        });
                return Future.succeededFuture();
            }).otherwise(throwable -> {
            testContext.failNow(throwable);
            return Future.failedFuture(throwable);
        });
    }
}