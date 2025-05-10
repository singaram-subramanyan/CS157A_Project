package Custom_Exceptions;

public class InvalidLoginException extends Exception{
    //custom exception for when the login credentials are incorrect
    public InvalidLoginException(String msg) {
        super(msg);
    }
}
