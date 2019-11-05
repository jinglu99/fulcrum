package cn.justl.fulcrum.jdbc.typehandler;

import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.jdbc.SupportTypes;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Date : 2019-10-29
 * @Author : 汪京陆(Ben Wang)[jingl.wang123@gmail.com]
 * @Desc :
 */
public class IntTypeHandler extends AbstractTypeHandler<Integer> {
    @Override
    public boolean isMatchByType(ValueHolder valueHolder) {
        return valueHolder.getVal() instanceof Integer;
    }

    @Override
    public boolean isMatchByTypeName(ValueHolder valueHolder) {
        return SupportTypes.INTEGER.getName().equals(valueHolder.getType());
    }

    @Override
    void setNonNullParam(PreparedStatement ps, int index, Integer val) throws SQLException {
        ps.setInt(index, val);
    }

    @Override
    void setNullParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws SQLException, ScriptFailedException {
        if (StringUtils.isNotBlank(valueHolder.getDefaultExp())) {
            try {
                ps.setInt(index, Integer.parseInt(valueHolder.getDefaultExp()));
            } catch (Exception e) {
                throw new ScriptFailedException("<" + valueHolder.getDefaultExp() + "> can't be parsed!", e);
            }
        } else
            throw new ScriptFailedException(valueHolder.getParamName() + "can't be null");
    }
}
