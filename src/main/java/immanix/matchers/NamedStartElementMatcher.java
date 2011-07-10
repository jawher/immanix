package immanix.matchers;

import javax.xml.namespace.QName;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * A matcher that only succeeds if it matches a start element event with the specified name
 */
public class NamedStartElementMatcher extends BaseEventMatcher<StartElement> {
    private final String namespaceURI;
    private final String name;

    /**
     * @param name the start element's name to match
     */
    public NamedStartElementMatcher(String name) {
        this.name = name;
        this.namespaceURI = null;
    }

    /**
     * @param namespaceURI the start element's namespace uri
     * @param name         the start element's name to match
     */
    public NamedStartElementMatcher(String namespaceURI, String name) {
        this.name = name;
        this.namespaceURI = namespaceURI;
    }

    @Override
    protected boolean accept(XMLEvent e) {
        if (e.isStartElement()) {
            QName qName = e.asStartElement().getName();
            return name.equals(qName.getLocalPart()) && (namespaceURI == null || namespaceURI.equals
                    (qName.getNamespaceURI()));
        }
        return false;
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
        return "<" + (namespaceURI == null ? "" : "{" + namespaceURI + "}") + name + ">";
    }

}