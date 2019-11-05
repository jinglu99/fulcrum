package cn.justl.fulcrum.scripthandler.handlers;

import cn.justl.fulcrum.data.ScriptContext;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.scripthandler.ScriptHandler;
import cn.justl.fulcrum.scripthandler.ScriptResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 * an implement of ScriptHandler to hold a group of ScriptHandler
 */
public class ListableScriptHandler extends AbstractScriptHandler {

    private final List<ScriptHandler> scriptList = new ArrayList<>();

    @Override
    public ScriptResult process(ScriptContext context) throws ScriptFailedException {
        ScriptResult sr = new ScriptResult();
        sr.setSql(new StringBuilder());
        for (ScriptHandler scriptHandler : scriptList) {
            ScriptResult subRs = scriptHandler.process(context);
            if (subRs == null && subRs instanceof ScriptResult.EmptyScriptResult) continue;
            sr.getSql().append(subRs.getSql()).append(" ");
            sr.addAllValue(subRs.getValueHolders());
        }

        return sr;
    }

    /**
     * add next ScriptHandler
     * @param scriptHandler
     * @return
     */
    public ListableScriptHandler addNext(ScriptHandler scriptHandler) {
        scriptList.add(scriptHandler);
        return this;
    }

    /**
     * return a copy of script list
     * @return
     */
    public List<ScriptHandler> getScripts() {
        return new ArrayList<>(scriptList);
    }
}
