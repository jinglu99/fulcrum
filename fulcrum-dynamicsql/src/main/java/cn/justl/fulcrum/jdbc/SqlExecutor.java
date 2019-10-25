package cn.justl.fulcrum.jdbc;

import cn.justl.fulcrum.contexts.ExecuteContext;
import cn.justl.fulcrum.jdbc.typehandler.TypeHandler;
import cn.justl.fulcrum.jdbc.typehandler.TypeHandlerRegistry;
import cn.justl.fulcrum.parsers.exceptions.ScriptFailedException;
import cn.justl.fulcrum.parsers.handlers.ScriptHandler;
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

    private Connection connection;

    private final ScriptHandler scriptHandler;

    public SqlExecutor(ScriptHandler scriptHandler) {
        this.scriptHandler = scriptHandler;
    }

    public Object execute(ExecuteContext context) throws ScriptFailedException, SQLException {
        PreparedStatement ps = connection
            .prepareStatement(scriptHandler.process(context).toString());

    }

    public static void prepareParameters(PreparedStatement ps, ExecuteContext context) {
        List<Object> paramList = context.getSqlParamList();

        for (int i = 0; i < paramList.size(); i++) {
            TypeHandlerRegistry.getTypeHandler(paramList.get(i)).setParam(ps, i, paramList.get(i));
        }
    }
}
