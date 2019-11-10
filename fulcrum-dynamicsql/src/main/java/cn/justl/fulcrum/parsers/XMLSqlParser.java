package cn.justl.fulcrum.parsers;

import cn.justl.fulcrum.exceptions.XmlParseException;
import cn.justl.fulcrum.scripthandler.*;
import cn.justl.fulcrum.scripthandler.handlers.ForeachScriptHandler;
import cn.justl.fulcrum.scripthandler.handlers.IfScriptHandler;
import cn.justl.fulcrum.scripthandler.handlers.ListableScriptHandler;
import cn.justl.fulcrum.scripthandler.handlers.TextScriptHandler;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;

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

    private ScriptHandler scriptHandler;

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
//            xPath.setNamespaceContext();
        } catch (ParserConfigurationException e) {
            throw new XmlParseException("xml parsing failed!", e);
        } catch (IOException e) {
            throw new XmlParseException("xml parsing failed!", e);
        } catch (SAXException e) {
            throw new XmlParseException("xml parsing failed!", e);
        }
    }

    public ScriptHandler parse() throws XmlParseException {
        try {
            Node sqlNode = (Node) xPath.evaluate("/*", doc, XPathConstants.NODE);

            if (sqlNode == null
                    || !StringUtils.equals(XMLParserConstants.NAMESPACE_URL, sqlNode.getNamespaceURI())
                    || !StringUtils.equals(XMLParserConstants.SQL_ELEMENT, sqlNode.getNodeName()))
                throw new XmlParseException("It's not a <SQL> root xml doc!");
            else
                scriptHandler = parse(sqlNode);
            return scriptHandler;
        } catch (XPathExpressionException e) {
            throw new XmlParseException("fail to parse Element <SQL>", e);
        }
    }

    private ScriptHandler parse(Node node) throws XmlParseException {
        NodeList nodeList = node.getChildNodes();
        ListableScriptHandler scriptHandler = new ListableScriptHandler();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node child = nodeList.item(i);
            if (child.getNodeType() == Node.TEXT_NODE && StringUtils.isNotBlank(child.getTextContent())) {
                scriptHandler.addNext(new TextScriptHandler(child.getTextContent()));
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                scriptHandler.addNext(parseElement(child));
            }
        }
        return scriptHandler;
    }

    private ScriptHandler parseElement(Node node) throws XmlParseException {
        if (XmlElements.isDefaultElement(node)) {
            return parseDefaultElement(node);
        } else {
            throw new XmlParseException("Element: <" + node.getNodeName() + "> can not be parsed");
        }
    }

    private ScriptHandler parseDefaultElement(Node node) throws XmlParseException {

        ScriptHandler handler = null;
        if ((handler = parseIfElementIfMatched(node)) != null) return handler;

        if ((handler = parseForeachElementIfMatched(node)) != null) return handler;

        throw new XmlParseException("Element: <" + node.getNodeName() + "> can not be parsed");
    }

    private ScriptHandler parseIfElementIfMatched(Node node) throws XmlParseException {
        if (!XmlElements.IF.isMatch(node)) return null;

        IfScriptHandler ifScriptHandler = new IfScriptHandler();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            if (item.getNodeName().toLowerCase().equals(XMLParserConstants.IF_CASE_ELEMENT)) {
                ifScriptHandler.addCase(item.getAttributes().getNamedItem(XMLParserConstants.IF_CASE_COND_ATTR).getTextContent(), parse(item));
            } else if (item.getNodeName().toLowerCase().equals(XMLParserConstants.IF_ELSE_ELEMENT)) {
                ifScriptHandler.addElse(parse(item));
            }
        }
        return ifScriptHandler;
    }

    private ScriptHandler parseForeachElementIfMatched(Node node) throws XmlParseException {
        if (!XmlElements.FOR.isMatch(node)) return null;

        Node collection = node.getAttributes().getNamedItem(XMLParserConstants.FOR_COLLECTION_ATTR);
        Node item = node.getAttributes().getNamedItem(XMLParserConstants.FOR_ITEM_ATTR);
        Node index = node.getAttributes().getNamedItem(XMLParserConstants.FOR_INDEX_ATTR);
        Node separator = node.getAttributes().getNamedItem(XMLParserConstants.FOR_SEPARATOR_ATTR);

        String collectionName = collection.getTextContent();
        String itemName = item.getTextContent();
        String indexName = index == null ? XMLParserConstants.DEFAULT_INDEX_NAME : index.getTextContent();
        String separator1 = separator == null ? XMLParserConstants.DEFAULT_SEPARATOR : separator.getTextContent();

        ForeachScriptHandler scriptHandler = new ForeachScriptHandler(collectionName, itemName, indexName, separator1);
        scriptHandler.setChild(parse(node));
        return scriptHandler;
    }
}
