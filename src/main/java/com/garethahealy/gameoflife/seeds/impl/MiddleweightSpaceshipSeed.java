package com.garethahealy.gameoflife.seeds.impl;

import com.garethahealy.gameoflife.seeds.Seed;
import com.garethahealy.gameoflife.seeds.SeedPattern;

public class MiddleweightSpaceshipSeed implements Seed {

    @Override
    public SeedPattern process() {
        String[] pattern = {
            "...O..",
            ".O...O",
            "O.....",
            "O....O",
            "OOOOO."
        };
        return SeedPattern.centered(pattern);
    }
}
