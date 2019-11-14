package cn.justl.fulcrum.exceptions;

/**
 * @Date : 2019-11-05
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class StatementExecuteException extends DynamicSqlException {

    public StatementExecuteException(String message) {
        super(message);
    }

    public StatementExecuteException(String message, Throwable cause) {
        super(message, cause);
    }
}
