# bank

This project aims to manage a bank account: add, retrieve money and check the history of the operations.

Before you can build this project, you must install and configure the following dependency on your machine :

[Oracle JDK][] : JDK 8

This project is using lombok, to configure this plugin, check this [link](https://projectlombok.org/)

### Database

This project is using an in memory database (H2) for testing purpose
The data are not persisted and after each restart the data stored before are lost.
Once the application is running, it's possible to access the H2 console:
In order to do that, go to [http://localhost:8080/h2-console](http://localhost:8080/h2-console) and as JDBC URL you need to put jdbc:h2:mem:testdb.

By default, the application is running on the port 8080

### AngularJS

NodeJS is needed to launch the front end application

```
npm install
```

### Running Unit Tests (Karma)
To run the unit tests, use the supplied npm script:

```
npm test
```
