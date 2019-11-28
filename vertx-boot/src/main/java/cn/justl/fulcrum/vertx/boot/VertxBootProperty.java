package cn.justl.fulcrum.vertx.boot;

import java.util.Optional;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VertxBootProperty {
    public String getProperty(String key) {
        return null;
    }

    public String getProperty(String key, String defaultVal) {
        return Optional.ofNullable(getProperty(key)).orElse(defaultVal);
    }

    public Integer getIntProperty(String key) {
        return Integer.valueOf(getProperty(key));
    }
}
