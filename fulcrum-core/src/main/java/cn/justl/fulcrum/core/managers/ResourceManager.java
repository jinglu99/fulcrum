package cn.justl.fulcrum.core.managers;

import cn.justl.fulcrum.vertx.boot.verticle.annotations.Start;
import cn.justl.fulcrum.vertx.boot.annotation.VertX;
import cn.justl.fulcrum.vertx.boot.verticle.annotations.Verticle;
import io.vertx.core.Vertx;
import io.vertx.ext.jdbc.JDBCClient;

/**
 * @Date : 2019/12/6
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Verticle
public class ResourceManager {

    private JDBCClient sqlClient;

    @VertX
    private Vertx vertx;

    @Start
    public void start() {
//        initMysqlClient();

        vertx.eventBus().consumer("test", msg -> {
            try {

                sqlClient.getConnection(conn -> {
                    if (conn.succeeded()) {
                        conn.result()
                                .query("select * from fulcrum_resource", res -> {
                                    if (res.succeeded()) {
                                        System.out.println(res.result().getResults());

                                        msg.reply("ok");
                                    } else {
                                        res.cause().printStackTrace();
                                        msg.reply("wrong");
                                    }
                                });
                    } else {
                        conn.cause().printStackTrace();
                        msg.reply("wrong");
                    }
                });

            } catch (Throwable throwable) {
                msg.reply("wrong");
            }
        });



    }

//    void initMysqlClient() {
//        JsonObject config = new JsonObject()
//                .put("url", VertxBootStrap.getContext().getProperties().getProperty("db.host"))
//                .put("driver_class", VertxBootStrap.getContext().getProperties().getProperty("db.driver"))
//                .put("user", VertxBootStrap.getContext().getProperties().getProperty("db.user"))
//                .put("password", VertxBootStrap.getContext().getProperties().getProperty("db.passwd"));
//        sqlClient = JDBCClient.createShared(vertx, config);
//    }


}
