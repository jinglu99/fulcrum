package cn.justL.fulcrum.test.jdbc;

import cn.justl.fulcrum.contexts.ExecuteContext;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.exceptions.XmlParseException;
import cn.justl.fulcrum.jdbc.SqlExecutor;
import cn.justl.fulcrum.parsers.XMLSqlParser;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date : 2019-10-29
 * @Author : 汪京陆(Ben Wang)[jingl.wang123@gmail.com]
 * @Desc :
 */
public class SqlExecuterTest {

    public static String url = "jdbc:mysql://localhost:3306/dbk";
    public static String user = "root";
    public static String passwd = "9998877wjL$";

    @Test
    public void sqlExecuterTest() throws ClassNotFoundException, SQLException, XmlParseException, ScriptFailedException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection(url, user, passwd);

        XMLSqlParser parser = new XMLSqlParser(XMLSqlParser.class.getResourceAsStream("/dbkTestSql.xml"));
        SqlExecutor executor = new SqlExecutor(parser.parse(), conn);


        Map param = new HashMap();
//        param.put("price", 1);

        ExecuteContext context = new ExecuteContext();
        context.getParams().setParams(param);


        ResultSet resultSet = (ResultSet) executor.execute(context);
        System.out.println(resultSet);
    }
}
