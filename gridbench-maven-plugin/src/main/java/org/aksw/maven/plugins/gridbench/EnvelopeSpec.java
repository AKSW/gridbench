package org.aksw.maven.plugins.gridbench;

import org.aksw.bench.geo.cmd.EnvelopeBuilder;
import org.apache.maven.plugins.annotations.Parameter;

public class EnvelopeSpec {
    protected EnvelopeBuilder builder = new EnvelopeBuilder();

    @Parameter(property = "maxX", defaultValue = "90") // 180
    public void setMinX(double minX) {
        builder.setMinX(minX);
    }

    @Parameter(property = "maxX", defaultValue = "90") // 180
    public void setMaxX(double maxX) {
        builder.setMaxX(maxX);
    }

    @Parameter(property = "minY", defaultValue = "-90")
    public void setMinY(double minY) {
        builder.setMinY(minY);
    }

    @Parameter(property = "maxY", defaultValue = "90")
    public void setMaxY(double maxY) {
        builder.setMaxY(maxY);
    }

    public EnvelopeBuilder getBuilder() {
        return builder;
    }
}
