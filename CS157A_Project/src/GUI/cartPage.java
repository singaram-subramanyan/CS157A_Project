package GUI;

import Custom_Exceptions.ItemNotInCartException;
import JDBC_Java.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class cartPage {

    private int custId;
    private DBMethods booksDB;

    //constructor for cartPage class
    public cartPage(int custId) {
        this.custId = custId;
        this.booksDB = new DBMethods();
    }

    public void start(Stage stage) {
        //initializing checkout, continue shopping and logout buttons
        Button checkoutButton = new Button("Checkout");
        Button continueShoppingButton = new Button("Continue Shopping");
        Button logoutButton = new Button("Logout");

        //initializes the vbox and adds padding
        VBox cartContainer = new VBox(20);
        cartContainer.setPadding(new Insets(10));

        //retrieves items in customers cart by calling on cartContent method from the DBMethods class
        List<cartObject> cartItems = booksDB.cartContent(custId);
        //retrieves the cart total by calling on cartTotal method from the DBMethods class and rounds the value
        double totalCost = Math.round(booksDB.cartTotal(custId) * 100.0) / 100.0;

        //Each cart object in the list is iterated through, displayed and a delete button is also displayed
        for (cartObject item : cartItems) {
            Label titleLabel = new Label(item.getTitle());
            Label quantityLabel = new Label("Quantity: " + item.getQuantity());
            Label priceLabel = new Label("$" + item.getPrice());

            Button deleteButton = new Button("Delete");
            //when the delete button is clicked a popup asks for the quantity to be deleted and calls on the deleteFromCart method in the DBMethods file to complete the action
            deleteButton.setOnAction(event -> {
                TextInputDialog quantityDialog = new TextInputDialog();
                quantityDialog.setTitle("Delete Cart");
                quantityDialog.setHeaderText("Enter quantity for: " + item.getTitle());
                quantityDialog.setContentText("Quantity:");

                quantityDialog.showAndWait().ifPresent(input -> {
                    try {
                        int quantity = Integer.parseInt(input);
                        if (quantity <= 0 || quantity > item.getQuantity()) {
                            throw new NumberFormatException();
                        }
                        booksDB.deleteFromCart(item.getId(), quantity, custId);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setContentText("Item Deleted from Cart");
                        successAlert.showAndWait();
                    } catch (NumberFormatException ex) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("Please enter a valid quantity.");
                        errorAlert.showAndWait();
                    } catch (ItemNotInCartException ignored) {

                    }
                });
                start(stage);
            });
            //an hbox with padding is created to display all the book attributes in a row
            HBox itemRow = new HBox(20, titleLabel, quantityLabel, priceLabel,deleteButton);
            itemRow.setPadding(new Insets(5));

            cartContainer.getChildren().add(itemRow);
        }

        //cart total is displayed
        Label totalLabel = new Label("Total: $" + totalCost);
        cartContainer.getChildren().add(totalLabel);

        //actions for checkout, continue shopping and logout buttons to go to respective pages are set
        checkoutButton.setOnAction(e -> {
            checkoutPage checkoutPage = new checkoutPage(custId);
            checkoutPage.start(stage);
        });

        continueShoppingButton.setOnAction(e -> {
            searchPage searchPage = new searchPage(custId);
            searchPage.start(stage);
        });

        logoutButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage();
            loginPage.start(stage);
        });

        //hbox with the buttons is created
        HBox buttonBox = new HBox(20, checkoutButton, continueShoppingButton, logoutButton);

        //Layout and scene are created
        VBox layout = new VBox(30, cartContainer, totalLabel, buttonBox);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 900, 500);
        stage.setScene(scene);
        stage.setTitle("Shopping Cart");
        stage.show();
    }
}
