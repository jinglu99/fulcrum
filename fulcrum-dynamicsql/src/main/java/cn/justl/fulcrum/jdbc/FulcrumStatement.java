package cn.justl.fulcrum.jdbc;

import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.SQLExecuteException;
import cn.justl.fulcrum.scripthandler.ScriptResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Date : 2019-11-05
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class FulcrumStatement {
    private static final Logger logger = LoggerFactory.getLogger(FulcrumStatement.class);

    private final Connection conn;
    private final PreparedStatement preparedStatement;
    private final String sql;
    private final List<ValueHolder> values;

    public FulcrumStatement(Connection conn, ScriptResult sr) throws SQLExecuteException {
        try {
            this.conn = conn;
            sql = sr.getSql().toString();
            values = sr.getValueHolders();
            preparedStatement = conn.prepareStatement(sr.getSql().toString());
        } catch (Exception e) {
            logger.error("Fail to prepare statement", e);
            throw new SQLExecuteException("fail to prepare statement", e);
        }
}

    public ResultSet execute() throws SQLExecuteException {
        return null;
    }

    public Connection getConn() {
        return conn;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public String getSql() {
        return sql;
    }

    public List<ValueHolder> getValues() {
        return values;
    }
}
