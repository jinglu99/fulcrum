package cn.justl.fulcrum.vertx.boot.codec;

import com.alibaba.fastjson.JSON;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

/**
 * @Date : 2019/12/11
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public abstract class BaseFulcrumMessageCodec<S> implements MessageCodec<S, S> {

    @Override
    public void encodeToWire(Buffer buffer, S s) {
        buffer.appendString(JSON.toJSONString(s));
    }

    @Override
    public S decodeFromWire(int pos, Buffer buffer) {
        return JSON.parseObject(buffer.getBytes(0,pos), getMessageType());
    }

    @Override
    public S transform(S s) {
        return s;
    }

    @Override
    public String name() {
        return getMessageType().getSimpleName() + "Codec";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }

    abstract Class getMessageType();
}
