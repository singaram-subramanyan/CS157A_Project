package GUI;
import JDBC_Java.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Custom_Exceptions.InvalidLoginException;

public class LoginPage extends Application {

    DBMethods booksDB = new DBMethods();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome to the Bookstore");

        //text asking customer to fill the login form
        Label welcomeLabel = new Label("Welcome! Please log in below.");
        welcomeLabel.setStyle("-fx-font-size: 15px;");

        //Labels and text fields to obtain email and password
        Label emailLabel = new Label("Email:");
        TextField user_email = new TextField();
        user_email.setPromptText("Enter your email");

        Label passwordLabel = new Label("Password:");
        PasswordField user_password = new PasswordField();
        user_password.setPromptText("Enter your password");

        //creating login and registration buttons
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        HBox buttonBox = new HBox(15);
        buttonBox.getChildren().addAll(loginButton, registerButton);

        VBox formLayout = new VBox(10);
        formLayout.getChildren().addAll(
                welcomeLabel, emailLabel, user_email,
                passwordLabel, user_password,
                buttonBox
        );
        formLayout.setPadding(new Insets(30));

        HBox outerLayout = new HBox();
        outerLayout.getChildren().add(formLayout);

        VBox root = new VBox(outerLayout);
        root.setPadding(new Insets(20));


        Scene scene = new Scene(root, 300, 350);
        primaryStage.setScene(scene);
        primaryStage.show();

        //action for login button to validate login and go to main search page if successful otherwise throw an exception
        loginButton.setOnAction(e -> {
            String email = user_email.getText();
            String password = user_password.getText();

            try {
                booksDB.validateLogin(email, password);
                searchPage mainPage = new searchPage(booksDB.getCustID(email));
                mainPage.start(primaryStage);
            } catch (InvalidLoginException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText(ex.getMessage());
                errorAlert.showAndWait();
            } catch (Exception ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("User was not found. Please check credentials or register if new.");
                errorAlert.showAndWait();
            }
        });

        //action for register button to go to registration page
        registerButton.setOnAction(e -> {
            registerPage registerPage = new registerPage();
            registerPage.start(primaryStage);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
