package fr.inria.gforge.spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtThrow;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import java.lang.reflect.Constructor;

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

        if (ctMethod == null) {
            return;
        }

        System.out.println("Class> " + ctMethod.getParent(CtClass.class).getQualifiedName());
        System.out.println("Method> " + ctMethod.getSimpleName());

        System.out.println("Return type> " + ctMethod.getType().getSimpleName());

        String defaultValue = null;

        if (ctMethod.getType().isPrimitive()) {
            System.out.println("isPrimitive> true");
            defaultValue = getTypeDefaultValue(ctMethod.getType().getSimpleName());
        } else {
            System.out.println("isPrimitive> false");
            defaultValue = getInitalizeString(ctMethod.getType().getActualClass());
        }

        if (defaultValue == null) {
            return;
        }

        System.out.println("Default value> " + defaultValue);

        snippet.setValue("return " + defaultValue);

        ctThrow.replace(snippet);

    }

    private String getInitalizeString(Class classToInitialize) {
        System.out.println("classToInitialize> " + classToInitialize.getSimpleName());
        System.out.println("isEnum> " + classToInitialize.isEnum());

        if (classToInitialize.isEnum()) {
            Object[] enumConstants = classToInitialize.getEnumConstants();
            return classToInitialize.getSimpleName() + "." + enumConstants[0].toString();
        }

        Constructor[] constructors = classToInitialize.getConstructors();

        if (constructors.length == 0) {
            return null;
        }

        Constructor constructor = constructors[0];

        System.out.println("constructors.length> " + constructors.length);

        for (int i = 1; i < constructors.length; i++) {
            if (constructors[i].getParameterCount() < constructor.getParameterCount()) {
                constructor = constructors[i];
            }
        }

        System.out.println("constructor.getParameterCount()> " + constructor.getParameterCount());
        if (constructor.getParameterCount() == 0) {
            return "new " + classToInitialize.getSimpleName() + "()";
        } else {
            String defaultValue = "new " + classToInitialize.getName() + "(";

            Class[] parameters = constructor.getParameterTypes();

            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].isPrimitive()) {
                    defaultValue += getTypeDefaultValue(parameters[i].getSimpleName());
                } else {
                    System.out.println(parameters[i]);
                    defaultValue += getInitalizeString(parameters[i]);
                }

                if (i < parameters.length - 1) {
                    defaultValue += ",";
                }
            }

            defaultValue += ")";

            return defaultValue;
        }
    }

    private String getTypeDefaultValue(String type) {
        PrimitiveType primitiveType = PrimitiveType.valueOf(type.toUpperCase());

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
                "-p", "fr.inria.gforge.spoon.processors.FastToSafeProcessor",
                "-i", "../commons-email/src/main/java",
                "-o", "target/spooned"
        });
    }
}
