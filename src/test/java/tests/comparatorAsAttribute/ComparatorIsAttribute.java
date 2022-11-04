package tests.comparatorAsAttribute;

import tests.MyIntComparator;

import java.util.Comparator;

public class ComparatorIsAttribute {

    private Comparator<Integer> comparatorAttribute;

    private MyIntComparator myIntComparator;

    public void setMyIntComparator(MyIntComparator myIntComparator) {
        this.myIntComparator = myIntComparator;
    }

    public void setComparatorAttribute(Comparator<Integer> comparatorAttribute){
        this.comparatorAttribute = comparatorAttribute;
    }
}
