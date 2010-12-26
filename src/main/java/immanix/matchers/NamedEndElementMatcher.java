package immanix.matchers;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;

public class NamedEndElementMatcher extends BaseEventMatcher<EndElement> {
    private final String name;

    public NamedEndElementMatcher(String name) {
        super();
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
    public String toString() {
        return "</"+name+">";
    }

}