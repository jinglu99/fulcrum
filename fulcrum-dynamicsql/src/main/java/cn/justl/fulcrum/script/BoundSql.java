package cn.justl.fulcrum.script;

import cn.justl.fulcrum.data.ValueHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2019-11-03
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class BoundSql {
    private StringBuilder sql;
    private final List<ValueHolder> valueHolders = new ArrayList<>();


    private final static BoundSql EMPTY_RESULT = new EmptyBoundSql();
    public static BoundSql emptyResult() {
        return EMPTY_RESULT;
    }

    public BoundSql() {
    }

    public BoundSql(StringBuilder sql) {
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

    public static final class EmptyBoundSql extends BoundSql {
        protected EmptyBoundSql() {}

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
