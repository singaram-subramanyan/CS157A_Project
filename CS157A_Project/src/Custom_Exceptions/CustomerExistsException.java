package Custom_Exceptions;

public class CustomerExistsException extends Exception{
    //custom exception for when the customer already exists during registration
    public CustomerExistsException(String msg) {
        super(msg);
    }

}
