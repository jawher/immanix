package immanix.matchers;

import javax.xml.stream.events.StartElement;

/**
 * A matcher that only succeeds if it matches a start element event with the specified name
 */
public class NamedStartElementMatcher extends CondMatcher<StartElement> {
    private final String name;

    /**
     * @param name the start element's name to match
     */
    public NamedStartElementMatcher(String name) {
        super(new StartElementMatcher());
        this.name = name;
    }


    @Override
    public boolean validate(StartElement data) {
        return name.equals(data.asStartElement().getName().getLocalPart());
    }

    @Override
    public String toString() {
        return "<" + name + ">";
    }

}