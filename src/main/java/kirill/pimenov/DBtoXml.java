package kirill.pimenov;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;

public class DBtoXml {
    private DataBase dataBase;
    private HashMap<CodeJobKey, String> departments;
    private Document doc;

    DBtoXml(DataBase dataBase) {
        this.dataBase = dataBase;
        departments = dataBase.pullAll();
    }

    void createDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
            Element rootElement =
                    doc.createElement("departments");
            doc.appendChild(rootElement);
            for (CodeJobKey key : departments.keySet()) {
                rootElement.appendChild(getDepartment(key.getCode(), key.getJob(), departments.get(key)));
            }
        } catch (Exception e) {
            iisSoftTask.log.error(e);
        }
    }

    Node getDepartment(String code, String job, String description) {
        Element department = doc.createElement("department");
        department.setAttribute("code", code);
        department.setAttribute("job", job);
        department.setAttribute("description", description);
        return department;
    }

    void saveToXml(String fileXml) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StreamResult file = new StreamResult(new File(fileXml));

            transformer.transform(source, file);
        } catch (TransformerException e) {
            iisSoftTask.log.error(e);
        }
    }
}
