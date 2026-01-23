package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.seeds.impl.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@ApplicationScoped
public class SeedRegistry {

    private static final String SEED_PACKAGE = "com.garethahealy.gameoflife.seeds.impl";
    private List<String> supportedSeeds = null;

    /**
     * Resolve supported seeds once during application startup.
     */
    @PostConstruct
    void init() {
        supportedSeeds = loadSupportedSeeds();
    }

    /**
     * Return the cached list of supported seed names.
     */
    public List<String> getSupportedSeeds() {
        if (supportedSeeds == null) {
            supportedSeeds = loadSupportedSeeds();
        }

        return supportedSeeds;
    }

    /**
     * Create a seed instance by name, passing through any option.
     */
    public Seed getSeed(String seed, String option) {
        return switch (seed) {
            case "AcornSeed" -> new AcornSeed();
            case "BeaconSeed" -> new BeaconSeed();
            case "BlinkerSeed" -> new BlinkerSeed();
            case "BoatSeed" -> new BoatSeed();
            case "DiehardSeed" -> new DiehardSeed();
            case "GliderSeed" -> new GliderSeed();
            case "GosperGliderGunSeed" -> new GosperGliderGunSeed();
            case "HeavyweightSpaceshipSeed" -> new HeavyweightSpaceshipSeed();
            case "LightweightSpaceshipSeed" -> new LightweightSpaceshipSeed();
            case "LoafSeed" -> new LoafSeed();
            case "MiddleweightSpaceshipSeed" -> new MiddleweightSpaceshipSeed();
            case "PentadecathlonSeed" -> new PentadecathlonSeed();
            case "PulsarSeed" -> new PulsarSeed();
            case "RPentominoSeed" -> new RPentominoSeed();
            case "ToadSeed" -> new ToadSeed();
            case "ThreeLineSeed" -> new ThreeLineSeed();
            case "TubSeed" -> new TubSeed();
            case "CsvLoaderSeed" -> new CsvLoaderSeed(option);
            default -> throw new NotFoundException("Seed not found: " + seed);
        };
    }

    /**
     * Scan the seed package and collect valid seed implementations.
     */
    private List<String> loadSupportedSeeds() {
        Set<String> seeds = new TreeSet<>();

        String path = SEED_PACKAGE.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                String protocol = resource.getProtocol();

                // Seeds can be loaded from filesystem in dev/test or from a JAR in production.
                if ("file".equals(protocol)) {
                    scanDirectory(seeds, java.nio.file.Path.of(resource.toURI()));
                } else if ("jar".equals(protocol)) {
                    scanJar(seeds, resource);
                }
            }
        } catch (IOException | URISyntaxException exception) {
            throw new IllegalStateException("Failed to scan seed classes", exception);
        }

        return List.copyOf(seeds);
    }

    /**
     * Scan a filesystem directory for seed classes.
     */
    private void scanDirectory(Set<String> seeds, java.nio.file.Path directory) throws IOException {
        if (!Files.exists(directory)) {
            return;
        }

        try (var paths = Files.list(directory)) {
            paths.filter(file -> file.getFileName().toString().endsWith(".class"))
                    .map(file -> file.getFileName().toString())
                    .map(fileName -> fileName.substring(0, fileName.length() - ".class".length()))
                    .forEach(className -> addSeedIfSupported(seeds, className));
        }
    }

    /**
     * Scan a JAR resource for seed classes.
     */
    private void scanJar(Set<String> seeds, URL resource) throws IOException {
        JarURLConnection connection = (JarURLConnection) resource.openConnection();
        String path = SEED_PACKAGE.replace('.', '/') + "/";

        try (var jar = connection.getJarFile()) {
            jar.stream()
                    .filter(entry -> !entry.isDirectory())
                    .map(entry -> entry.getName())
                    .filter(name -> name.startsWith(path))
                    .filter(name -> name.endsWith(".class"))
                    .map(name -> name.substring(path.length(), name.length() - ".class".length()))
                    .forEach(className -> addSeedIfSupported(seeds, className));
        }
    }

    /**
     * Add the class if it is a concrete Seed implementation.
     */
    private void addSeedIfSupported(Set<String> seeds, String className) {
        String fqcn = SEED_PACKAGE + "." + className;
        try {
            Class<?> candidate = Class.forName(fqcn);
            if (!Seed.class.isAssignableFrom(candidate)) {
                return;
            }

            if (FileSeed.class.isAssignableFrom(candidate)) {
                return;
            }

            if (candidate.isInterface() || Modifier.isAbstract(candidate.getModifiers())) {
                return;
            }

            if (!candidate.getSimpleName().endsWith("Seed")) {
                return;
            }

            seeds.add(candidate.getSimpleName());
        } catch (ClassNotFoundException ignored) {
            // Ignore classes that cannot be loaded at runtime.
        }
    }
}
