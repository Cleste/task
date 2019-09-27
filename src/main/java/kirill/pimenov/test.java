package kirill.pimenov;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class test {

    private static final Logger log = Logger.getLogger(test.class);

    public static void main(String[] args) {
        log.info("Запуск программы!");
        switch (args[0]) {
            case ("sync"): {
                Document document;
                document = pullXml(args[1]);
                NodeList name = document.getElementsByTagName("firstname");
                System.out.println(name);
                break;
            }
            default: {
                log.error("Введена не существующая команда не верная комманда.");
                System.out.println("Введена не существующая команда. Введите одну из двух команд" +
                        ": sync xml_name или pull xml_name");

            }
        }
    }

    private static Document pullXml(String fileName) {
        Document document = null;
        File xmlFile;
        try {
            xmlFile = new File(fileName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(xmlFile);
        } catch (Exception e) {
            log.error("Ошибка", e);
        }
        return document;
    }
}
