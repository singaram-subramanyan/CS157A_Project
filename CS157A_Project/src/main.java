import Custom_Exceptions.CustomerExistsException;
import Custom_Exceptions.InvalidLoginException;
import Custom_Exceptions.ItemNotInCartException;
import Custom_Exceptions.SearchNotFoundException;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class main {
    public static void main(String[] args) throws CustomerExistsException, ClassNotFoundException, InvalidLoginException, SQLException, ItemNotInCartException, SearchNotFoundException {

        DBMethods bookstore = new DBMethods();
        //bookstore.register("Luke", "Danes", "luke.danes@gmail.com", 1098126676, "luke'sdiner@01");
        //bookstore.validateLogin("kevin.pearson@gmail.com", "therealkevinpearson");
        //bookstore.addToCart(1,1,2370152);
        //bookstore.cartContent(2370152);
        //bookstore.deleteFromCart(10,1,2370152);
        //System.out.println(bookstore.cartTotal(2370152));
        //bookstore.placeOrder(2370152);
        //bookstore.cartContent(2370152);

        List<Book> s = bookstore.search("Kill");

        System.out.println(s);

    }
}
