package cn.justl.fulcrum.data;

import cn.justl.fulcrum.DynamicSQLParams;
import cn.justl.fulcrum.script.BoundSql;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 *  An ExecuteContext instance hold the parameters and result of the whole execution period.
 */
public class ExecuteContext {

    private DynamicSQLParams dynamicSQLParams;

    private ScriptContext scriptContext;

    private BoundSql boundSql;


    public DynamicSQLParams getDynamicSQLParams() {
        return dynamicSQLParams;
    }

    public void setDynamicSQLParams(DynamicSQLParams dynamicSQLParams) {
        this.dynamicSQLParams = dynamicSQLParams;
    }

    public ScriptContext getScriptContext() {
        return scriptContext;
    }

    public void setScriptContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
    }

    public BoundSql getBoundSql() {
        return boundSql;
    }

    public void setBoundSql(BoundSql boundSql) {
        this.boundSql = boundSql;
    }

    @Override
    public String toString() {
        return "ExecuteContext{" +
                "dynamicSQLParams=" + dynamicSQLParams +
                ", scriptContext=" + scriptContext +
                ", boundSql=" + boundSql +
                '}';
    }
}
