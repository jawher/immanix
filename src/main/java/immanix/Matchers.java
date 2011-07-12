package immanix;

import immanix.matchers.*;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class Matchers {
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

    /**
     * Augments a matcher so that it accepts characters (whitespace generally) before it
     *
     * @param m   the matcher to wrap
     * @param <T> the result type of the matcher to wrap
     * @return the augmented matcher
     */
    public static <T> StaxMatcher<T> wsl(StaxMatcher<T> m) {
        return WS_MATCHER.thenr(m);
    }

    /**
     * Augments a matcher so that it accepts characters (whitespace generally) after it
     *
     * @param m   the matcher to wrap
     * @param <T> the result type of the matcher to wrap
     * @return the augmented matcher
     */
    public static <T> StaxMatcher<T> wsr(StaxMatcher<T> m) {
        return m.thenl(WS_MATCHER);
    }

    /**
     * Augments a matcher so that it accepts characters (whitespace generally) before and after it
     *
     * @param m   the matcher to wrap
     * @param <T> the result type of the matcher to wrap
     * @return the augmented matcher
     */
    public static <T> StaxMatcher<T> ws(StaxMatcher<T> m) {
        return WS_MATCHER.thenr(m).thenl(WS_MATCHER);
    }

    private static final StaxMatcher<String> WS_MATCHER = new CharsMatcher() {
        @Override
        public String toString() {
            return " ";
        }
    }.optional();
}