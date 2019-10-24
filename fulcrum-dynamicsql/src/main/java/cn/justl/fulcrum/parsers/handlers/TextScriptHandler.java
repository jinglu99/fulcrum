package cn.justl.fulcrum.parsers.handlers;

import cn.justl.fulcrum.contexts.ExecuteContext;
import cn.justl.fulcrum.parsers.exceptions.ScriptFailedException;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public final class TextScriptHandler extends AbstractScriptHandler {

    private final String text;

    public TextScriptHandler(String text) {
        this.text = text.trim();
    }

    @Override
    public StringBuilder process(ExecuteContext context) throws ScriptFailedException {
        return new StringBuilder(" ").append(resolveText(context));
    }

    private StringBuilder resolveText(ExecuteContext context) throws ScriptFailedException {
        return resolveText(new StringBuilder(text), context);
    }

    private static StringBuilder resolveText(StringBuilder textBuilder, ExecuteContext context)
        throws ScriptFailedException {
        int start, end;
        if ((start = textBuilder.indexOf("{$")) > 0 && (end = textBuilder.indexOf("}")) > 0) {
            Object obj = context.getParams().getParam(textBuilder.substring(start + 2, end));
            textBuilder.replace(start, end + 1, "?");
            context.getSqlParamList().add(obj);

            return resolveText(textBuilder, context);
        } else {
            return textBuilder;
        }
    }
}
