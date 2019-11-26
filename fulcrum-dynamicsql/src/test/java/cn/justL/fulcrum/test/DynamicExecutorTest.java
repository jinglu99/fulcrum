package cn.justL.fulcrum.test;

import cn.justL.fulcrum.test.databases.DBTest;
import cn.justl.fulcrum.dynamicsql.DynamicSQLExecutor;
import cn.justl.fulcrum.dynamicsql.DynamicSQLParams;
import cn.justl.fulcrum.dynamicsql.DynamicSQLResult;
import cn.justl.fulcrum.dynamicsql.DynamicSQL;
import cn.justl.fulcrum.dynamicsql.exceptions.DynamicSqlException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date : 2019-11-09
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for DynamicSQLExecutor")
public class DynamicExecutorTest extends DBTest {
    public Connection conn;

    private static final String dynamicSqlPath = "/cn/justL/fulcrum/test/xmls/dynamicSql.xml";
    private static final String staticSqlPath = "/cn/justL/fulcrum/test/xmls/staticSql.xml";

    @BeforeAll
    public static void setup() throws SQLException, IOException, ClassNotFoundException {
        createBlogDataSource();
        createJPetstoreDataSource();
    }


    @BeforeEach
    public void createConn() throws SQLException, ClassNotFoundException {
        conn = createConnection();
    }

    @Test
    public void dynamicSqlExecutorSimpleTest()
        throws DynamicSqlException, SQLException, ClassNotFoundException {
        DynamicSQL dynamicSql = new DynamicSQL();
        dynamicSql.setSql(DynamicExecutorTest.class.getResourceAsStream(dynamicSqlPath));


        String goalSql1 = "select itemid, productid, listprice, unitcost, supplier, status, attr2 from item where 1 = 1 and itemid = 'EST-1'";
        DynamicSQLExecutor executor = new DynamicSQLExecutor(dynamicSql);
        List<Map> goalResult1 = executeSql(goalSql1);
        DynamicSQLParams params1 = new DynamicSQLParams();
        params1.setParams(new HashMap() {{
            put("itemId", "EST-1");
        }});
        params1.setConnection(conn);
        DynamicSQLResult rs1 = executor.execute(params1);
        assertNotNull(rs1);
        assertNotNull(rs1.getResult());
        assertTrue(compareResult(goalResult1, rs1.getResult()));


        String goalSql2 = "select itemid, productid, listprice, unitcost, supplier, status, attr2 from item where 1 = 1 and (1=2 or itemid = 'EST-1' or itemid = 'EST-2')";
        List<Map> goalResult2 = executeSql(goalSql1);
        DynamicSQLParams params2 = new DynamicSQLParams();
        params2.setParams(new HashMap() {{
            put("itemIds", Arrays.asList("EST-1","EST-2"));
        }});
        params2.setConnection(conn);
        DynamicSQLResult rs2 = executor.execute(params2);
        assertNotNull(rs2);
        assertNotNull(rs2.getResult());
        assertTrue(compareResult(goalResult2, rs2.getResult()));
    }


    @Test
    public void staticSqlTest() throws SQLException, ClassNotFoundException, DynamicSqlException {
        String targetSql = "select * from author where id = 101";
        List goalResult = executeSql(targetSql);

        DynamicSQL dynamicSql = new DynamicSQL();
        dynamicSql.setSql(DynamicExecutorTest.class.getResourceAsStream(staticSqlPath));
        DynamicSQLExecutor executor = new DynamicSQLExecutor(dynamicSql);


        DynamicSQLParams params = new DynamicSQLParams();
        params.setConnection(createConnection());
        DynamicSQLResult result = executor.execute(params);

        assertTrue(compareResult(goalResult, result.getResult()));

    }


}
