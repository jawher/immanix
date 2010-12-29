package immanix;

import immanix.matchers.*;
import immanix.tuples.*;

import javax.xml.stream.XMLStreamException;
import java.util.List;

public abstract class StaxMatcher<T> {
    public abstract MatcherResult<T> match(EventReader reader)
            throws XMLStreamException;

    public <U> StaxMatcher<Tuple2<T, U>> then(StaxMatcher<U> m) {
        return new SuccMatcher<T, U>(this, m);
    }

    public <T1, T2> StaxMatcher<Tuple3<T, T1, T2>> then(StaxMatcher<T1> m1, StaxMatcher<T2> m2) {
        return new Succ3Matcher<T, T1, T2>(this, m1, m2);
    }


    public <T1, T2, T3> StaxMatcher<Tuple4<T, T1, T2, T3>> then(StaxMatcher<T1> m1, StaxMatcher<T2> m2, StaxMatcher<T3> m3) {
        return new Succ4Matcher<T, T1, T2, T3>(this, m1, m2, m3);
    }


    public <T1, T2, T3, T4> StaxMatcher<Tuple5<T, T1, T2, T3, T4>> then(StaxMatcher<T1> m1, StaxMatcher<T2> m2, StaxMatcher<T3> m3, StaxMatcher<T4> m4) {
        return new Succ5Matcher<T, T1, T2, T3, T4>(this, m1, m2, m3, m4);
    }


    public <T1, T2, T3, T4, T5> StaxMatcher<Tuple6<T, T1, T2, T3, T4, T5>> then(StaxMatcher<T1> m1, StaxMatcher<T2> m2, StaxMatcher<T3> m3, StaxMatcher<T4> m4, StaxMatcher<T5> m5) {
        return new Succ6Matcher<T, T1, T2, T3, T4, T5>(this, m1, m2, m3, m4, m5);
    }


    public <T1, T2, T3, T4, T5, T6> StaxMatcher<Tuple7<T, T1, T2, T3, T4, T5, T6>> then(StaxMatcher<T1> m1, StaxMatcher<T2> m2, StaxMatcher<T3> m3, StaxMatcher<T4> m4, StaxMatcher<T5> m5, StaxMatcher<T6> m6) {
        return new Succ7Matcher<T, T1, T2, T3, T4, T5, T6>(this, m1, m2, m3, m4, m5, m6);
    }


    public <T1, T2, T3, T4, T5, T6, T7> StaxMatcher<Tuple8<T, T1, T2, T3, T4, T5, T6, T7>> then(StaxMatcher<T1> m1, StaxMatcher<T2> m2, StaxMatcher<T3> m3, StaxMatcher<T4> m4, StaxMatcher<T5> m5, StaxMatcher<T6> m6, StaxMatcher<T7> m7) {
        return new Succ8Matcher<T, T1, T2, T3, T4, T5, T6, T7>(this, m1, m2, m3, m4, m5, m6, m7);
    }


    public <T1, T2, T3, T4, T5, T6, T7, T8> StaxMatcher<Tuple9<T, T1, T2, T3, T4, T5, T6, T7, T8>> then(StaxMatcher<T1> m1, StaxMatcher<T2> m2, StaxMatcher<T3> m3, StaxMatcher<T4> m4, StaxMatcher<T5> m5, StaxMatcher<T6> m6, StaxMatcher<T7> m7, StaxMatcher<T8> m8) {
        return new Succ9Matcher<T, T1, T2, T3, T4, T5, T6, T7, T8>(this, m1, m2, m3, m4, m5, m6, m7, m8);
    }


    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> StaxMatcher<Tuple10<T, T1, T2, T3, T4, T5, T6, T7, T8, T9>> then(StaxMatcher<T1> m1, StaxMatcher<T2> m2, StaxMatcher<T3> m3, StaxMatcher<T4> m4, StaxMatcher<T5> m5, StaxMatcher<T6> m6, StaxMatcher<T7> m7, StaxMatcher<T8> m8, StaxMatcher<T9> m9) {
        return new Succ10Matcher<T, T1, T2, T3, T4, T5, T6, T7, T8, T9>(this, m1, m2, m3, m4, m5, m6, m7, m8, m9);
    }

    public <U> StaxMatcher<T> thenl(StaxMatcher<U> m) {
        return new LeftSuccMatcher<T, U>(this, m);
    }

    public <U> StaxMatcher<U> thenr(StaxMatcher<U> m) {
        return new RightSuccMatcher<T, U>(this, m);
    }

    public StaxMatcher<T> or(StaxMatcher<T> m) {
        return new OrMatcher<T>(this, m);
    }

    public StaxMatcher<Void> zeroOrMore() {
        return new AtLeastMatcher(this, 0);
    }

    public StaxMatcher<Void> oneOrMore() {
        return new AtLeastMatcher(this, 1);
    }

    public StaxMatcher<Void> atLeast(int n) {
        return new AtLeastMatcher(this, n);
    }

    public StaxMatcher<List<T>> zeroOrMoreButFinite() {
        return new AtLeastButFiniteMatcher<T>(this, 0);
    }

    public StaxMatcher<List<T>> oneOrMoreButFinite() {
        return new AtLeastButFiniteMatcher<T>(this, 1);
    }

    public StaxMatcher<List<T>> atLeastButFinite(int n) {
        return new AtLeastButFiniteMatcher<T>(this, n);
    }

    public StaxMatcher<List<T>> atMost(int n) {
        return new AtMostMatcher<T>(this, n);
    }

    public StaxMatcher<T> optional() {
        return new Mapper<List<T>, T>(new AtMostMatcher<T>(this, 1)) {

            @Override
            public T process(List<T> data) {
                return data.size() == 0 ? null : data.get(0);
            }

            @Override
            public String toString() {
                return "[" + StaxMatcher.this + "]";
            }
        };
    }

    public StaxMatcher<List<T>> nTimes(int n) {
        return new NTimesMatcher<T>(this, n);
    }

    public <U> StaxMatcher<U> map(final Function1<T, U> map) {
        return new Mapper<T, U>(this) {
            @Override
            public U process(T data) {
                return map.apply(data);
            }

            @Override
            public String toString() {
                return StaxMatcher.this + "";
            }
        };
    }

    public StaxMatcher<T> cond(final Function1<T, Boolean> predicate) {
        return new CondMatcher<T>(this) {
            @Override
            public boolean validate(T data) {
                return predicate.apply(data);
            }
        };
    }

}