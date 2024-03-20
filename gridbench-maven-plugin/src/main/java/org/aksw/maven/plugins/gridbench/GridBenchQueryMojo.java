package org.aksw.maven.plugins.gridbench;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.aksw.bench.geo.cmd.GridBenchQueryGen;
import org.apache.jena.query.Query;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.locationtech.jts.geom.Envelope;

@Mojo(name = "query", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GridBenchQueryMojo
    extends AbstractMojo
{
    @Parameter
    protected EnvelopeSpec envelopeSpec = new EnvelopeSpec();

    @Parameter(property = "rows", defaultValue = "2")
    protected int rows;

    @Parameter(property = "cols", defaultValue = "2")
    protected int cols;

    @Parameter(property = "graphs", defaultValue = "2")
    protected int graphs;

    @Parameter(property = "allGraphs", defaultValue = "false")
    protected boolean allGraphs;

    /** If --allGraphs is given. Use union default graph rather than ?g. */
    @Parameter(property = "unionDefaultGraph", defaultValue = "false")
    protected boolean unionDefaultGraph;

    /** Generate queries by traversing grid cells from the last to the first. */
    @Parameter(property = "reverse", defaultValue = "false")
    protected boolean reverse;

    @Parameter(property = "outputFile", defaultValue="${project.build.directory}/gridbench-queries.rq")
    protected File outputFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            doExecute();
        } catch (Exception e) {
            throw new MojoExecutionException(e);
        }
    }

    public void doExecute() throws IOException {
        GridBenchQueryGen queryGen = new GridBenchQueryGen();

        Envelope envelope = envelopeSpec.getBuilder().build();
        Stream<Query> queries = queryGen.generate(envelope, rows, cols, graphs, reverse, allGraphs, unionDefaultGraph);

        Path outputPath = outputFile.toPath();
        Path outputFolder = outputPath.getParent();
        if (outputFolder != null) {
            Files.createDirectories(outputFolder);
        }

        try (PrintWriter writer = new PrintWriter(Files.newOutputStream(outputPath))) {
            queries.sequential().forEach(query -> writer.println(query));
            writer.flush();
        }
    }
}
