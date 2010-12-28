package immanix;

import immanix.matchers.UntilMatcher;
import immanix.readers.ReplayEventReader;
import immanix.utils.LatentPacman;
import org.junit.Test;

import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;
import java.util.List;

import static immanix.utils.XMLEventsUtils.c;
import static immanix.utils.XMLEventsUtils.se;
import static org.junit.Assert.*;

public class UntilMatcherTest {
    @Test
    public void testSuccessMatch() throws Exception {
        UntilMatcher<Object> m = new UntilMatcher<Object>(new LatentPacman(2));
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
    public void testFailureMatch() throws Exception {
        UntilMatcher<Object> m = new UntilMatcher<Object>(new LatentPacman(3));
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("chars"), se("42"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<Object> res = m.match(reader);
        //must fail
        assertTrue(res.isFailure());
        //reader must be consumed
        assertFalse(res.reader.hasNext());

    }
}
