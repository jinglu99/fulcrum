package cn.justl.fulcrum.vertx.boot.excetions;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class BeanInitializeException extends VertxBootInitializeException {
    public BeanInitializeException(String message) {
        super(message);
    }

    public BeanInitializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanInitializeException() {
    }
}
