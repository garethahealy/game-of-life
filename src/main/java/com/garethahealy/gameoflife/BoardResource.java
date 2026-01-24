package com.garethahealy.gameoflife;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/board")
public class BoardResource {

    private final GameBoard gameBoard;

    @Inject
    public BoardResource(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/next-generation")
    public Cell[][] nextGeneration() {
        gameBoard.nextGeneration();

        return gameBoard.getCells();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reset")
    public Cell[][] reset() {
        gameBoard.reset();

        return gameBoard.getCells();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/randomize")
    public Cell[][] randomize(@QueryParam("aliveProbability") Double aliveProbability) {
        double probability = aliveProbability == null ? 0.3 : aliveProbability;
        gameBoard.randomize(probability);

        return gameBoard.getCells();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cell/{x}/{y}/toggle")
    public Cell[][] toggleCell(@PathParam("x") int x, @PathParam("y") int y) {
        Cell cell = gameBoard.getCellAt(x, y);
        if (cell == null) {
            throw new WebApplicationException("Cell not found", 404);
        }

        if (cell.isAlive()) {
            cell.kill();
        } else {
            cell.resurrect();
        }
        cell.commitState();

        return gameBoard.getCells();
    }
}
