package Custom_Exceptions;

public class InvalidLoginException extends Exception{
    public InvalidLoginException(String msg) {
        super(msg);
    }
}
