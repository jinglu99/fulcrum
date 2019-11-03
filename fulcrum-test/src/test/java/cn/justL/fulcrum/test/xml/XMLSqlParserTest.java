//package cn.justL.fulcrum.test.xml;
//
//import cn.justl.fulcrum.contexts.ExecuteContext;
//import cn.justl.fulcrum.exceptions.ScriptFailedException;
//import cn.justl.fulcrum.exceptions.XmlParseException;
//import cn.justl.fulcrum.scripthandler.ScriptHandler;
//import cn.justl.fulcrum.parsers.XMLSqlParser;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Date : 2019/9/27
// * @Author : jingl.wang [jingl.wang123@gmail.com]
// * @Desc :
// */
//public class XMLSqlParserTest {
//
//    @Test
//    public void XMLSqlParserSimpleTest() throws  XmlParseException, ScriptFailedException {
//        Map param = new HashMap();
//        param.put("a", 1);
//        param.put("list", new ArrayList() {{
//            add(new HashMap() {{
//                put("a",2);
//            }});
//            add(new HashMap() {{
//                put("a","3");
//            }});
//        }});
//
//        ExecuteContext context = new ExecuteContext();
//        context.getParams().setParams(param);
//
//        XMLSqlParser parser = new XMLSqlParser(XMLSqlParser.class.getResourceAsStream("/sql.xml"));
//        ScriptHandler scriptHandler = parser.parse();
//
//        StringBuilder process = scriptHandler.process(context);
//        System.out.println(process.toString());
//        System.out.println(context.getSqlParamList());
//    }
//}
