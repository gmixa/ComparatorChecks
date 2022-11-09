package tests;

public class MyTestSubClass extends MyTestClass {

    private final int l;

    public MyTestSubClass(final int x, final int y, final int l) {
        super(x, y);
        this.l = l;
    }

    public int getL() {
        return l;
    }
}
