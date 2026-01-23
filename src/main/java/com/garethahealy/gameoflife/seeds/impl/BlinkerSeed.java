package com.garethahealy.gameoflife.seeds.impl;

import com.garethahealy.gameoflife.seeds.Seed;
import com.garethahealy.gameoflife.seeds.SeedPattern;

public class BlinkerSeed implements Seed {

    @Override
    public SeedPattern process() {
        String[] pattern = {"OOO"};
        return SeedPattern.centered(pattern);
    }
}
