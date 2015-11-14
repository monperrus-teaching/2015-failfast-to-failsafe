package fr.inria.gforge.spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;

public class FastToSafeProcessor extends AbstractProcessor<CtThrow> {

    private static final boolean PROCESS_PRIMITIVE = true;
    private static final boolean PROCESS_ENUM = true;
    private static final boolean PROCESS_INTERFACE = true;
    private static final boolean PROCESS_ABSTRACT = true;
    private static final boolean PROCESS_OBJECT = true;
    private static final boolean PROCESS_CONSTRUCTOR = true;


    private int mThrowNumber;
    private int mThrowByPrimitive;
    private int mThrowByEnum;
    private int mThrowByInterface;
    private int mThrowByAbstract;
    private int mThrowByObject;
    private int mThrowByConstructor;

    private HashMap<String, String> mElementsProcessed;
    private ProcessTypeEnum mProcessType;


    public FastToSafeProcessor() {
        mThrowNumber = 0;
        mThrowByPrimitive = 0;
        mThrowByEnum = 0;
        mThrowByObject = 0;
        mThrowByInterface = 0;
        mThrowByAbstract = 0;
        mThrowByConstructor = 0;

        mElementsProcessed = new HashMap<String, String>();
    }

    @Override
    public boolean isToBeProcessed(CtThrow ctThrow) {
        CtMethod ctMethod = ctThrow.getParent(CtMethod.class);

        if (ctMethod == null) {
            // if ctMethod is null, maybe it's a constructor ?
            CtConstructor ctConstructor = ctThrow.getParent(CtConstructor.class);
            if (ctConstructor != null) {
                if (PROCESS_CONSTRUCTOR) {
                    return true;
                }
            }

            return false;
        }

        CtTry ctTry = ctThrow.getParent(CtTry.class);

        if (ctTry != null) {
            boolean stop = false;
            for (CtStatement ctStatement : ctTry.getBody().getStatements()) {
                if (ctStatement.toString().contains(ctThrow.toString())) {
                    stop = true;
                    break;
                }
            }

            if (stop) {
                return false;
            }
        }

        CtTypeReference ctTypeReference = ctMethod.getType();
        Class classToProcess = ctTypeReference.getActualClass();

        if (ctTypeReference.isPrimitive() && PROCESS_PRIMITIVE) {
            mProcessType = ProcessTypeEnum.PRIMITIVE;
            return true;
        } else if (classToProcess.isEnum() && PROCESS_ENUM) {
            mProcessType = ProcessTypeEnum.ENUM;
            return true;
        } else if (classToProcess.isInterface() && PROCESS_INTERFACE) {
            mProcessType = ProcessTypeEnum.INTERFACE;
            return true;
        } else if (Modifier.isAbstract(classToProcess.getModifiers()) && PROCESS_ABSTRACT) {
            mProcessType = ProcessTypeEnum.ABSTRACT;
            return true;
        } else if (PROCESS_OBJECT) {
            mProcessType = ProcessTypeEnum.OBJECT;
            return true;
        }

        return false;
    }

    @Override
    public void processingDone() {
        System.out.println("-------------------------");
        System.out.println("Stats");
        System.out.println("-------------------------");
        System.out.println("Throw number: " + mThrowNumber);
        System.out.println("Throw replaced by primitive: " + mThrowByPrimitive);
        System.out.println("Throw replaced by enum: " + mThrowByEnum);
        System.out.println("Throw replaced by object: " + mThrowByObject);
        System.out.println("Throw replaced by interface: " + mThrowByInterface);
        System.out.println("Throw replaced by abstract: " + mThrowByAbstract);
        System.out.println("Throw replaced by constructor: " + mThrowByConstructor);
        System.out.println("Elements processed: " + mElementsProcessed.size());
        for (String className : mElementsProcessed.keySet()) {
            System.out.println("|-" + className);
        }
    }

    private void processPrimitive(CtThrow ctThrow) {
        mThrowByPrimitive++;

        CtMethod ctMethod = ctThrow.getParent(CtMethod.class);
        String defaultValue = PrimitiveType.getTypeDefaultValue(ctMethod.getType().getSimpleName());
        String value = "return";

        if (!"void".equals(defaultValue)) {
            value += " " + defaultValue;
        }

        applySnippet(ctThrow, value);
    }

    private void processInterface(CtThrow ctThrow) {
        mThrowByInterface++;

        applySnippet(ctThrow, "return null");
    }

    private void processEnum(CtThrow ctThrow) {
        mThrowByEnum++;

        CtMethod ctMethod = ctThrow.getParent(CtMethod.class);
        Class enumToInitialize = ctMethod.getType().getActualClass();

        Object[] enumConstants = enumToInitialize.getEnumConstants();

        String value = "return null";

        if (enumConstants.length == 0) {
            System.out.println("[ERROR]: enumConstants.length == 0");
            applySnippet(ctThrow, value);
            return;
        }

        value = enumToInitialize.getSimpleName() + "." + enumConstants[0].toString();
        applySnippet(ctThrow, value);
    }

    private void processAbstract(CtThrow ctThrow) {
        mThrowByAbstract++;

        CtMethod ctMethod = ctThrow.getParent(CtMethod.class);
        Class abstractToInitialize = ctMethod.getType().getActualClass();
        Class currentClass = ctMethod.getParent(CtClass.class).getActualClass();

        String value = "return null";

        if (abstractToInitialize.isAssignableFrom(currentClass) && !abstractToInitialize.getSimpleName().equals(currentClass.getSimpleName())) {
            value = "return this";
        }

        applySnippet(ctThrow, value);
    }

    private void processObject(CtThrow ctThrow) {
        mThrowByObject++;

        CtMethod ctMethod = ctThrow.getParent(CtMethod.class);
        Class objectToInitialize = ctMethod.getType().getActualClass();

        String value = "return ";
        value += getObjectInstantiationString(objectToInitialize);
        applySnippet(ctThrow, value);
    }

    private void processConstructor(CtThrow ctThrow) {
        mThrowByConstructor++;

        String value = "return";
        applySnippet(ctThrow, value);
    }

    @Override
    public void process(CtThrow ctThrow) {

        mThrowNumber++;

        System.out.println("-------------------------");
        System.out.println(ctThrow);
        System.out.println("|-Position: " + ctThrow.getPosition());

        CtMethod ctMethod = ctThrow.getParent(CtMethod.class);

        if (ctMethod == null) {
            // if ctMethod is null, maybe it's a constructor ?
            CtConstructor ctConstructor = ctThrow.getParent(CtConstructor.class);
            if (ctConstructor != null) {
                mElementsProcessed.put(ctConstructor.getParent(CtClass.class).getQualifiedName(), null);
                System.out.println("|-Class: " + ctConstructor.getParent(CtClass.class).getQualifiedName());
                System.out.println("|-Method: " + ctConstructor.getSimpleName());
                processConstructor(ctThrow);
            }

            return;
        }

        mElementsProcessed.put(ctMethod.getParent(CtClass.class).getQualifiedName(), null);
        System.out.println("|-Class: " + ctMethod.getParent(CtClass.class).getQualifiedName());
        System.out.println("|-Method: " + ctMethod.getSimpleName());

        System.out.println("|-Type to return: " + ctMethod.getType().getSimpleName());

        if (mProcessType == ProcessTypeEnum.PRIMITIVE) {
            System.out.println("    |-Primitive");
            processPrimitive(ctThrow);
        } else if (mProcessType == ProcessTypeEnum.ENUM) {
            System.out.println("    |-Enum");
            processEnum(ctThrow);
        } else if (mProcessType == ProcessTypeEnum.INTERFACE){
            System.out.println("    |-Interface");
            processInterface(ctThrow);
        } else if (mProcessType == ProcessTypeEnum.ABSTRACT){
            System.out.println("    |-Abstract");
            processAbstract(ctThrow);
        }else if (mProcessType == ProcessTypeEnum.OBJECT){
            System.out.println("    |-Object");
            processObject(ctThrow);
        }
    }

    private String getObjectInstantiationString(Class classToInitialize) {
        Constructor[] constructors = classToInitialize.getConstructors();

        if (constructors.length == 0) {
            return "null";
        }

        Constructor constructor = constructors[0];

        System.out.println("|-Constructors number: " + constructors.length);

        for (int i = 1; i < constructors.length; i++) {
            if (constructors[i].getParameterCount() < constructor.getParameterCount()) {
                constructor = constructors[i];
            }
        }

        System.out.println("|-Minimum constructor parameters: " + constructor.getParameterCount());
        if (constructor.getParameterCount() == 0) {
            return "new " + classToInitialize.getName() + "()";
        } else {
            String defaultValue = "new " + classToInitialize.getName() + "(";

            Class[] parameters = constructor.getParameterTypes();

            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].isPrimitive()) {
                    defaultValue += PrimitiveType.getTypeDefaultValue(parameters[i].getSimpleName());
                } else {
                    System.out.println(parameters[i]);
                    defaultValue += getObjectInstantiationString(parameters[i]);
                }

                if (i < parameters.length - 1) {
                    defaultValue += ",";
                }
            }

            defaultValue += ")";

            return defaultValue;
        }
    }

    private void applySnippet(CtThrow element, String value) {
        CtCodeSnippetStatement snippet = getFactory().Core().createCodeSnippetStatement();
        System.out.println("|-snippet: \"" + value + ";\"");
        snippet.setValue(value);
        element.replace(snippet);
    }

    public static void main(String[] args) throws Exception {
        spoon.Launcher.main(new String[] {
                "-p", "fr.inria.gforge.spoon.processors.FastToSafeProcessor",
                "-i", "../commons-email/src/main/java",
                "-o", "target/spooned/commons-email/src/main/java"
        });
    }
}
