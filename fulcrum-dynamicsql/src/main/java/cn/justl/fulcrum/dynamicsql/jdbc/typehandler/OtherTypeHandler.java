package cn.justl.fulcrum.dynamicsql.jdbc.typehandler;

import cn.justl.fulcrum.dynamicsql.ValueHolder;
import cn.justl.fulcrum.dynamicsql.exceptions.TypeHandleException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

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


    @Override
    public void setNonNullParam(PreparedStatement ps, int index, ValueHolder valueHolder)
        throws TypeHandleException {
        try {
            ps.setObject(index, valueHolder.getVal());
        } catch (SQLException e) {
            throw new TypeHandleException(String
                .format("Can't set parameter for %s with value %s", valueHolder.getParamName(),
                    valueHolder.getVal()), e);
        }
    }

    @Override
    public void setNullParam(PreparedStatement ps, int index, ValueHolder valueHolder)
        throws TypeHandleException {
        throw new TypeHandleException(String.format("The parameter %s is not found!", valueHolder.getVal()));
    }

    @Override
    public Set<String> getSupportTypeNames() {
        return null;
    }

    @Override
    public Set<Class> getSupportTypes() {
        return null;
    }
}
