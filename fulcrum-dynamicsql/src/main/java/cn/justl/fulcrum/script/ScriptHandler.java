package cn.justl.fulcrum.script;

import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.script.handlers.*;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 * a description of the operation of a xml element
 *
 * @see AbstractScriptHandler
 * @see TextScriptHandler
 * @see IfScriptHandler
 * @see ForeachScriptHandler
 * @see ListableScriptHandler
 */
public interface ScriptHandler {
    BoundSql process(ScriptContext context) throws ScriptFailedException;

    ScriptHandler getParent();

    ScriptHandler setParent(ScriptHandler parent);

    ScriptHandler getChild();

    ScriptHandler setChild(ScriptHandler scriptHandler);
}
