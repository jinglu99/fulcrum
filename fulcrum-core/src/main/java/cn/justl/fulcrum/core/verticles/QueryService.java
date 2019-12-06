package cn.justl.fulcrum.core.verticles;

import cn.justl.fulcrum.common.modal.QueryRequest;
import cn.justl.fulcrum.common.modal.codec.QueryRequestMessageCodec;
import cn.justl.fulcrum.vertx.boot.annotation.Start;
import cn.justl.fulcrum.vertx.boot.annotation.VertX;
import cn.justl.fulcrum.vertx.boot.annotation.Verticle;
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

        vertx.eventBus().registerDefaultCodec(QueryRequest.class, new QueryRequestMessageCodec());

        vertx.eventBus().consumer(QUERY_SERVICE, msg->{
            try {
                QueryRequest request = (QueryRequest) msg.body();
                System.out.println(request);
                msg.reply("ok");
            } catch (Throwable throwable) {
                msg.reply("wrong");
            }
        });
    }

}
