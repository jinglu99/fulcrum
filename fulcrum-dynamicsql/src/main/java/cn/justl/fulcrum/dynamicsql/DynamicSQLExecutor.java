package cn.justl.fulcrum.dynamicsql;

import cn.justl.fulcrum.dynamicsql.exceptions.DynamicSqlConstructionException;
import cn.justl.fulcrum.dynamicsql.exceptions.ScriptFailedException;
import cn.justl.fulcrum.dynamicsql.exceptions.DynamicSqlException;
import cn.justl.fulcrum.dynamicsql.jdbc.SQLExecutor;
import cn.justl.fulcrum.dynamicsql.result.ResultSetResolveExecutor;
import cn.justl.fulcrum.dynamicsql.script.ScriptExecutor;
import java.util.Collections;
import java.util.Optional;

/**
 * @Date : 2019-11-09
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc : A DynamicSQLExecutor is used to execute the dynamic sql. For each dynamic sql defined by
 * xml, an individual DynamicSQLExecutor should be created,a {@link DynamicSQL} is required when
 * creating DynamicSQLExecutor, if the input dynamic sql doesn't satisfied with the xml schema
 * definition (http://jingl.wang/fulcrum-dynamic-sql.xsd), a {@link DynamicSqlConstructionException}
 * will be thrown. It's safe for multiple calling {{@link #execute(DynamicSQLParams)}} at same
 * time.
 */
public final class DynamicSQLExecutor {

    private DynamicSQL dynamicSQL;
    private final ScriptExecutor scriptRunner = new ScriptExecutor();
    private final SQLExecutor sqlExecutor = new SQLExecutor();
    private final ResultSetResolveExecutor resultSetResolveExecutor = new ResultSetResolveExecutor();

    /**
     * Create a DynamicSQLExecutor for the given DynamicSql
     */
    public DynamicSQLExecutor(DynamicSQL sql) throws DynamicSqlException {
        this.dynamicSQL = sql;
        scriptRunner.parse(dynamicSQL.getSql());
    }


    public DynamicSQLResult execute(DynamicSQLParams params) throws DynamicSqlException {
        ExecuteContext context = createContext(params);

        executeSQLScript(context);

        executeSQL(context);

        resolveResultSet(context);

        return new DynamicSQLResult(context.getQueryDatas());
    }

    public ExecuteContext createContext(DynamicSQLParams params) {
        ExecuteContext context = new ExecuteContext();
        context.setParams(Optional.ofNullable(params.getParams()).orElse(Collections.emptyMap()));
        context.setConnection(params.getConnection());
        return context;
    }

    public void executeSQLScript(ExecuteContext context) throws ScriptFailedException {
        scriptRunner.execute(context);
    }

    public void executeSQL(ExecuteContext context) throws DynamicSqlException {
        sqlExecutor.execute(context);
    }

    public void resolveResultSet(ExecuteContext context) throws DynamicSqlException {
        resultSetResolveExecutor.execute(context);
    }


}
