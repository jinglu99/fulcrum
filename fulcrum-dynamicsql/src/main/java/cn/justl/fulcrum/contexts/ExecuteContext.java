package cn.justl.fulcrum.contexts;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class ExecuteContext {
    private final ScriptContext params = new ScriptContext();

    private final List<ValueHolder> sqlParamList = new ArrayList<>();

    public ScriptContext getParams() {
        return params;
    }

    public List<ValueHolder> getSqlParamList() {
        return sqlParamList;
    }
}
