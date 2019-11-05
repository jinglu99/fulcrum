package cn.justl.fulcrum.scripthandler;

import cn.justl.fulcrum.data.ValueHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2019-11-03
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class ScriptResult {
    private StringBuilder sql;
    private final List<ValueHolder> valueHolders = new ArrayList<>();


    private final static ScriptResult EMPTY_RESULT = new EmptyScriptResult();
    public static ScriptResult emptyResult() {
        return EMPTY_RESULT;
    }

    public ScriptResult() {
    }

    public ScriptResult(StringBuilder sql) {
        this.sql = sql;
    }

    public StringBuilder getSql() {
        return sql;
    }

    public void setSql(StringBuilder sql) {
        this.sql = sql;
    }

    public List<ValueHolder> getValueHolders() {
        return valueHolders;
    }

    public void addValue(ValueHolder holder) {
        this.valueHolders.add(holder);
    }

    public void addAllValue(List<ValueHolder> holders) {
        this.valueHolders.addAll(holders);
    }

    @Override
    public String toString() {
        return "ScriptResult{" +
                "sql=" + sql +
                ", valueHolders=" + valueHolders +
                '}';
    }

    public static final class EmptyScriptResult extends ScriptResult {
        protected EmptyScriptResult() {}

        @Override
        public StringBuilder getSql() {
            return new StringBuilder();
        }

        @Override
        public void setSql(StringBuilder sql) {
            throw new RuntimeException("EmptyScript is immutable!");
        }
    }
}
