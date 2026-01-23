package com.garethahealy.gameoflife.seeds;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class SeedRegistryTest {

    @Inject
    SeedRegistry registry;

    @Test
    void supportedSeedsExcludeFileSeed() {
        List<String> supported = registry.getSupportedSeeds();

        assertTrue(supported.contains("GliderSeed"));
        assertFalse(supported.contains("CsvLoaderSeed"));
    }

    @Test
    void getSeedCreatesSeedInstance() {
        Seed seed = registry.getSeed("GliderSeed", null);

        assertNotNull(seed);
    }

    @Test
    void getSeedThrowsForUnknownSeed() {
        assertThrows(jakarta.ws.rs.NotFoundException.class, () -> registry.getSeed("MissingSeed", null));
    }
}
