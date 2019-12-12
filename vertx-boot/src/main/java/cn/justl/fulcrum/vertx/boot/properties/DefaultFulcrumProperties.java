package cn.justl.fulcrum.vertx.boot.properties;

import cn.justl.fulcrum.vertx.boot.helper.ClassHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @Date : 2019-12-10
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefaultFulcrumProperties implements FulcrumProperties {

    Properties properties = new Properties();

    @Override
    public void load(List<String > paths) throws IOException {
        if (paths == null || paths.size() == 0) return;

        for (String path : paths) {
            InputStream in = ClassHelper.getClassLoader().getResourceAsStream(path);
            if (in == null) continue;
            properties.load(in);
        }
    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public String getProperty(String key, String defaultVal) {
        return properties.getProperty(key, defaultVal);
    }

    @Override
    public Integer getInteger(String key) {
        return properties.get(key) == null ? null : Integer.valueOf(properties.get(key).toString());
    }

    @Override
    public Integer getInteger(String key, Integer defaultVal) {
        return properties.get(key) == null ? defaultVal : Integer.valueOf(properties.get(key).toString());
    }

    @Override
    public Boolean getBoolean(String key) {
        return properties.get(key) == null ? null : Boolean.valueOf(properties.get(key).toString());
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultVal) {
        return properties.get(key) == null ? defaultVal : Boolean.valueOf(properties.get(key).toString());
    }
}
