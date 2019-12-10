package cn.justl.fulcrum.core.db;


import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Date : 2019-11-04
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DBTest {
    public static final String db_schema = "../db/tables.sql";



    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName ("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:mem:fulcrum");
    }


    public static void runScript(Connection connection, String resource) throws IOException, SQLException {
        ScriptRunner runner = new ScriptRunner(connection);
        runner.setAutoCommit(true);
        runner.setStopOnError(false);
        runner.setLogWriter(null);
        runner.setErrorLogWriter(null);
        runScript(runner, resource);
    }

    public static void runScript(ScriptRunner runner, String resource) throws IOException, SQLException {

        try (Reader reader = new FileReader(new File(resource))) {
            runner.runScript(reader);
        }
    }

    public static void initDB() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = createConnection();
        runScript(connection, db_schema);
    }

    public static void prepareData(String path) throws SQLException, ClassNotFoundException {
        ScriptRunner runner = new ScriptRunner(createConnection());
        runner.setAutoCommit(true);
        runner.setStopOnError(false);
        runner.setLogWriter(null);
        runner.setErrorLogWriter(null);
        runner.runScript(new InputStreamReader(DBTest.class.getResourceAsStream(path)));
    }

    public static List<Map> executeSql(String sql) throws SQLException, ClassNotFoundException {
        Connection conn = createConnection();

        Statement statement = conn.createStatement();
        statement.execute(sql);
        ResultSet resultSet = statement.getResultSet();
        return convertList(resultSet);
    }

    public static boolean compareResult(List<Map> list1, List<Map> list2) {

        if (list1 == null && list2 != null) return false;
        if (list1 != null && list2 == null) return false;
        if (list1 == null && list2 == null) return true;
        if (list1.size() != list1.size()) return false;

        for (int i = 0; i < list1.size(); i++) {
            Map item1 = list1.get(i);
            Map item2 = list2.get(i);

            if (item1 == null && item2 != null) return false;
            if (item1 != null && item2 == null) return false;
            if (item1 == null && item2 == null) continue;

            if (item1.keySet().size() != item2.keySet().size()) return false;

            if (!item1.keySet().containsAll(item2.keySet())) return false;
            for (Object key : item1.keySet()) {
                if (!(item1.get(key) instanceof Number) || !(item1.get(key) instanceof String)) continue;
                if (item1.get(key) == null && item2.get(key) != null) return false;
                if (item1.get(key) != null && item2.get(key) == null) return false;
                if (item1.get(key) == null && item2.get(key) == null) continue;
                if (!item1.get(key).equals(item2.get(key))) return false;
            }
        }
        return true;
    }

    protected static List<Map> convertList(ResultSet rs) throws SQLException {

        List list = new ArrayList();

        ResultSetMetaData md = rs.getMetaData();

        int columnCount = md.getColumnCount(); //Map rowData;

        while (rs.next()) { //rowData = new HashMap(columnCount);

            Map rowData = new HashMap();

            for (int i = 1; i <= columnCount; i++) {

                rowData.put(md.getColumnName(i), rs.getObject(i));

            }

            list.add(rowData);

        }
        return list;
    }

}
