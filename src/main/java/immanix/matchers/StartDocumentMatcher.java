package immanix.matchers;

import javax.xml.stream.events.XMLEvent;

public class StartDocumentMatcher extends BaseEventMatcher<Void> {

    @Override
    protected boolean accept(XMLEvent e) {
        return e.isStartDocument();
    }

    @Override
    protected Void process(XMLEvent e) {
        return null;
    }

    @Override
    public String toString() {
        return "SOF";
    }

}