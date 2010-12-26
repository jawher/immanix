package immanix;

import org.junit.Test;
import immanix.matchers.NamedStartElementMatcher;
import immanix.readers.ReplayEventReader;

import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static immanix.utils.XMLEventsUtils.c;
import static immanix.utils.XMLEventsUtils.se;

public class NamedStartElementMatcherTest {
    @Test
    public void testSuccessMatch() throws Exception {
        NamedStartElementMatcher m = new NamedStartElementMatcher("42");
        NamedStartElementMatcher sm = spy(m);
        List<XMLEvent> events = Arrays.asList(se("42"), c("hi"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<StartElement> res = sm.match(reader);
        //must match
        assertFalse(res.isFailure());
       
        //reader must return next element
        assertTrue(res.reader.hasNext());
        assertEquals(c("hi"), res.reader.next());
    }

     @Test
    public void testFailureMatchBecauseOfName() throws Exception {
        NamedStartElementMatcher m = new NamedStartElementMatcher("elem");
        NamedStartElementMatcher sm = spy(m);
        List<XMLEvent> events = Arrays.<XMLEvent>asList(se("42"), c("chars"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<StartElement> res = sm.match(reader);
        //must fail
        assertTrue(res.isFailure());

        //reader must remain untouched
        assertTrue(res.reader.hasNext());
        assertEquals(se("42"), res.reader.next());

    }

    @Test
    public void testFailureMatchBecauseOfDifferentType() throws Exception {
        NamedStartElementMatcher m = new NamedStartElementMatcher("elem");
        NamedStartElementMatcher sm = spy(m);
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("chars"), se("42"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<StartElement> res = sm.match(reader);
        //must fail
        assertTrue(res.isFailure());
        //reader must remain untouched
        assertTrue(res.reader.hasNext());
        assertEquals(c("chars"), res.reader.next());

    }
}
