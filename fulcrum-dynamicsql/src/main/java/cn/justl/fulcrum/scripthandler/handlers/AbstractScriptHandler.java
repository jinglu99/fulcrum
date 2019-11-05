package cn.justl.fulcrum.scripthandler.handlers;

import cn.justl.fulcrum.scripthandler.ScriptHandler;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public abstract class AbstractScriptHandler implements ScriptHandler {
    protected ScriptHandler parent = null;
    protected ScriptHandler child = null;

    @Override
    public ScriptHandler getChild() {
        return child;
    }

    @Override
    public ScriptHandler setChild(ScriptHandler scriptHandler) {
        child = scriptHandler;
        return this;
    }

    @Override
    public ScriptHandler getParent() {
        return this.parent;
    }

    @Override
    public ScriptHandler setParent(ScriptHandler parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public String toString() {
        return "AbstractScriptHandler{" + "parent=" + parent + ", child=" + child + '}';
    }
}
