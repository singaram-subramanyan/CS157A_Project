BEGIN TRANSACTION;

INSERT INTO Cart (customer_id, book_id, quantity) VALUES (2370152, 1, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (2370152, 10, 2);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (123374088, 6, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (123374088, 12, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (226839863, 14, 2);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (226839863, 15, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (153397236, 9, 1);




INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (2370152, "Jane", "Doe", 1234567890, "jane.doe@gmail.com", "ilovebooks123");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (2383429, "John", "Doe", 1234560987, "john.doe@gmail.com", "booksareamazing123");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (64350162, "Ted", "Black", 7891234560, "ted.black@gmail.com", "tedreadsbooks768");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (4951443, "Mike", "Ross", 7891324560, "mike.ross@gmail.com", "mikebuysbooks78");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (1699963747, "Rory", "Gilmore", 7891324650, "rory.gilmore@gmail.com", "yalerules567");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (123374088, "Randall", "Pearson", 6678957667, "randall.pearson@gmail.com", "randalllovesbooks665");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (237569837, "Kevin", "Pearson", 7892314650, "kevin.pearson@gmail.com", "therealkevinpearson");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (167511347, "Kate", "Pearson", 9871324650, "kate.pearson@gmail.com", "musicisamazing456");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (226839863, "Rebecca", "Pearson", 9872314650, "rebecca.pearson@gmail.com", "booksarefantastic298");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (167481035, "Jack", "Pearson", 5101234567, "jack.pearson@gmail.com", "footballisamazing358");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (224785597, "Lorelai", "Gilmore", 4021234567, "lorelai.gilmore@gmail.com", "starshollow@23");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (153397236, "Max", "Medina", 2051234567, "max.medina@gmail.com", "iworkatchilton@8");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (153872365, "Emily", "Gilmore", 4022464567, "emily.gilmore@gmail.com", "ilovefinedining");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (263985622, "Richard", "Gilmore", 2053694567, "richard.gilmore@gmail.com", "workingintheinsurancebiz");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (154884823, "Suki", "St.James", 2054814567, "suki.stjames@gmail.com", "chef@theindependenceinn");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (73372123, "Stuart", "Lane", 3132344567, "stuart.lane@gmail.com", "iliveforlaw");


INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (1, 'To Kill a Mockingbird', 'Harper Lee', 'Fiction', 10.99, 100);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (2, '1984', 'George Orwell', 'Dystopian', 8.99, 50);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (3, 'Moby-Dick', 'Herman Melville', 'Adventure', 12.99, 200);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (4, 'The Great Gatsby', 'F. Scott Fitzgerald', 'Classic', 11.99, 75);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (5, 'Pride and Prejudice', 'Jane Austen', 'Romance', 9.49, 150);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (6, 'The Catcher in the Rye', 'J.D. Salinger', 'Literature', 8.49, 125);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (7, 'The Hobbit', 'J.R.R. Tolkien', 'Fantasy', 14.99, 80);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (8, 'The Lord of the Rings', 'J.R.R. Tolkien', 'Fantasy', 19.99, 300);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (9, 'The Chronicles of Narnia', 'C.S. Lewis', 'Fantasy', 13.99, 110);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (10, 'Harry Potter and the Goblet of Fire', 'J.K. Rowling', 'Fantasy', 10.99, 250);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (11, 'The Hunger Games', 'Suzanne Collins', 'Dystopian', 12.49, 90);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (12, 'The Alchemist', 'Paulo Coelho', 'Philosophical', 15.99, 60);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (13, 'The Fault in Our Stars', 'John Green', 'Romance', 10.49, 70);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (14, 'Brave New World', 'Aldous Huxley', 'Dystopian', 9.99, 200);
INSERT INTO Books ("id", "Title", "Author", "Genre", "Price", "stock") VALUES (15, 'The Road', 'Cormac McCarthy', 'Post-apocalyptic', 11.49, 130);

INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107077, 153397236, 9, 1, '2025-04-23');

COMMIT;