package com.garethahealy.gameoflife;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class SeedResourceTest {

    @Test
    void canListSeeds() {
        given()
                .when().get("/seeds")
                .then()
                .statusCode(200);
    }

    @Test
    void canSeedBoard() {
        given()
                .when().post("/seeds/{seed}", "GliderSeed")
                .then()
                .statusCode(200);
    }
}
