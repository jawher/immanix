package immanix;

import immanix.matchers.NamedStartElementMatcher;
import immanix.readers.ReplayEventReader;
import org.junit.Test;

import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;
import java.util.List;

import static immanix.utils.XMLEventsUtils.c;
import static immanix.utils.XMLEventsUtils.se;
import static org.junit.Assert.*;

public class NamedStartElementMatcherTest {

    public static final String NS = "http://immanix.net/ns";

    @Test
    public void testSuccessMatchOnLocalPartWithNoNsXml() throws Exception {
        mustSucceed(new NamedStartElementMatcher("42"), Arrays.asList(se("42"), c("hi")));
    }

    @Test
    public void testSuccessMatchOnLocalPartWithNsXml() throws Exception {
        mustSucceed(new NamedStartElementMatcher("42"), Arrays.asList(se(NS, "42"), c("hi")));
    }

    @Test
    public void testSuccessMatchOnLocalPartAndNamespace() throws Exception {
        mustSucceed(new NamedStartElementMatcher(NS, "42"), Arrays.asList(se(NS, "42"), c("hi")));
    }

    @Test
    public void testFailureMatchBecauseOfNameWithNoNsXml() throws Exception {
        mustFail(new NamedStartElementMatcher("elem"), Arrays.<XMLEvent>asList(se("42"), c("chars")));
    }

    @Test
    public void testFailureMatchBecauseOfNameWithNsXml() throws Exception {
        mustFail(new NamedStartElementMatcher("elem"), Arrays.<XMLEvent>asList(se(NS, "42"), c("chars")));
    }

    @Test
    public void testFailureMatchBecauseOfNsWithNoNsXml() throws Exception {
        mustFail(new NamedStartElementMatcher(NS, "elem"), Arrays.<XMLEvent>asList(se("elem"), c("chars")));
    }

    @Test
    public void testFailureMatchBecauseOfNsWithNsXml() throws Exception {
        String ns2 = NS + "x";
        mustFail(new NamedStartElementMatcher(NS, "elem"), Arrays.<XMLEvent>asList(se(ns2,
                "elem"), c("chars")));
    }

    @Test
    public void testFailureMatchBecauseOfDifferentType() throws Exception {
        mustFail(new NamedStartElementMatcher("elem"), Arrays.<XMLEvent>asList(c("chars"), se("42")));
    }

    private void mustFail(NamedStartElementMatcher m, List<XMLEvent> events) throws Exception {
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<StartElement> res = m.match(reader);
        //must fail
        assertTrue(res.isFailure());
    }

    private void mustSucceed(NamedStartElementMatcher m, List<XMLEvent> events) throws Exception {
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<StartElement> res = m.match(reader);
        //must succeed
        assertFalse(res.isFailure());
    }

    @Test
    public void testReaderStateIsRestoredAfterFailure() throws Exception {
        NamedStartElementMatcher m = new NamedStartElementMatcher("elem");
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("chars"), se("42"));
        ReplayEventReader reader = new ReplayEventReader(events);

        MatcherResult<StartElement> res = m.match(reader);//failure
        //reader must remain untouched
        assertTrue(res.reader.hasNext());
        assertEquals(c("chars"), res.reader.next());
    }
}
