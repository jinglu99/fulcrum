package cn.justl.fulcrum.vertx.boot.excetions;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VerticleInstantiateException extends VertxBootInitializeException {

    public VerticleInstantiateException() {
    }

    public VerticleInstantiateException(String message) {
        super(message);
    }

    public VerticleInstantiateException(String message, Throwable cause) {
        super(message, cause);
    }
}
