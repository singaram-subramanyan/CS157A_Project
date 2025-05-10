package Custom_Exceptions;

public class InvalidEntryException extends Exception{
    //custom exception for when the inputted item is invalid
    public InvalidEntryException(String msg) {
        super(msg);
    }
}
