package com.garethahealy.gameoflife;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;
import com.garethahealy.gameoflife.records.Cells;
import com.garethahealy.gameoflife.seeds.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/board")
public class BoardResource {

    @Inject
    GameBoard gameBoard;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/seed/{seed}")
    public Cells seed(@PathParam(value = "seed") String seed, @QueryParam(value = "option") String option) throws IOException {
        Seed answer = getSeed(seed, option);
        answer.process(gameBoard);

        return convert(gameBoard.getCells());
    }

    private Seed getSeed(String seed, String option) {
        return switch (seed) {
            case "AllAliveSeed" -> new AllAliveSeed();
            case "GosperGliderGunSeed" -> new GosperGliderGunSeed();
            case "SquareSeed" -> new SquareSeed();
            case "ThreeLineSeed" -> new ThreeLineSeed();
            default -> new CsvLoaderSeed(option);
        };
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/next-generation")
    public Cells nextGeneration() {
        List<Cell> cells = gameBoard.nextGeneration();

        return convert(cells);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reset")
    public Cells reset() {
        List<Cell> cells = gameBoard.reset();

        return convert(cells);
    }

    private Cells convert(List<Cell> cells) {
        List<List<com.garethahealy.gameoflife.records.Cell>> allRows = new ArrayList<>();
        List<com.garethahealy.gameoflife.records.Cell> rowOfCells = new ArrayList<>();

        int currentY = 0;
        for (Cell current : cells) {
            if (currentY != current.getYCords()) {
                allRows.add(rowOfCells);
                rowOfCells = new ArrayList<>();
            }

            rowOfCells.add(current.toRecord());
            currentY = current.getYCords();
        }

        return new Cells(allRows);
    }
}
