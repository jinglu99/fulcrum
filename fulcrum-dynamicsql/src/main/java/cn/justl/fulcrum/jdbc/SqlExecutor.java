package cn.justl.fulcrum.jdbc;

import cn.justl.fulcrum.contexts.ExecuteContext;
import cn.justl.fulcrum.contexts.ScriptContext;
import cn.justl.fulcrum.contexts.ValueHolder;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.jdbc.typehandler.TypeHandlerResolver;
import cn.justl.fulcrum.scripthandler.ScriptHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Date : 2019/10/25
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class SqlExecutor {

    private final Connection connection;

    private final ScriptHandler scriptHandler;

    public SqlExecutor(ScriptHandler scriptHandler, Connection connection) {
        this.scriptHandler = scriptHandler;
        this.connection = connection;
    }

    public Object execute(ScriptContext context) throws ScriptFailedException, SQLException {
        String sql = scriptHandler.process(context).toString();
        PreparedStatement ps = connection
            .prepareStatement(sql);

        System.out.println(sql);
        System.out.println(context.getSqlParamList());

        prepareParameters(ps, context);

        ps.execute();

        return ps.getResultSet();
    }

    public static void prepareParameters(PreparedStatement ps, ScriptContext context) throws ScriptFailedException, SQLException {
        List<ValueHolder> paramList = context.getSqlParamList();

        for (int i = 0; i < paramList.size(); i++) {
            ValueHolder holder = paramList.get(i);
            TypeHandlerResolver.resolveTypeHandler(holder).setParam(ps, i + 1, holder);
        }
    }
}
