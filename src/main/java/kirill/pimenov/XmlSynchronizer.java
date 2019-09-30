package kirill.pimenov;

import kirill.pimenov.Exceptions.EmptyXmlException;
import kirill.pimenov.Exceptions.WrongXmlFormat;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

class XmlSynchronizer {
    private Document document;
    private HashMap<CodeJobKey, String> departmentsMap = new HashMap<>();

    public XmlSynchronizer(String file) throws EmptyXmlException {
        File xmlFile;
        try {
            xmlFile = new File(file);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
        } catch (Exception e) {
            iisSoftTask.log.error(e);
            System.out.println(e);
        }
        if (document == null) throw new EmptyXmlException();
    }
    void parseNodeToMap() throws WrongXmlFormat {
        NodeList departmentsList = document.getElementsByTagName("department");
        for (int temp = 0, length = departmentsList.getLength(); temp < length; temp++) {
            try {
                Node department = departmentsList.item(temp);
                NamedNodeMap attributes = department.getAttributes();
                String code = attributes.getNamedItem("code").getNodeValue();
                String job = attributes.getNamedItem("job").getNodeValue();
                CodeJobKey key = new CodeJobKey(code, job);
                String description = attributes.getNamedItem("description").getNodeValue() != null?
                        attributes.getNamedItem("description").getNodeValue():"";
                if (departmentsMap.get(key) != null) throw new WrongXmlFormat();
                departmentsMap.put(key, description);
            } catch (NullPointerException e) {
                throw new WrongXmlFormat();
            }

        }
    }
    void sync(DataBase dataBase, ArrayList<CodeJobKey> keys){
        for (CodeJobKey key: departmentsMap.keySet()){
            if(keys.contains(key)){
                dataBase.update(key, departmentsMap.get(key));
                keys.remove(key);
            } else
                dataBase.insert(key, departmentsMap.get(key));
        }
        if(!keys.isEmpty()) {
            for (CodeJobKey key: keys){
                dataBase.delete(key);
            }
        }
    }
}
