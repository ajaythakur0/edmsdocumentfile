//package com.edms;
//
//import com.edms.EnterprisedocumentmanagementsoftwareApplication;
//import com.edms.dto.DocumentDTO;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.notNullValue;
//
//@SpringBootTest(classes = EnterprisedocumentmanagementsoftwareApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class DocumentControllerFunctionalTest {
//
//    @LocalServerPort
//    private int port;
//
//    @BeforeEach
//    public void setup() {
//        RestAssured.port = port;
//    }
//
//    @Test
//    public void testGetDocument() {
//        String documentId = "1"; // Replace with a valid document ID
//
//        DocumentDTO response = given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/document/{documentId}", documentId)
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .extract()
//                .as(DocumentDTO.class);
//
//        // Add assertions to validate the response
//        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
//        org.assertj.core.api.Assertions.assertThat(response.getId()).isEqualTo(documentId);
//        // Add more assertions as needed
//    }
//
//    // Add more functional tests for other endpoints
//}
