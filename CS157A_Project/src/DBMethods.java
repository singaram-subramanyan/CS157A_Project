import Custom_Exceptions.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DBMethods {
    public Connection connect() {

        String url = "jdbc:sqlite:/Users/singaramsubramanyan/Documents/GitHub/CS157A_Project/CS157A_Project/src/bookstoreDB.db"; // Change the path to your database file
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }



    public void register(String fName, String lName, String email, int phoneNum, String password) throws CustomerExistsException {
        int id = ((fName.hashCode() + lName.hashCode()) & 0xfffffff);
        String existsQuery = String.format("SELECT CASE WHEN EXISTS (SELECT * FROM Customer WHERE id = %d) THEN 'Found' ELSE 'Not Found' END AS custExists;",id);
        String insertQuery = String.format("INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (%d, '%s', '%s', %d, '%s', '%s');",id,fName,lName,phoneNum,email, BCrypt.hashpw(password, BCrypt.gensalt()));

        try (Statement statement = connect().createStatement()){
            ResultSet result_exists = statement.executeQuery(existsQuery);

            if(Objects.equals(result_exists.getString("custExists"), "Found")){
                throw new CustomerExistsException("Customer already has account. Please login using email and password.");
            }
            else{
                statement.execute(insertQuery);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void validateLogin(String email, String password) throws InvalidLoginException {
        String loginQuery = String.format("SELECT account_password FROM Customer WHERE email_id = '%s';",email);

        try (Statement statement = connect().createStatement()){
            ResultSet result_exists = statement.executeQuery(loginQuery);

            if(!BCrypt.checkpw(password, result_exists.getString("account_password"))){
                throw new InvalidLoginException("Invalid Login info. Please check credentials or create account if new user.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void deleteFromCart(int bookID, int quantity, int custID) throws ItemNotInCartException {
        String itemCartQuery = String.format("SELECT book_id, quantity FROM Cart WHERE book_id = %d AND customer_id = %d;", bookID, custID);
        String itemQuantityUpdate = String.format("UPDATE Cart SET quantity = quantity - %d WHERE Cart.book_id = %d AND Cart.customer_id = %d;", quantity, bookID, custID);
        String itemDelete = String.format("DELETE FROM Cart WHERE Cart.book_id = %d AND Cart.customer_id = %d;", bookID, custID);

        try (Statement statement = connect().createStatement()) {
            ResultSet itemInCart = statement.executeQuery(itemCartQuery);

            if (!itemInCart.next()) {
                throw new ItemNotInCartException("Item not in Cart.");
            } else {
                int currentQuantity = itemInCart.getInt("quantity");

                if (currentQuantity > quantity) {
                    statement.executeUpdate(itemQuantityUpdate);
                } else if (currentQuantity == quantity) {
                    statement.executeUpdate(itemDelete);
                } else {
                    throw new ItemNotInCartException("Not enough quantity in cart to delete.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addToCart (int bookID, int quantity, int custID) throws ItemNotInStockException{
        String stockCheck = String.format("SELECT id, Title, stock FROM Books WHERE id = %d;", bookID);
        String itemCartQuery = String.format("SELECT book_id, quantity, Title, stock FROM Cart LEFT JOIN Books ON Cart.book_id = Books.id WHERE book_id = %d AND customer_id = %d;", bookID, custID);
        String itemQuantityUpdate = String.format("UPDATE Cart SET quantity = quantity + %d WHERE Cart.book_id = %d AND Cart.customer_id = %d;", quantity, bookID, custID);
        String itemAdd = String.format("INSERT INTO Cart (customer_id, book_id, quantity) VALUES (%d, %d, %d);",custID,bookID,quantity);

        try (Statement statement = connect().createStatement()) {
            ResultSet DBStockCheck = statement.executeQuery(stockCheck);
            int currStock = DBStockCheck.getInt("stock"); //change to new query to get
            System.out.println(DBStockCheck.getInt("stock"));
            if(currStock==0){
                throw new ItemNotInStockException(String.format("%s is currently not in stock, but more is on the way! \n Please remove item to place order.",DBStockCheck.getString("Title")));
            }
            else if(currStock < quantity){
                throw new ItemNotInStockException(String.format("%s available stock: %d \n Please reduce quantity to place order",DBStockCheck.getString("Title"),currStock));
            }

            ResultSet itemInCart = statement.executeQuery(itemCartQuery);
            if (itemInCart.next()) {
                statement.executeUpdate(itemQuantityUpdate);
            } else {
                statement.executeUpdate(itemAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        } catch (ItemNotInStockException e) {
            throw new RuntimeException(e);
        }
    }

    public float cartTotal(int custID) {
        String cartTotalQuery = String.format("""
                SELECT Cart.customer_id AS customer_id, SUM(Books.Price * Cart.quantity) AS Total 
                FROM Cart 
                LEFT JOIN Books ON Cart.book_id = Books.id  
                WHERE Cart.customer_id = %d  
                GROUP BY Cart.customer_id;""", custID);
        try (Statement statement = connect().createStatement()) {
            ResultSet total = statement.executeQuery(cartTotalQuery);

            if (total.next()) {
                return total.getFloat("Total");
            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return 0;
    }

    public void placeOrder(int custID) throws ItemNotInStockException {
        String itemsInOrder = String.format("SELECT book_id, quantity, stock, Title FROM Cart LEFT JOIN Books ON Cart.book_id = Books.id WHERE customer_id = %d;", custID);
        String customerInfoQuery = String.format("SELECT first_name, last_name, email_id FROM Customer WHERE id = %d;", custID);

        try (Statement statement = connect().createStatement()) {
            ResultSet customerInfo = statement.executeQuery(customerInfoQuery);
            int orderID = ((customerInfo.getString("first_name").hashCode() + customerInfo.getString("last_name").hashCode() + customerInfo.getString("email_id").hashCode() + LocalDateTime.now().hashCode()) & 0xfffffff);
            ResultSet itemsInCart = statement.executeQuery(itemsInOrder);
            while(itemsInCart.next()){
                int currStock = itemsInCart.getInt("stock");
                int purchaseQuantity = itemsInCart.getInt("quantity");
                if(currStock==0){
                    throw new ItemNotInStockException(String.format("%s is currently not in stock, but more is on the way! \n Please remove item to place order.",itemsInCart.getString("Title")));
                }
                else if(currStock < purchaseQuantity){
                    throw new ItemNotInStockException(String.format("%s available stock: %d \n Please reduce quantity to place order",itemsInCart.getString("Title"),currStock));
                }
            }
            ResultSet itemsInCart_1 = statement.executeQuery(itemsInOrder);
            while(itemsInCart_1.next()) {
                String itemOrder = String.format("INSERT INTO Orders (id, customer_id, book_id, quantity, order_date) VALUES (%d, %d, %d,%d, '%s');",orderID,custID,itemsInCart_1.getInt("book_id"), itemsInCart_1.getInt("quantity"), LocalDate.now());
                String itemDelete = String.format("DELETE FROM Cart WHERE Cart.book_id = %d AND Cart.customer_id = %d;", itemsInCart_1.getInt("book_id"), custID);
                String itemQuantityUpdate = String.format("UPDATE Books SET stock = stock - %d WHERE Books.id = %d;", itemsInCart_1.getInt("quantity"), itemsInCart_1.getInt("book_id"));
                statement.executeUpdate(itemOrder);
                statement.executeUpdate(itemDelete);
                statement.executeUpdate(itemQuantityUpdate);
            }

//            ResultSetMetaData meta = itemsInCart.getMetaData();
//            int columnCount = meta.getColumnCount();
//
//            // Print all column names
//            System.out.println("Columns in ResultSet:");
//            for (int i = 1; i <= columnCount; i++) {
//                System.out.println(i + ": " + meta.getColumnName(i));
//            }
//
//            // Print each row
//            System.out.println("\nResultSet data:");
//            while (itemsInCart.next()) {
//                for (int i = 1; i <= columnCount; i++) {
//                    System.out.print(meta.getColumnName(i) + ": " + itemsInCart.getString(i) + " | ");
//                }
//                System.out.println();
//            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

    }

    public void cartContent(int custID) {
        String itemsInCartQuery = String.format("""
        SELECT Books.Title, quantity
        FROM Cart LEFT JOIN Books ON Cart.book_id = Books.id
        WHERE Cart.customer_id = %d;
        """, custID);



        try (Statement statement = connect().createStatement()) {
            ResultSet cart = statement.executeQuery(itemsInCartQuery);
            while(cart.next()){
                System.out.println("Title: " + cart.getString("title"));
                System.out.println("Quantity: " + cart.getInt("quantity"));;
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }


    }

//    public void cartContent(int custID) {
//        String itemsInCartQuery = String.format("""
//        SELECT *
//        FROM Customer
//    """, custID);
//
//        try (Statement statement = connect().createStatement();
//             ResultSet cart = statement.executeQuery(itemsInCartQuery)) {
//
//            // Get metadata
//            ResultSetMetaData meta = cart.getMetaData();
//            int columnCount = meta.getColumnCount();
//
//            // Print all column names
//            System.out.println("Columns in ResultSet:");
//            for (int i = 1; i <= columnCount; i++) {
//                System.out.println(i + ": " + meta.getColumnName(i));
//            }
//
//            // Print each row
//            System.out.println("\nResultSet data:");
//            while (cart.next()) {
//                for (int i = 1; i <= columnCount; i++) {
//                    System.out.print(meta.getColumnName(i) + ": " + cart.getString(i) + " | ");
//                }
//                System.out.println();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace(System.err);
//        }
//    }

//    public void DBUpdate() { //Method to only update table content - no application function
//        String itemsInOrder = String.format("SELECT id, account_password FROM Customer WHERE id=1699963747;");
//
//
//        try (Statement statement = connect().createStatement()) {
//            ResultSet itemsInCart = statement.executeQuery(itemsInOrder);
//            while (itemsInCart.next()) {
//                String pass = BCrypt.hashpw(itemsInCart.getString("account_password"), BCrypt.gensalt());
//                System.out.println(pass);
//                int id = itemsInCart.getInt("id");
//                System.out.println(id);
//                String itemOrder = String.format("UPDATE Customer SET account_password = '%s' WHERE id = %d;", pass, id);
//                statement.executeUpdate(itemOrder);
//            }
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public List<Book> search(String param) throws SearchNotFoundException {
        String searchQuery = "SELECT Title, Author, Genre, Price, CASE WHEN stock > 0 THEN 'In Stock' ELSE 'Out of Stock' END AS Availability FROM Books;";
        List<Book> books = new LinkedList<>();

        try (PreparedStatement pstmt = connect().prepareStatement(searchQuery)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("Title");
                    String author = rs.getString("Author");
                    String genre = rs.getString("Genre");
                    float price = rs.getFloat("Price");
                    String availability = rs.getString("Availability");

                    if(title.toLowerCase().contains(param.toLowerCase()) || author.contains(param.toLowerCase()) || genre.contains(param.toLowerCase())){
                        books.add(new Book(title,author,genre,price, availability));
                    }

                }
            }

            if (books.isEmpty()) {
                throw new SearchNotFoundException("No results found, please search for something else.");
            }

            return books;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCustID(String email) {
        String searchQuery = String.format("SELECT id FROM Customer WHERE email_id='%s", email);
        int id = 0;
        try (Statement statement = connect().createStatement()) {
            ResultSet custID = statement.executeQuery(searchQuery);
            id = custID.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return id;
    }
    }

