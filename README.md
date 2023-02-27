# Definex Spring Practicum Final Case
This repository consists of the final case code for DefineX - Java Spring Practicum

## Credit Limit Application Program

RESTful application that is for a credit application system. Credit application requests are accepted and in return , users are informed about their application status, and credit limit.

### Requirements
* Spring Boot framework must be used.
* User CRUD features.
* Credit limit application approval and credit limit calculation logic.
  * Credit score below 500 means a negative application. Credit limit is calculated by credit score, monthly income, a guaranteed valuue (optional) and a credit limit multiplier.
* Credit score service (can return a random value)
* SMS service (only a command line response, mimics an sms)

### Scorecard
* Writing of tests
* Use of meaningful design patterns
* Documentation
* ORM and JPA technologies to be used
* Logging mechanism


## Installation and Usage(For Linux/MacOS)
1. Clone the project using HTTPS or SSH:
HTTPS
```
git clone https://github.com/sanelg7/final_case.git
```
SSH
```
git clone git@github.com:sanelg7/final-case.git
```
2. Install MySQL Community Server
2.1 After installation, start the MySQL server (command may vary according to OS/MySQL Version):
```
sudo service mysql start
```
2.2 Connect to MySQL:
```
mysql -u root -p
```
2.3 It will prompt for your root password. Now create a new database. In current properties, the name is set as "final_case". If you opt into using a different name, do not forget to change it under persistence-mysql.properties.
```
CREATE DATABASE database_name;
```
2.4 Now you need to populate the database with some data. You can find necessary queries within data.sql file.
2.5 Create a MySQL user and set the username/passwword within persistence-mysql.properties.
2.6 (Optional) You can change the database settings to your liking. Do not forget to also change them in persistence-mysql.properties.

3. Run the program with Maven
3.1 Open the terminal and navigate to the root directory of the project.
3.2 Run below command:
```
mvn clean install
```
3.3 Once the build is complete, run the following command to start the application:
```
mvn spring-boot:run
```
This will start the Spring Boot application and you should be able to access it at http://localhost:8080.
* You can also use the wrapper files.

4. (Optional) Accessing the swagger page.
You can go to this url:
```
http://localhost:8080/swagger-ui/index.html
```
5. (Optional) Running Tests
To run all tests, below Maven command is used.
```
mvn test
```
You can also choose to run a specific test/test method. Do not forget to choose the TestClass to the preferred class, and testMethod as well:
```
mvn test -Dtest=TestClass#testMethod
```
6. Running PostmanCollections
6.1 To run the collections first, you will need to download Postman.
6.2 Open Postman and click on the "Import" button. Click on "Choose Files" and select the postman_collections folder.
6.3 After importing you are good to go. Just click Send on a request. 

* Start with auth for previously created ADMIN user. You can figure out the rest from there.
* Remember to check the request and mind the relations/authorizations etc.
* You can also save your own requests or modify existing ones to your need.

## Endpoints
Some endpoints do not need authorization, while others need a certain role of user that is authenticated beforehand. This is provided by Spring Security and JWT Bearer Tokens.

### Public Endpoints
POST /register

This endpoint is used to register a new user. The user's details are passed in the request body in the form of a RegisterDto object. If the registration is successful, a 201 Created response is returned with the newly created user object in the response body along with a success message. If the registration fails due to validation errors or any other reason, a 400 Bad Request response is returned with an error message.

POST /login

This endpoint is used to authenticate a user and generate a JWT token that can be used for authorization in subsequent requests. The user's TCKN and password are passed in the request body in the form of a LoginDto object. If the authentication is successful, a 200 OK response is returned with a LoginResponseDto object in the response body containing the JWT token and the user ID. If the authentication fails due to invalid credentials, a 401 Unauthorized response is returned.

POST /credit-limit-applications

This endpoint allows a user to apply for a credit limit by sending a POST request with the required information in the request body. If the application is successful, a 202 Accepted response is returned with the newly created credit limit application object in the response body. If the user is not found or the credit limit application is invalid, a 400 Bad Request response is returned with an error message in the response body.

POST /credit-limit-applications/status

This endpoint allows a user to check the status of their credit limit application by sending a POST request with the required information in the request body. If the credit limit application exists, a 202 Accepted response is returned with the credit limit object in the response body. If the user is not found, a 400 Bad Request response is returned with an error message in the response body.

### Authorized Endpoints 

GET /credit-limits/{userId}

Retrieves the credit limit of a user by the user ID. The ID is passed in the path parameter. If the user has a credit limit, a 200 OK response is returned with the credit limit object in the response body. If the user does not have a credit limit, a 400 Bad Request response is returned with an appropriate message in the response body.

POST /credit-limits/admin/{userId}

Creates a credit limit for a user by the user ID. The ID is passed in the path parameter. The amount of the credit limit is passed in the request body. If the credit limit is successfully created, a 201 Created response is returned with the credit limit object in the response body. If the user does not exist, a 400 Bad Request response is returned with an appropriate message in the response body.

DELETE /credit-limits/admin/{id}

Deletes the credit limit of a user by the credit limit ID. The ID is passed in the path parameter. If the credit limit is successfully deleted, a 200 OK response is returned with an appropriate message in the response body. If the credit limit does not exist, a 400 Bad Request response is returned with an appropriate message in the response body.

GET /credit-scores/{userId}

This endpoint is used to retrieve the credit score of a user by sending a GET request with the user ID in the request path and the required information in the request body. If the credit score is found, a 200 OK response is returned with the credit score object in the response body. If the user is not found or the credit score does not exist, a 400 Bad Request response is returned with an error message in the response body.

POST /credit-scores/{userId}

This endpoint is used to generate a credit score for a user by sending a POST request with the user ID in the request path. If the credit score is generated successfully, a 201 Created response is returned with the newly created credit score object in the response body. If the user is not found or the credit score generation fails, a 400 Bad Request response is returned with an error message in the response body.

PUT /credit-scores/admin

This endpoint is used to update a credit score by sending a PUT request with the required information in the request body. If the credit score is updated successfully, a 200 OK response is returned with the updated credit score object in the response body. If the credit score to update is not found or the update fails, a 400 Bad Request response is returned with an error message in the response body.

DELETE /credit-scores/{id}

This endpoint is used to delete a credit score by sending a DELETE request with the credit score ID in the request path. If the credit score is deleted successfully, a 200 OK response is returned with a success message in the response body. If the credit score to delete is not found, a 400 Bad Request response is returned with an error message in the response body.

GET /users/{id}

This endpoint allows a user to fetch information about themselves by sending a GET request with their user ID as a path parameter. If the user is found, a 200 OK response is returned with the user object in the response body. If the user is not found, a 400 Bad Request response is returned with an error message in the response body.

DELETE /users/{id}

This endpoint allows a user to delete their account by sending a DELETE request with their user ID as a path parameter. If the user is found and the account is deleted successfully, a 200 OK response is returned with a success message in the response body. If the user is not found, a 400 Bad Request response is returned with an error message in the response body.

PUT /users/{id}

This endpoint allows a user to update their password by sending a PUT request with their user ID as a path parameter and a request body containing their new password. If the user is found and the password is updated successfully, a 200 OK response is returned with the updated user object in the response body. If the user is not found or the password update fails, a 400 Bad Request response is returned with an error message in the response body.

POST /users/admin

This endpoint allows an administrator to create a new user account by sending a POST request with the required information in the request body. If the user account is created successfully, a 201 Created response is returned with the newly created user object in the response body. If the request is invalid or the user creation fails, a 400 Bad Request response is returned with an error message in the response body.

GET /users/admin

This endpoint allows an administrator to fetch information about all users by sending a GET request. If there are users in the system, a 200 OK response is returned with a list of user objects in the response body. If there are no users in the system, an empty list is returned.

PUT /users/admin/{id}

This endpoint allows an administrator to update a user's account information by sending a PUT request with the user ID as a path parameter and the updated user information in the request body. If the user is found and the update is successful, a 200 OK response is returned with the updated user object in the response body. If the user is not found or the update fails, a 400 Bad Request response is returned with an error message in the response body.


