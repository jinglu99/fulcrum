package cn.justl.fulcrum.script;

import cn.justl.fulcrum.ExecuteContext;
import cn.justl.fulcrum.Executor;
import cn.justl.fulcrum.exceptions.DynamicSQLParseException;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.parser.XMLSqlParser;

import java.io.InputStream;

/**
 * @Date : 2019-11-09
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 * A ScriptRunner is the executor of dynamic sql script, the dynamic sql will be parsed to a
 * group of {@link ScriptHandler} in it's constructor.
 */
public final class ScriptExecutor implements Executor {

    private XMLSqlParser parser;
    private ScriptHandler handler;

    public void parse(InputStream dynamicSql) throws DynamicSQLParseException {
        parser = new XMLSqlParser(dynamicSql);
        handler = parser.parse();
    }

    /**
     * To execute the dynamic sql script with the parameters in {@link ExecuteContext#getDynamicSQLParams()},
     * the result will be stored in {@link ExecuteContext#boundSql} as {@link BoundSql}
     *
     * @param context
     */
    public void execute(ExecuteContext context) throws ScriptFailedException {
        ScriptContext scriptContext = new ScriptContext();
        scriptContext.setParams(context.getParams());

        BoundSql boundSql = handler.process(scriptContext);

        context.setSql(boundSql.getSql().toString());
        context.setValueHolders(boundSql.getValueHolders());
    }
}
