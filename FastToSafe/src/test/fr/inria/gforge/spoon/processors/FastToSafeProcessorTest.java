package fr.inria.gforge.spoon.processors;

import org.junit.Test;
import spoon.Launcher;
import org.eclipse.jdt.internal.compiler.batch.Main;

import java.io.PrintWriter;

import static org.junit.Assert.assertTrue;

public class FastToSafeProcessorTest {
    @Test
    public void testCompileSourceCodeAfterProcessSourceCodeWithFastToSafeProcessor() throws Exception {
        final String[] args = {
                "-p", "fr.inria.gforge.spoon.processors.FastToSafeProcessor",
                "-i", "../commons-email/src/main/java",
                "-o", "target/spooned/commons-email/src/main/java"
        };

        final Launcher launcher = new Launcher();
        launcher.setArgs(args);
        launcher.run();

        assertTrue(Main.compile(Main.tokenize("-1.6 target/spooned/"), new PrintWriter(System.out), new PrintWriter(System.err), null));
    }
}
