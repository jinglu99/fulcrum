package cn.justl.fulcrum.jdbc.typehandler;

import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.ScriptFailedException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Date : 2019/10/25
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface TypeHandler {

    void setParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws SQLException, ScriptFailedException;

    boolean isMatchByType(ValueHolder valueHolder);

    boolean isMatchByTypeName(ValueHolder valueHolder);
}
