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
 * A matcher that advances the events stream until a delegate matcher succeeds (and returns its result) or a specified
 * number of events has been consumed or when the stream is consumed.<br/>
 * Since this matcher is bounded (is guaranteed to finish after a reasonable number of events), it is compatible with
 * backtracking, i.e. it restores the events stream state on failure.<br/>
 * If you need a similar matcher but one that can handle an arbitrary number of events before succeeding and you don't
 * need backtracking support, you can use {@link UntilMatcher}.
 *
 * @param <T>
 */
public class UntilButFiniteMatcher<T> extends StaxMatcher<T> {
    private final StaxMatcher<T> delegate;
    private final int giveUpBarrier;

    public UntilButFiniteMatcher(StaxMatcher<T> delegate, int giveUpBarrier) {
        this.delegate = delegate;
        this.giveUpBarrier = giveUpBarrier;
    }

    @Override
    public MatcherResult<T> match(EventReader reader) throws XMLStreamException {
        MatcherResult<T> res = null;
        List<XMLEvent> consumedEvents = new ArrayList<XMLEvent>();
        while ((res = delegate.match(reader)).isFailure()) {
            reader = res.reader.unwrap();
            if (reader.hasNext() && consumedEvents.size() < giveUpBarrier) {
                consumedEvents.add(reader.next());
            } else {
                return MatcherResult.failure(new BacktrackEventReader(consumedEvents, reader));
            }

        }
        consumedEvents.addAll(res.consumedEvents);
        return MatcherResult.success(res.data, res.reader, consumedEvents);
    }
}
