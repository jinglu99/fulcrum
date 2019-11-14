package cn.justl.fulcrum;

import cn.justl.fulcrum.exceptions.DynamicSqlException;
import cn.justl.fulcrum.jdbc.SQLExecutor;
import cn.justl.fulcrum.script.ScriptExecutor;

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
