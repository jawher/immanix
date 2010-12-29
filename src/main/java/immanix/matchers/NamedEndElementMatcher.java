package immanix.matchers;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * A matcher that only succeeds if it matches an end element event with the specified name
 */
public class NamedEndElementMatcher extends CondMatcher<EndElement> {
    private final String name;

    /**
     * @param name the end element's name to match
     */
    public NamedEndElementMatcher(String name) {
        super(new EndElementMatcher());
        this.name = name;
    }


    @Override
    public boolean validate(EndElement data) {
        return name.equals(data.asEndElement().getName().getLocalPart());
    }

    @Override
    public String toString() {
        return "</" + name + ">";
    }

}