package cn.justl.fulcrum;

import cn.justl.fulcrum.Closeable;
import cn.justl.fulcrum.jdbc.SQLResult;
import cn.justl.fulcrum.script.BoundSql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 *  An ExecuteContext instance hold the parameters and result of the whole execution period.
 */
public class ExecuteContext implements Closeable {

    /**
     * The parameter map that user defined.
     */
    private Map params;

    /**
     * JDBC connection
     */
    private Connection connection;

    private String sql;

    private List<ValueHolder> valueHolders = new ArrayList<>();

    private ResultSet resultSet;

    private List<Map> queryDatas;

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

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ValueHolder> getValueHolders() {
        return valueHolders;
    }

    public void setValueHolders(List<ValueHolder> valueHolders) {
        this.valueHolders = valueHolders;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public List<Map> getQueryDatas() {
        return queryDatas;
    }

    public void setQueryDatas(List<Map> queryDatas) {
        this.queryDatas = queryDatas;
    }

    @Override
    public String toString() {
        return "ExecuteContext{" +
            "params=" + params +
            ", connection=" + connection +
            ", sql=" + sql +
            ", valueHolders=" + valueHolders +
            ", resultSet=" + resultSet +
            '}';
    }

    @Override
    public void close() {

    }
}
