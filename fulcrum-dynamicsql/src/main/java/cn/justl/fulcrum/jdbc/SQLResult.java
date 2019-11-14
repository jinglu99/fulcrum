package cn.justl.fulcrum.jdbc;

import cn.justl.fulcrum.Closeable;
import cn.justl.fulcrum.exceptions.DynamicSqlException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Date : 2019/11/14
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class SQLResult implements Closeable {


    private ResultSet resultSet;

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public void close() throws DynamicSqlException {
        try {
            resultSet.close();
        } catch (SQLException e) {
            throw new DynamicSqlException("SQLResult can't be closed correctly!", e);
        }
    }

    @Override
    public String toString() {
        return "SQLResult{" +
            "resultSet=" + resultSet +
            '}';
    }
}
