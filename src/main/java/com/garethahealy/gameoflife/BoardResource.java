package com.garethahealy.gameoflife;

import com.garethahealy.gameoflife.model.GameBoard;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
    public com.garethahealy.gameoflife.model.Cell[][] nextGeneration() {
        gameBoard.nextGeneration();

        return gameBoard.getCells();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reset")
    public com.garethahealy.gameoflife.model.Cell[][] reset() {
        gameBoard.reset();

        return gameBoard.getCells();
    }
}
