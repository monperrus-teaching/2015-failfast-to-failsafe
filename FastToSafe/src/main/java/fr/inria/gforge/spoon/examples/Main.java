package fr.inria.gforge.spoon.examples;

public class Main {

    public static void main(String[] args) throws Exception {
        String aString = "This is a String.\n";

        new FastToSafeExample(aString);
        new FastToSafeExample(null);
    }

}
