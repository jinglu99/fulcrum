package cn.justl.fulcrum.vertx.boot.codec;

import io.vertx.core.eventbus.MessageCodec;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Date : 2019/12/11
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class Codec {
    private static final Map<Class, MessageCodec> map = new ConcurrentHashMap();

    public static <T> MessageCodec getCodec(Class<T> clazz) {
        return map.computeIfAbsent(clazz, x -> {
            MessageCodec codec = new BaseFulcrumMessageCodec<T>() {
                @Override
                Class getMessageType() {
                    return clazz;
                }
            };
            return codec;
        });
    }

    public static String getCodecName(Class clazz) {
        return getCodec(clazz).name();
    }
}
