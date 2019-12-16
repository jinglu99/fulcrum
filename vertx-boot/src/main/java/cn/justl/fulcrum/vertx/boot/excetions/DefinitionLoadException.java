package cn.justl.fulcrum.vertx.boot.excetions;

/**
 * @Date : 2019/12/13
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DefinitionLoadException extends VertxBootException {

    public DefinitionLoadException() {
        super();
    }

    public DefinitionLoadException(String message) {
        super(message);
    }

    public DefinitionLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
