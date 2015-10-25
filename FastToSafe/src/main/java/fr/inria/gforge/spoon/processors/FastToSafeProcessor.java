package fr.inria.gforge.spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtThrow;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;


public class FastToSafeProcessor extends AbstractProcessor<CtThrow> {

    private enum PrimitiveType {
        BYTE,
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        CHAR,
        BOOLEAN,
        VOID
    }

    @Override
    public void process(CtThrow ctThrow) {

        System.out.println("-------------------------");
        System.out.println(ctThrow);

        CtCodeSnippetStatement snippet = getFactory().Core().createCodeSnippetStatement();

        CtMethod ctMethod = ctThrow.getParent(CtMethod.class);

        System.out.println("Return type> " + ctMethod.getType().getSimpleName());

        String defaultValue = null;

        if (ctMethod.getType().isPrimitive()) {
            defaultValue = getTypeDefaultValue(ctMethod.getType());
        } else {
            // TODO: See if default constructor takes arguments.
            defaultValue = "new " + ctMethod.getType().getSimpleName() + "()";
        }



        System.out.println("Default value> " + defaultValue);

        String value = null;

        if (ctMethod.getType().getSimpleName().equals("void")) {
            value = "return";
        } else if (ctMethod.getType().isPrimitive()) {

        } else {

            // TODO: Check if the contructor takes parameters.

            value = "return new " + ctMethod.getType().getSimpleName() + "()";
        }

        snippet.setValue(value);

        ctThrow.replace(snippet);

    }

    private String getTypeDefaultValue(CtTypeReference ctTypeReference) {
        PrimitiveType primitiveType = PrimitiveType.valueOf(ctTypeReference.getSimpleName().toUpperCase());

        switch (primitiveType) {
            case LONG:
                return "0L";
            case FLOAT:
                return "0.0f";
            case DOUBLE:
                return "0.0d";
            case CHAR:
                return "\u0000";
            case BOOLEAN:
                return "false";
            case VOID:
                return "void";
            default: // byte, short and int
                return "0";
        }
    }

    public static void main(String[] args) throws Exception {
        spoon.Launcher.main(new String[] {
                "-p",
                "fr.inria.gforge.spoon.processors.FastToSafeProcessor",
                "-i",
                "src/main/java/fr/inria/gforge/spoon/examples/"
        });
    }
}
