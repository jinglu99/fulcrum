package cn.justl.fulcrum.dynamicsql;

import java.util.List;
import java.util.Map;

/**
 * @Date : 2019-11-09
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc : An DynamicSQLResult describes the result that generated after calling {@link
 * DynamicSQLExecutor#execute(DynamicSQLParams)}
 */
public class DynamicSQLResult {

    /**
     * the result of the execution
     */
    private List<Map> result;

    public DynamicSQLResult(List<Map> result) {
        this.result = result;
    }

    public List<Map> getResult() {
        return result;
    }

    public void setResult(List<Map> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DynamicSQLResult{" +
            "result=" + result +
            '}';
    }
}
