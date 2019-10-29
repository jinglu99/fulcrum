package cn.justl.fulcrum.contexts;

import cn.justl.fulcrum.exceptions.ScriptFailedException;
import com.sun.istack.internal.Nullable;
import java.lang.reflect.Array;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

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
     */
    public ParamContext popTempParams() {
        contexts.removeLast();
        return this;
    }

    @Nullable
    public Object getParam(String placeHolder) throws ScriptFailedException {
        return resolveParam(placeHolder);
    }

    public Map getCombinedParams() {
        Map params = new HashMap();
        params.putAll(globalContext.params);

        for (Context context : contexts) {
            params.putAll(context.params);
        }
        return params;
    }

    public boolean containsParam(String placeHolder) {
        try {
            getParam(placeHolder);
            return true;
        } catch (ScriptFailedException e) {
            return false;
        }
    }

    private Object resolveParam(String placeHolder) throws ScriptFailedException {
        String param = placeHolder.indexOf(".") < 0 ? placeHolder : placeHolder.substring(0, placeHolder.indexOf("."));
        try {
            Object obj = resolveTrueParam(param);

            String resolvingName = placeHolder.indexOf(".") < 0 ? "" : placeHolder.substring(placeHolder.indexOf(".") + 1);

            int index = -1;
            while (StringUtils.isNotBlank(resolvingName)) {
                if((index = resolvingName.indexOf(".")) >= 0) {
                    param = resolvingName.substring(0, index);
                    resolvingName = resolvingName.substring(index + 1);
                } else {
                    param = resolvingName;
                    resolvingName = "";
                }
                obj = MapUtils.getObject(describeObj(obj), param);
            }

            return obj;

        } catch (Exception e) {
            throw new ScriptFailedException("placeholder <" + placeHolder + "> can't be resolved!",
                e);
        }

    }

    private Object resolveTrueParam(String placeHolder) throws ScriptFailedException {
        if (isArrayType(placeHolder)) {
            return resolveArrayParam(placeHolder);
        } else {
            return resolveObjParam(placeHolder);
        }
    }

    private Object resolveObjParam(String placeHolder) throws ScriptFailedException {
        Iterator iterator = contexts.descendingIterator();
        while (iterator.hasNext()) {
            Context cur = (Context) iterator.next();
            if (cur.contains(placeHolder)) {
                return cur.getParam(placeHolder);
            }
        }

        if (globalContext.contains(placeHolder)) {
            return globalContext.getParam(placeHolder);
        } else {
            throw new ScriptFailedException("param <\" + placeHolder + \"> not exist!");
        }
    }

    private Object resolveArrayParam(String placeHolder) throws ScriptFailedException {
        String indexStr = getArrayIndexStr(placeHolder);
        Object obj = resolveObjParam(getArrayParamKey(placeHolder));
        while (isArrayType(indexStr)) {
            if (obj.getClass().isArray()) {
                obj = Array.get(obj, getArrayIndex(indexStr));
            } else if (obj instanceof List) {
                obj = ((List) obj).get(getArrayIndex(indexStr));
            } else {
                throw new ScriptFailedException(placeHolder + " is not an Array or a List object");
            }

            indexStr = indexStr.substring(indexStr.indexOf("]" + 1));
        }

        return obj;
    }

    private static Map describeObj(Object object)
        throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (object instanceof Map) return (Map) object;
        else return BeanUtils.describe(object);
    }

    private static boolean isArrayType(String paramName) {
        return paramName.contains("[") && paramName.contains("]");
    }

    private static int getArrayIndex(String paramName) {
        return Integer
            .parseInt(paramName.substring(paramName.indexOf("[") + 1, paramName.indexOf("]")));
    }

    private static String getArrayIndexStr(String placeHolder) {
        return placeHolder.substring(placeHolder.indexOf("["));
    }

    private static String getArrayParamKey(String placeHolder) {
        return placeHolder.substring(0, placeHolder.indexOf("["));
    }

    public static void main(String[] args) {
        System.out.println(isArrayType("a[1]"));
        System.out.println(getArrayIndex("a[1]"));
    }

    private static class Context {

        Map<String, Object> params;

        public Context() {
            this(null);
        }

        public Context(Map<String, Object> params) {
            this.params = Optional.ofNullable(params).orElse(new HashMap<>());
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
