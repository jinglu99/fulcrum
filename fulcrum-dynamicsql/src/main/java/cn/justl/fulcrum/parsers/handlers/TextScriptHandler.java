package cn.justl.fulcrum.parsers.handlers;

import cn.justl.fulcrum.contexts.ExecuteContext;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public final class TextScriptHandler extends AbstractScriptHandler {

    private final String text;

    public TextScriptHandler(String text) {
        this.text = text;
    }

    @Override
    public StringBuilder process(ExecuteContext context) {
        return new StringBuilder(" ").append(resolveText());
    }

    private StringBuilder resolveText() {
        return new StringBuilder(text);
    }
}
