package immanix.matchers;

import immanix.StaxMatcher;

public class LeftSuccMatcher<S, T> extends BaseSuccMatcher<S, T, S> {
    public LeftSuccMatcher(StaxMatcher<S> m1, StaxMatcher<T> m2) {
        super(m1, m2);
    }

    @Override
    protected S genRes(S res1, T res2) {
        return res1;
    }

    @Override
    public String toString() {
        return "(" + m1 + " <~ " + m2 + ")";
    }

}