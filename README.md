# customer-rest-api

Spring boot 3 rest api app. Simple REST app where it is possible to perform CRUD operations on two different endpoints:

1. Customer endpoint available in http://localhost:8080/api/v1/customers

2. Document endpoint available in http://localhost:8080/api/v1/documents

Application will create db schema on start up and populate Customer and Documents tables with a few records. Data is stored in an in-memory database. 
CRUD operations can be performed and are protected with Basic Authentication.
