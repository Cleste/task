package kirill.pimenov;

import org.apache.log4j.Logger;
import org.w3c.dom.*;
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
                document = XmlParser.parse(args[1]);
                NodeList departments = document.getElementsByTagName("department");
                break;
            }
            default: {
                log.error("Введена не существующая команда.");
                System.out.println("Введена не существующая команда. Введите одну из двух команд" +
                        ": sync xml_name или pull xml_name");

            }
        }
    }


}
