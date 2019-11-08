package cn.justl.fulcrum.jdbc.typehandler;

import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.TypeHandleException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Set;

/**
 * @Date : 2019-11-06
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class StringTypeHandler extends AbstractTypeHandler {
    @Override
    public void setNonNullParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws TypeHandleException {

    }

    @Override
    public void setNullParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws TypeHandleException {

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
