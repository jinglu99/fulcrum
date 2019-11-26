package cn.justl.fulcrum.vertxboot.excetions;

/**
 * @Date : 2019-11-26
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VertxBootException extends Exception {
    public VertxBootException() {
    }

    public VertxBootException(String message) {
        super(message);
    }

    public VertxBootException(String message, Throwable cause) {
        super(message, cause);
    }
}
