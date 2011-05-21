package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;
import immanix.tuples.Tuple10;
import immanix.tuples.Tuple2;
import immanix.tuples.Tuple9;

import javax.xml.stream.XMLStreamException;

public class Succ10Matcher<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends StaxMatcher<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> {
    private SuccMatcher<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, T10> m;
    private final String str;

    public Succ10Matcher(StaxMatcher<T1> m1, StaxMatcher<T2> m2, StaxMatcher<T3> m3, StaxMatcher<T4> m4, StaxMatcher<T5> m5, StaxMatcher<T6> m6, StaxMatcher<T7> m7, StaxMatcher<T8> m8, StaxMatcher<T9> m9, StaxMatcher<T10> m10) {
        m = new SuccMatcher<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, T10>(new Succ9Matcher<T1, T2, T3, T4, T5, T6, T7, T8, T9>(m1, m2, m3, m4, m5, m6, m7, m8, m9), m10);

        str = "(" + m1 + " ~ " + m2 + " ~ " + m3 + " ~ " + m4 + " ~ " + m5 + " ~ " + m6 + " ~ " + m7 + " ~ " + m8 + " ~ " + m9 + " ~ " + m10 + ")";
    }

    @Override
    public MatcherResult<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> match(EventReader reader) throws XMLStreamException {
        MatcherResult<Tuple2<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, T10>> res = m.match(reader);

        if (res.isFailure()) {
            return MatcherResult.failure(res.reader, res.errorMessage);
        } else {
            return MatcherResult.success(new Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(res.data._1._1, res.data._1._2, res.data._1._3, res.data._1._4, res.data._1._5, res.data._1._6, res.data._1._7, res.data._1._8, res.data._1._9, res.data._2), res.reader, res.consumedEvents);
        }
    }

    @Override
    public String toString() {
        return str;
    }

}