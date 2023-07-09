

INSERT INTO Document (id, name, CUSTOMER_ID, CREATION_DATE, TYPE, CONTENTS) VALUES
( NEXT VALUE FOR DOCUMENT_ID_SEQUENCE, 'PlainText.txt', 1, DATE '2023-03-15', 'text/plain','This is a plain text file.');

INSERT INTO Document (id, name, CUSTOMER_ID, CREATION_DATE, TYPE, CONTENTS) VALUES
( NEXT VALUE FOR DOCUMENT_ID_SEQUENCE, 'LinuxCommands.jpg', 1, DATE '2023-02-07', 'image/jpeg', FILE_READ('C:/workspace/files/LinuxCommands.jpg'));