package cn.justl.fulcrum.vertx.boot.excetions;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VerticleCreationException extends VertxBootInitializeException {

    public VerticleCreationException() {
    }

    public VerticleCreationException(String message) {
        super(message);
    }

    public VerticleCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
