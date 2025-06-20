package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.GameBoard;

import java.io.IOException;

public interface Seed {

    void process(GameBoard board) throws IOException;
}
