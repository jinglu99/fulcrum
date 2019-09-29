package cn.justl.fulcrum.contexts;

import com.sun.istack.internal.Nullable;

import java.util.*;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class ParamContext {

    private final Context globalContext = new Context();
    private final Deque<Context> contexts = new LinkedList<>();

    public ParamContext setParams(Map params) {
        globalContext.reset(params);
        return this;
    }
    /**
     * this method must be called before a temp param being added to the context
     * @return
     */
    public ParamContext prepareTempParamsContext() {
        contexts.add(new Context());
        return this;
    }

    public ParamContext addTempParams(Map params) {
        contexts.getLast().params.putAll(params);
        return this;
    }

    public ParamContext addTempParam(String key, Object val) {
        contexts.getLast().setParam(key, val);
        return this;
    }

    /**
     * used to pop the temp context from running context
     * @return
     */
    public ParamContext popTempParams() {
        contexts.removeLast();
        return this;
    }

    @Nullable
    public Object getParam(String name) {
        Iterator iterator = contexts.descendingIterator();
        while (iterator.hasNext()) {
            Context cur = (Context) iterator.next();
            if (cur.contains(name))
                return cur.getParam(name);
        }

        return globalContext.getParam(name);
    }

    public Map getCombinedParams() {
        Map params = new HashMap();
        params.putAll(globalContext.params);

        for (Context context : contexts) {
            params.putAll(context.params);
        }
        return params;
    }

    public boolean containsParam(String name) {
        Iterator iterator = contexts.descendingIterator();
        while (iterator.hasNext()) {
            if (((Context)iterator.next()).contains(name))
                return true;
        }

        return globalContext.contains(name);
    }


    private static class Context {
        Map<String, Object> params;

        public Context() {
            this(null);
        }

        public Context(Map<String, Object> params) {

        }

        public Context reset(Map<String, Object> params) {
            this.params = Optional.of(params).orElse(Collections.emptyMap());
            return null;
        }

        public Context setParam(String key, Object val) {
            params.put(key, val);
            return this;
        }

        public Object getParam(String key) {
            return params.get(key);
        }

        public boolean contains(String key) {
            return params.containsKey(key);
        }
    }
}
