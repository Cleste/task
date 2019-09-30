package kirill.pimenov.Exceptions;

public class EmptyDBException extends Throwable {
    @Override
    public String toString() {
        return "Подключение пустой или не соответствующей формату базе данных. Ознокомтесь с инструкцие в файле readme";
    }
}
