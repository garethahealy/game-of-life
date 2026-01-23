package com.garethahealy.gameoflife.seeds.impl;

import com.garethahealy.gameoflife.seeds.Seed;
import com.garethahealy.gameoflife.seeds.SeedPattern;

public class GosperGliderGunSeed implements Seed {

    @Override
    public SeedPattern process() {
        String[] pattern = {
                "......OO.",
                ".....O...",
                "....O....",
                "OO..O...O",
                "OO..O....",
                ".....O...",
                "......OO."
        };
        return SeedPattern.centered(pattern);
    }
}
