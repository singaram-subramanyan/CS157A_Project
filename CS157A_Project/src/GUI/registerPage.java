package GUI;
import Custom_Exceptions.CustomerExistsException;
import Custom_Exceptions.InvalidEntryException;
import JDBC_Java.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class registerPage {
    DBMethods booksDB = new DBMethods();

    public void start(Stage stage) {
        stage.setTitle("Create an Account");

        //text asking customer to fill the registration form
        Label welcomeLabel = new Label("Please fill out the form to register.");
        welcomeLabel.setStyle("-fx-font-size: 15px;");

        //Labels and text fields to obtain first name, last name, email, phone number and password
        Label fNameLabel = new Label("First Name:");
        TextField fName = new TextField();
        fName.setPromptText("Enter your First Name");

        Label lNameLabel = new Label("Last Name:");
        TextField lName = new TextField();
        lName.setPromptText("Enter your Last Name");

        Label emailLabel = new Label("Email:");
        TextField userEmail = new TextField();
        userEmail.setPromptText("Enter your Email");

        Label phoneNumLabel = new Label("Phone Number:");
        TextField phoneNum = new TextField();
        phoneNum.setPromptText("Enter your Phone Number");

        Label passwordLabel = new Label("Password:");
        PasswordField userPassword = new PasswordField();
        userPassword.setPromptText("Enter your password");

        //creating login and registration buttons
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        HBox buttonBox = new HBox(15);
        buttonBox.getChildren().addAll(registerButton, loginButton);

        VBox formLayout = new VBox(10);
        formLayout.getChildren().addAll(welcomeLabel, fNameLabel, fName, lNameLabel, lName, emailLabel, userEmail,phoneNumLabel, phoneNum, passwordLabel, userPassword, buttonBox);
        formLayout.setPadding(new Insets(30));

        HBox outerLayout = new HBox();
        outerLayout.getChildren().add(formLayout);

        VBox root = new VBox(outerLayout);
        root.setPadding(new Insets(20));


        Scene scene = new Scene(root, 320, 600);
        stage.setScene(scene);
        stage.show();

        //action for registration button to use inputted information to create entry in Customer table if the customer doesn't exist but throw an exception if they do
        registerButton.setOnAction(e -> {
            String firstName = fName.getText();
            String lastName = lName.getText();
            String email = userEmail.getText();
            String phoneNumber = phoneNum.getText();
            String password = userPassword.getText();

            try {
                //checks for valid email and phone number
                if(!email.contains("@") || !email.contains(".")){
                    throw new InvalidEntryException("Please Enter a valid email");
                }
                if(phoneNumber.length() != 10){
                    throw new InvalidEntryException("Please Enter a valid phone number");
                }
                booksDB.register(firstName,lastName,email, phoneNumber,password);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setContentText("Registration Successful!");
                successAlert.showAndWait();
                LoginPage loginPage = new LoginPage();
                loginPage.start(stage);
            } catch (InvalidEntryException | CustomerExistsException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText(ex.getMessage());
                errorAlert.showAndWait();
            }
        });

        //action for register button to go to registration page
        loginButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage();
            loginPage.start(stage);
        });
    }
}
