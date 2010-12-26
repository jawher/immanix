package immanix.readers;

import immanix.EventReader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

@SuppressWarnings("restriction")
public class StaxEventReader implements EventReader {
    private XMLEventReader evr;

    public StaxEventReader(XMLEventReader evr) {
        super();
        this.evr = evr;
    }

    public boolean hasNext() {
        return evr.hasNext();
    }

    public XMLEvent next() throws XMLStreamException {
        XMLEvent event = evr.nextEvent();
        return event;
    }

    public EventReader unwrap() {
        return this;
    }

    @Override
    public String toString() {
        return "Stax";
    }

}