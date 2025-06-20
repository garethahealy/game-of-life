package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvLoaderSeed implements Seed {

    private final String path;

    public CsvLoaderSeed(String path) {
        this.path = path;
    }

    @Override
    public void process(GameBoard board) throws IOException {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path to CSV file cannot be null or empty");
        }

        List<List<com.garethahealy.gameoflife.records.Cell>> lines = loadCsv(path);
        if (lines.size() != board.getHeight()) {
            throw new IllegalArgumentException("CSV file size does not match board height: " + lines.size() + " != " + board.getHeight());
        }

        for (List<com.garethahealy.gameoflife.records.Cell> line : lines) {
            if (line.size() != board.getWidth()) {
                throw new IllegalArgumentException("CSV file size does not match board width: " + lines.size() + " != " + board.getWidth());
            }

            for (com.garethahealy.gameoflife.records.Cell column : line) {
                Cell cell = board.getCellAt(column.xCords(), column.yCords());
                if (column.state() == Cell.State.DEAD) {
                    cell.kill();
                } else {
                    cell.resurrect();
                }
            }
        }

        //Commit
        for (Cell current : board.getCells()) {
            current.commitState();
        }
    }

    private List<List<com.garethahealy.gameoflife.records.Cell>> loadCsv(String path) throws IOException {
        List<List<com.garethahealy.gameoflife.records.Cell>> lines = new ArrayList<>();

        CSVFormat csvFormat = CSVFormat.Builder.create(CSVFormat.DEFAULT).get();
        try (Reader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            Iterable<CSVRecord> records = csvFormat.parse(reader);

            int y = 0;
            for (CSVRecord record : records) {
                List<com.garethahealy.gameoflife.records.Cell> line = new ArrayList<>();

                int x = 0;
                for (String value : record.values()) {
                    int state = Integer.parseInt(value);
                    line.add(new com.garethahealy.gameoflife.records.Cell(state == 0 ? Cell.State.DEAD : Cell.State.ALIVE, x, y));

                    x++;
                }

                y++;
                lines.add(line);
            }
        }

        return lines;
    }
}
