package com.djzhao.test;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlTest {

	public static void main(String[] args) {
		try {
			String xmlPath = "C:\\toolsZ\\test.xml";
			File xmlFile = new File(xmlPath);
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("book");
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				System.out.println("Node name: " + node.getNodeName());
				Element ele = (Element) node;
				System.out.println("----------------------------");
				if (node.getNodeType() == Element.ELEMENT_NODE) {
					System.out.println("book category: " + ele.getAttribute("category"));
					System.out.println("title name: " + ele.getElementsByTagName("title").item(0).getTextContent());
					System.out.println("author name: " + ele.getElementsByTagName("author").item(0).getTextContent());
					System.out.println("year :" + ele.getElementsByTagName("year").item(0).getTextContent());
					System.out.println("price : " + ele.getElementsByTagName("price").item(0).getTextContent());
					System.out.println("-------------------------");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}