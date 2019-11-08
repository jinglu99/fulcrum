package cn.justl.fulcrum.jdbc.typehandler;

import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.TypeHandleException;
import cn.justl.fulcrum.exceptions.ScriptFailedException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
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
