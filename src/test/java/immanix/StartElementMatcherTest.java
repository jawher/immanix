package immanix;

import org.junit.Test;
import immanix.matchers.StartElementMatcher;
import immanix.readers.ReplayEventReader;

import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static immanix.utils.XMLEventsUtils.c;
import static immanix.utils.XMLEventsUtils.se;

public class StartElementMatcherTest {
    @Test
    public void testSuccessMatch() throws Exception {
        StartElementMatcher m = new StartElementMatcher();
        List<XMLEvent> events = Arrays.asList(se("42"), c("hi"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<StartElement> res = m.match(reader);
        //must match
        assertFalse(res.isFailure());
        //must call the data method with "hi"
        assertEquals(se("42"), res.data);
        //reader must return next element
        assertTrue(res.reader.hasNext());
        assertEquals(c("hi"), res.reader.next());
    }

    @Test
    public void testFailureMatch() throws Exception {
        StartElementMatcher m = new StartElementMatcher();
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("chars"), se("42"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<StartElement> res = m.match(reader);
        //must fail
        assertTrue(res.isFailure());
        //reader must remain untouched
        assertTrue(res.reader.hasNext());
        assertEquals(c("chars"), res.reader.next());

    }
}
