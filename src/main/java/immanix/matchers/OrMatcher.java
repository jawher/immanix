package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;

import javax.xml.stream.XMLStreamException;

public class OrMatcher<T> extends StaxMatcher<T> {
    private final StaxMatcher<T> m1;
    private final StaxMatcher<T> m2;

    public OrMatcher(StaxMatcher<T> m1, StaxMatcher<T> m2) {
        super();
        this.m1 = m1;
        this.m2 = m2;
    }

    @Override
    public MatcherResult<T> match(EventReader reader)
            throws XMLStreamException {
        MatcherResult<T> res1 = m1.match(reader);
        if (res1.isFailure()) {
            return m2.match(res1.reader);
        } else {
            return res1;
        }
    }

}