package com.garethahealy.gameoflife;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class BoardResourceTest {

    @Test
    void canNextGeneration() {
        given().when().post("/board/next-generation").then().statusCode(200);
    }

    @Test
    void canReset() {
        given().when().post("/board/reset").then().statusCode(200);
    }
}
