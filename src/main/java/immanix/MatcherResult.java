package immanix;

import javax.xml.stream.events.XMLEvent;
import java.util.Collections;
import java.util.List;

public final class MatcherResult<T> {
    public final T data;
    public final EventReader reader;
    public final List<XMLEvent> consumedEvents;

    final boolean failure;

    private MatcherResult(T data, EventReader reader, List<XMLEvent> consumedEvents, boolean failure) {
        super();
        this.data = data;
        this.reader = reader;
        this.consumedEvents = consumedEvents;
        this.failure = failure;
    }

    public static <S> MatcherResult<S> success(S data, EventReader reader, List<XMLEvent> consumedEvents) {
        return new MatcherResult<S>(data, reader, consumedEvents, false);
    }

    public static <S> MatcherResult<S> failure(EventReader reader) {
        return new MatcherResult<S>(null, reader, Collections.<XMLEvent>emptyList(), true);
    }

    public boolean isFailure() {
        return failure;
    }

}
