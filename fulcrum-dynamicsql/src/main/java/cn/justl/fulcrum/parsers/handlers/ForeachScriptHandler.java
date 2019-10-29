package cn.justl.fulcrum.parsers.handlers;

import cn.justl.fulcrum.contexts.ExecuteContext;
import cn.justl.fulcrum.exceptions.ScriptFailedException;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @Date : 2019/9/29
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 * an implement of ScriptHandler to process foreach script
 */
public final class ForeachScriptHandler extends AbstractScriptHandler {

    private final String collectionName;
    private final String itemName;
    private final String indexName;
    private final String separator;

    public ForeachScriptHandler(String collectionName, String itemName, String indexName, String separator) {
        this.collectionName = collectionName;
        this.itemName = itemName;
        this.indexName = indexName;
        this.separator = separator;
    }

    @Override
    public StringBuilder process(ExecuteContext context) throws ScriptFailedException {
        Object collection = validate(context);
         if (collection instanceof Map) {
            return processMap(context, (Map) collection);
        } else if (collection instanceof Collection) {
            return processCollection(context, (Collection) collection);
        } else {
            throw new ScriptFailedException("The type of <" + collectionName + "> can not be used" + " in foreach script, expected Map or Collection!");
        }
    }

    private StringBuilder processMap(ExecuteContext context, Map target) throws ScriptFailedException {
        return doProcess(context, target.keySet().toArray());
    }

    private StringBuilder processCollection(ExecuteContext context, Collection target) throws ScriptFailedException {
        return doProcess(context, target.toArray());
    }

    private StringBuilder doProcess(ExecuteContext context, Object[] items) throws ScriptFailedException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            try {
                prepareParam(context, items[i], i);
                StringBuilder subBuilder = getChild().process(context);
                if (subBuilder == null) continue;

                if (i != 0 && separator != null && !separator.equals(""))
                    sb.append(separator);
                sb.append(subBuilder);
            } finally {
                clearParam(context);
            }
        }
        return sb;
    }

    private Object validate(ExecuteContext context) throws ScriptFailedException {
        if (getChild() == null) {
            throw new ScriptFailedException("No child ScriptHandler exist in execution tree!");
        }

        if (!context.getParams().containsParam(collectionName))
            throw new ScriptFailedException("The param <" + collectionName + "> can not be " + "founded!");

        Object collection;
        if ((collection = context.getParams().getParam(collectionName)) == null)
            throw new ScriptFailedException("The param <" + collectionName + "> must not be Null!");

        return collection;
    }

    private void prepareParam(ExecuteContext context, Object item, int index) {
        context.getParams().prepareTempParamsContext();
        context.getParams().addTempParam(itemName, item);
        Optional.ofNullable(indexName).ifPresent(x -> {
            context.getParams().addTempParam(x, index);
        });
    }

    private void clearParam(ExecuteContext context) {
        context.getParams().popTempParams();
    }

    @Override
    public String toString() {
        return "ForeachScriptHandler{" + "collectionName='" + collectionName + '\'' + ", itemName='" + itemName + '\'' + ", indexName='" + indexName + '\'' + ", separator='" + separator + '\'' + ", child=" + child + '}';
    }
}
