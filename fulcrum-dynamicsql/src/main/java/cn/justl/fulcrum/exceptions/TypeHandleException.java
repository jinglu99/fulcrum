package cn.justl.fulcrum.exceptions;

/**
 * @Date : 2019/11/8
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class TypeHandleException extends Exception {

    public TypeHandleException(String message) {
        super(message);
    }

    public TypeHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}
