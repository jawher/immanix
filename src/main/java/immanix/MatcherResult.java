package immanix;

import javax.xml.stream.events.XMLEvent;
import java.util.Collections;
import java.util.List;

public final class MatcherResult<T> {
    public final T data;
    public final EventReader reader;
    public final List<XMLEvent> consumedEvents;

    private final boolean failure;
    public final String errorMessage;

    private MatcherResult(T data, EventReader reader, List<XMLEvent> consumedEvents, boolean failure, String errorMessage) {
        super();
        this.data = data;
        this.reader = reader;
        this.consumedEvents = consumedEvents;
        this.failure = failure;
        this.errorMessage = errorMessage;
    }

    public static <S> MatcherResult<S> success(S data, EventReader reader, List<XMLEvent> consumedEvents) {
        return new MatcherResult<S>(data, reader, consumedEvents, false, null);
    }

    public static <S> MatcherResult<S> failure(EventReader reader, String errorMessage) {
        return new MatcherResult<S>(null, reader, Collections.<XMLEvent>emptyList(), true, errorMessage);
    }

    public boolean isFailure() {
        return failure;
    }

}
