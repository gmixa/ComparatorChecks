package tests.comparatorAsAttribute;

import java.util.Comparator;

public class ComparatorIsAttributeUseCases {

    public void comparatorSetterViaAnonymousClass() {
        ComparatorIsAttribute test = new ComparatorIsAttribute();
        test.setComparatorAttribute(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
    }

    public void comparatorSetterViaLambda() {
        ComparatorIsAttribute test = new ComparatorIsAttribute();
        Comparator<Integer> lambda = (Integer y, Integer x) -> x + y;
        test.setComparatorAttribute(lambda);
    }

}
