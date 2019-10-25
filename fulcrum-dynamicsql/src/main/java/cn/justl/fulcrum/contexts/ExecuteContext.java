package cn.justl.fulcrum.contexts;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class ExecuteContext {
    private final ParamContext params = new ParamContext();

    private final List<Object> sqlParamList = new ArrayList<>();

    public ParamContext getParams() {
        return params;
    }

    public List<Object> getSqlParamList() {
        return sqlParamList;
    }
}
