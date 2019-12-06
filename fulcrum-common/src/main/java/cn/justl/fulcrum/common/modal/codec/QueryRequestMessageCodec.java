package cn.justl.fulcrum.common.modal.codec;

import cn.justl.fulcrum.common.modal.QueryRequest;
import com.alibaba.fastjson.JSON;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

/**
 * @Date : 2019/12/6
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class QueryRequestMessageCodec implements MessageCodec<QueryRequest, QueryRequest> {

    @Override
    public void encodeToWire(Buffer buffer, QueryRequest queryRequest) {
        buffer.appendString(JSON.toJSONString(queryRequest));
    }

    @Override
    public QueryRequest decodeFromWire(int pos, Buffer buffer) {
        return JSON.parseObject(buffer.getBytes(0,pos), QueryRequest.class);
    }

    @Override
    public QueryRequest transform(QueryRequest queryRequest) {
        return queryRequest;
    }

    @Override
    public String name() {
        return "QueryRequestMessageCodec";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
