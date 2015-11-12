package fr.inria.gforge.spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;

import java.util.HashMap;
import java.util.Map;

public class FastToSafeTestProcessor extends AbstractProcessor<CtAnnotation> {

    private int mExpectedNumber;
    private HashMap<String, String> mElementsProcessed;

    public FastToSafeTestProcessor() {
        mExpectedNumber = 0;

        mElementsProcessed = new HashMap<String, String>();
    }

    @Override
    public boolean isToBeProcessed(CtAnnotation ctAnnotation) {
        Map<String, Object> elementValues = ctAnnotation.getElementValues();

        if (elementValues.containsKey("expected")) {
            return true;
        }

        return false;
    }

    @Override
    public void processingDone() {
        System.out.println("-------------------------");
        System.out.println("Stats");
        System.out.println("-------------------------");
        System.out.println("Expected number: " + mExpectedNumber);
        System.out.println("Elements processed: " + mElementsProcessed.size());
        for (String className : mElementsProcessed.keySet()) {
            System.out.println("|-" + className);
        }
    }

    @Override
    public void process(CtAnnotation ctAnnotation) {
        mExpectedNumber++;

        mElementsProcessed.put(ctAnnotation.getParent(CtClass.class).getQualifiedName(), null);

        Map<String, Object> elementValues = new HashMap<String, Object>(ctAnnotation.getElementValues());

        elementValues.remove("expected");

        ctAnnotation.setElementValues(elementValues);
    }

    public static void main(String[] args) throws Exception {
        spoon.Launcher.main(new String[] {
                "-p", "fr.inria.gforge.spoon.processors.FastToSafeTestProcessor",
                "-i", "../commons-email/src",
                "-o", "target/spooned/commons-email/src"
        });
    }
}
