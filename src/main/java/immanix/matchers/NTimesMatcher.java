package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;
import immanix.readers.BacktrackEventReader;

import javax.xml.stream.XMLStreamException;
import java.util.List;


public class NTimesMatcher<T> extends StaxMatcher<List<T>> {
    private final StaxMatcher<T> delegate;
    private int n;

    public NTimesMatcher(StaxMatcher<T> delegate, int n) {
        this.delegate = delegate;
        this.n = n;

    }

    @Override
    public MatcherResult<List<T>> match(EventReader reader) throws XMLStreamException {
        MatcherResult<List<T>> res = new AtMostMatcher<T>(delegate, n).match(reader);
        if (res.isFailure()) {
            return res;
        } else {
            if (res.data.size() < n) {
                return MatcherResult.failure(new BacktrackEventReader(res.consumedEvents, res.reader));
            } else {
                return res;
            }
        }
    }
}