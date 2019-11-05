package cn.justl.fulcrum.jdbc;

import cn.justl.fulcrum.exceptions.SQLExecuteException;
import cn.justl.fulcrum.scripthandler.ScriptResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Date : 2019-11-05
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class Connections {
    private static final Logger logger = LoggerFactory.getLogger(Connections.class);

    public static FulcrumStatement prepareStatement(Connection connection, ScriptResult sr) throws SQLExecuteException {
        try {
            if (connection == null && connection.isClosed()) {
                logger.error("JDBC connection is not available!");
                throw new SQLException("JDBC connection is not available!");
            }
            return new FulcrumStatement(connection, sr);
        } catch (SQLExecuteException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Something wrong when prepare statement!", e);
            throw new SQLExecuteException("Fail to prepare statement", e);
        }
    }
}
