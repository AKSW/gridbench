package org.aksw.bench.geo.cmd;

import org.locationtech.jts.geom.Envelope;

public class EnvelopeBuilder {
    // @Option(names = "--minX", defaultValue = "-90") // -180
    protected double minX;

    // @Option(names = "--maxX", defaultValue = "90") // 180
    protected double maxX;

    // @Option(names = "--minY", defaultValue = "-90")
    protected double minY;

    //@Option(names = "--maxY", defaultValue = "90")
    protected double maxY;

    public double getMinX() {
        return minX;
    }

    public EnvelopeBuilder setMinX(double minX) {
        this.minX = minX;
        return this;
    }

    public double getMaxX() {
        return maxX;
    }

    public EnvelopeBuilder setMaxX(double maxX) {
        this.maxX = maxX;
        return this;
    }

    public double getMinY() {
        return minY;
    }

    public EnvelopeBuilder setMinY(double minY) {
        this.minY = minY;
        return this;
    }

    public double getMaxY() {
        return maxY;
    }

    public EnvelopeBuilder setMaxY(double maxY) {
        this.maxY = maxY;
        return this;
    }

    public Envelope build() {
        return new Envelope(minX, maxX, minY, maxY);
    }
}
