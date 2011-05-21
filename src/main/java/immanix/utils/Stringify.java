package immanix.utils;

import javax.xml.stream.events.XMLEvent;

public class Stringify {
    public static String s(XMLEvent e) {
        if (e.isStartDocument()) {
            return "Document start";
        } else if (e.isEndDocument()) {
            return "Document end";
        } else if (e.isStartElement()) {
            return "<" + e.asStartElement().getName() + ">";
        } else if (e.isEndElement()) {
            return "</" + e.asEndElement().getName() + ">";
        } else if (e.isCharacters()) {
            return "Characters[" + excerpt(e.asCharacters().getData()) + "]";
        } else return "";
    }

    private static final int MAX_LEN = 50;

    private static String excerpt(String s) {
        if (s == null || s.length() <= MAX_LEN) {
            return s;
        } else {
            return s.substring(0, MAX_LEN) + "...";
        }
    }
}
