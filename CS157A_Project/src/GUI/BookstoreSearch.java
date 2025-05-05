package GUI;
import JDBC_Java.*;
import Custom_Exceptions.ItemNotInStockException;
import Custom_Exceptions.SearchNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class BookstoreSearch {

    private int custId;
    private DBMethods db;

    public BookstoreSearch(int custId) {
        this.custId = custId;
        this.db = new DBMethods();
    }

    public void start(Stage stage) {
        TextField searchField = new TextField();
        searchField.setPromptText("Search for books...");

        Button searchButton = new Button("Search");
        Button viewCartButton = new Button("Cart");
        Button logoutButton = new Button("Logout");



        VBox resultsContainer = new VBox(30);  // Container for search results
        resultsContainer.setPadding(new Insets(10));

        searchButton.setOnAction(e -> {
            String query = searchField.getText();
            resultsContainer.getChildren().clear();  // Clear previous results

            try {
                List<Book> results = db.search(query);

                for (Book book : results) {
                    Label titleLabel = new Label(book.getTitle());
                    Label authorLabel = new Label(book.getAuthor());
                    Label genreLabel = new Label(book.getGenre());
                    Label priceLabel = new Label("$" + book.getPrice());
                    Label availLabel = new Label(book.getAvailability());

                    Button addButton = new Button("Add");
                    addButton.setOnAction(event -> {
                        if (!Objects.equals(book.getAvailability(), "In Stock")) {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setContentText("This book is not currently available.");
                            errorAlert.showAndWait();
                            return;
                        }

                        TextInputDialog quantityDialog = new TextInputDialog("1");
                        quantityDialog.setTitle("Add to Cart");
                        quantityDialog.setHeaderText("Enter quantity for: " + book.getTitle());
                        quantityDialog.setContentText("Quantity:");

                        quantityDialog.showAndWait().ifPresent(input -> {
                            try {
                                int quantity = Integer.parseInt(input);
                                if (quantity <= 0) {
                                    throw new NumberFormatException();
                                }
                                db.addToCart(book.getId(), quantity, custId);
                                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                                successAlert.setContentText("Item Added to Cart!");
                                successAlert.showAndWait();
                            } catch (NumberFormatException ex) {
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                errorAlert.setContentText("Please enter a valid positive number.");
                                errorAlert.showAndWait();
                            } catch (ItemNotInStockException ex) {
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                errorAlert.setContentText(ex.getMessage());
                                errorAlert.showAndWait();
                            }
                        });
                    });

                    HBox row = new HBox(30, titleLabel, authorLabel, genreLabel, priceLabel, availLabel, addButton);
                    row.setPadding(new Insets(5));
                    resultsContainer.getChildren().add(row);
                }

            } catch (SearchNotFoundException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText(ex.getMessage());
                errorAlert.showAndWait();
            }
        });
        logoutButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage();
            loginPage.start(stage);
        });
        viewCartButton.setOnAction(e -> {
            cartPage cartPage = new cartPage(custId);
            cartPage.start(stage);
        });
        HBox searchBar = new HBox(20, searchField, searchButton, viewCartButton, logoutButton);
        VBox layout = new VBox(30, searchBar, resultsContainer);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 900, 500);
        stage.setScene(scene);
        stage.setTitle("Bookstore");
        stage.show();
    }
}

