package cn.justl.fulcrum.common.modal;

import java.io.Serializable;
import java.util.Map;

/**
 * @Date : 2019/12/6
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = -6171225289317960039L;

    private String resource;
    private Map<String, Object> params;
    private Map<String, Object> options;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "QueryRequest{" +
            "resource='" + resource + '\'' +
            ", params=" + params +
            ", options=" + options +
            '}';
    }
}
