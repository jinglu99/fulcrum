//package cn.justl.fulcrum.jdbc;
//
//import cn.justl.fulcrum.exceptions.StatementExecuteException;
//import cn.justl.fulcrum.script.BoundSql;
//import java.util.List;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
///**
// * @Date : 2019-11-05
// * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
// * @Desc :
// */
//public class Connections {
//    private static final Logger logger = LoggerFactory.getLogger(Connections.class);
//
//    public static FulcrumStatement prepareStatement(Connection connection, String sql, List) throws StatementExecuteException {
//        try {
//            if (connection == null && connection.isClosed()) {
//                logger.error("JDBC connection is not available!");
//                throw new SQLException("JDBC connection is not available!");
//            }
//            return new FulcrumStatement(connection, sr);
//        } catch (StatementExecuteException e) {
//            throw e;
//        } catch (Exception e) {
//            logger.error("Something wrong when prepare statement!", e);
//            throw new StatementExecuteException("Fail to prepare statement", e);
//        }
//    }
//}
