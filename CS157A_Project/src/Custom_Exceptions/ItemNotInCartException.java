package Custom_Exceptions;

public class ItemNotInCartException extends Exception{
    public ItemNotInCartException(String msg) {
        super(msg);
}
}
