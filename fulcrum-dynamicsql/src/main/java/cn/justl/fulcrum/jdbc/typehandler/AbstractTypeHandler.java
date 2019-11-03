package cn.justl.fulcrum.jdbc.typehandler;

import cn.justl.fulcrum.contexts.ValueHolder;
import cn.justl.fulcrum.exceptions.ScriptFailedException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Date : 2019/10/25
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public abstract class AbstractTypeHandler<T> implements TypeHandler {

    @Override
    public void setParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws SQLException, ScriptFailedException {
        if (valueHolder.getVal() != null)
            setNonNullParam(ps, index, (T) valueHolder.getVal());
        else
            setNullParam(ps, index, valueHolder);
    }


    abstract void setNonNullParam(PreparedStatement ps, int index, T val) throws SQLException;


    abstract void setNullParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws SQLException, ScriptFailedException;

}
