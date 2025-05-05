package Custom_Exceptions;

public class InvalidEntryException extends Exception{
    public InvalidEntryException(String msg) {
        super(msg);
    }
}
