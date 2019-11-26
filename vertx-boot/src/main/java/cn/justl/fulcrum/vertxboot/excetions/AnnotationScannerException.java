package cn.justl.fulcrum.vertxboot.excetions;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class AnnotationScannerException extends VertxBootInitializeException {

    public AnnotationScannerException() {
    }

    public AnnotationScannerException(String message) {
        super(message);
    }

    public AnnotationScannerException(String message, Throwable cause) {
        super(message, cause);
    }
}
