package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;
import immanix.readers.BacktrackEventReader;
import immanix.utils.Stringify;

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
            return MatcherResult.failure(new BacktrackEventReader(Arrays.asList(event), reader), toString() + " failed: Was expecting '" +
                    expects() + "' but got '" + Stringify.s(event) +
                    "'");
        }
        return MatcherResult.failure(reader, toString() + " failed: unexpected end of stream");
    }

    protected abstract boolean accept(XMLEvent e);

    protected abstract String expects();

    protected abstract T process(XMLEvent e);
}
