package immanix.matchers;

import javax.xml.namespace.QName;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;

/**
 * A matcher that only succeeds if it matches an end element event with the specified name
 */
public class NamedEndElementMatcher extends BaseEventMatcher<EndElement> {
    private final String namespaceURI;
    private final String name;

    /**
     * @param name the end element's name to match
     */
    public NamedEndElementMatcher(String name) {
        this.name = name;
        this.namespaceURI = null;
    }

    /**
     * @param namespaceURI the end element's namespace uri
     * @param name         the end element's name to match
     */
    public NamedEndElementMatcher(String namespaceURI, String name) {
        this.name = name;
        this.namespaceURI = namespaceURI;
    }

    @Override
    protected boolean accept(XMLEvent e) {
        if (e.isEndElement()) {
            QName qName = e.asEndElement().getName();
            return name.equals(qName.getLocalPart()) && (namespaceURI == null || namespaceURI.equals
                    (qName.getNamespaceURI()));
        }
        return false;
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
        return "</" + (namespaceURI == null ? "" : "{" + namespaceURI + "}") + name + ">";
    }

}