package immanix.matchers;

import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * A matcher that only succeeds if it matches a start element event with the specified name
 */
public class NamedStartElementMatcher extends BaseEventMatcher<StartElement> {
    private final String name;

    /**
     * @param name the start element's name to match
     */
    public NamedStartElementMatcher(String name) {
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
    protected String expects() {
        return toString();
    }

    @Override
    public String toString() {
        return "<" + name + ">";
    }

}