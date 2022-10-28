package tests;

public class MyTestSubClass extends MyTestClass {

    private final int a;

    public MyTestSubClass(int x, int y, int a) {
        super(x, y);
        this.a = a;
    }

    public int getA() {
        return a;
    }
}
