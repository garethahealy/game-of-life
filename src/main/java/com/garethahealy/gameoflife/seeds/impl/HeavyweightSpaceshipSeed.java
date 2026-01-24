package com.garethahealy.gameoflife.seeds.impl;

import com.garethahealy.gameoflife.seeds.Seed;
import com.garethahealy.gameoflife.seeds.SeedPattern;

public class HeavyweightSpaceshipSeed implements Seed {

    @Override
    public SeedPattern process() {
        String[] pattern = {
            "...O..O",
            "O...O.O",
            ".O....O",
            "OOOOOO."
        };
        return SeedPattern.centered(pattern);
    }
}
