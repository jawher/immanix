package immanix.matchers;

import javax.xml.stream.events.XMLEvent;

public class EndDocumentMatcher extends BaseEventMatcher<Void> {

    @Override
    protected boolean accept(XMLEvent e) {
        return e.isEndDocument();
    }

    @Override
    protected Void process(XMLEvent e) {
        return null;
    }

    @Override
    public String toString() {
        return "EOF";
    }

}