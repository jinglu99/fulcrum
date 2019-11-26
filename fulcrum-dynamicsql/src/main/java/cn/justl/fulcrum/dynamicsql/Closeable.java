package cn.justl.fulcrum.dynamicsql;

import cn.justl.fulcrum.dynamicsql.exceptions.DynamicSqlException;

/**
 * @Date : 2019/11/14
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface Closeable {
    void close() throws DynamicSqlException;
}
