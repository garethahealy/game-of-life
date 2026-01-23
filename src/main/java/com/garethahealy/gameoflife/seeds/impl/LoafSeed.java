package com.garethahealy.gameoflife.seeds.impl;

import com.garethahealy.gameoflife.seeds.Seed;
import com.garethahealy.gameoflife.seeds.SeedPattern;

public class LoafSeed implements Seed {

    @Override
    public SeedPattern process() {
        String[] pattern = {
                ".OO.",
                "O..O",
                ".O.O",
                "..O."
        };
        return SeedPattern.centered(pattern);
    }
}
