package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;
import immanix.readers.BacktrackEventReader;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A matcher that only succeeds if its delegate matcher succeeds at least a specified number of times.
 * This matcher accumulates its delgate matcher's results into a list and returns it as its result (when it succeeds).
 * this can only work for a reasonable number of iteratitons, and thus the mention of "Finite" in its name.
 * If you need a version that supports an arbitrary number of repetitions but are willing to give up
 * intermediate results accumulation, you may want to use {@link AtLeastMatcher}.</br>
 * This matcher supports backrtacking.
 *
 * @param <T> the delegate matcher's result type
 */
public class AtLeastButFiniteMatcher<T> extends StaxMatcher<List<T>> {
    private final StaxMatcher<T> delegate;
    private final int n;

    public AtLeastButFiniteMatcher(StaxMatcher<T> delegate, int n) {
        this.delegate = delegate;
        this.n = n;
    }

    @Override
    public MatcherResult<List<T>> match(EventReader reader) throws XMLStreamException {
        List<T> res = new ArrayList<T>();
        List<XMLEvent> consumedEvents = new ArrayList<XMLEvent>();

        for (int i = 0; i < n; i++) {
            MatcherResult<T> partialRes = delegate.match(reader);
            if (partialRes.isFailure()) {
                return MatcherResult.failure(new BacktrackEventReader(consumedEvents, partialRes.reader));
            } else {
                res.add(partialRes.data);
                consumedEvents.addAll(partialRes.consumedEvents);
                reader = partialRes.reader;
            }
        }
        MatcherResult<T> partialRes = null;
        while (!(partialRes = delegate.match(reader)).isFailure()) {
            res.add(partialRes.data);
            consumedEvents.addAll(partialRes.consumedEvents);
            reader = partialRes.reader;
        }
        return MatcherResult.success(res, partialRes.reader, consumedEvents);
    }
}
