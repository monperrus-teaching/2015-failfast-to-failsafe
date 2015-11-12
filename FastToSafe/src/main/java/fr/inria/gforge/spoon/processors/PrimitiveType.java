package fr.inria.gforge.spoon.processors;

public class PrimitiveType {

    public static String getTypeDefaultValue(String type) {
        PrimitiveTypeEnum primitiveType = PrimitiveTypeEnum.valueOf(type.toUpperCase());

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

}
