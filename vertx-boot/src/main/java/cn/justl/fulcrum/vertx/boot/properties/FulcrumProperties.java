package cn.justl.fulcrum.vertx.boot.properties;

import java.io.IOException;
import java.util.List;

/**
 * @Date : 2019-12-10
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface FulcrumProperties {

    void load(List<String> path) throws IOException;

    String getProperty(String key);

    String getProperty(String key, String defaultVal);

    Integer getInteger(String key);

    Integer getInteger(String key, Integer defaultVal);

    Boolean getBoolean(String key);

    Boolean getBoolean(String key, Boolean defaultVal);
}
