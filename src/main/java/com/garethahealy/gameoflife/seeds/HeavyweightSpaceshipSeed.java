package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;

public class HeavyweightSpaceshipSeed implements Seed {

    @Override
    public void process(GameBoard board) {
        int offsetX = 10;
        int offsetY = 10;

        board.getCellAt(0 + offsetX, 1 + offsetY).resurrect();
        board.getCellAt(3 + offsetX, 0 + offsetY).resurrect();
        board.getCellAt(6 + offsetX, 0 + offsetY).resurrect();
        board.getCellAt(4 + offsetX, 1 + offsetY).resurrect();
        board.getCellAt(6 + offsetX, 1 + offsetY).resurrect();
        board.getCellAt(1 + offsetX, 2 + offsetY).resurrect();
        board.getCellAt(6 + offsetX, 2 + offsetY).resurrect();
        board.getCellAt(0 + offsetX, 3 + offsetY).resurrect();
        board.getCellAt(1 + offsetX, 3 + offsetY).resurrect();
        board.getCellAt(2 + offsetX, 3 + offsetY).resurrect();
        board.getCellAt(3 + offsetX, 3 + offsetY).resurrect();
        board.getCellAt(4 + offsetX, 3 + offsetY).resurrect();
        board.getCellAt(5 + offsetX, 3 + offsetY).resurrect();

        //Commit
        for (Cell current : board.getCells()) {
            current.commitState();
        }
    }
}
