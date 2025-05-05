package GUI;
import Custom_Exceptions.CustomerExistsException;
import Custom_Exceptions.InvalidEntryException;
import Custom_Exceptions.ItemNotInStockException;
import JDBC_Java.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class checkoutPage {
    private int custId;
    private DBMethods booksDB;

    public checkoutPage(int custId) {
        this.custId = custId;
        this.booksDB = new DBMethods();
    }

    public void start(Stage stage) {
        stage.setTitle("Checkout");

        Label welcomeLabel = new Label("Please fill out the form to place order.");
        welcomeLabel.setStyle("-fx-font-size: 15px;");

        Label fNameLabel = new Label("First Name:");
        TextField fName = new TextField();
        fName.setPromptText("Enter your First Name");

        Label lNameLabel = new Label("Last Name:");
        TextField lName = new TextField();
        lName.setPromptText("Enter your Last Name");

        Label addressLabel = new Label("Shipping Address:");
        TextField userAddress = new TextField();
        userAddress.setPromptText("Enter the Shipping Address");

        Label cardNumlabel = new Label("Card Number:");
        TextField cardNum = new TextField();
        cardNum.setPromptText("Enter your Card Number");

        Label cVVLabel = new Label("CVV:");
        PasswordField cVV = new PasswordField();
        cVV.setPromptText("Enter Card CVV");

        Label expLabel = new Label("Exp Date:");
        TextField expDate = new TextField();
        expDate.setPromptText("Enter card expiration date");


        Button orderButton = new Button("Place Order");
        Button cartButton = new Button("Return to Cart");

        HBox buttonBox = new HBox(15);
        buttonBox.getChildren().addAll(cartButton, orderButton);

        VBox formLayout = new VBox(10);
        formLayout.getChildren().addAll(
                welcomeLabel, fNameLabel, fName, lNameLabel, lName, addressLabel, userAddress,
                cardNumlabel, cardNum, cVVLabel, cVV,expLabel,expDate,
                buttonBox
        );
        formLayout.setPadding(new Insets(30));

        HBox outerLayout = new HBox();
        outerLayout.getChildren().add(formLayout);

        VBox root = new VBox(outerLayout);
        root.setPadding(new Insets(20));


        Scene scene = new Scene(root, 320, 600);
        stage.setScene(scene);
        stage.show();

        orderButton.setOnAction(e -> {


            try {
                if(cardNum.getText().length() != 16){
                    throw new InvalidEntryException("Please Enter a valid Card Number");
                }
                if(cVV.getText().length() != 3){
                    throw new InvalidEntryException("Please Enter a valid CVV");
                }
                if(expDate.getText().length() != 5){
                    throw new InvalidEntryException("Please Enter a valid Expiration date in format MM/YY");
                }
                booksDB.placeOrder(custId);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setContentText("Order placed successfully and will be shipped in 5-7 business days!");
                successAlert.showAndWait();
                searchPage searchPage = new searchPage(custId);
                searchPage.start(stage);
            } catch (ItemNotInStockException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText(ex.getMessage());
                errorAlert.showAndWait();
                cartPage cartPage = new cartPage(custId);
                cartPage.start(stage);
            } catch (InvalidEntryException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText(ex.getMessage());
                errorAlert.showAndWait();
            }
        });

        cartButton.setOnAction(e -> {
            cartPage cartPage = new cartPage(custId);
            cartPage.start(stage);
        });
    }
}
