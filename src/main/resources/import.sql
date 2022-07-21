INSERT INTO book (authors, isbn, number_of_copies, number_of_pages, publisher, synopsis, title, `year`) VALUES('Alessandro Manzoni', '9788838439193', 5, 190, 'Piemme', '', 'I promessi sposi', 2009);
INSERT INTO book (authors, isbn, number_of_copies, number_of_pages, publisher, synopsis, title, `year`) VALUES('J.K.Rowling', '9788869183157', 3, 256, 'Salani', 'IL PRIMO LIBRO DELLA SAGA DI HARRY POTTER COMPLETAMENTE ILLUSTRATO', 'Harry Potter e la pietra filosofale', 2002);

INSERT INTO category (name) VALUES('Fiction');
INSERT INTO category (name) VALUES('Fantasy');
INSERT INTO category (name) VALUES('Novel');


INSERT INTO book_categories (books_id, categories_id) VALUES(1, 3);
INSERT INTO book_categories (books_id, categories_id) VALUES(2, 2);
INSERT INTO book_categories (books_id, categories_id) VALUES(2, 1);
