package com.garethahealy.gameoflife.seeds.impl;

import com.garethahealy.gameoflife.seeds.Seed;
import com.garethahealy.gameoflife.seeds.SeedPattern;

public class BeaconSeed implements Seed {

    @Override
    public SeedPattern process() {
        String[] pattern = {
                "OO..",
                "OO..",
                "..OO",
                "..OO"
        };
        return SeedPattern.centered(pattern);
    }
}
