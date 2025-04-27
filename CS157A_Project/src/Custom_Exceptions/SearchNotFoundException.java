package Custom_Exceptions;

public class SearchNotFoundException extends Exception {
    public SearchNotFoundException(String msg) {
        super(msg);
    }
}
