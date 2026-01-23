package com.garethahealy.gameoflife.seeds;

import java.io.IOException;

public interface FileSeed extends Seed {

    SeedPattern processFile() throws IOException;

    @Override
    default SeedPattern process() {
        try {
            return processFile();
        } catch (IOException exception) {
            throw new java.io.UncheckedIOException(exception);
        }
    }
}
