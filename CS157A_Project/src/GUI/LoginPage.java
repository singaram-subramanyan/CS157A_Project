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

    DBMethods db = new DBMethods();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome to the Bookstore");

        Label welcomeLabel = new Label("Welcome! Please log in below.");
        welcomeLabel.setStyle("-fx-font-size: 15px;");

        Label emailLabel = new Label("Email:");
        TextField user_email = new TextField();
        user_email.setPromptText("Enter your email");

        Label passwordLabel = new Label("Password:");
        PasswordField user_password = new PasswordField();
        user_password.setPromptText("Enter your password");

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

        loginButton.setOnAction(e -> {
            String email = user_email.getText();
            String password = user_password.getText();

            try {
                db.validateLogin(email, password);
                BookstoreSearch mainPage = new BookstoreSearch(db.getCustID(email));
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

//        registerButton.setOnAction(e -> {
//            RegisterPage registerPage = new RegisterPage();
//            registerPage.start(primaryStage);
//        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
