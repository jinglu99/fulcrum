package cn.justl.fulcrum.vertx.boot.excetions;

/**
 * @Date : 2019-11-27
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VerticleStartException extends VerticleInitializeException {
    public VerticleStartException(String message) {
        super(message);
    }

    public VerticleStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerticleStartException() {
    }
}
