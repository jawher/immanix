package immanix.matchers;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;

/**
 * A matcher that only succeeds if it matches an end element event with the specified name
 */
public class NamedEndElementMatcher extends BaseEventMatcher<EndElement> {
    private final String name;

    /**
     * @param name the end element's name to match
     */
    public NamedEndElementMatcher(String name) {
        this.name = name;
    }

    @Override
    protected boolean accept(XMLEvent e) {
        return e.isEndElement() && name.equals(e.asEndElement().getName().getLocalPart());
    }

    @Override
    protected EndElement process(XMLEvent e) {
        return e.asEndElement();
    }

    @Override
    protected String expects() {
        return toString();
    }

    @Override
    public String toString() {
        return "</" + name + ">";
    }

}