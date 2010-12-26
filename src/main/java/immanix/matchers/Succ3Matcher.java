package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;
import immanix.tuples.Tuple2;
import immanix.tuples.Tuple3;

import javax.xml.stream.XMLStreamException;

public class Succ3Matcher<T1, T2, T3> extends StaxMatcher<Tuple3<T1, T2, T3>> {
    private SuccMatcher<Tuple2<T1, T2>, T3> m;
    private final String str;

    public Succ3Matcher(StaxMatcher<T1> m1, StaxMatcher<T2> m2, StaxMatcher<T3> m3) {
        m = new SuccMatcher<Tuple2<T1, T2>, T3>(new SuccMatcher<T1, T2>(m1, m2), m3);

        str = "(" + m1 + " ~ " + m2 + " ~ " + m3 + ")";
    }

    @Override
    public MatcherResult<Tuple3<T1, T2, T3>> match(EventReader reader) throws XMLStreamException {
        MatcherResult<Tuple2<Tuple2<T1, T2>, T3>> res = m.match(reader);

        if (res.isFailure()) {
            return MatcherResult.failure(res.reader);
        } else {
            return MatcherResult.success(new Tuple3<T1, T2, T3>(res.data._1._1, res.data._1._2, res.data._2), res.reader, res.consumedEvents);
        }
    }

    @Override
    public String toString() {
        return str;
    }

}