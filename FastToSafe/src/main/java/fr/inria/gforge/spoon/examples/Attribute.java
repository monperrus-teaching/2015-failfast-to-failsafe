package fr.inria.gforge.spoon.examples;

public class Attribute
{
    private String _name;
    private String _value;

    Attribute(String n, String v)
    {
        _name = n;
        _value = v;
    }

    public String getName()
    {
        return _name;
    }

    public String getValue()
    {
        return _value;
    }
}