package cn.justl.fulcrum.vertxboot.excetions;

/**
 * @Date : 2019/11/24
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class AnnotationHandlerNotFoundException extends AnnotationScannerException {

    public AnnotationHandlerNotFoundException(String message) {
        super(message);
    }
}
