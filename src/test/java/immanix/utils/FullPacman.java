package immanix.utils;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;

import javax.xml.stream.XMLStreamException;
import java.util.Collections;

public class FullPacman extends StaxMatcher<Object> {
    int count;

    public FullPacman(int count) {
        this.count = count;
    }

    @Override
    public MatcherResult<Object> match(EventReader reader) throws XMLStreamException {
        if (count-- > 0) {
            return MatcherResult.<Object>success("S", reader, Collections.EMPTY_LIST);
        } else {
            return MatcherResult.failure(reader, "Pacman is full");
        }
    }
}