package com.garethahealy.gameoflife.records;

import com.garethahealy.gameoflife.model.Cell.State;

public record Cell(State state, int xCords, int yCords) {

}
