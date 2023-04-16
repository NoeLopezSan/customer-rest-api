INSERT INTO Customer (id, name, email, date_of_birth) VALUES
( NEXT VALUE FOR CUSTOMER_ID_SEQUENCE, 'Joe Smith', 'joe.smith@gmail.com',DATE '2008-01-01');
INSERT INTO Customer (id, name, email, date_of_birth) VALUES
( NEXT VALUE FOR CUSTOMER_ID_SEQUENCE, 'Robert Moody', 'robert.moody@gmail.com',DATE '1985-06-21');
INSERT INTO Customer (id, name, email, date_of_birth) VALUES
( NEXT VALUE FOR CUSTOMER_ID_SEQUENCE, 'Jennifer Dolan', 'jennifer.dolan@gmail.com',DATE '1966-11-11');
INSERT INTO Customer (id, name, email, date_of_birth) VALUES
( NEXT VALUE FOR CUSTOMER_ID_SEQUENCE, 'Christopher Farrel', 'christopher.farrel@gmail.com',DATE '1970-04-15');
INSERT INTO Customer (id, name, email, date_of_birth) VALUES
( NEXT VALUE FOR CUSTOMER_ID_SEQUENCE, 'Caroline Red', 'caroline.red@gmail.com',DATE '1992-03-05');

INSERT INTO Document (id, name, CUSTOMER_ID, CREATION_DATE, TYPE, CONTENTS) VALUES
( NEXT VALUE FOR DOCUMENT_ID_SEQUENCE, 'PlainText.txt', 1, DATE '2023-03-15', 'text/plain','This is a plain text file.');

INSERT INTO Document (id, name, CUSTOMER_ID, CREATION_DATE, TYPE, CONTENTS) VALUES
( NEXT VALUE FOR DOCUMENT_ID_SEQUENCE, 'LinuxCommands.jpg', 1, DATE '2023-02-07', 'image/jpeg', FILE_READ('C:/workspace/files/LinuxCommands.jpg'));