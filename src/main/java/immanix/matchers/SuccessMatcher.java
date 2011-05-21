package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;


public class SuccessMatcher extends StaxMatcher<XMLEvent> {
    @Override
    public MatcherResult<XMLEvent> match(EventReader reader) throws XMLStreamException {
        if (reader.hasNext()) {
            XMLEvent event = reader.next();
            reader = reader.unwrap();
            return MatcherResult.success(event, reader, Arrays.asList(event));
        } else {
            return MatcherResult.failure(reader, toString() + " failed: unexpected end of stream");
        }
    }

}
