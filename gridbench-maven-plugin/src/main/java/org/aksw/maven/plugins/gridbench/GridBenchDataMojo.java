package org.aksw.maven.plugins.gridbench;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.aksw.bench.geo.cmd.GridBenchDataGen;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.riot.system.StreamRDFWriter;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sys.JenaSystem;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.locationtech.jts.geom.Envelope;

@Mojo(name = "data", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GridBenchDataMojo
    extends AbstractMojo
{
    static { JenaSystem.init(); }

    @Parameter
    protected EnvelopeSpec envelopeSpec = new EnvelopeSpec();

    @Parameter(property = "rows", defaultValue = "2")
    protected int rows;

    @Parameter(property = "cols", defaultValue = "2")
    protected int cols;

    @Parameter(property = "graphs", defaultValue = "2")
    protected int graphs;

    // @Option(names = "--scale", required = true, defaultValue = "false", fallbackValue = "true", description = "Make polygons smaller the higher the graph id.")
    @Parameter(property = "scale", defaultValue = "false")
    protected boolean scale;

    @Parameter(property = "outputFile", defaultValue="${project.build.directory}/gridbench-data.nt")
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
        GridBenchDataGen dataGen = new GridBenchDataGen();

        Envelope envelope = envelopeSpec.getBuilder().build();
        Stream<Quad> quads = dataGen.generate(envelope, rows, cols, graphs, scale);

        Path outputPath = outputFile.toPath();
        Path outputFolder = outputPath.getParent();
        if (outputFolder != null) {
            Files.createDirectories(outputFolder);
        }

        try (OutputStream out = Files.newOutputStream(outputPath)) {
            RDFFormat rdfFormat = RDFFormat.NQUADS;
            StreamRDF writer = StreamRDFWriter.getWriterStream(out, rdfFormat);
            writer.start();
            quads.forEach(writer::quad);
            writer.finish();
            out.flush();
        }
    }
}
