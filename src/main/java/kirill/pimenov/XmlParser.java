package kirill.pimenov;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlParser {
    static Document parse(String fileName) {
        Document document = null;
        File xmlFile;
        try {
            xmlFile = new File(fileName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
        } catch (Exception e) {
            System.out.println(e);
        }
        return document;
    }
}
