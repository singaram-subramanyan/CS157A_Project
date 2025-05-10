package JDBC_Java;

import Custom_Exceptions.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DBMethods {
    //class to create connection with SQLite Database
    public Connection connect() {
        //the url/file path of the database, change the part after the 'sqlite:' to the absolute file path of the BookstoreDatabase.db file
        String url = "jdbc:sqlite:/Users/singaramsubramanyan/Documents/GitHub/CS157A_Project/CS157A_Project/src/Database/BookstoreDatabase.db";
        Connection conn = null;
        //try and catch to make connection with database and return the connection or throw an exception if connection unsuccessful
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    //method for registering new customer info and adding to database
    public void register(String fName, String lName, String email, String phoneNum, String password) throws CustomerExistsException {
        //creates unique id using first and last name and bit shifts so that id isn't negative
        int id = ((fName.hashCode() + lName.hashCode()) & 0xfffffff);
        //query to check if the customer exists already
        String existsQuery = String.format("SELECT CASE WHEN EXISTS (SELECT * FROM Customer WHERE email = '%s') THEN 'Found' ELSE 'Not Found' END AS custExists;",email);
        //query to insert new customer info, including encrypted password, into database
        String insertQuery = String.format("INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (%d, '%s', '%s', '%s', '%s', '%s');",id,fName,lName,phoneNum,email, BCrypt.hashpw(password, BCrypt.gensalt()));

        try (Statement statement = connect().createStatement()){
            //checks if customer already exists by executing check query and throws exception if it does, otherwise adds customer info to database
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

    //method to validate login
    public void validateLogin(String email, String password) throws InvalidLoginException {
        //query to check obtain stored user password
        String loginQuery = String.format("SELECT account_password FROM Customer WHERE email_id = '%s';",email);

        //executes query and checks if the encrypted password in the database matches the entered password and throws exception if it doesn't
        try (Statement statement = connect().createStatement()){
            ResultSet result_exists = statement.executeQuery(loginQuery);

            if(!BCrypt.checkpw(password, result_exists.getString("account_password"))){
                throw new InvalidLoginException("Invalid password. Please check credentials.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //method to delete an item from cart
    public void deleteFromCart(int bookID, int quantity, int custID) throws ItemNotInCartException {
        //query to obtain particular item from customer's cart
        String itemCartQuery = String.format("SELECT book_id, quantity FROM Cart WHERE book_id = %d AND customer_id = %d;", bookID, custID);
        //query to decrease the quantity of an item
        String itemQuantityUpdate = String.format("UPDATE Cart SET quantity = quantity - %d WHERE Cart.book_id = %d AND Cart.customer_id = %d;", quantity, bookID, custID);
        //query to delete the item from cart
        String itemDelete = String.format("DELETE FROM Cart WHERE Cart.book_id = %d AND Cart.customer_id = %d;", bookID, custID);

        try (Statement statement = connect().createStatement()) {
            ResultSet itemInCart = statement.executeQuery(itemCartQuery);

            //checks if the item exists and throws exception if it doesn't
            if (!itemInCart.next()) {
                throw new ItemNotInCartException("Item not in Cart.");
            } else {
                //gets current quantity in cart
                int currentQuantity = itemInCart.getInt("quantity");

                //updates the quantity if quantity to be deleted is less than current quantity, deletes the item if both are equal and throws exception if quantity to be deleted exceeds current quantity
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

    //method to add item/increase quantity of item
    public void addToCart (int bookID, int quantity, int custID) throws ItemNotInStockException{
        //query to obtain the current available stock of an item
        String stockCheck = String.format("SELECT id, Title, stock FROM Books WHERE id = %d;", bookID);
        //query to check/obtain item and corresponding quantity if items exists in customer's cart
        String itemCartQuery = String.format("SELECT book_id, quantity, Title, stock FROM Cart LEFT JOIN Books ON Cart.book_id = Books.id WHERE book_id = %d AND customer_id = %d;", bookID, custID);
        //query to increase quantity of an item in the customer's cart
        String itemQuantityUpdate = String.format("UPDATE Cart SET quantity = quantity + %d WHERE Cart.book_id = %d AND Cart.customer_id = %d;", quantity, bookID, custID);
        //query to add item to add item to customer's cart in the cart table
        String itemAdd = String.format("INSERT INTO Cart (customer_id, book_id, quantity) VALUES (%d, %d, %d);",custID,bookID,quantity);

        try (Statement statement = connect().createStatement()) {
            //executes stock check query and stores the current stock of item
            ResultSet DBStockCheck = statement.executeQuery(stockCheck);
            int currStock = DBStockCheck.getInt("stock");
            //throws exception if item is not in stock or customer is trying to add more than the available stock
            if(currStock==0){
                throw new ItemNotInStockException(String.format("%s is currently not in stock, but more is on the way! \n Please remove item to place order.",DBStockCheck.getString("Title")));
            }
            else if(currStock < quantity){
                throw new ItemNotInStockException(String.format("%s available stock: %d \n Please reduce quantity to place order",DBStockCheck.getString("Title"),currStock));
            }

            //executes query to obtain customer's cart and if the item exists already the quantity is increase otherwise the item is added to the customer's cart in the cart table
            ResultSet itemInCart = statement.executeQuery(itemCartQuery);
            if (itemInCart.next()) {
                statement.executeUpdate(itemQuantityUpdate);
            } else {
                statement.executeUpdate(itemAdd);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //method to obtain the cart total
    public float cartTotal(int custID) {
        //query to obtain the cart total of a particular customer
        String cartTotalQuery = String.format("SELECT Cart.customer_id AS customer_id, SUM(Books.Price * Cart.quantity) AS Total FROM Cart LEFT JOIN Books ON Cart.book_id = Books.id WHERE Cart.customer_id = %d GROUP BY Cart.customer_id;", custID);
        try (Statement statement = connect().createStatement()) {
            ResultSet total = statement.executeQuery(cartTotalQuery);

            //if the cart has items in it the total is returned, otherwise 0 is returned
            if (total.next()) {
                return total.getFloat("Total");
            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return 0;
    }

    //method to place order for items in customer's cart
    public void placeOrder(int custID) throws ItemNotInStockException {
        //query to obtain the items in customer's cart
        String itemsInOrder = String.format("SELECT book_id, quantity, stock, Title FROM Cart LEFT JOIN Books ON Cart.book_id = Books.id WHERE customer_id = %d;", custID);
        //query to obtain customer information
        String customerInfoQuery = String.format("SELECT first_name, last_name, email_id FROM Customer WHERE id = %d;", custID);

        try (Statement statement = connect().createStatement()) {
            //executes query to obtain customer information, and uses it and the current date and time to create a unique non-negative order id through hashing
            ResultSet customerInfo = statement.executeQuery(customerInfoQuery);
            int orderID = ((customerInfo.getString("first_name").hashCode() + customerInfo.getString("last_name").hashCode() + customerInfo.getString("email_id").hashCode() + LocalDateTime.now().hashCode()) & 0xfffffff);
            //obtains the cart items by executing query and initializes 2D list to store book id and quantity
            ResultSet itemsInCart = statement.executeQuery(itemsInOrder);
            List<List<Integer>> cartList = new LinkedList<>();
            //iterates through the cart items and adds the book id and quantity to a list but throws an exception if the item is not in stock or quantity needed exceeds available stock
            while(itemsInCart.next()){
                System.out.println(1);
                List<Integer> itemList = new LinkedList<>();
                itemList.add(itemsInCart.getInt("book_id"));
                itemList.add(itemsInCart.getInt("quantity"));
                cartList.add(itemList);
                int currStock = itemsInCart.getInt("stock");
                int purchaseQuantity = itemsInCart.getInt("quantity");
                if(currStock==0){
                    throw new ItemNotInStockException(String.format("%s is currently not in stock, but more is on the way! \n Please remove item to place order.",itemsInCart.getString("Title")));
                }
                else if(currStock < purchaseQuantity){
                    throw new ItemNotInStockException(String.format("%s available stock: %d \n Please reduce quantity to place order",itemsInCart.getString("Title"),currStock));
                }
            }

            //iterates through the list created above and adds the item to the order table, deletes the item from the cart and updates the available quantity of the item
            for(List<Integer> item: cartList) {
                System.out.println(2);
                String itemOrder = String.format("INSERT INTO Orders (id, customer_id, book_id, quantity, order_date) VALUES (%d, %d, %d,%d, '%s');",orderID,custID,item.get(0), item.get(1), LocalDate.now());
                String itemDelete = String.format("DELETE FROM Cart WHERE Cart.book_id = %d AND Cart.customer_id = %d;", item.get(0), custID);
                String itemQuantityUpdate = String.format("UPDATE Books SET stock = stock - %d WHERE Books.id = %d;", item.get(1), item.get(0));
                statement.executeUpdate(itemOrder);
                statement.executeUpdate(itemDelete);
                statement.executeUpdate(itemQuantityUpdate);
            }


        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

    }

    //method to obtain items in customer's cart
    public List<cartObject> cartContent(int custID) {
        //query to obtain all the items in a customer's cart
        String itemsInCartQuery = String.format("SELECT book_id, Title, quantity, quantity*Price AS Price FROM Cart LEFT JOIN Books ON Cart.book_id = Books.id WHERE Cart.customer_id = %d;", custID);
        List<cartObject> cart_list = new LinkedList<>();

        try (Statement statement = connect().createStatement()) {
            //executes query and iterates through the items in the cart, extracts the information, creates a new cart object containing this formation and adds it to a list
            ResultSet cart = statement.executeQuery(itemsInCartQuery);
            while(cart.next()){
                String title = cart.getString("Title");
                int quantity = cart.getInt("quantity");
                float price = cart.getFloat("Price");
                int id = cart.getInt("book_id");
                cart_list.add((new cartObject(title,quantity,price,id)));
            }
            //the list created above containing all the books is returned
            return cart_list;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return cart_list;
    }

    //method to search books table for a particular book
    public List<Book> search(String param) throws SearchNotFoundException {
        //query to obtain the books in the database and the corresponding availability status
        String searchQuery = "SELECT id, Title, Author, Genre, Price, CASE WHEN stock > 0 THEN 'In Stock' ELSE 'Out of Stock' END AS Availability FROM Books;";
        List<Book> books = new LinkedList<>();


            try (Statement statement = connect().createStatement()) {

                //executes the query, iterates through the items in the books table, and extracts the information
                ResultSet rs = statement.executeQuery(searchQuery);
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("Title");
                    String author = rs.getString("Author");
                    String genre = rs.getString("Genre");
                    float price = rs.getFloat("Price");
                    String availability = rs.getString("Availability");

                    //checks if the current book's title, author or genre matches the search criteria and creates a book object and adds it to a list if it's met
                    if(title.toLowerCase().contains(param.toLowerCase()) || author.toLowerCase().contains(param.toLowerCase()) || genre.toLowerCase().contains(param.toLowerCase())){
                        books.add(new Book(id,title,author,genre,price, availability));
                    }

                }

            //if no such books were found and exception is thrown highlighting this
            if (books.isEmpty()) {
                throw new SearchNotFoundException("No results found, please search for something else.");
            }

            return books;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //method to return all books in the database
    public List<Book> returnAll() {
        //query to obtain all the books in the database
        String searchQuery = "SELECT * FROM Books;";
        List<Book> books = new LinkedList<>();

        try (Statement statement = connect().createStatement()) {
            //executes query, iterates through books, and extracts book information
            ResultSet searchResult = statement.executeQuery(searchQuery);
                while (searchResult.next()) {
                    int id = searchResult.getInt("id");
                    String title = searchResult.getString("Title");
                    String author = searchResult.getString("Author");
                    String genre = searchResult.getString("Genre");
                    float price = searchResult.getFloat("Price");
                    //gets availability information by checking if the stock is greater than 0
                    String availability = "";
                    if(searchResult.getInt("stock") > 0){
                        availability = "In Stock";
                    }
                    else{
                        availability = "Out of Stock";
                    }
                    //creates a new book object with the extracted information and adds it to a list
                    books.add(new Book(id,title,author,genre,price, availability));
                }
            //the list created above is returned
            return books;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //method to obtain customer id using email
    public int getCustID(String email) {
        //query to obtain customer's id using email
        String searchQuery = String.format("SELECT id FROM Customer WHERE email_id='%s'", email);
        //initializes id variable
        int id = 0;
        try (Statement statement = connect().createStatement()) {
            //extracts id information and saves it to the id variable
            ResultSet custID = statement.executeQuery(searchQuery);
            id = custID.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        //the id variable is returned
        return id;
    }
    }

