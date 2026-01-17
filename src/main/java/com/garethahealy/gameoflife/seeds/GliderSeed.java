package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;

public class GliderSeed implements Seed {

    @Override
    public void process(GameBoard board) {
        board.getCellAt(2, 1).resurrect();
        board.getCellAt(3, 2).resurrect();
        board.getCellAt(1, 3).resurrect();
        board.getCellAt(2, 3).resurrect();
        board.getCellAt(3, 3).resurrect();

        //Commit
        for (Cell current : board.getCells()) {
            current.commitState();
        }
    }
}
