package cn.justl.fulcrum.exceptions;

/**
 * @Date : 2019/9/29
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class ScriptFailedException extends DynamicSqlException {
    public ScriptFailedException(String message) {
        super(message);
    }

    public ScriptFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
