package immanix.readers;

import immanix.EventReader;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("restriction")
public class ReplayEventReader implements EventReader {

    private Iterator<? extends XMLEvent> events;

    public ReplayEventReader(List<? extends XMLEvent> events) {
        super();
        this.events = new ArrayList(events).iterator();
    }

    public boolean hasNext() {
        return events.hasNext();
    }

    public XMLEvent next() throws XMLStreamException {
        return events.next();
    }

    public EventReader unwrap() {
        return this;
    }

    @Override
    public String toString() {
        return "Replayer";
    }
}