package cn.justl.fulcrum.dynamicsql;

import cn.justl.fulcrum.dynamicsql.jdbc.typehandler.TypeHandler;

/**
 * @Date : 2019-10-29
 * @Author : jinglu.wang[jingl.wang123@gmail.com]
 * @Desc : A container to hold the parameters required to resolve sql parameters in {@link
 * TypeHandler}
 */
public class ValueHolder {

    private final String paramName;
    private final Object val;
    private final String type;
    private final String defaultExp;

    public ValueHolder(String paramName, Object val, String type, String defaultExp) {
        this.paramName = paramName;
        this.val = val;
        this.type = type;
        this.defaultExp = defaultExp;
    }

    public String getParamName() {
        return paramName;
    }

    public Object getVal() {
        return val;
    }

    public String getType() {
        return type;
    }

    public String getDefaultExp() {
        return defaultExp;
    }

    @Override
    public String toString() {
        return "ValueHolder{" +
            "paramName='" + paramName + '\'' +
            ", val=" + val +
            ", type='" + type + '\'' +
            ", defaultExp='" + defaultExp + '\'' +
            '}';
    }
}
