package kirill.pimenov;

import kirill.pimenov.Exceptions.EmptyXmlException;
import kirill.pimenov.Exceptions.WrongXmlFormat;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class iisSoftTask {

    static final Logger log = Logger.getLogger(iisSoftTask.class);

    public static void main(String[] args) {
        log.info("Запуск программы!");
        DataBase dataBase = new DataBase();
        switch (args[0]) {
            case ("sync"): {
                try {
                    XmlSynchronizer synchronizer = new XmlSynchronizer(args[1]);
                    log.info("Данные выгружены из XML файла.");
                    synchronizer.parseNodeToMap();
                    log.info("Данные скопированы в HashMap.");
                    dataBase.connect();
                    log.info("Установлено соединение с базой данных.");
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
                DBtoXml dBtoXml = new DBtoXml(dataBase);
                log.info("Данные выгружены из базы данных.");
                dBtoXml.createDocument();
                log.info("Документ создан.");
                dBtoXml.saveToXml(args[1]);
                log.info("Данные сохранены в XML файл.");
                dataBase.close();
                log.info("Соединение закрыто");
                break;
            }
            default: {
                log.error("Введена не существующая команда.");
                System.out.println("Введена не существующая команда. Ознакомтесь с инструкцией в файле " +
                        "readme.txt");

            }
        }
    }


}
