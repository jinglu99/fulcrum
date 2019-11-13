package cn.justl.fulcrum.exceptions;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DynamicSQLParseException extends Exception {
    public DynamicSQLParseException(String message) {
        super(message);
    }

    public DynamicSQLParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
