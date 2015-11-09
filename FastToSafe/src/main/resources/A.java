package main.resources;

public class A {

    private int _a;
    private int _b;
    private boolean _c;

    public A(int a, int b, boolean c) {
        this._a = a;
        this._b = b;
        this._c = c;
    }

    public A(int a, int b) {
        this(a, b, false);
    }

    public void fooA() throws Exception {
        throw new Exception();
    }

    public static B bar(boolean a) throws Exception {
        if (true) {
            throw new Exception();
        }

        return new B("test");
    }

    public String fooB(String a) throws Exception {
        if (a == null) {
            throw new Exception();
        }

        return a;
    }

    public B fooC() throws Exception {
        if (true) {
            throw new Exception();
        }

        return new B("test");
    }
}
