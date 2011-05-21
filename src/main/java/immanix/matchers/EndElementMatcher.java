package immanix.matchers;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;

public class EndElementMatcher extends BaseEventMatcher<EndElement> {

    @Override
    protected boolean accept(XMLEvent e) {
        return e.isEndElement();
    }

    @Override
    protected EndElement process(XMLEvent e) {
        return e.asEndElement();
    }

    @Override
    protected String expects() {
        return "End of any element";
    }

    @Override
    public String toString() {
        return "End of any element";
    }
}