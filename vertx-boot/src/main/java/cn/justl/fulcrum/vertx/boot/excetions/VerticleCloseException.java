package cn.justl.fulcrum.vertx.boot.excetions;

/**
 * @Date : 2019-11-28
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VerticleCloseException extends VertxBootInitializeException {
    public VerticleCloseException(String message) {
        super(message);
    }

    public VerticleCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerticleCloseException() {
    }
}
