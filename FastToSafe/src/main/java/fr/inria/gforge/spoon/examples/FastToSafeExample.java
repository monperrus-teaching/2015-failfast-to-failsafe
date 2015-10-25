package fr.inria.gforge.spoon.examples;

public class FastToSafeExample {

    private String quz;
    private int rad;

    public FastToSafeExample(String aString) {
        foo(aString);
        this.quz = bar(aString);
        this.rad = jiu();
    }

    // TODO: Check if it works with int[]
    private int jiu() {
        if (true) {
            throw new IllegalArgumentException();
        }

        return 9;
    }

    // TODO: Need to initialize aString instead of return a String.
    private String bar(String aString) {
        if (aString == null) {
            throw new IllegalArgumentException();
        }

        return aString;
    }

    public void foo(String aString) {
        if (aString == null) {
            throw new IllegalArgumentException();
        }

        System.out.print(aString);
    }

}
