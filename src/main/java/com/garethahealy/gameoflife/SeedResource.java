package com.garethahealy.gameoflife;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;
import com.garethahealy.gameoflife.seeds.PatternApplier;
import com.garethahealy.gameoflife.seeds.Seed;
import com.garethahealy.gameoflife.seeds.SeedPattern;
import com.garethahealy.gameoflife.seeds.SeedRegistry;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/seeds")
public class SeedResource {

    private final GameBoard gameBoard;
    private final PatternApplier patternApplier;
    private final SeedRegistry seedRegistry;

    @Inject
    public SeedResource(GameBoard gameBoard, PatternApplier patternApplier, SeedRegistry seedRegistry) {
        this.gameBoard = gameBoard;
        this.patternApplier = patternApplier;
        this.seedRegistry = seedRegistry;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> seeds() {
        return seedRegistry.getSupportedSeeds();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{seed}")
    public Cell[][] seed(@PathParam(value = "seed") String seed, @QueryParam(value = "option") String option) {
        Seed answer = seedRegistry.getSeed(seed, option);
        SeedPattern pattern;
        try {
            pattern = answer.process();
        } catch (java.io.UncheckedIOException exception) {
            throw new WebApplicationException("Unable to load seed file", exception, 400);
        }

        patternApplier.applyAndCommit(gameBoard, pattern);

        return gameBoard.getCells();
    }
}
