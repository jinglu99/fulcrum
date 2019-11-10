package cn.justl.fulcrum;

import cn.justl.fulcrum.exceptions.DynamicSqlConstructionException;
import cn.justl.fulcrum.exceptions.DynamicSqlExecutionException;

/**
 * @Date : 2019-11-09
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 * A DynamicSQLExecutor is used to execute the dynamic sql.
 * For each dynamic sql defined by xml, an individual DynamicSQLExecutor
 * should be created,a {@link DynamicSql} is required when creating DynamicSQLExecutor,
 * if the input dynamic sql doesn't satisfied with the xml schema definition
 * (http://jingl.wang/fulcrum-dynamic-sql.xsd), a {@link DynamicSqlConstructionException}
 * will be thrown.
 * It's safe for multiple calling {{@link #execute(DynamicSQLParams)}} at same time.
 *
 */
public class DynamicSQLExecutor {
    /**
     * Create a DynamicSQLExecutor for the given DynamicSql
     * @param sql
     * @throws DynamicSqlConstructionException
     */
    public DynamicSQLExecutor(DynamicSql sql) throws DynamicSqlConstructionException {

    }


    public DynamicSQLResult execute(DynamicSQLParams params) throws DynamicSqlExecutionException {
        return null;
    }


}
