package immanix.matchers;

import immanix.StaxMatcher;

public class RightSuccMatcher<S, T> extends BaseSuccMatcher<S, T, T> {
    public RightSuccMatcher(StaxMatcher<S> m1, StaxMatcher<T> m2) {
        super(m1, m2);
    }

    @Override
    protected T genRes(S res1, T res2) {
        return res2;
    }

    @Override
    public String toString() {
        return "(" + m1 + " ~> " + m2 + ")";
    }

}