package com.garethahealy.gameoflife.seeds.impl;

import com.garethahealy.gameoflife.seeds.FileSeed;
import com.garethahealy.gameoflife.seeds.SeedPattern;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvLoaderSeed implements FileSeed {

    private final String path;

    public CsvLoaderSeed(String path) {
        this.path = path;
    }

    @Override
    public SeedPattern processFile() throws IOException {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path to CSV file cannot be null or empty");
        }

        String[] rows = loadCsv(path);
        return SeedPattern.centered(rows);
    }

    private String[] loadCsv(String path) throws IOException {
        List<String> lines = new ArrayList<>();

        CSVFormat csvFormat = CSVFormat.Builder.create(CSVFormat.DEFAULT).get();
        try (Reader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            Iterable<CSVRecord> records = csvFormat.parse(reader);

            for (CSVRecord record : records) {
                StringBuilder line = new StringBuilder();
                for (String value : record.values()) {
                    line.append(value.trim());
                }

                lines.add(line.toString());
            }
        }

        return lines.toArray(new String[0]);
    }
}
