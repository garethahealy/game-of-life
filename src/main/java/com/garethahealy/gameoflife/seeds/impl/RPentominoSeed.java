package com.garethahealy.gameoflife.seeds.impl;

import com.garethahealy.gameoflife.seeds.Seed;
import com.garethahealy.gameoflife.seeds.SeedPattern;

public class RPentominoSeed implements Seed {

    @Override
    public SeedPattern process() {
        String[] pattern = {
            ".OO",
            "OO.",
            ".O."
        };
        return SeedPattern.centered(pattern);
    }
}
