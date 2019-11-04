package cn.justL.fulcrum.test.databases;

import cn.justL.fulcrum.test.jdbc.ScriptRunner;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Date : 2019-11-04
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class DBTest {

    public static final String BLOG_DDL = "cn/justL/fulcrum/test/databases/blog/blog-derby-schema.sql";
    public static final String BLOG_DATA = "cn/justL/fulcrum/test/databases/blog/blog-derby-dataload.sql";

    public static final String JPETSTORE_DDL = "cn/justL/fulcrum/test/databases/jpetstore/jpetstore-hsqldb-schema.sql";
    public static final String JPETSTORE_DATA = "cn/justL/fulcrum/test/databases/jpetstore/jpetstore-hsqldb-dataload.sql";

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        return DriverManager.getConnection("jdbc:hsqldb:mem:db;sql.syntax_mys=true","sa","");
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
        try (Reader reader = new InputStreamReader(DBTest.class.getClassLoader().getResourceAsStream(resource))) {
            runner.runScript(reader);
        }
    }

    public static Connection createBlogDataSource() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        runScript(connection, BLOG_DDL);
        runScript(connection, BLOG_DATA);
        return connection;
    }

    public static Connection createJPetstoreDataSource() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        runScript(connection, JPETSTORE_DDL);
        runScript(connection, JPETSTORE_DATA);
        return connection;
    }

}
