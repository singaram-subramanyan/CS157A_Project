public class Book {
    public String title;
    public String author;
    public String genre;
    public float price;
    public String availability;

    public Book(String title, String author, String genre, float price, String availability) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Title: " + title + '\n' + '\t' +
                "Author: " + author + '\n' + '\t' +
                "Genre: " + genre + '\n' + '\t' +
                "price: " + price;
    }
}
