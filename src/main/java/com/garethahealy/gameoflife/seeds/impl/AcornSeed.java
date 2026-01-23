package com.garethahealy.gameoflife.seeds.impl;

import com.garethahealy.gameoflife.seeds.Seed;
import com.garethahealy.gameoflife.seeds.SeedPattern;

public class AcornSeed implements Seed {

    @Override
    public SeedPattern process() {
        String[] pattern = {
                ".O.....",
                "...O...",
                "OO..OOO"
        };
        return SeedPattern.centered(pattern);
    }
}
