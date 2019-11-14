package cn.justl.fulcrum.jdbc.typehandler;

import cn.justl.fulcrum.ValueHolder;
import cn.justl.fulcrum.exceptions.StatementExecuteException;

import cn.justl.fulcrum.exceptions.TypeHandleException;
import java.sql.PreparedStatement;

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
     * @throws StatementExecuteException
     */
    void setParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws TypeHandleException;

    /**
     *  Whether the {@link ValueHolder} can be handled by current {@link TypeHandler}
     * @param holder
     * @return
     */
    boolean isMatch(ValueHolder holder);
}
