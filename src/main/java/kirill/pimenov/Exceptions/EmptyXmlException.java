package kirill.pimenov.Exceptions;

public class EmptyXmlException extends Throwable {
    @Override
    public String toString() {
        return "Передан пустой XML файл";
    }
}
