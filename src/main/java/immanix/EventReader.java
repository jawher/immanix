package immanix;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public interface EventReader {

    boolean hasNext();

    XMLEvent next() throws XMLStreamException;

    EventReader unwrap();

}