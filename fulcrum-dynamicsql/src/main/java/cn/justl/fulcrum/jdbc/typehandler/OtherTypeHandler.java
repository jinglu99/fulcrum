package cn.justl.fulcrum.jdbc.typehandler;

import java.sql.PreparedStatement;

/**
 * @Date : 2019/10/25
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class OtherTypeHandler extends AbstractTypeHandler {

    @Override
    public void setNonNullParam(PreparedStatement ps, int index, Object val) {

    }

    @Override
    public void setNullParam(PreparedStatement ps, int index) {

    }

    @Override
    public boolean isMatch(Object val) {
        return true;
    }
}
