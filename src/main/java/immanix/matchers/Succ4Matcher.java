package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;
import immanix.tuples.Tuple2;
import immanix.tuples.Tuple3;
import immanix.tuples.Tuple4;

import javax.xml.stream.XMLStreamException;

public class Succ4Matcher<T1, T2, T3, T4> extends StaxMatcher<Tuple4<T1, T2, T3, T4>> {
    private SuccMatcher<Tuple3<T1, T2, T3>, T4> m;
    private final String str;

    public Succ4Matcher(StaxMatcher<T1> m1, StaxMatcher<T2> m2, StaxMatcher<T3> m3, StaxMatcher<T4> m4) {
        m = new SuccMatcher<Tuple3<T1, T2, T3>, T4>(new Succ3Matcher<T1, T2, T3>(m1, m2, m3), m4);

        str = "(" + m1 + " ~ " + m2 + " ~ " + m3 + " ~ " + m4 + ")";
    }

    @Override
    public MatcherResult<Tuple4<T1, T2, T3, T4>> match(EventReader reader) throws XMLStreamException {
        MatcherResult<Tuple2<Tuple3<T1, T2, T3>, T4>> res = m.match(reader);

        if (res.isFailure()) {
            return MatcherResult.failure(res.reader, res.errorMessage);
        } else {
            return MatcherResult.success(new Tuple4<T1, T2, T3, T4>(res.data._1._1, res.data._1._2, res.data._1._3, res.data._2), res.reader, res.consumedEvents);
        }
    }

    @Override
    public String toString() {
        return str;
    }

}