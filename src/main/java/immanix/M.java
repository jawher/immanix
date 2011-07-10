package immanix;

import immanix.matchers.*;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class M {
    public static StartDocumentMatcher startDoc() {
        return new StartDocumentMatcher();
    }

    public static EndDocumentMatcher endDoc() {
        return new EndDocumentMatcher();
    }

    public static StaxMatcher<String> chars() {
        return new CharsMatcher();
    }

    public static StaxMatcher<StartElement> start() {
        return new StartElementMatcher();
    }

    public static StaxMatcher<StartElement> start(String name) {
        return new NamedStartElementMatcher(name);
    }

    public static StaxMatcher<StartElement> start(String namespaceURI, String name) {
        return new NamedStartElementMatcher(namespaceURI, name);
    }

    public static StaxMatcher<EndElement> end() {
        return new EndElementMatcher();
    }

    public static StaxMatcher<EndElement> end(String name) {
        return new NamedEndElementMatcher(name);
    }

    public static StaxMatcher<EndElement> end(String namespaceURI, String name) {
        return new NamedEndElementMatcher(namespaceURI, name);
    }

    public static StaxMatcher<XMLEvent> success() {
        return new SuccessMatcher();
    }

    public static <T> StaxMatcher<T> until(StaxMatcher<T> m) {
        return new UntilMatcher<T>(m);
    }

    public static <T> StaxMatcher<T> untilButFinite(StaxMatcher<T> m, int giveUpBarrier) {
        return new UntilButFiniteMatcher<T>(m, giveUpBarrier);
    }
}