package cn.justl.fulcrum.vertx.boot.excetions;

/**
 * @Date : 2019-11-27
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class BeanStartException extends BeanInitializeException {
    public BeanStartException(String message) {
        super(message);
    }

    public BeanStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanStartException() {
    }
}
