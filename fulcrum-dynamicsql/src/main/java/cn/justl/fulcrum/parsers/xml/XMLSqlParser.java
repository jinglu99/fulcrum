package cn.justl.fulcrum.parsers.xml;

import cn.justl.fulcrum.parsers.exceptions.XmlParseException;
import cn.justl.fulcrum.parsers.objs.enums.XmlElements;
import cn.justl.fulcrum.parsers.utils.JavaCodeUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.collections.MapUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2019/9/27
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class XMLSqlParser {
    //xml parse required
    private DocumentBuilderFactory factory = null;
    private DocumentBuilder builder = null;
    private Document doc = null;
    private XPath xPath = null;

    private StringBuilder codeBuilder = JavaCodeUtils.start();
    private List<Object> paramList = new ArrayList<>();

    private int foreachListCn = 0;


    public XMLSqlParser(InputStream inputStream) throws XmlParseException {
        try {
            //Build DOM
            factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // never forget this!
            builder = factory.newDocumentBuilder();
            doc = builder.parse(inputStream);

            //Create XPath
            XPathFactory xpathfactory = XPathFactory.newInstance();
            xPath = xpathfactory.newXPath();
        } catch (ParserConfigurationException e) {
            throw new XmlParseException("xml parsing failed!", e);
        } catch (IOException e) {
            throw new XmlParseException("xml parsing failed!", e);
        } catch (SAXException e) {
            throw new XmlParseException("xml parsing failed!", e);
        }
    }

    public StringBuilder parse() throws XmlParseException {
        try {
            Node sqlNode = (Node) xPath.evaluate("//Sql", doc, XPathConstants.NODE);
            parse(sqlNode);
            return null;
        } catch (XPathExpressionException e) {
            throw new XmlParseException("Element <Sql> not found", e);
        }
    }

    private void parse(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node child = nodeList.item(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                JavaCodeUtils.append(codeBuilder, resolveText(child.getTextContent()));
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                parse(child);
            }
        }
    }

    private void parseElement(Node node) throws XmlParseException {
        if (XmlElements.isDefaultElement(node)) {
            parseDefaultElement(node);
        } else {
            throw new XmlParseException("Element: <" + node.getNodeName() + "> can not be parsed");
        }
    }

    private void parseDefaultElement(Node node) {

    }

    private void parseIfElementIfMatched(Node node) {
        if (!XmlElements.IF.isMatch(node)) return;

        boolean needElse = false;
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            if (item.getNodeName().toLowerCase().equals("case")) {
                String cond = item.getAttributes().getNamedItem("cond").getTextContent();
                JavaCodeUtils.If(codeBuilder, resolveCondExp(cond), needElse);
                parse(item);
                JavaCodeUtils.EndIf(codeBuilder);
                needElse = true;
            } else if (item.getNodeName().toLowerCase().equals("else")) {
                JavaCodeUtils.Else(codeBuilder);
                parse(item);
                JavaCodeUtils.EndElse(codeBuilder);
            }
        }
    }

    private void parseForeachElementIfMatched(Node node) {
        if (!XmlElements.FOREACH.isMatch(node)) return;

//        String list = node.getAttributes().getNamedItem("list").getTextContent();
//        String map = node.getAttributes().getNamedItem("map").getTextContent();
//        String item = node.getAttributes().getNamedItem("item").getTextContent();
//        String separator = node.getAttributes().getNamedItem("separator").getTextContent();
//
////        codeBuilder.append("for(int index = 0; index < collection.size(); i++")
//        Object col = null;
//        List<StringBuilder>
//        for (Object i : ((Map)col).entrySet()) {
//            codeBuilder.append("")
//        }

    }

    public StringBuilder resolveParam(String param) {
        return new StringBuilder(param);
    }

    public StringBuilder resolveText(String text) {
        return new StringBuilder(text);
    }

    public StringBuilder resolveCondExp(String cond) {
        return new StringBuilder(cond);
    }


}
