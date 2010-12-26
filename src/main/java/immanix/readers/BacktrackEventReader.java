package immanix.readers;

import immanix.EventReader;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;
import java.util.List;


public class BacktrackEventReader implements EventReader {
    private final Iterator<XMLEvent> events;
    private EventReader reader;

    public BacktrackEventReader(List<XMLEvent> events, EventReader reader) {
        this.events = events.iterator();
        this.reader = reader;
    }


    public boolean hasNext() {
        return events.hasNext() || reader.hasNext();
    }

    public XMLEvent next() throws XMLStreamException {
        if (events.hasNext()) {
            XMLEvent event = events.next();
            return event;
        } else {
            XMLEvent res = reader.next();
            reader = reader.unwrap();
            return res;
        }
    }

    public EventReader unwrap() {
        if (events.hasNext()) {
            return this;
        } else {
            return reader;
        }
    }
}
