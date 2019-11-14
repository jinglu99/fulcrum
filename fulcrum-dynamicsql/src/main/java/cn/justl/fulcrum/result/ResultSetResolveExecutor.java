package cn.justl.fulcrum.result;

import cn.justl.fulcrum.ExecuteContext;
import cn.justl.fulcrum.Executor;
import cn.justl.fulcrum.exceptions.DynamicSqlException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date : 2019/11/14
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 * An implementation of {@link Executor} to convert {@link java.sql.ResultSet}
 * to a list of Map.
 */
public class ResultSetResolveExecutor implements Executor {

    @Override
    public void execute(ExecuteContext context) throws DynamicSqlException {
        try {
            List list = new ArrayList();

            ResultSetMetaData md = context.getResultSet().getMetaData();

            int columnCount = md.getColumnCount(); //Map rowData;

            while (context.getResultSet().next()) { //rowData = new HashMap(columnCount);
                Map rowData = new HashMap();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), context.getResultSet().getObject(i));
                }
                list.add(rowData);
            }
            context.setQueryDatas(list);
        } catch (SQLException e) {
            throw new DynamicSqlException("failed to resolve ResultSet", e);
        }

    }
}
