BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Books" (
	"id"	INTEGER NOT NULL,
	"Title"	TEXT NOT NULL,
	"Author" TEXT NOT NULL,
	"Genre"	TEXT NOT NULL,
	"Price"	REAL NOT NULL,
	"stock"	INTEGER NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Customer" (
	"id"	INTEGER NOT NULL,
	"first_name"	TEXT NOT NULL,
	"last_name"	TEXT NOT NULL,
	"phone"	TEXT NOT NULL,
	"email_id"        TEXT NOT NULL,
	"account_password" TEXT NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Orders" (
	"id"	INTEGER NOT NULL,
	"customer_id"	INTEGER NOT NULL,
	"book_id"	INTEGER NOT NULL,
	"quantity"  INTEGER NOT NULL,
	"order_date" DATE NOT NULL,
	FOREIGN KEY("customer_id") REFERENCES "Customer"("id"),
	FOREIGN KEY("book_id") REFERENCES "Book"("id"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Cart" (
	"customer_id"	INTEGER NOT NULL,
	"book_id"	INTEGER NOT NULL,
	"quantity" INTEGER NOT NULL,
	FOREIGN KEY("customer_id") REFERENCES "Customer"("id"),
	FOREIGN KEY("book_id") REFERENCES "Book"("id")
	PRIMARY KEY("customer_id", "book_id")
);
COMMIT;
