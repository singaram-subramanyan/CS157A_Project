package Custom_Exceptions;

public class CustomerExistsException extends Exception{
    public CustomerExistsException(String msg) {
        super(msg);
    }

}
