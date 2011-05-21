package immanix.utils;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;

import javax.xml.stream.XMLStreamException;
import java.util.Arrays;

public class HungryPacman extends StaxMatcher<Object> {
    int count;

    public HungryPacman(int count) {
        this.count = count;
    }

    @Override
    public MatcherResult<Object> match(EventReader reader) throws XMLStreamException {
        if (count-- > 0) {
            return MatcherResult.<Object>success("S", reader, Arrays.asList(reader.next()));
        } else {
            return MatcherResult.failure(reader, "Pacman is full");
        }
    }
}