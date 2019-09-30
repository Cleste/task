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
                    XmlSynchronizer parser = new XmlSynchronizer(args[1]);
                    log.info("Данные выгружены из XML файла.");
                    parser.parseNodeToMap();
                    log.info("Данные скопированы в HashMap.");
                    dataBase.connect();
                    log.info("Установлено соединение с базой данных.");
                    ArrayList<CodeJobKey> keys = dataBase.pullKeys();
                    parser.sync(dataBase, keys);
                    log.info("Данные синхронизированы.");
                    dataBase.close();
                    log.info("Соединение закрытою");
                } catch (EmptyXmlException | WrongXmlFormat e) {
                    log.error(e);
                    System.out.println(e);
                }
                break;
            }
            case ("pull"): {
                dataBase.connect();
                log.info("Установлено соединение с базой данных.");
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
