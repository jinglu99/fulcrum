package cn.justl.fulcrum.exceptions;

/**
 * @Date : 2019-11-05
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class SQLExecuteException extends Exception {

    public SQLExecuteException(String message) {
        super(message);
    }

    public SQLExecuteException(String message, Throwable cause) {
        super(message, cause);
    }
}
