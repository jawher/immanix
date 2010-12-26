package immanix.matchers;

import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StartElementMatcher extends BaseEventMatcher<StartElement> {

    @Override
    protected boolean accept(XMLEvent e) {
        if(!e.isStartElement()){
            System.out.println("failed to match <");
        }
        return e.isStartElement();
    }

    @Override
    protected StartElement process(XMLEvent e) {
        return e.asStartElement();
    }

}