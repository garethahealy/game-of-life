package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;

public class GosperGliderGunSeed implements Seed {

    @Override
    public void process(GameBoard board) {
        board.getCellAt(1, 5).resurrect();
        board.getCellAt(2, 5).resurrect();
        board.getCellAt(1, 6).resurrect();
        board.getCellAt(2, 6).resurrect();
        board.getCellAt(5, 4).resurrect();
        board.getCellAt(5, 5).resurrect();
        board.getCellAt(5, 6).resurrect();
        board.getCellAt(6, 3).resurrect();
        board.getCellAt(6, 7).resurrect();
        board.getCellAt(7, 2).resurrect();
        board.getCellAt(8, 2).resurrect();
        board.getCellAt(7, 8).resurrect();
        board.getCellAt(8, 8).resurrect();
        board.getCellAt(9, 5).resurrect();

        //Commit
        for (Cell current : board.getCells()) {
            current.commitState();
        }
    }
}
