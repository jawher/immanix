package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;
import immanix.readers.BacktrackEventReader;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

public class AtMostMatcher<T> extends StaxMatcher<List<T>> {
    private final StaxMatcher<T> delegate;
    private final int n;

    public AtMostMatcher(StaxMatcher<T> delegate, int n) {
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
                return MatcherResult.success(res, partialRes.reader, consumedEvents);
            } else {
                res.add(partialRes.data);
                consumedEvents.addAll(partialRes.consumedEvents);
                reader = partialRes.reader;
            }
        }

        MatcherResult<T> partialRes = delegate.match(reader);
        if (partialRes.isFailure()) {
            return MatcherResult.success(res, partialRes.reader, consumedEvents);
        } else {
            consumedEvents.addAll(partialRes.consumedEvents);
            return MatcherResult.failure(new BacktrackEventReader(consumedEvents, partialRes.reader));
        }
    }
}
