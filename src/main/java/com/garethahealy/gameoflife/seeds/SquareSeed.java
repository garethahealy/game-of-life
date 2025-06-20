package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;

public class SquareSeed implements Seed {

    @Override
    public void process(GameBoard board) {
        board.getCellAt(1, 1).resurrect();
        board.getCellAt(2, 1).resurrect();
        board.getCellAt(2, 2).resurrect();
        board.getCellAt(1, 2).resurrect();

        //Commit
        for (Cell current : board.getCells()) {
            current.commitState();
        }
    }
}
