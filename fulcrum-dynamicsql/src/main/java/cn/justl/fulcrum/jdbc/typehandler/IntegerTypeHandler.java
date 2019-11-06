package cn.justl.fulcrum.jdbc.typehandler;

import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.SQLExecuteException;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.jdbc.TypeHandlers;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Date : 2019-10-29
 * @Author : jinglu.wang[jingl.wang123@gmail.com]
 * @Desc :
 * An implementation of {@link TypeHandler} for handle integer value(byte, short, int, long).
 * @case when the type of value is byte, the parameter would be set as {@link java.sql.Types#TINYINT};
 * @case when the type of value is short, the parameter would be set as {@link java.sql.Types#SMALLINT};
 * @case when the type of value is int, the parameter would be set as {@link java.sql.Types#INTEGER};
 * @case when the type of value is long, the parameter would be set as {@link java.sql.Types#BIGINT};
 * @case when the type of value is not in support types of {@link IntegerTypeHandler}, but the declare type is "int",
 * the parameter would be set as {@link java.sql.Types#BIGINT};
 * @case when {@link ValueHolder#getVal()} returns a null value, if default value expression is not set, an
 * exception {@link SQLExecuteException} will be thrown; if default value expression is set, then it will
 * be used to produce an value as parameter, and the type of parameter will be set as {@link java.sql.Types#BIGINT}
 */
public class IntegerTypeHandler extends AbstractTypeHandler {


    @Override
    public void setNonNullParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws SQLExecuteException {
        try {
            Object val = valueHolder.getVal();
            if (val instanceof Byte) {
                ps.setByte(index, (Byte) val);
            } else if (val instanceof Short) {
                ps.setShort(index, (Short) val);
            } else if (val instanceof Integer) {
                ps.setInt(index, (Integer) val);
            } else if (val instanceof Long) {
                ps.setLong(index, (Long) val);
            } else if (val instanceof Number) {
                ps.setLong(index, ((Number) val).longValue());
            } else {
                ps.setLong(index, Long.valueOf(val.toString()));
            }
        } catch (Exception e) {
            throw new SQLExecuteException(String.format("fail to set parameter<%s>", valueHolder.getParamName()), e);
        }
    }

    @Override
    public void setNullParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws SQLExecuteException {
        try {
            if (StringUtils.isNotBlank(valueHolder.getDefaultExp())) {
                Long val = Long.valueOf(valueHolder.getDefaultExp());
                ps.setLong(index, val);
            } else
                throw new SQLExecuteException(String.format("%s can't be null", valueHolder.getParamName()));
        } catch (Exception e) {
            throw new SQLExecuteException(String.format("fail to set parameter<%s>", valueHolder.getParamName()), e);
        }
    }

    @Override
    public Set<String> getSupportTypeNames() {
        return new HashSet<>(Arrays.asList("int"));
    }

    @Override
    public Set<Class> getSupportTypes() {
        return new HashSet<>(Arrays.asList(
                byte.class, Byte.class,
                short.class, Short.class,
                int.class, Integer.class,
                long.class, Long.class
        ));
    }
}
