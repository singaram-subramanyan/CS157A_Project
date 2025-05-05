package GUI;

import JDBC_Java.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class cartPage {

    private int custId;
    private DBMethods db;

    public cartPage(int custId) {
        this.custId = custId;
        this.db = new DBMethods();
    }

    public void start(Stage stage) {
        Button checkoutButton = new Button("Checkout");
        Button continueShoppingButton = new Button("Continue Shopping");
        Button logoutButton = new Button("Logout");

        VBox cartContainer = new VBox(20);
        cartContainer.setPadding(new Insets(10));

        List<cartObject> cartItems = db.cartContent(custId);  // Fetch cart items using DB method
        double totalCost = Math.round(db.cartTotal(custId) * 100.0) / 100.0;  // Fetch total cost of cart

        for (cartObject item : cartItems) {
            Label titleLabel = new Label(item.getTitle());
            Label quantityLabel = new Label("Quantity: " + item.getQuantity());
            Label priceLabel = new Label("$" + item.getPrice());

            HBox itemRow = new HBox(20, titleLabel, quantityLabel, priceLabel);
            itemRow.setPadding(new Insets(5));

            cartContainer.getChildren().add(itemRow);
        }

        Label totalLabel = new Label("Total: $" + totalCost);
        cartContainer.getChildren().add(totalLabel);

//        checkoutButton.setOnAction(e -> {
//            Alert checkoutAlert = new Alert(Alert.AlertType.INFORMATION);
//            checkoutAlert.setContentText("Proceeding to checkout...");
//            checkoutAlert.showAndWait();
//        });

        continueShoppingButton.setOnAction(e -> {
            BookstoreSearch searchPage = new BookstoreSearch(custId);
            searchPage.start(stage);
        });

        logoutButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage();
            loginPage.start(stage);
        });

        HBox buttonBox = new HBox(20, checkoutButton, continueShoppingButton, logoutButton);

        VBox layout = new VBox(30, cartContainer, totalLabel, buttonBox);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 900, 500);
        stage.setScene(scene);
        stage.setTitle("Shopping Cart");
        stage.show();
    }
}
