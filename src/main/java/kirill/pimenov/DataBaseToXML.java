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

/**
 * Unloads data from a database and stores it in XML file.
 */
class DataBaseToXML {
    /*
    * Data base connection, departments Map and document are used to
    * unload all data from data base to XML file.
    *
     */
    private DataBase dataBase;
    private HashMap<CodeJobKey, String> departments;
    private Document document;

    /**
     * Creates an object that unloads data from a database.
     * @param dataBase connection
     */
    DataBaseToXML(DataBase dataBase) {
        this.dataBase = dataBase;
        departments = dataBase.pullAll();
    }

    /**
     * Creat new Document by departments HashMap
     */
    void createDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.newDocument();
            Element rootElement =
                    document.createElement("departments");
            document.appendChild(rootElement);
            for (CodeJobKey key : departments.keySet()) {
                rootElement.appendChild(getDepartment(key.getCode(), key.getJob(), departments.get(key)));
            }
        } catch (Exception e) {
            iisSoftTask.log.error(e);
        }
    }

    /**
     * Creates new department Node by code, job and description.
     * @param code of department
     * @param job of department
     * @param description of department
     * @return department Node
     */
    private Node getDepartment(String code, String job, String description) {
        Element department = document.createElement("department");
        department.setAttribute("code", code);
        department.setAttribute("job", job);
        department.setAttribute("description", description);
        return department;
    }

    /**
     * Upload all data to XML file
     * @param fileXml name of XML file
     */
    void saveToXml(String fileXml) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);

            StreamResult file = new StreamResult(new File(fileXml));

            transformer.transform(source, file);
        } catch (TransformerException e) {
            iisSoftTask.log.error(e);
        }
    }
}
