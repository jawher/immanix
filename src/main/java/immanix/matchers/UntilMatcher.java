package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;

import javax.xml.stream.XMLStreamException;

/**
 * A matcher that advances the events stream until a delegate matcher succeeds (and returns its result) or the stream is
 * consumed.<br/>
 * This matcher <b>doesn't restore</b> the events stream to its original state in case of failure but rather consumes it all.
 * This choice was made in order to handle arbitrarily large streams.
 * If you are sure that the <b>delegate matcher will succeed</b> after a <b>reasonable</b> number of events, and want to
 * be able to backtrack, you can use {@link UntilButFiniteMatcher}.
 *
 * @param <T>
 */
public class UntilMatcher<T> extends StaxMatcher<T> {
    private final StaxMatcher<T> delegate;

    public UntilMatcher(StaxMatcher<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public MatcherResult<T> match(EventReader reader) throws XMLStreamException {
        MatcherResult<T> res = null;

        while ((res = delegate.match(reader)).isFailure()) {
            reader = res.reader.unwrap();
            if (reader.hasNext()) {
                reader.next();
            } else {
                return MatcherResult.failure(reader, toString() + " failed: the stream was consumed without the delegate matcher '" + delegate +
                        "' matching");
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return "(Until " + delegate + ")";
    }
}
