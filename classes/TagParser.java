package classes;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class TagParser{
	public static String parse(String filePath) throws Exception {	   
	   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	   factory.setNamespaceAware(false); // never forget this!
	   DocumentBuilder builder = factory.newDocumentBuilder();
	   String parsedText = null;
	
	   Document doc = builder.parse(filePath);
	
	   XPathFactory xPathFactory = XPathFactory.newInstance();
	   XPath xpath = xPathFactory.newXPath();
	
	   XPathExpression xPathExpression = xpath.compile("//TEXT/text()");
	
	   Object result = xPathExpression.evaluate(doc, XPathConstants.NODESET);		  
	
	   NodeList nodes = (NodeList) result;
	
	   System.out.println(nodes.getLength());
	   for (int i = 0; i < nodes.getLength(); i++) {
	       parsedText = nodes.item(i).getNodeValue(); 
	   }
	   return parsedText;
	}
}