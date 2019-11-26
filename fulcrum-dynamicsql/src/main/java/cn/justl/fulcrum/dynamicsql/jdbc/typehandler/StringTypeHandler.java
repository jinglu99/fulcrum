package cn.justl.fulcrum.dynamicsql.jdbc.typehandler;

import cn.justl.fulcrum.dynamicsql.ValueHolder;
import cn.justl.fulcrum.dynamicsql.exceptions.TypeHandleException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Date : 2019-11-06
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class StringTypeHandler extends AbstractTypeHandler {
    @Override
    public void setNonNullParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws TypeHandleException {
        try {
            ps.setString(index, valueHolder.toString());
        } catch (SQLException e) {
            throw new TypeHandleException(String.format("Can't set parameter for %s with value: %s", valueHolder.getParamName(), valueHolder.getVal()), e);
        }
    }

    @Override
    public void setNullParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws TypeHandleException {
        try {
            if (valueHolder.getDefaultExp() == null) {
                ps.setString(index, "null");
            } else {
                ps.setString(index, valueHolder.getDefaultExp());
            }
        } catch (SQLException e) {
            throw new TypeHandleException(String.format("Can't set parameter for %s with defaultExp: %s", valueHolder.getParamName(), valueHolder.getDefaultExp()), e);
        }
    }

    @Override
    public Set<String> getSupportTypeNames() {
        return new HashSet<>(Arrays.asList("String"));
    }

    @Override
    public Set<Class> getSupportTypes() {
        return new HashSet<>(Arrays.asList(String.class));
    }
}
