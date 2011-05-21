package immanix.matchers;

import immanix.EventReader;
import immanix.MatcherResult;
import immanix.StaxMatcher;

import javax.xml.stream.XMLStreamException;

public abstract class Mapper<S, T> extends StaxMatcher<T> {
    private final StaxMatcher<S> delegate;

    public Mapper(StaxMatcher<S> delegate) {
        this.delegate = delegate;
    }

    @Override
    public MatcherResult<T> match(EventReader reader) throws XMLStreamException {
        MatcherResult<S> res = delegate.match(reader);
        if (res.isFailure()) {
            return MatcherResult.failure(res.reader, res.errorMessage + "\n" + toString() +
                    " failed due to the previous error");
        } else {
            return MatcherResult.success(process(res.data), res.reader, res.consumedEvents);
        }
    }

    public abstract T process(S data);
}
