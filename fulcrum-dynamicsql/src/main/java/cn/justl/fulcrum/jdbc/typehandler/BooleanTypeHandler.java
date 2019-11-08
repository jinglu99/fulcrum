package cn.justl.fulcrum.jdbc.typehandler;

import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.TypeHandleException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 * @Date : 2019/11/8
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc : An implementation of {@link TypeHandler} that handle boolean type parameter. if the input
 * is not a {@link Boolean} object, but a {@link Number} or {@link String} object, it will be
 * converted to boolean.
 * <p>
 * If the input param was {@link Number}, this handler will regard the number greater than 0 as true
 * and the others as false.
 * <p>
 * If the input param was {@link String}, only "true"(ignore case) will be regarded as true.
 *
 * <p>
 * If the input param was other type, it will throw an
 */
public class BooleanTypeHandler extends AbstractTypeHandler {

    @Override
    public void setNonNullParam(PreparedStatement ps, int index, ValueHolder valueHolder)
        throws TypeHandleException {
        try {
            if (valueHolder.getVal() instanceof Boolean) {
                ps.setBoolean(index, (Boolean) valueHolder.getVal());
            } else if (valueHolder.getVal() instanceof Number) {
                setNumberParam(ps, index, valueHolder);
            } else if (valueHolder.getVal() instanceof String) {
                setStringParam(ps, index, valueHolder);
            } else {
                throw new Exception(String
                    .format("the type of %s's value <%s> is %s, can not be converted to boolean",
                        valueHolder.getParamName(), valueHolder.getVal().toString(),
                        valueHolder.getVal().getClass().getName()));
            }
        } catch (Exception e) {
            throw new TypeHandleException(String
                .format("failed to handle parameter<%s>, input val is <%s>",
                    valueHolder.getParamName(), valueHolder.getVal()), e);
        }
    }

    @Override
    public void setNullParam(PreparedStatement ps, int index, ValueHolder valueHolder)
        throws TypeHandleException {
        if (StringUtils.isBlank(valueHolder.getDefaultExp())) {
            throw new TypeHandleException(String
                .format("parameter<%s> not found and default expression of %s is not defined",
                    valueHolder.getParamName(), valueHolder.getParamName()));
        }

        try {
            if (StringUtils.equals("true", valueHolder.getDefaultExp().toLowerCase())) {
                ps.setBoolean(index, true);
            } else if (StringUtils.equals("false", valueHolder.getDefaultExp().toLowerCase())) {
                ps.setBoolean(index, false);
            } else {
                throw new Exception(String.format("value <%s> of <%s> can't be parsed to boolean",
                    valueHolder.getDefaultExp(), valueHolder.getParamName()));
            }
        } catch (Exception e) {
            throw new TypeHandleException(String
                .format("failed to handel parameter<%s> through default expression <%s>",
                    valueHolder.getParamName(), valueHolder.getDefaultExp()), e);
        }


    }

    @Override
    public Set<String> getSupportTypeNames() {
        return new HashSet<>(Arrays.asList("bool"));
    }

    @Override
    public Set<Class> getSupportTypes() {
        return new HashSet(Arrays.asList(Boolean.class, boolean.class));
    }

    private void setNumberParam(PreparedStatement ps, int index, ValueHolder valueHolder)
        throws SQLException {
        ps.setBoolean(index, ((Number) valueHolder.getVal()).intValue() > 0 ? true : false);
    }

    private void setStringParam(PreparedStatement ps, int index, ValueHolder valueHolder)
        throws Exception {
        if (StringUtils.equals("true", ((String) valueHolder.getVal()).toLowerCase())) {
            ps.setBoolean(index, true);
        } else if (StringUtils.equals("false", ((String) valueHolder.getVal()).toLowerCase())) {
            ps.setBoolean(index, false);
        } else {
            throw new Exception(String.format("value <%s> of <%s> can't be parsed to boolean",
                valueHolder.getVal().toString(), valueHolder.getParamName()));
        }
    }
}
