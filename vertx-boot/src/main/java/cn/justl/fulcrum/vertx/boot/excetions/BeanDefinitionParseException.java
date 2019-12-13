package cn.justl.fulcrum.vertx.boot.excetions;

/**
 * @Date : 2019/12/13
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class BeanDefinitionParseException extends VertxBootException {

    public BeanDefinitionParseException() {
        super();
    }

    public BeanDefinitionParseException(String message) {
        super(message);
    }

    public BeanDefinitionParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
