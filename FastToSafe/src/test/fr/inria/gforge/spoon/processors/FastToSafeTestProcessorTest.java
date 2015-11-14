package fr.inria.gforge.spoon.processors;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.junit.Test;
import spoon.Launcher;

import java.io.PrintWriter;

import static org.junit.Assert.assertTrue;

public class FastToSafeTestProcessorTest {

    @Test
    public void testCompileSourceCodeAfterProcessSourceCodeWithFastToSafeTestProcessor() throws Exception {
        final String[] args = {
                "-p", "fr.inria.gforge.spoon.processors.FastToSafeTestProcessor",
                "-i", "../commons-email/src/test/java",
                "-o", "target/spooned/commons-email/src/test/java"
        };

        final Launcher launcher = new Launcher();
        launcher.setArgs(args);
        launcher.run();

        assertTrue(Main.compile(Main.tokenize("-1.6 target/spooned/"), new PrintWriter(System.out), new PrintWriter(System.err), null));
    }
}
