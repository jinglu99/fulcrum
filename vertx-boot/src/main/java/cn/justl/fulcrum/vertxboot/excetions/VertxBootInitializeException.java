package cn.justl.fulcrum.vertxboot.excetions;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class VertxBootInitializeException extends VertxBootException {

    public VertxBootInitializeException(String message) {
        super(message);
    }

    public VertxBootInitializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public VertxBootInitializeException() {
    }
}
