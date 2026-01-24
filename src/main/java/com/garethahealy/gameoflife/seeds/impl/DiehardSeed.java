package com.garethahealy.gameoflife.seeds.impl;

import com.garethahealy.gameoflife.seeds.Seed;
import com.garethahealy.gameoflife.seeds.SeedPattern;

public class DiehardSeed implements Seed {

    @Override
    public SeedPattern process() {
        String[] pattern = {
            "......O.",
            "OO......",
            ".O...OOO"
        };
        return SeedPattern.centered(pattern);
    }
}
