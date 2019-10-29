package cn.justl.fulcrum.parsers.handlers;

import cn.justl.fulcrum.contexts.ExecuteContext;
import cn.justl.fulcrum.contexts.ValueHolder;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.parsers.utils.ValueELResolver;

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
        String exp = null;
        if ((start = textBuilder.indexOf("{$")) > 0 && (end = textBuilder.indexOf("}")) > 0) {
            try {
                exp = textBuilder.substring(start + 2, end);
                ValueHolder valueHolder = ValueELResolver.getValueHolder(x -> {
                    try {
                        return context.getParams().getParam(x);
                    } catch (Exception e) {
                        return null;
                    }
                }, exp);

                textBuilder.replace(start, end + 1, "?");
                context.getSqlParamList().add(valueHolder);
            } catch (Exception e) {
                throw new ScriptFailedException("<" + exp + "> can't be resolved", e);
            }
            return resolveText(textBuilder, context);
        } else {
            return textBuilder;
        }
    }
}
