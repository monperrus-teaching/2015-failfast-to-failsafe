package fr.inria.gforge.spoon.examples;

public interface JsonTokenId
{

    public final static int ID_NOT_AVAILABLE = -1;

    public final static int ID_NO_TOKEN = 0;

    public final static int ID_START_OBJECT = 1;

    public final static int ID_END_OBJECT = 2;

    public final static int ID_START_ARRAY = 3;

    public final static int ID_END_ARRAY = 4;

    public final static int ID_FIELD_NAME = 5;

    public final static int ID_STRING = 6;

    public final static int ID_NUMBER_INT = 7;

    public final static int ID_NUMBER_FLOAT = 8;

    public final static int ID_TRUE = 9;

    public final static int ID_FALSE = 10;

    public final static int ID_NULL = 11;

    public final static int ID_EMBEDDED_OBJECT = 12;
}