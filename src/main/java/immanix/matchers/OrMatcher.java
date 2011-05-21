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
            MatcherResult<T> res2 = m2.match(res1.reader);
            if (res2.isFailure()) {
                return MatcherResult.failure(res2.reader,
                        toString() + " failed as both alternatives failed:\n" +
                                "Alternative 1:\n\t" +
                                res1.errorMessage + "\nAlternative 2:\n\t" + res2.errorMessage + "\n" +
                                toString() + " failed due to the previous errors");
            } else {
                return res2;
            }
        } else {
            return res1;
        }
    }

    @Override
    public String toString() {
        return "(" + m1 + ") | (" + m2 + ")";
    }
}