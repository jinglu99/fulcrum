package cn.justl.fulcrum.dynamicsql.exceptions;

/**
 * @Date : 2019-11-09
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DynamicSqlExecutionException extends DynamicSqlException {
    public DynamicSqlExecutionException(String message) {
        super(message);
    }

    public DynamicSqlExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
