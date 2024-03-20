package org.aksw.bench.geo.cmd;

import picocli.CommandLine.Option;

public class GridOffsetsOption {
    protected EnvelopeBuilder builder = new EnvelopeBuilder();

    public EnvelopeBuilder getBuilder() {
        return builder;
    }

    @Option(names = "--maxX", defaultValue = "90") // 180
    public void setMinX(double minX) {
        builder.setMinX(minX);
    }

    @Option(names = "--maxX", defaultValue = "90") // 180
    public void setMaxX(double maxX) {
        builder.setMaxX(maxX);
    }

    @Option(names = "--minY", defaultValue = "-90")
    public void setMinY(double minY) {
        builder.setMinY(minY);
    }

    @Option(names = "--maxY", defaultValue = "90")
    public void setMaxY(double maxY) {
        builder.setMaxY(maxY);
    }
}
