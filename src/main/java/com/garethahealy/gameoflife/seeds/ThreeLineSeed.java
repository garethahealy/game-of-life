package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;

public class ThreeLineSeed implements Seed {

    @Override
    public void process(GameBoard board) {
        board.getCellAt(1, 1).resurrect();
        board.getCellAt(2, 1).resurrect();
        board.getCellAt(3, 1).resurrect();

        //Commit
        for (Cell current : board.getCells()) {
            current.commitState();
        }
    }
}
