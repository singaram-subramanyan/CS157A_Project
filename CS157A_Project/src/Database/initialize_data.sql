BEGIN TRANSACTION;

INSERT INTO Cart (customer_id, book_id, quantity) VALUES (2370152, 1, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (2370152, 10, 2);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (123374088, 6, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (123374088, 12, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (226839863, 14, 2);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (226839863, 15, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (153397236, 9, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (2370152, 4, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (2383429, 6, 2);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (64350162, 10, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (4951443, 2, 3);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (1699963747, 8, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (123374088, 11, 2);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (237569837, 5, 1);
INSERT INTO Cart (customer_id, book_id, quantity) VALUES (167511347, 7, 1);

INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (2370152, "Jane", "Doe", "1234567890", "jane.doe@gmail.com", "$2a$10$aOTjIzQIjrlvCi1xvWD5fuNDhuaO6xH6hQaS.f.UppyHgXDdEQOnC");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (2383429, "John", "Doe", "1234560987", "john.doe@gmail.com", "$2a$10$328gDCoXFfIpRqqteHmsd.tg6Nbwswp3EUN1Gun0hgtVj3z4b4nOS");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (64350162, "Ted", "Black", "7891234560", "ted.black@gmail.com", "$2a$10$PwaerjiLr3ZkfrWMdfGhuuYj2SxuoR6w50HMvnYuiz0PqmxHxxNdK");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (4951443, "Mike", "Ross", "7891324560", "mike.ross@gmail.com", "$2a$10$328gDCoXFfIpRqqteHmsd.tg6Nbwswp3EUN1Gun0hgtVj3z4b4nOS");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (1699963747, "Rory", "Gilmore", "7891324650", "rory.gilmore@gmail.com", "$2a$10$sZEyHBhi.moZVjvq8vgz3ec.azqh5lYutlV8XYdgQ.XhEM4Hnn1Zi");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (123374088, "Randall", "Pearson", "6678957667", "randall.pearson@gmail.com", "$2a$10$zDTQLYjEXVGEx8icIw4qxeux0YRNwMTSdvR2vaaQHvyMB8PwMSZEe");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (237569837, "Kevin", "Pearson", "7892314650", "kevin.pearson@gmail.com", "$2a$10$4gbawwh9tsZ.LwpO3Trei.AAYPAtCOI1I3f71o4V7B5DDHkUQbbmm");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (167511347, "Kate", "Pearson", "9871324650", "kate.pearson@gmail.com", "$2a$10$0vLIBFFlstX81YPqydfh8eazMstD/D3Q10fQpxG23kRJC4SUYQkPi");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (226839863, "Rebecca", "Pearson", "9872314650", "rebecca.pearson@gmail.com", "$2a$10$i.03ExlUMx25.or6OjhjQuXoqSpXKcHMlIUonsaliMyYNA/OUqbLK");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (167481035, "Jack", "Pearson", "5101234567", "jack.pearson@gmail.com", "$2a$10$bzXyvgO9XEPr5v.n5tpKUOPmm5fmeyiNS9ony6nrRfEqcO93A1N1e");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (224785597, "Lorelai", "Gilmore", "4021234567", "lorelai.gilmore@gmail.com", "$2a$10$rf9VJ8wuAckvEKydJXm36.wqFdBRSurNV8L8yvhSSMk0b48AQqg/W");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (153397236, "Max", "Medina", "2051234567", "max.medina@gmail.com", "$2a$10$fDoZ63os5dgRzlWkk52aG.k3I27iBLuB7tetoc.TIpE6zcp9te89m");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (153872365, "Emily", "Gilmore", "4022464567", "emily.gilmore@gmail.com", "$2a$10$X.1NpD.0HNKuyv6jpyjqL..8qhx3JfD96gDfCUWeiMeBidu8It4/u");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (263985622, "Richard", "Gilmore", "2053694567", "richard.gilmore@gmail.com", "$2a$10$S5/.hykPvJGTMCstZyD70.L4RNKV.bJPii17z7kAJmp24V8cDbZiy");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (154884823, "Suki", "St.James", "2054814567", "suki.stjames@gmail.com", "$2a$10$un.02ojGMVGvm.8usD7XYOyUMlCMYNB6HWDUzf23MKH5/CFWn7R/K");
INSERT INTO Customer (id, first_name, last_name, phone, email_id, account_password) VALUES (73372123, "Stuart", "Lane", "3132344567", "stuart.lane@gmail.com", "$2a$10$LIqq5A1bLJkfs6kh6T7vl.jQJQvCf8kEf1vwszbroyHKUEcTOOaKa");


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
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107070, 153397236, 2, 1, '2025-04-23');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107070, 153397236, 3, 1, '2025-04-23');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107071, 160004112, 5, 2, '2025-04-24');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107072, 160004112, 1, 1, '2025-04-24');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107073, 147891204, 7, 1, '2025-04-25');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107074, 147891204, 8, 3, '2025-04-25');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107080, 164200301, 4, 1, '2025-04-26');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107080, 164200301, 6, 2, '2025-04-26');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107081, 172903112, 11, 1, '2025-04-26');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107082, 172903112, 14, 1, '2025-04-27');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107083, 178882910, 7, 2, '2025-04-27');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107084, 190033221, 10, 1, '2025-04-27');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date") VALUES (114107085, 190033221, 1, 1, '2025-04-28');
INSERT INTO Orders ("id", "customer_id", "book_id", "quantity", "order_date")  VALUES (114107085, 190033221, 13, 1, '2025-04-28');

COMMIT;