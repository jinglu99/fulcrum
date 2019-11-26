package cn.justl.fulcrum.dynamicsql;

import cn.justl.fulcrum.dynamicsql.script.ScriptExecutor;
import cn.justl.fulcrum.dynamicsql.exceptions.DynamicSqlException;
import cn.justl.fulcrum.dynamicsql.jdbc.SQLExecutor;

/**
 * @Date : 2019/11/14
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 * Executor define the behavior of each components in dynamicsql
 *
 * @see SQLExecutor
 * @see ScriptExecutor
 */
public interface Executor {
    void execute(ExecuteContext context) throws DynamicSqlException;
}
