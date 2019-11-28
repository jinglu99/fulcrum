package cn.justl.fulcrum.vertx.boot.excetions;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VerticleInitializeException extends VertxBootInitializeException {
    public VerticleInitializeException(String message) {
        super(message);
    }

    public VerticleInitializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerticleInitializeException() {
    }
}
