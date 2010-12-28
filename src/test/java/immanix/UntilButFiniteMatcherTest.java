package immanix;

import immanix.matchers.UntilButFiniteMatcher;
import immanix.readers.ReplayEventReader;
import immanix.utils.LatentPacman;
import org.junit.Test;

import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;
import java.util.List;

import static immanix.utils.XMLEventsUtils.*;
import static org.junit.Assert.*;

public class UntilButFiniteMatcherTest {
    @Test
    public void testSuccessMatch() throws Exception {
        UntilButFiniteMatcher<Object> m = new UntilButFiniteMatcher<Object>(new LatentPacman(2), 10);
        List<XMLEvent> events = Arrays.asList(se("42"), c("hi"), se("remains"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<Object> res = m.match(reader);
        //must match
        assertFalse(res.isFailure());

        assertEquals("S", res.data);
        //reader must return next element
        assertTrue(res.reader.hasNext());
        assertEquals(se("remains"), res.reader.next());
    }

    @Test
    public void testFailureAtEOF() throws Exception {
        UntilButFiniteMatcher<Object> m = new UntilButFiniteMatcher<Object>(new LatentPacman(3), 10);
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("chars"), se("42"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<Object> res = m.match(reader);
        //must fail
        assertTrue(res.isFailure());
        //reader must remain untouched
        assertTrue(res.reader.hasNext());
        assertEquals(c("chars"), res.reader.next());

    }

    @Test
    public void testFailureAtGivupBarrier() throws Exception {
        UntilButFiniteMatcher<Object> m = new UntilButFiniteMatcher<Object>(new LatentPacman(5), 3);
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("chars"), se("42"), c("a"), c("b"), c("c"), ee("42"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<Object> res = m.match(reader);
        //must fail
        assertTrue(res.isFailure());
        //reader must remain untouched
        assertTrue(res.reader.hasNext());
        assertEquals(c("chars"), res.reader.next());

    }
}
