package com.garethahealy.gameoflife.seeds.impl;

import com.garethahealy.gameoflife.seeds.Seed;
import com.garethahealy.gameoflife.seeds.SeedPattern;

public class ToadSeed implements Seed {

    @Override
    public SeedPattern process() {
        String[] pattern = {
            ".OOO",
            "OOO."
        };
        return SeedPattern.centered(pattern);
    }
}
