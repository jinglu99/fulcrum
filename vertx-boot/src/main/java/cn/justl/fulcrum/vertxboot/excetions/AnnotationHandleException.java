package cn.justl.fulcrum.vertxboot.excetions;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class AnnotationHandleException extends Exception {

    public AnnotationHandleException(String message) {
        super(message);
    }

    public AnnotationHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}
