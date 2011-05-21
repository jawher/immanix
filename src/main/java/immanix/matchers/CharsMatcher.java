package immanix.matchers;

import javax.xml.stream.events.XMLEvent;


public class CharsMatcher extends BaseEventMatcher<String> {

    @Override
    protected boolean accept(XMLEvent e) {
        return e.isCharacters();
    }

    @Override
    protected String process(XMLEvent e) {
        return e.asCharacters().getData();
    }

    @Override
    protected String expects() {
        return "Characters";
    }

    @Override
    public String toString() {
        return "Characters";
    }

}
