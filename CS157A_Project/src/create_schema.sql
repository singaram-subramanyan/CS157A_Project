BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Books" (
	"id"	INTEGER,
	"Title"	TEXT NOT NULL,
	"Author" TEXT NOT NULL,
	"Genre"	TEXT NOT NULL,
	"Price"	REAL,
	"stock"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Customer" (
	"id"	INTEGER,
	"first_name"	TEXT NOT NULL,
	"last_name"	TEXT NOT NULL,
	"phone"	INTEGER,
	"email_id"        TEXT NOT NULL,
	"account_password" TEXT NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Orders" (
	"id"	INTEGER,
	"customer_id"	INTEGER,
	"book_id"	INTEGER,
	"quantity"  INTEGER,
	"order_date" DATE,
	FOREIGN KEY("customer_id") REFERENCES "Customer"("id"),
	FOREIGN KEY("book_id") REFERENCES "Book"("id"),
	PRIMARY KEY("id", "customer_id", "book_id")
);
CREATE TABLE IF NOT EXISTS "Cart" (
	"customer_id"	INTEGER,
	"book_id"	INTEGER,
	"quantity" INTEGER,
	FOREIGN KEY("customer_id") REFERENCES "Customer"("id"),
	FOREIGN KEY("book_id") REFERENCES "Book"("id")
	PRIMARY KEY("customer_id", "book_id")
);
COMMIT;
