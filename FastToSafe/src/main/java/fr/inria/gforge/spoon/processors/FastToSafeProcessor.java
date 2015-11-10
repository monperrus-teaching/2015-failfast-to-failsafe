package fr.inria.gforge.spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtThrow;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.FactoryImpl;
import spoon.reflect.reference.CtTypeReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ServiceLoader;

public class FastToSafeProcessor extends AbstractProcessor<CtThrow> {

    private int throwNumber = 0;

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

        throwNumber++;

        System.out.println("-------------------------");
        System.out.println(throwNumber);
        System.out.println("-------------------------");
        System.out.println("> " + ctThrow);
        System.out.println("> Position> " + ctThrow.getPosition());

        CtCodeSnippetStatement snippet = getFactory().Core().createCodeSnippetStatement();

        CtMethod ctMethod = ctThrow.getParent(CtMethod.class);

        if (ctMethod == null) {
            CtConstructor ctConstructor = ctThrow.getParent(CtConstructor.class);
            if (ctConstructor != null) {
                System.out.println("> Constructor> " + ctConstructor.getSimpleName());
                snippet.setValue("return");
                System.out.println("> snippet> return");
                ctThrow.replace(snippet);
            }
            return;
        }

        System.out.println("> Class> " + ctMethod.getParent(CtClass.class).getQualifiedName());
        System.out.println("> Method> " + ctMethod.getSimpleName());

        System.out.println("> Return type> " + ctMethod.getType().getSimpleName());

        String defaultValue = null;

        if (ctMethod.getType().isPrimitive()) {
            System.out.println("> isPrimitive> true");
            defaultValue = getTypeDefaultValue(ctMethod.getType().getSimpleName());
        } else {
            System.out.println("> isPrimitive> false");
            Class classToInitialize = ctMethod.getType().getActualClass();
            System.out.println("> classToInitialize> " + classToInitialize.getSimpleName());
            System.out.println("> isEnum> " + classToInitialize.isEnum());
            System.out.println("> isInterface> " + classToInitialize.isInterface());
            System.out.println("> isAbstract> " + Modifier.isAbstract(classToInitialize.getModifiers()));

            if (Modifier.isAbstract(classToInitialize.getModifiers())) {
                Class currentClass = ctMethod.getParent(CtClass.class).getActualClass();
                if (classToInitialize.isAssignableFrom(currentClass)) {
                    System.out.println("> isAssignableFrom> " + currentClass.getSimpleName() + " is assignable for " + classToInitialize.getSimpleName());
                    classToInitialize = currentClass;
                } else {
                    System.out.println("> isAssignableFrom> " + currentClass.getSimpleName() + " is not assignable for " + classToInitialize.getSimpleName());
                    classToInitialize = Object.class;
                }
            }
            
            defaultValue = getInitalizeString(classToInitialize);
        }

        System.out.println("> Default value> " + defaultValue);

        if (defaultValue.equals("void")) {
            System.out.println("> snippet> return");
            snippet.setValue("return");
        } else {
            System.out.println("> snippet> return " + defaultValue);
            snippet.setValue("return " + defaultValue);
        }

        ctThrow.replace(snippet);

    }

    private String getInitalizeString(Class classToInitialize) {
        System.out.println("> classToInitialize> " + classToInitialize.getSimpleName());
        System.out.println("> isEnum> " + classToInitialize.isEnum());
        System.out.println("> isInterface> " + classToInitialize.isInterface());
        System.out.println("> isAbstract> " + Modifier.isAbstract(classToInitialize.getModifiers()));

        // TODO: abstract class
        if (Modifier.isAbstract(classToInitialize.getModifiers())) {

        }

        if (classToInitialize.isEnum()) {
            Object[] enumConstants = classToInitialize.getEnumConstants();
            return classToInitialize.getSimpleName() + "." + enumConstants[0].toString();
        }

        if (classToInitialize.isInterface()) {
            return "new Object()";
        }

        Constructor[] constructors = classToInitialize.getConstructors();

        if (constructors.length == 0) {
            return "null";
        }

        Constructor constructor = constructors[0];

        System.out.println("> constructors.length> " + constructors.length);

        for (int i = 1; i < constructors.length; i++) {
            if (constructors[i].getParameterCount() < constructor.getParameterCount()) {
                constructor = constructors[i];
            }
        }

        System.out.println("> constructor.getParameterCount> " + constructor.getParameterCount());
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
