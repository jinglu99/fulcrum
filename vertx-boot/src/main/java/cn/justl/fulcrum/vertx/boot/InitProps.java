package cn.justl.fulcrum.vertx.boot;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2019-12-10
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class InitProps {
    private List<String> properties = new ArrayList<>();

    public InitProps setProperties(List<String> properties) {
        this.properties.addAll(properties);
        return this;
    }

    public InitProps setProperties(String property) {
        this.properties.add(property);
        return this;
    }

    public List<String> getProperties() {
        return new ArrayList<>(properties);
    }
}
