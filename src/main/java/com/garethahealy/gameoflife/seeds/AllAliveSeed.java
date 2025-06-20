package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;

public class AllAliveSeed implements Seed {

    @Override
    public void process(GameBoard board) {
        for (Cell current : board.getCells()) {
            current.resurrect();
        }

        //Commit
        for (Cell current : board.getCells()) {
            current.commitState();
        }
    }
}
