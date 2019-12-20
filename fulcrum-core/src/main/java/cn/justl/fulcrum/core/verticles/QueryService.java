package cn.justl.fulcrum.core.verticles;

import cn.justl.fulcrum.core.data.FulcrumResource;
import cn.justl.fulcrum.vertx.boot.verticle.annotations.Start;
import cn.justl.fulcrum.vertx.boot.annotation.VertX;
import cn.justl.fulcrum.vertx.boot.verticle.annotations.Verticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date : 2019/12/6
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@Verticle("queryService")
public class QueryService {

    public static final String QUERY_SERVICE = "QUERY_SERVICE";

    private static final Logger logger = LoggerFactory.getLogger(QueryService.class);

    @VertX
    private Vertx vertx;


    @Start
    private void start() {
        logger.info("QueryService starting...");

        vertx.eventBus().consumer(QUERY_SERVICE, msg->{
            try {
                FulcrumResource res = new FulcrumResource();
                res.setId(1);
                res.setResourceId("tesrttestsets");
                msg.reply(res);
//
            } catch (Throwable throwable) {
                msg.reply("wrong");
            }
        });
    }

}
