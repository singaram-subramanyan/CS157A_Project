package Custom_Exceptions;

public class SearchNotFoundException extends Exception {
    //custom exception for when the search yielded no results
    public SearchNotFoundException(String msg) {
        super(msg);
    }
}
