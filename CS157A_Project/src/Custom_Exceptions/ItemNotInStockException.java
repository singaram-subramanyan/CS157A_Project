package Custom_Exceptions;

public class ItemNotInStockException extends Exception{
    //custom exception for when a customer is trying to add to cart or place an order for an item not in stock
    public ItemNotInStockException(String msg) {
        super(msg);
    }
}
