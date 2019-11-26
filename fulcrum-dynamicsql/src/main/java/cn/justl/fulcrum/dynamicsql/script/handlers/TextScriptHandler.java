package cn.justl.fulcrum.dynamicsql.script.handlers;

import cn.justl.fulcrum.dynamicsql.script.ScriptContext;
import cn.justl.fulcrum.dynamicsql.ValueHolder;
import cn.justl.fulcrum.dynamicsql.exceptions.ScriptFailedException;
import cn.justl.fulcrum.dynamicsql.script.BoundSql;
import cn.justl.fulcrum.dynamicsql.script.ValueELResolver;

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
    public BoundSql process(ScriptContext context) throws ScriptFailedException {
        return resolveText(context);
    }

    private BoundSql resolveText(ScriptContext context) throws ScriptFailedException {
        BoundSql sr = new BoundSql(new StringBuilder(text));
        resolveText(sr, context);
        return sr;
    }

    private static void resolveText(BoundSql sr, ScriptContext context)
            throws ScriptFailedException {
        int start, end;
        String exp = null;
        if ((start = sr.getSql().indexOf("{$")) >= 0 && (end = sr.getSql().indexOf("}")) >= 0) {
            try {
                exp = sr.getSql().substring(start + 2, end);
                ValueHolder valueHolder = ValueELResolver.getValueHolder(x -> {
                    try {
                        return context.getParam(x);
                    } catch (Exception e) {
                        return null;
                    }
                }, exp);

                sr.getSql().replace(start, end + 1, "?");
                sr.addValue(valueHolder);
            } catch (Exception e) {
                throw new ScriptFailedException("<" + exp + "> can't be resolved", e);
            }
            resolveText(sr, context);
        }
    }
}
