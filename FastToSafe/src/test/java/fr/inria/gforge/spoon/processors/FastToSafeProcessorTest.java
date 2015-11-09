package fr.inria.gforge.spoon.processors;

import org.junit.Before;
import org.junit.Test;
import spoon.Launcher;
import spoon.processing.ProcessingManager;
import spoon.reflect.factory.Factory;
import spoon.support.QueueProcessingManager;

import fr.inria.gforge.spoon.processors.FastToSafeProcessor;

import static org.junit.Assert.assertEquals;

public class FastToSafeProcessorTest {

    private Launcher mLauncher;

    @Before
    public void before() {
        mLauncher = new Launcher();
    }

    @Test
    public void testFastToSafeProcessor() throws Exception {
        testFastToSafeProcessorOnSrc();
        testFastToSafeProcessorOnSpoonedSrc();
    }

    private void testFastToSafeProcessorOnSrc() throws Exception {
        String[] args = {
                "-i", "src/test/resources/src",
                "-o", "target/spooned"
        };

        mLauncher.setArgs(args);
        mLauncher.run();

        Factory factory = mLauncher.getFactory();
        ProcessingManager processingManager = new QueueProcessingManager(factory);
        FastToSafeProcessor processor = new FastToSafeProcessor();

        processingManager.addProcessor(processor);
        processingManager.process(factory.Class().getAll());
        //assertEquals(3, processor.throwNumber);
    }

    private void testFastToSafeProcessorOnSpoonedSrc() throws Exception {
        String[] args = {
                "-i", "target/spooned/"
        };

        mLauncher.setArgs(args);
        mLauncher.run();

        Factory factory = mLauncher.getFactory();
        ProcessingManager processingManager = new QueueProcessingManager(factory);
        FastToSafeProcessor processor = new FastToSafeProcessor();

        processingManager.addProcessor(processor);
        processingManager.process(factory.Class().getAll());
        //assertEquals(0, processor.throwNumber);
    }
}
