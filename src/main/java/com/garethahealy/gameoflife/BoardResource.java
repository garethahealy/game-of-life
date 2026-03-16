package com.garethahealy.gameoflife;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;
import io.smallrye.mutiny.Uni;
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
    public Uni<Cell[][]> nextGeneration() {
        return Uni.createFrom().item(gameBoard::nextGeneration);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reset")
    public Uni<Cell[][]> reset() {
        return Uni.createFrom().item(gameBoard::reset);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/randomize")
    public Uni<Cell[][]> randomize(@QueryParam("aliveProbability") Double aliveProbability) {
        double probability = aliveProbability == null ? 0.3 : aliveProbability;
        return Uni.createFrom().item(() -> gameBoard.randomize(probability));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cell/{x}/{y}/toggle")
    public Uni<Cell[][]> toggleCell(@PathParam("x") int x, @PathParam("y") int y) {
        return Uni.createFrom().item(() -> {
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
        });
    }
}
