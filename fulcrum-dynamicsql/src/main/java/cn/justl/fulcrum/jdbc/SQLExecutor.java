package cn.justl.fulcrum.jdbc;

import cn.justl.fulcrum.ExecuteContext;
import cn.justl.fulcrum.Executor;
import cn.justl.fulcrum.exceptions.DynamicSqlException;
import cn.justl.fulcrum.exceptions.StatementExecuteException;

/**
 * @Date : 2019-11-13
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc : An Executor to execute sql with JDBC.
 */
public class SQLExecutor implements Executor {

    /**
     * Query the result from database through JDBC with the sql and parameters defined in {@link
     * ExecuteContext}. The {@link ExecuteContext#connection} is required for jdbc operation. A
     * {@link java.sql.ResultSet} will be stored in {@link ExecuteContext#resultSet} after
     * execution.
     */
    public void execute(ExecuteContext context) throws DynamicSqlException {
        FulcrumStatement statement = new FulcrumStatement(context.getConnection(), context.getSql(),
            context.getValueHolders());
        context.setResultSet(statement.execute());
    }
}
