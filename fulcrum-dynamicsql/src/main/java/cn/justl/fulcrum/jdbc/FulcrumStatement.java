package cn.justl.fulcrum.jdbc;

import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.SQLExecuteException;
import cn.justl.fulcrum.jdbc.typehandler.TypeHandlerResolver;
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
        setParams();

        return doExecute();
    }

    private ResultSet doExecute() throws SQLExecuteException {
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new SQLExecuteException("fail to execute prepareStatement!", e);
        }
    }

    /**
     * set params for prepareStatement
     */
    private void setParams() throws SQLExecuteException {
        for (int i = 0; i < values.size(); i++) {
            TypeHandlerResolver.resolveTypeHandler(values.get(i))
                    .setParam(preparedStatement, i + 1, values.get(i));
        }
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
