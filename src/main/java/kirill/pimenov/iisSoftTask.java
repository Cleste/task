package kirill.pimenov;

import kirill.pimenov.Exceptions.EmptyXmlException;
import kirill.pimenov.Exceptions.WrongXmlFormat;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * The main class. It performs one of two functions:
 * synchronizing an XML file with a database or uploading a database to an XML file.
 */
public class iisSoftTask {
    /**
     * Logging variable.
     */
    static final Logger log = Logger.getLogger(iisSoftTask.class);

    public static void main(String[] args) {
        log.info("Запуск программы!");
        /*
          Database instance
         */
        DataBase dataBase = new DataBase();
        switch (args[0]) {
            case ("sync"): {
                try {
                    dataBase.connect();
                    log.info("Установлено соединение с базой данных.");
                    XmlSynchronizer synchronizer = new XmlSynchronizer(args[1]);
                    log.info("Данные выгружены из XML файла.");
                    synchronizer.parseNodeToMap();
                    /*
                      An array containing keys from the database.
                     */
                    ArrayList<CodeJobKey> keys = dataBase.pullKeys();
                    synchronizer.sync(dataBase, keys);
                    log.info("Данные синхронизированы.");
                    dataBase.close();
                    log.info("Соединение закрыто");
                } catch (EmptyXmlException | WrongXmlFormat e) {
                    log.error(e);
                }
                break;
            }
            case ("pull"): {
                dataBase.connect();
                log.info("Установлено соединение с базой данных.");
                /*
                  Class instance created to unload data from a database to an XML file.
                 */
                DataBaseToXML DBtoXML = new DataBaseToXML(dataBase);
                log.info("Данные выгружены из базы данных.");
                DBtoXML.createDocument();
                DBtoXML.saveToXml(args[1]);
                log.info("Данные сохранены в XML файл.");
                dataBase.close();
                log.info("Соединение закрыто.");
                break;
            }
            default: {
                log.error("Введена не существующая команда.");
                System.out.println("Введена не существующая команда. Ознакомтесь с инструкцией в файле " +
                        "readme.txt");

            }

        }
        log.info("Завершение программы.");
    }


}
