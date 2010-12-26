package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;
import immanix.readers.BacktrackEventReader;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;

public abstract class BaseEventMatcher<T> extends StaxMatcher<T> {
    @Override
    public MatcherResult<T> match(EventReader reader) throws XMLStreamException {
        if (reader.hasNext()) {
            XMLEvent event = reader.next();
            reader = reader.unwrap();
            if (accept(event)) {
                return MatcherResult.success(process(event),
                        reader, Arrays.asList(event));
            }
            return MatcherResult.failure(new BacktrackEventReader(Arrays.asList(event), reader));
        }
        return MatcherResult.failure(reader);
    }

    protected abstract boolean accept(XMLEvent e);

    protected abstract T process(XMLEvent e);
}
