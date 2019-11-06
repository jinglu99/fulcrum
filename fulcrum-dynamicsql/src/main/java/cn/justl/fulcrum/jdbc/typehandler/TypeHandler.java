package cn.justl.fulcrum.jdbc.typehandler;

import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.SQLExecuteException;
import cn.justl.fulcrum.exceptions.ScriptFailedException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Date : 2019/10/25
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 *  Interface to be implemented by objects that define behaviors when handle different types of value.
 *
 * @see AbstractTypeHandler
 * @see IntegerTypeHandler
 */
public interface TypeHandler {

    /**
     * Set parameter for {@link PreparedStatement} with the value handled from {@link ValueHolder}
     * @param ps
     * @param index
     * @param valueHolder
     * @throws SQLExecuteException
     */
    void setParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws SQLExecuteException;

    /**
     *  Whether the {@link ValueHolder} can be handled by current {@link TypeHandler}
     * @param holder
     * @return
     */
    boolean isMatch(ValueHolder holder);
}
