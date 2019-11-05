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
public class OtherTypeHandler extends AbstractTypeHandler {

    private static OtherTypeHandler instance = new OtherTypeHandler();

    public static OtherTypeHandler getInstance() {
        return instance;
    }

    public void setNonNullParam(PreparedStatement ps, int index, Object val) throws SQLException {
        ps.setObject(index, val);
    }

    @Override
    void setNullParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws SQLException, ScriptFailedException {
        ps.setString(index, valueHolder.getDefaultExp());
    }


    @Override
    public boolean isMatchByType(ValueHolder valueHolder) {
        return true;
    }

    @Override
    public boolean isMatchByTypeName(ValueHolder valueHolder) {
        return true;
    }
}
