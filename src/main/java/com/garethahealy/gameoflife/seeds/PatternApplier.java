package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Objects;

@ApplicationScoped
public class PatternApplier {

    public void apply(GameBoard board, SeedPattern pattern) {
        Objects.requireNonNull(board, "board");
        Objects.requireNonNull(pattern, "pattern");

        String[] rows = pattern.rows();
        int offsetX = pattern.hasOffset()
                ? pattern.offsetX()
                : (board.getWidth() - pattern.width()) / 2;

        int offsetY = pattern.hasOffset()
                ? pattern.offsetY()
                : (board.getHeight() - pattern.height()) / 2;

        for (int y = 0; y < rows.length; y++) {
            String row = rows[y];
            for (int x = 0; x < row.length(); x++) {
                char cellValue = row.charAt(x);
                if (cellValue == 'O' || cellValue == 'o') {
                    Cell cell = board.getCellAt(x + offsetX, y + offsetY);
                    if (cell == null) {
                        throw new IllegalArgumentException("Pattern out of bounds at x=" + (x + offsetX) + ", y=" + (y + offsetY));
                    }

                    cell.resurrect();
                }
            }
        }
    }

    public void applyAndCommit(GameBoard board, SeedPattern pattern) {
        board.reset();

        apply(board, pattern);
        commit(board);
    }

    private void commit(GameBoard board) {
        for (Cell[] row : board.getCells()) {
            for (Cell current : row) {
                current.commitState();
            }
        }
    }
}
