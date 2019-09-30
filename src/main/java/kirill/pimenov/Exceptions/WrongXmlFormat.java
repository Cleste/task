package kirill.pimenov.Exceptions;

public class WrongXmlFormat extends Throwable{
    @Override
    public String toString() {
        return "Передан XML файл неверного формата. Ознокомтесь с инструкцие в файле readme";
    }
}
