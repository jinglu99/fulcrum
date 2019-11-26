package cn.justL.fulcrum.test.parser;

import cn.justl.fulcrum.dynamicsql.exceptions.ScriptFailedException;
import cn.justl.fulcrum.dynamicsql.exceptions.DynamicSQLParseException;
import cn.justl.fulcrum.dynamicsql.parser.XMLParserConstants;
import cn.justl.fulcrum.dynamicsql.parser.XMLSqlParser;
import cn.justl.fulcrum.dynamicsql.script.ScriptHandler;

import java.io.IOException;

import cn.justl.fulcrum.dynamicsql.script.handlers.ForeachScriptHandler;
import cn.justl.fulcrum.dynamicsql.script.handlers.IfScriptHandler;
import cn.justl.fulcrum.dynamicsql.script.handlers.ListableScriptHandler;
import cn.justl.fulcrum.dynamicsql.script.handlers.TextScriptHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date : 2019/11/4
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@DisplayName("XMLSqlParser Test")
public class XMLSqlParserTest {

    private final static String simpleSqlPath = "/cn/justL/fulcrum/test/xmls/parsers/simpleSql.xml";
    private final static String dynamicSqlPath = "/cn/justL/fulcrum/test/xmls/parsers/dynamicSql.xml";
    private final static String unknownTagExistSqlPath = "/cn/justL/fulcrum/test/xmls/parsers/unknowTagExistSql.xml";
    private final static String wrongStructXmlPath = "/cn/justL/fulcrum/test/xmls/parsers/wrongStrutXml.xml";


    @Test
    public void simpleSqlTest() throws IOException , ScriptFailedException, DynamicSQLParseException {
        XMLSqlParser parser = new XMLSqlParser(XMLSqlParserTest.class.getResourceAsStream(simpleSqlPath));
        ScriptHandler handler = parser.parse();

        assertTrue(handler instanceof ListableScriptHandler);

        ListableScriptHandler listableScriptHandler = (ListableScriptHandler) handler;
        assertEquals(1, listableScriptHandler.getScripts().size());
        assertTrue(listableScriptHandler.getScripts().get(0) instanceof TextScriptHandler);
    }


    @Test
    public void dynamicSqlTest() throws DynamicSQLParseException {
        XMLSqlParser parser = new XMLSqlParser(XMLSqlParserTest.class.getResourceAsStream(dynamicSqlPath));
        ListableScriptHandler handler = (ListableScriptHandler) parser.parse();

        assertEquals(3, handler.getScripts().size());
        assertTrue(handler.getScripts().get(0) instanceof TextScriptHandler);
        assertTrue(handler.getScripts().get(1) instanceof IfScriptHandler);
        assertTrue(handler.getScripts().get(2) instanceof IfScriptHandler);

        IfScriptHandler ifScriptHandler1 = (IfScriptHandler) handler.getScripts().get(1);
        assertEquals(2, ifScriptHandler1.getCases().size());
        assertTrue(ifScriptHandler1.getCases().get(0) instanceof IfScriptHandler.Case);
        assertEquals("a != nil", ifScriptHandler1.getCases().get(0).getCond());
        assertTrue(ifScriptHandler1.getCases().get(1) instanceof IfScriptHandler.Else);
        assertNull(ifScriptHandler1.getCases().get(1).getCond());


        IfScriptHandler ifScriptHandler2 = (IfScriptHandler) handler.getScripts().get(2);
        assertEquals(1, ifScriptHandler2.getCases().size());
        assertEquals("items != nil", ifScriptHandler2.getCases().get(0).getCond());
        assertTrue(ifScriptHandler2.getCases().get(0).getChild() instanceof ListableScriptHandler);

        ListableScriptHandler caseHandler = (ListableScriptHandler) ifScriptHandler2.getCases().get(0).getChild();
        assertEquals(3, caseHandler.getScripts().size());
        assertTrue(caseHandler.getScripts().get(0) instanceof TextScriptHandler);
        assertTrue(caseHandler.getScripts().get(1) instanceof ForeachScriptHandler);

        ForeachScriptHandler foreachScriptHandler = (ForeachScriptHandler) caseHandler.getScripts().get(1);
        assertEquals("items", foreachScriptHandler.getCollectionName());
        assertEquals("item", foreachScriptHandler.getItemName());
        assertEquals(XMLParserConstants.DEFAULT_INDEX_NAME, foreachScriptHandler.getIndexName());
        assertEquals(XMLParserConstants.DEFAULT_SEPARATOR, foreachScriptHandler.getSeparator());
        assertTrue(foreachScriptHandler.getChild() instanceof ListableScriptHandler);

        ListableScriptHandler foreachListableHandler = (ListableScriptHandler) foreachScriptHandler.getChild();
        assertEquals(1, foreachListableHandler.getScripts().size());
        assertTrue(foreachListableHandler.getScripts().get(0) instanceof TextScriptHandler);
    }

    @Test
    public void unknownTagExistsTest() throws DynamicSQLParseException {
        XMLSqlParser parser = new XMLSqlParser(XMLSqlParserTest.class.getResourceAsStream(unknownTagExistSqlPath));
        assertThrows(DynamicSQLParseException.class, ()->{parser.parse();});
    }


    @Test
    public void wrongStructureXmlTest() throws DynamicSQLParseException {
        assertThrows(DynamicSQLParseException.class, ()->{
            XMLSqlParser parser = new XMLSqlParser(XMLSqlParserTest.class.getResourceAsStream(wrongStructXmlPath));
            parser.parse();
        });
    }
}
