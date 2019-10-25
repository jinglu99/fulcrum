package cn.justl.fulcrum.jdbc.typehandler;

import java.sql.PreparedStatement;

/**
 * @Date : 2019/10/25
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public abstract class AbstractTypeHandler<T> implements TypeHandler<T> {

    @Override
    public void setParam(PreparedStatement ps, int index, T val) {
        //todo set param
    }

    abstract void setNonNullParam(PreparedStatement ps, int index, T val);

    abstract void setNullParam(PreparedStatement ps, int index);
}
