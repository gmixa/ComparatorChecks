import java.util.Comparator;
import java.util.Objects;

public class MyTestClass implements Comparable<MyTestClass>{

    private final int x;
    private final int y;

    public MyTestClass(final int x, final int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public String toString(){
        return x+"/"+y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }else{
            MyTestClass other = (MyTestClass) obj;
            return Objects.equals(x,other.x) && Objects.equals(y,other.y);
        }

    }

    @Override
    public int compareTo(MyTestClass o) {
        return  Comparators.DEFAULT.reversed().compare(this,o);
    }

    public final static class Comparators {
        public final static Comparator<MyTestClass> DEFAULT = Comparator.comparing(MyTestClass::getX).thenComparing(MyTestClass::getY);
        public final static Comparator<MyTestClass> INVERSE = Comparator.comparing(MyTestClass::getY).thenComparing(MyTestClass::getX);
        public final static Comparator<MyTestClass> BUGGY = (o1,o2) ->  o1.getX() < o2.getY() ? -1 : 1;
    }
}
