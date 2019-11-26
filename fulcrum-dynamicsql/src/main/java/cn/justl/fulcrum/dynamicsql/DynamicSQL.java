package cn.justl.fulcrum.dynamicsql;

import java.io.InputStream;

/**
 * @Date : 2019-11-09
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 * A container to hold dynamic sql input stream which used in the constructor of {@link DynamicSQLExecutor}
 */
public class DynamicSQL {
    private InputStream sql;

    public InputStream getSql() {
        return sql;
    }

    public void setSql(InputStream sql) {
        this.sql = sql;
    }
}
