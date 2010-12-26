package immanix;

import org.junit.Test;
import immanix.matchers.CharsMatcher;
import immanix.readers.ReplayEventReader;

import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static immanix.utils.XMLEventsUtils.*;

public class CharsMatcherTest {
    @Test
    public void testSuccessMatch() throws Exception {
        CharsMatcher m = new CharsMatcher();
        CharsMatcher sm = spy(m);
        List<XMLEvent> events = Arrays.asList(c("hi"), se("42"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<String> res = sm.match(reader);
        //must match
        assertFalse(res.isFailure());
        //reader must return next element
        assertTrue(res.reader.hasNext());
        assertEquals(se("42"), res.reader.next());
    }

    @Test
    public void testFailureMatch() throws Exception {
        CharsMatcher m = new CharsMatcher();
        CharsMatcher sm = spy(m);
        List<XMLEvent> events = Arrays.<XMLEvent>asList(se("elem"), se("42"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<String> res = sm.match(reader);
        //must fail
        assertTrue(res.isFailure());
        //reader must remain untouched
        assertTrue(res.reader.hasNext());
        assertEquals(se("elem"), res.reader.next());

    }
}
