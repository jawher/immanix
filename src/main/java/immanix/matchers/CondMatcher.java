package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;
import immanix.readers.BacktrackEventReader;

import javax.xml.stream.XMLStreamException;

/**
 * A matcher that only succeeds if its delegate matcher succeeds and its result matches a predicate (by implementing
 * the abstract method {@link CondMatcher#validate(Object)}
 *
 * @param <T> the result type (the same as the delgate's result type)
 */
public abstract class CondMatcher<T> extends StaxMatcher<T> {
    private final StaxMatcher<T> delegate;

    public CondMatcher(StaxMatcher<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public MatcherResult<T> match(EventReader reader) throws XMLStreamException {
        MatcherResult<T> res = delegate.match(reader);
        if (res.isFailure()) {
            return res;
        } else {
            if (validate(res.data)) {
                return res;
            } else {
                return MatcherResult.failure(new BacktrackEventReader(res.consumedEvents, res.reader.unwrap()));
            }
        }
    }

    /**
     *
     * @param data the delgate matcher's result
     * @return whether to accept this result or not
     */
    public abstract boolean validate(T data);

    @Override
    public String toString() {
        return "(cond " + delegate + ")";
    }
}
