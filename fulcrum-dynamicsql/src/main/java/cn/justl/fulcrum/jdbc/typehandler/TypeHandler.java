package cn.justl.fulcrum.jdbc.typehandler;

import java.sql.PreparedStatement;

/**
 * @Date : 2019/10/25
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public interface TypeHandler<T> {

    void setParam(PreparedStatement ps, int index, T val);

    boolean isMatch(Object val);
}
