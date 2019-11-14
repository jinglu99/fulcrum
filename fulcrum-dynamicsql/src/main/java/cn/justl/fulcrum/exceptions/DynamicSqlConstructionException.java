package cn.justl.fulcrum.exceptions;

/**
 * @Date : 2019-11-09
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DynamicSqlConstructionException extends DynamicSqlException {
    public DynamicSqlConstructionException(String message) {
        super(message);
    }

    public DynamicSqlConstructionException(String message, Throwable cause) {
        super(message, cause);
    }
}
