package cn.justL.fulcrum.test.jdbc.typehandler;

import cn.justL.fulcrum.test.databases.DBTest;
import cn.justl.fulcrum.data.ValueHolder;
import cn.justl.fulcrum.exceptions.TypeHandleException;
import cn.justl.fulcrum.exceptions.ScriptFailedException;
import cn.justl.fulcrum.jdbc.typehandler.AbstractTypeHandler;
import cn.justl.fulcrum.jdbc.typehandler.TypeHandler;
import java.lang.reflect.Field;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.collections.SetUtils;
import org.hsqldb.HsqlException;
import org.hsqldb.jdbc.JDBCConnection;
import org.hsqldb.jdbc.JDBCPreparedStatement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;

/**
 * @Date : 2019-11-06
 * @Author : Jinglu.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("Test for AbstractTypeHandler")
public class AbstractTypeHandlerTest extends TypeHandlerBaseTest {
    private TestPrepareStatement ps;
    private List<Object> params;

    @Test
    public void typeHandlerTestForOnlyValueExist() throws TypeHandleException, SQLException {
        TypeHandler handler = new IntHandler();
        ValueHolder holder = new ValueHolder("test", 1, null, null);
        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertNotNull(params.get(0));
        assertEquals(Integer.class, params.get(0).getClass());
        assertEquals(1, params.get(0));
    }

    @Test
    public void typeHandlerTestForStringValueAndIntTypeExist() throws TypeHandleException, SQLException {
        TypeHandler handler = new IntHandler();

        ValueHolder holder = new ValueHolder("test", "1", "int", null);
        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertNotNull(params.get(0));
        assertEquals(Integer.class, params.get(0).getClass());
        assertEquals(1, params.get(0));
    }

    @Test
    public void typeHandlerTestForNullValueAndNullType() {
        TypeHandler handler = new IntHandler();

        ValueHolder holder = new ValueHolder("test", null, null, null);
        assertFalse(handler.isMatch(holder));
    }

    @Test
    public void typeHandlerTestForNullValueAndType() {
        TypeHandler handler = new IntHandler();

        ValueHolder holder = new ValueHolder("test", null, "int", null);
        assertTrue(handler.isMatch(holder));

        assertThrows(TypeHandleException.class, () -> {
            handler.setParam(ps, 1, holder);
        });
    }

    @Test
    public void typeHandlerTestForNullValueAndTypeAndDefaultExp() throws TypeHandleException, SQLException {
        TypeHandler handler = new IntHandler();

        ValueHolder holder = new ValueHolder("test", null, "int", "1");
        assertTrue(handler.isMatch(holder));

        handler.setParam(ps, 1, holder);

        assertNotNull(params.get(0));
        assertEquals(Integer.class, params.get(0).getClass());
        assertEquals(1, params.get(0));
    }

    @Test
    public void typeHanderTestForUnsupportType() {
        TypeHandler handler = new IntHandler();

        ValueHolder holder = new ValueHolder("test", null, "string", null);
        assertFalse(handler.isMatch(holder));
    }


    @BeforeEach
    public void createConnAndPS() {
        ps = new TestPrepareStatement();
        params = ps.getParams();
    }

    @BeforeAll
    public static void newDBANDHandler() throws SQLException, IOException, ClassNotFoundException {
        createBlogDataSource();
    }


    private class IntHandler extends AbstractTypeHandler {
        @Override
        public void setNonNullParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws TypeHandleException {
            try {
                Object val = valueHolder.getVal();
                if (val instanceof Byte) {
                    ps.setByte(index, (Byte) val);
                } else if (val instanceof Short) {
                    ps.setShort(index, (Short) val);
                } else if (val instanceof Integer) {
                    ps.setInt(index, (Integer) val);
                } else if (val instanceof Long) {
                    ps.setLong(index, (Long) val);
                } else if (val instanceof Number) {
                    ps.setInt(index, ((Number) val).intValue());
                } else {
                    ps.setInt(index, Integer.parseInt(val.toString()));
                }
            } catch (Exception e) {
                throw new TypeHandleException("error", e);
            }
        }

        @Override
        public void setNullParam(PreparedStatement ps, int index, ValueHolder valueHolder) throws TypeHandleException {
            try {
                if (StringUtils.isNotBlank(valueHolder.getDefaultExp())) {
                    Integer val = Integer.valueOf(valueHolder.getDefaultExp());
                    ps.setInt(index, val);
                } else
                    throw new TypeHandleException(String.format("%s can't be null", valueHolder.getParamName()));
            } catch (SQLException e) {
                throw new TypeHandleException("error", e);
            }

        }

        @Override
        public Set<String> getSupportTypeNames() {
            return new HashSet<>(Arrays.asList("int"));
        }

        @Override
        public Set<Class> getSupportTypes() {
            return new HashSet<>(Arrays.asList(
                    byte.class, Byte.class,
                    short.class, Short.class,
                    int.class, Integer.class,
                    long.class, Long.class
                    ));
        }
    }
}
