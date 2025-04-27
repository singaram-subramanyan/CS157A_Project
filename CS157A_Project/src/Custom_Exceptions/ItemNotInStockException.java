package Custom_Exceptions;

public class ItemNotInStockException extends Exception{
    public ItemNotInStockException(String msg) {
        super(msg);
    }
}
