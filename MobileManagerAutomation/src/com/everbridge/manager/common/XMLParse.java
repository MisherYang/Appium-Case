package com.everbridge.manager.common;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * @ClassName: XMLParse
 * @Description: Parse Config.xml
 */
public class XMLParse {
	//config.xml path is conf/config.xml, It's unmodified.
	private String xmlpath = System.getProperty("user.dir")+File.separator+"config.xml";
	
	/**
	 * parse a given xml file,return a element type
	 *
	 * @MethodName: xmlElement
	 * @Param: String filepath, String attrName
	 * @return: Element 
	 * @throws: 
	 */
	public static Element xmlElement(String filepath, String attrName){
		SAXReader reader = new SAXReader();
		Element element = null;
		try {
			Document document  = reader.read(new File(filepath));
			Element root = document.getRootElement();
			Iterator it_root = root.elementIterator();
			while (it_root.hasNext()){
				Element el = (Element)it_root.next();
				Iterator it_ele = el.elementIterator();
				 	while (it_ele.hasNext()){
				 		Element el3 = (Element)it_ele.next();
				 		String s = el3.getText();
				 		String el3Name = el3.getName();
				 		if (el3Name.equals(attrName)){
							element = el3;
						}
				 	}
				
				//undefined property name handle
				/*Iterator attrIt = element.attributeIterator();
				while (attrIt.hasNext()){
					Attribute a = (Attribute) attrIt.next();
					System.out.println(a.getValue());
				}*/

				
				//undefined element handole
				/*Iterator eleIt = element.elementIterator();
				while (eleIt.hasNext()){
					Element e = (Element) eleIt.next();
					System.out.println(e.getName()+":"+e.getText());
				}
				System.out.println()*/
			}
			document.clearContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return element;
	}

	/**
	 * Get config.xml attributeValue which the paoperty name is attrName
	 *
	 * @MethodName: getConfAttributeValue
	 * @Param: String attrName
	 * @return: String 
	 * @throws: 
	 */
	public String getConfAttributeValue(String attrName){
		Element element = xmlElement(xmlpath, attrName);
		String attrValue = null;
		if(!element.equals(null)){
			element.attribute(attrName);
			attrValue = element.attributeValue(attrName);
		}
		return attrValue;	
	}

	/**
	 * Get config.xml element text which the property name is attrName
	 *
	 * @MethodName: getConfElementText
	 * @Param: String attrName
	 * @return: String 
	 * @throws: 
	 */
	public String getConfElementText(String attrName) {
		Element element = xmlElement(xmlpath, attrName);
		String eleText = null;
		try{
			if(!element.equals("")||!element.equals(null)){
				eleText = element.getText();
			}
			else
				System.out.println("element is null");
		}catch(Exception e){
			e.printStackTrace();
		}
		return eleText;
	}
}
