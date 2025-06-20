package com.garethahealy.gameoflife;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class BoardResourceTest {

    @Test
    void canSeed() {
        given()
                .when().post("/board/seed/{seed}?option=src/main/resources/CsvLoaderSeed.csv", "CsvLoaderSeed")
                .then()
                .statusCode(200);
    }

    @Test
    void canNextGeneration() {
        given()
                .when().get("/board/next-generation")
                .then()
                .statusCode(200);
    }

    @Test
    void canReset() {
        given()
                .when().get("/board/reset")
                .then()
                .statusCode(200);
    }

}
