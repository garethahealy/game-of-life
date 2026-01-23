package com.garethahealy.gameoflife.seeds;

import java.util.Objects;

public final class SeedPattern {

    private final String[] rows;
    private final Integer offsetX;
    private final Integer offsetY;

    private SeedPattern(String[] rows, Integer offsetX, Integer offsetY) {
        this.rows = validateRows(rows);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public static SeedPattern centered(String[] rows) {
        return new SeedPattern(rows, null, null);
    }

    public static SeedPattern atOffset(String[] rows, int offsetX, int offsetY) {
        return new SeedPattern(rows, offsetX, offsetY);
    }

    public String[] rows() {
        return rows.clone();
    }

    public boolean hasOffset() {
        return offsetX != null && offsetY != null;
    }

    public int offsetX() {
        return offsetX;
    }

    public int offsetY() {
        return offsetY;
    }

    public int width() {
        int width = 0;
        for (String row : rows) {
            width = Math.max(width, row.length());
        }
        return width;
    }

    public int height() {
        return rows.length;
    }

    private static String[] validateRows(String[] rows) {
        Objects.requireNonNull(rows, "rows");
        if (rows.length == 0) {
            throw new IllegalArgumentException("Pattern rows cannot be empty");
        }

        for (String row : rows) {
            if (row == null) {
                throw new IllegalArgumentException("Pattern row cannot be null");
            }
        }

        return rows.clone();
    }
}
