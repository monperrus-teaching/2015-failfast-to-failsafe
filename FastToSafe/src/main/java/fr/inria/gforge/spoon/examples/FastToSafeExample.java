package fr.inria.gforge.spoon.examples;

public class FastToSafeExample {

    private String quz;
    private int rad;
    private A guy;
    private B kie;

    public FastToSafeExample(String aString) {
        foo(aString);
        this.quz = bar(aString);
        this.rad = jiu();
        this.guy = jds(5, 6, true);
        this.kie = mel();
    }

    public JsonToken splats() throws Exception {
        throw new Exception();
    }

    private B mel() {
        if (true) {
            throw new IllegalArgumentException();
        }

        return new B(5, new C(true));
    }

    private A jds(int a, int b, boolean c) {
        if (c) {
            throw new IllegalArgumentException();
        }

        return new A(a, b, c);
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
