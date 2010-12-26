package immanix.matchers;

import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class NamedStartElementMatcher extends BaseEventMatcher<StartElement> {
    private final String name;

    public NamedStartElementMatcher(String name) {
        super();
        this.name = name;
    }


    @Override
    protected boolean accept(XMLEvent e) {
        return e.isStartElement() && name.equals(e.asStartElement().getName().getLocalPart());
    }

    @Override
    protected StartElement process(XMLEvent e) {
        return e.asStartElement();
    }

    @Override
    public String toString() {
        return "<"+name+">";
    }
}