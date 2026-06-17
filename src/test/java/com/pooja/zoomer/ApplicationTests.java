package com.pooja.zoomer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

 
/*
JUnit only runs Java code - we have spring boot application
Before running the tests, start the entire Spring Boot application.
*/
@Disabled
@SpringBootTest  
class ApplicationTests {

	@Test //JUnit, execute this method as a test.
	void contextLoads() {
	}

}
/*
Checks: 
Can Spring create all beans?
Can Security start?
Can JPA start?
Can Controllers start?

If something is broken:
Application failed to start

JUnit starts
      ↓
@SpringBootTest
      ↓
Spring Boot starts
      ↓
Creates all beans
      ↓
Loads Application Context ioc
      ↓
Runs contextLoads()
      ↓
Method does nothing - jus to check Can Spring create the ApplicationContext?
      ↓
PASS
*/