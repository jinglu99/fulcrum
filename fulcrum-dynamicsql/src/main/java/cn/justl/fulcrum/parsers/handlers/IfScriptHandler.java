package cn.justl.fulcrum.parsers.handlers;

import cn.justl.fulcrum.contexts.ExecuteContext;
import cn.justl.fulcrum.parsers.exceptions.ScriptFailedException;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2019/9/29
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public final class IfScriptHandler extends AbstractScriptHandler {
    private final List<Case> cazes = new ArrayList();

    @Override
    public StringBuilder process(ExecuteContext context) throws ScriptFailedException {
        for (Case caze : cazes) {
            if (caze.isMatch(context)) {
                return caze.process(context);
            }
        }
        return null;
    }

    public IfScriptHandler addCase(String cond, ScriptHandler scriptHandler) {
        Case caze = new Case(cond);
        caze.setChild(scriptHandler);
        cazes.add(caze);
        return this;
    }

    public IfScriptHandler addElse(ScriptHandler scriptHandler) {
        Else elze = new Else();
        elze.setChild(scriptHandler);
        cazes.add(elze);
        return this;
    }

    @Override
    public String toString() {
        return "IfScriptHandler{" + "cases=" + cazes + '}';
    }

    private static class Case extends AbstractScriptHandler {
        private final String cond;
        private final Expression compiledExp;

        public Case(String cond) {
            this.cond = cond;
            if (cond != null) compiledExp = AviatorEvaluator.compile(cond);
            else compiledExp = null;

        }

        public boolean isMatch(ExecuteContext context) throws ScriptFailedException {
            try {
                return (boolean) compiledExp.execute(context.getParams().getCombinedParams());
            } catch (Exception e) {
                throw new ScriptFailedException("if condition <" + cond + "> executed failed!", e);
            }

        }

        @Override
        public StringBuilder process(ExecuteContext context) throws ScriptFailedException {
            if (getChild() == null) {
                throw new ScriptFailedException("No child ScriptHandler exist in execution tree!");
            }

            return getChild().process(context);
        }

        @Override
        public String toString() {
            return "Case{" + "cond='" + cond + '\'' + ", child=" + child + '}';
        }
    }

    private static class Else extends Case {

        public Else() {
            super(null);
        }

        @Override
        public boolean isMatch(ExecuteContext context) {
            return true;
        }

        @Override
        public String toString() {
            return "Else{" + "child=" + child + '}';
        }
    }
}
