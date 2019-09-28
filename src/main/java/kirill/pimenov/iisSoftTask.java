package kirill.pimenov;

import kirill.pimenov.Exceptions.EmptyXmlException;
import kirill.pimenov.Exceptions.WrongXmlFormat;
import org.apache.log4j.Logger;
import org.w3c.dom.*;

import java.util.Arrays;
import java.util.HashMap;

public class iisSoftTask {

    private static final Logger log = Logger.getLogger(iisSoftTask.class);

    public static void main(String[] args) {
        log.info("Запуск программы!");
        switch (args[0]) {
            case ("sync"): {
                try {
                    Document document;
                    document = XmlParser.parse(args[1]);
                    log.info("Данные выгружены из XML файла.");
                    NodeList departmentsList = document.getElementsByTagName("department");
                    HashMap<CodeJobKey, String> departmentsMap = parseNodeToMap(departmentsList);
                    log.info("Данные скопированы в HashMap.");
                    System.out.println(Arrays.asList(departmentsMap));
                } catch (EmptyXmlException | WrongXmlFormat e) {
                    log.error("Ошибка неверного формата.", e);
                    System.out.println(e);
                }
                break;
            }
            case ("pull"): {
                break;
            }
            default: {
                log.error("Введена не существующая команда.");
                System.out.println("Введена не существующая команда. Ознакомтесь с инструкцией в файле " +
                        "readme.txt");

            }
        }
    }

    private static HashMap<CodeJobKey, String> parseNodeToMap(NodeList departmentsList) throws WrongXmlFormat {
        HashMap<CodeJobKey, String> departmentsMap = new HashMap<>();
        for (int temp = 0, lenght = departmentsList.getLength(); temp < lenght; temp++) {
            try {
                Node department = departmentsList.item(temp);
                NamedNodeMap attributes = department.getAttributes();
                String code = attributes.getNamedItem("code").getNodeValue();
                String job = attributes.getNamedItem("job").getNodeValue();
                String description = attributes.getNamedItem("description").getNodeValue();
                departmentsMap.put(new CodeJobKey(code, job), description);
            } catch (NullPointerException e) {
                throw new WrongXmlFormat();
            }

        }
        return departmentsMap;
    }


}
