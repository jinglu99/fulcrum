package cn.justl.fulcrum.vertx.boot.excetions;

/**
 * @Date : 2019-11-28
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class BeanCloseException extends VertxBootInitializeException {
    public BeanCloseException(String message) {
        super(message);
    }

    public BeanCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanCloseException() {
    }
}