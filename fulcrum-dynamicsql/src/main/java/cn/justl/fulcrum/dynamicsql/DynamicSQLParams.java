package cn.justl.fulcrum.dynamicsql;

import java.sql.Connection;
import java.util.Map;

/**
 * @Date : 2019-11-09
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 *  A DynamicSqlParams describes the parameters needed when call {@link DynamicSQLExecutor#execute(DynamicSQLParams)}
 *
 */
public class DynamicSQLParams {

    /**
     * The parameter map that user defined.
     */
    private Map params;

    /**
     * JDBC connection
     */
    private Connection connection;

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
