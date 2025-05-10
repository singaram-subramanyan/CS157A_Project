package Custom_Exceptions;

public class ItemNotInCartException extends Exception{
    //custom exception for when an attempt to update an item not in cart is made
    public ItemNotInCartException(String msg) {
        super(msg);
}
}
