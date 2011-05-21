package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseSuccMatcher<S, T, U> extends StaxMatcher<U> {
    protected final StaxMatcher<S> m1;
    protected final StaxMatcher<T> m2;

    public BaseSuccMatcher(StaxMatcher<S> m1, StaxMatcher<T> m2) {
        super();
        this.m1 = m1;
        this.m2 = m2;
    }

    @Override
    public MatcherResult<U> match(EventReader reader)
            throws XMLStreamException {

        MatcherResult<S> res1 = m1.match(reader);
        if (res1.isFailure()) {
            return MatcherResult.failure(res1.reader, res1.errorMessage + "\n" + toString() +
                    " failed due to the previous error");
        } else {
            MatcherResult<T> res2 = m2.match(res1.reader);
            if (res2.isFailure()) {
                return MatcherResult.failure(res2.reader, res2.errorMessage + "\n" + toString() +
                        " failed due to the previous error");
            } else {
                List<XMLEvent> consumedEvents = new ArrayList<XMLEvent>(res1.consumedEvents);
                consumedEvents.addAll(res2.consumedEvents);
                return MatcherResult.success(genRes(res1.data, res2.data), res2.reader, consumedEvents);
            }
        }
    }

    protected abstract U genRes(S res1, T res2);


    @Override
    public String toString() {
        return "(" + m1 + " ~ " + m2 + ")";
    }
}