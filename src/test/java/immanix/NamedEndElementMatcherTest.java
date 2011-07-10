package immanix;

import immanix.matchers.NamedEndElementMatcher;
import immanix.readers.ReplayEventReader;
import org.junit.Test;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;
import java.util.List;

import static immanix.utils.XMLEventsUtils.*;
import static org.junit.Assert.*;

public class NamedEndElementMatcherTest {

    public static final String NS = "http://immanix.net/ns";

    @Test
    public void testSuccessMatchOnLocalPartWithNoNsXml() throws Exception {
        mustSucceed(new NamedEndElementMatcher("42"), Arrays.asList(ee("42"), c("hi")));
    }

    @Test
    public void testSuccessMatchOnLocalPartWithNsXml() throws Exception {
        mustSucceed(new NamedEndElementMatcher("42"), Arrays.asList(ee(NS, "42"), c("hi")));
    }

    @Test
    public void testSuccessMatchOnLocalAndNamespace() throws Exception {
        mustSucceed(new NamedEndElementMatcher(NS, "42"), Arrays.asList(ee(NS, "42"), c("hi")));
    }

    @Test
    public void testFailureMatchBecauseOfDifferentNameWithNoNsXml() throws Exception {
        mustFail(new NamedEndElementMatcher("elem"), Arrays.<XMLEvent>asList(ee("42"), c("chars")));
    }

    @Test
    public void testFailureMatchBecauseOfDifferentNameWithNsXml() throws Exception {
        mustFail(new NamedEndElementMatcher("elem"), Arrays.<XMLEvent>asList(ee(NS, "42"), c("chars")));
    }

    @Test
    public void testFailureMatchBecauseOfNsWithNoNsXml() throws Exception {
        mustFail(new NamedEndElementMatcher(NS, "elem"), Arrays.<XMLEvent>asList(ee("elem"), c("chars")));
    }

    @Test
    public void testFailureMatchBecauseOfNsWithNsXml() throws Exception {
        mustFail(new NamedEndElementMatcher(NS, "elem"), Arrays.<XMLEvent>asList(ee(NS + "x",
                "elem"),
                c("chars")));
    }


    @Test
    public void testFailureMatchBecauseOfDifferentType() throws Exception {
        mustFail(new NamedEndElementMatcher("elem"), Arrays.<XMLEvent>asList(c("chars"), ee("42")));
    }

    private void mustFail(NamedEndElementMatcher m, List<XMLEvent> events) throws Exception {
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<EndElement> res = m.match(reader);
        //must fail
        assertTrue(res.isFailure());
    }

    private void mustSucceed(NamedEndElementMatcher m, List<XMLEvent> events) throws Exception {
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<EndElement> res = m.match(reader);
        //must succeed
        assertFalse(res.isFailure());
    }

    @Test
    public void testReaderStateIsRestoredAfterFailure() throws Exception {
        NamedEndElementMatcher m = new NamedEndElementMatcher("elem");
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("chars"), se("42"));
        ReplayEventReader reader = new ReplayEventReader(events);

        MatcherResult<EndElement> res = m.match(reader);//failure
        //reader must remain untouched
        assertTrue(res.reader.hasNext());
        assertEquals(c("chars"), res.reader.next());
    }
}
