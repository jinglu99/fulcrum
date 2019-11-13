package cn.justl.fulcrum.script.handlers;

import cn.justl.fulcrum.data.ScriptContext;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.script.ScriptHandler;
import cn.justl.fulcrum.script.BoundSql;
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
    public BoundSql process(ScriptContext context) throws ScriptFailedException {
        for (Case caze : cazes) {
            if (caze.isMatch(context)) {
                return caze.process(context);
            }
        }
        return BoundSql.emptyResult();
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

    /**
     * return a copy of cases
     * @return
     */
    public List<Case> getCases() {
        return new ArrayList<>(cazes);
    }

    @Override
    public String toString() {
        return "IfScriptHandler{" + "cases=" + cazes + '}';
    }

    public static class Case extends AbstractScriptHandler {
        private final String cond;
        private final Expression compiledExp;

        public Case(String cond) {
            this.cond = cond;
            if (cond != null) compiledExp = AviatorEvaluator.compile(cond);
            else compiledExp = null;

        }

        public boolean isMatch(ScriptContext context) throws ScriptFailedException {
            try {
                return (boolean) compiledExp.execute(context.getCombinedParams());
            } catch (Exception e) {
                throw new ScriptFailedException("if condition <" + cond + "> executed failed!", e);
            }

        }

        @Override
        public BoundSql process(ScriptContext context) throws ScriptFailedException {
            if (getChild() == null) {
                throw new ScriptFailedException("No child ScriptHandler exist in execution tree!");
            }

            return getChild().process(context);
        }

        public String getCond() {
            return cond;
        }

        @Override
        public String toString() {
            return "Case{" + "cond='" + cond + '\'' + ", child=" + child + '}';
        }
    }

    public static class Else extends Case {

        public Else() {
            super(null);
        }

        @Override
        public boolean isMatch(ScriptContext context) {
            return true;
        }

        @Override
        public String toString() {
            return "Else{" + "child=" + child + '}';
        }
    }
}
