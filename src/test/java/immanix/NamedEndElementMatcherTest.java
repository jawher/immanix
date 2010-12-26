package immanix;

import org.junit.Test;
import immanix.matchers.NamedEndElementMatcher;
import immanix.readers.ReplayEventReader;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static immanix.utils.XMLEventsUtils.*;

public class NamedEndElementMatcherTest {
    @Test
    public void testSuccessMatch() throws Exception {
        NamedEndElementMatcher m = new NamedEndElementMatcher("42");
        NamedEndElementMatcher sm = spy(m);
        List<XMLEvent> events = Arrays.asList(ee("42"), c("hi"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<EndElement> res = sm.match(reader);
        //must match
        assertFalse(res.isFailure());
        //reader must return next element
        assertTrue(res.reader.hasNext());
        assertEquals(c("hi"), res.reader.next());
    }

    @Test
    public void testFailureMatchBecauseOfDifferentName() throws Exception {
        NamedEndElementMatcher m = new NamedEndElementMatcher("elem");
        NamedEndElementMatcher sm = spy(m);
        List<XMLEvent> events = Arrays.<XMLEvent>asList(ee("42"), c("chars"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<EndElement> res = sm.match(reader);
        //must fail
        assertTrue(res.isFailure());
        //reader must remain untouched
        assertTrue(res.reader.hasNext());
        assertEquals(ee("42"), res.reader.next());

    }

    @Test
    public void testFailureMatchBecauseOfDifferentType() throws Exception {
        NamedEndElementMatcher m = new NamedEndElementMatcher("elem");
        NamedEndElementMatcher sm = spy(m);
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("chars"), ee("42"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<EndElement> res = sm.match(reader);
        //must fail
        assertTrue(res.isFailure());
        //reader must remain untouched
        assertTrue(res.reader.hasNext());
        assertEquals(c("chars"), res.reader.next());

    }
}
