package com.daveo.bank;

import com.daveo.bank.dto.OperationRequest;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.ServletContext;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OperationApiIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ServletContext context;


    @Before
    public void init() {
        RestAssured.port = port;
    }

    @Test
    public void history_account_not_found_should_fail(){
        given()
                .param("accountId", 9999)
                .when()
                .get(context.getContextPath() + "/api/history")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void deposit_account_not_found_should_fail(){
        OperationRequest operationRequest = new OperationRequest();
        operationRequest.setAmount(5);
        given()
                .pathParam("accountId", 9999)
                .contentType("application/json")
                .body(operationRequest)
                .when()
                .post(context.getContextPath() + "/api/deposit/{accountId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void deposit_should_succeed(){
        OperationRequest operationRequest = new OperationRequest();
        operationRequest.setAmount(5);
        given()
                .pathParam("accountId", 1)
                .contentType("application/json")
                .body(operationRequest)
                .when()
                .post(context.getContextPath() + "/api/deposit/{accountId}")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void retrieve_should_succeed(){
        OperationRequest operationRequest = new OperationRequest();
        operationRequest.setAmount(5);
        given()
                .pathParam("accountId", 3)
                .contentType("application/json")
                .body(operationRequest)
                .when()
                .post(context.getContextPath() + "/api/retrieve/{accountId}")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void retrieve_should_fail(){
        OperationRequest operationRequest = new OperationRequest();
        operationRequest.setAmount(5);
        given()
                .pathParam("accountId", 3)
                .contentType("application/json")
                .body(operationRequest)
                .when()
                .post(context.getContextPath() + "/api/retrieve/{accountId}")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
