package cn.justl.fulcrum.scripthandler.handlers;

import cn.justl.fulcrum.contexts.ScriptContext;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.contexts.ExecuteContext;
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

    List<ScriptHandler> scriptList = new ArrayList<>();

    @Override
    public StringBuilder process(ExecuteContext context) throws ScriptFailedException {
        StringBuilder sb = new StringBuilder();
        for (ScriptHandler scriptHandler : scriptList) {
            sb.append(scriptHandler.process(context));
        }
        return sb;
    }

    @Override
    public ScriptResult process(ScriptContext context) throws ScriptFailedException {
        return null;
    }

    public ListableScriptHandler addNext(ScriptHandler scriptHandler) {
        scriptList.add(scriptHandler);
        return this;
    }
}
