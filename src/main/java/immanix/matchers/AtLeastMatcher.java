package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;
import immanix.readers.BacktrackEventReader;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

public class AtLeastMatcher extends StaxMatcher<Void> {
    private final StaxMatcher<?> delegate;
    private final int n;

    public AtLeastMatcher(StaxMatcher<?> delegate, int n) {
        this.delegate = delegate;
        this.n = n;
    }

    @Override
    public MatcherResult<Void> match(EventReader reader) throws XMLStreamException {
        List<XMLEvent> consumedEvents = new ArrayList<XMLEvent>();

        for (int i = 0; i < n; i++) {
            MatcherResult<?> partialRes = delegate.match(reader);
            if (partialRes.isFailure()) {
                return MatcherResult.failure(new BacktrackEventReader(consumedEvents, partialRes.reader));
            } else {
                consumedEvents.addAll(partialRes.consumedEvents);
                reader = partialRes.reader;
            }
        }
        MatcherResult<?> partialRes = null;
        while (!(partialRes = delegate.match(reader)).isFailure()) {
            reader = partialRes.reader;
        }
        return MatcherResult.success(null, partialRes.reader, consumedEvents);
    }

    @Override
    public String toString() {
        return "("+delegate+"){"+n+"| }";
    }
}
