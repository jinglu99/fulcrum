package cn.justl.fulcrum.dynamicsql.exceptions;

/**
 * @Date : 2019/11/8
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class TypeHandleException extends DynamicSqlException {

    public TypeHandleException(String message) {
        super(message);
    }

    public TypeHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}
