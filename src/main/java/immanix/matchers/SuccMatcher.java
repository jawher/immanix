package immanix.matchers;

import immanix.StaxMatcher;
import immanix.tuples.Tuple2;

public class SuccMatcher<S, T> extends BaseSuccMatcher<S, T, Tuple2<S, T>> {
    public SuccMatcher(StaxMatcher<S> m1, StaxMatcher<T> m2) {
        super(m1, m2);
    }

    @Override
    protected Tuple2<S, T> genRes(S res1, T res2) {
        return new Tuple2<S, T>(res1, res2);
    }

}