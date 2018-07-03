package com.daveo.bank;

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
public class AccountApiIT {

	@LocalServerPort
	private int port;

	@Autowired
	private ServletContext context;


	@Before
	public void init() {
		RestAssured.port = port;
	}

	@Test
	public void get_account_should_succeed() {

		given()
				.pathParam("accountId", 1)
				.when()
				.get(context.getContextPath() + "/api/account/{accountId}")
				.then()
				.statusCode(HttpStatus.SC_OK)
				.body("id", CoreMatchers.equalTo(1))
				.body("name", CoreMatchers.equalTo("Account1"));
	}

	@Test
	public void get_account_should_fail_not_found() {

		given()
				.pathParam("accountId", 9999)
				.when()
				.get(context.getContextPath() + "/api/account/{accountId}")
				.then()
				.statusCode(HttpStatus.SC_NOT_FOUND);
	}

	@Test
	public void list_accounts_should_succeed() {
		given()
				.when()
				.get(context.getContextPath() + "/api/account")
				.then()
				.statusCode(HttpStatus.SC_OK)
				.body("size()", CoreMatchers.equalTo(3));
	}

}
