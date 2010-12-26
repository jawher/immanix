package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;
import immanix.tuples.Tuple2;
import immanix.tuples.Tuple4;
import immanix.tuples.Tuple5;

import javax.xml.stream.XMLStreamException;

public class Succ5Matcher<T1, T2, T3, T4, T5> extends StaxMatcher<Tuple5<T1, T2, T3, T4, T5>> {
	private SuccMatcher<Tuple4<T1, T2, T3, T4>, T5> m;
	private final String str;

	public Succ5Matcher(StaxMatcher<T1> m1, StaxMatcher<T2> m2, StaxMatcher<T3> m3, StaxMatcher<T4> m4, StaxMatcher<T5> m5) {
		m = new SuccMatcher<Tuple4<T1, T2, T3, T4>, T5>(new Succ4Matcher<T1, T2, T3, T4>(m1, m2, m3, m4), m5);

		str="("+m1 + " ~ " + m2 + " ~ " + m3 + " ~ " + m4 + " ~ " + m5+")";
	}

	@Override
	public MatcherResult<Tuple5<T1, T2, T3, T4, T5>> match(EventReader reader) throws XMLStreamException {
		MatcherResult<Tuple2<Tuple4<T1, T2, T3, T4>, T5>> res = m.match(reader);

		if (res.isFailure()) {
			return MatcherResult.failure(res.reader);
		} else {
			return MatcherResult.success(new Tuple5<T1, T2, T3, T4, T5>(res.data._1._1, res.data._1._2, res.data._1._3, res.data._1._4, res.data._2), res.reader, res.consumedEvents);
		}
	}

	@Override
	public String toString() {
		return str;
	}

}