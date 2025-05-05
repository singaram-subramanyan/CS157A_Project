package JDBC_Java;

public class Book {
    public int id;
    public String title;
    public String author;
    public String genre;
    public float price;
    public String availability;

    public Book(int id, String title, String author, String genre, float price, String availability) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.availability = availability;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Title: " + title + '\n' + '\t' +
                "Author: " + author + '\n' + '\t' +
                "Genre: " + genre + '\n' + '\t' +
                "price: " + price + '\n' + '\t' +
                "Availability: " + availability;
    }
}
