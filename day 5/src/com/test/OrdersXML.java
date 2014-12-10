package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class OrdersXML {
	
	DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance ();
	DocumentBuilder domBuilder = null;
	TransformerFactory transformerFactory = TransformerFactory.newInstance ();
	
	public OrdersXML () {
		try {
			domBuilder = domFactory.newDocumentBuilder ();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public String readXML () {
		StringBuffer buffer = new StringBuffer ();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader ("/OrdersList.xml"));
			
			String line = "";
			while ((line = reader.readLine ()) != null) {
				buffer.append (line);
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close ();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return buffer.toString ();
	}
	
	public void writeXML (LinkedHashMap<String, ArrayList<String>> orders) {
		Document doc = domBuilder.newDocument ();
		
		Element rootEle = doc.createElement("orderList");
		
		Iterator <ArrayList<String>> iter = orders.values().iterator ();
		
		while (iter.hasNext ()) {
			ArrayList<String> order = iter.next ();
			
			Element orderEle = doc.createElement ("order");
			
			Element orderNumEle = doc.createElement ("ordernum");
			Element nameEle = doc.createElement ("name");
			Element itemEle = doc.createElement ("item");
			
			orderNumEle.setTextContent (order.get (0));
			nameEle.setTextContent (order.get (1));
			itemEle.setTextContent (order.get (2));
			
			orderEle.appendChild(orderNumEle);
			orderEle.appendChild (nameEle);
			orderEle.appendChild (itemEle);
			
			rootEle.appendChild (orderEle);
		}
		
		doc.appendChild (rootEle);
		
		DOMSource source = new DOMSource (doc);
		StreamResult results = new StreamResult(new File ("/OrdersList.xml"));
		
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer ();
			transformer.transform(source, results);
		}
		catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
