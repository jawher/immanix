package immanix;

import org.junit.Test;
import immanix.matchers.AtLeastButFiniteMatcher;
import immanix.readers.ReplayEventReader;
import immanix.utils.FullPacman;
import immanix.utils.HungryPacman;

import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static immanix.utils.XMLEventsUtils.c;

public class AtLeastButFiniteMatcherTest {

    @Test
    public void testDelegateMatcherIsCalledCorrectly() throws Exception {
        StaxMatcher< Object> delegateMatcher = new FullPacman(6);
        StaxMatcher<Object> spyDelegateMatcher = spy(delegateMatcher);

        AtLeastButFiniteMatcher<Object> m = new AtLeastButFiniteMatcher<Object>(spyDelegateMatcher, 4);
        AtLeastButFiniteMatcher<Object> sm = spy(m);

        sm.match(null);

        verify(spyDelegateMatcher, times(7)).match(null);
    }

    @Test
    public void testReaderIsRestoredInCaseOfFailure() throws Exception {
        StaxMatcher<Object> delegateMatcher = new HungryPacman(3);

        AtLeastButFiniteMatcher<Object> m = new AtLeastButFiniteMatcher<Object>(delegateMatcher, 4);
        AtLeastButFiniteMatcher<Object> sm = spy(m);
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("_1"), c("_2"), c("c"), c("d"), c("e"), c("f"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<List<Object>> res = sm.match(reader);


        assertTrue(res.reader.hasNext());
        assertEquals(c("_1"), res.reader.next());
    }

    @Test
    public void testReaderStateInCaseOfSuccess() throws Exception {
        StaxMatcher<Object> delegateMatcher = new HungryPacman(5);

        AtLeastButFiniteMatcher<Object> m = new AtLeastButFiniteMatcher<Object>(delegateMatcher, 4);
        AtLeastButFiniteMatcher<Object> sm = spy(m);
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("_1"), c("_2"), c("c"), c("d"), c("e"), c("f"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<List<Object>> res = sm.match(reader);


        assertTrue(res.reader.hasNext());
        assertEquals(c("f"), res.reader.next());
    }


    @Test
    public void testSuccessWhenWantedNumberOfMatchingsIsReached() throws Exception {
        StaxMatcher<Object> delegateMatcher = new FullPacman(4);


        AtLeastButFiniteMatcher<Object> m = new AtLeastButFiniteMatcher<Object>(delegateMatcher, 4);
        AtLeastButFiniteMatcher<Object> sm = spy(m);

        MatcherResult<List<Object>> res = sm.match(null);

        //must match
        assertFalse(res.isFailure());
        assertEquals(4, res.data.size());

        //this should suffice, as the interactions with the delegate matcher (and hence the reader are tested in  testDelegateMatcherIsCalledCorrectly)
    }

    @Test
    public void testSuccessWhenMoreThanTheWantedNumberOfMatchingsAreFound() throws Exception {
        StaxMatcher<Object> delegateMatcher = new FullPacman(6);

        AtLeastButFiniteMatcher<Object> m = new AtLeastButFiniteMatcher<Object>(delegateMatcher, 4);
        AtLeastButFiniteMatcher<Object> sm = spy(m);

        MatcherResult<List<Object>> res = sm.match(null);

        //must match
        assertFalse(res.isFailure());
        assertEquals(6, res.data.size());
        //this should suffice, as the interactions with the delegate matcher (and hence the reader are tested in  testDelegateMatcherIsCalledCorrectly)
    }

    @Test
    public void testFailureWhenLessThanTheWantedNumberOfMatchingsIsFound() throws Exception {
        StaxMatcher<Object> delegateMatcher = new FullPacman(3);
        AtLeastButFiniteMatcher<Object> m = new AtLeastButFiniteMatcher<Object>(delegateMatcher, 4);
        AtLeastButFiniteMatcher<Object> sm = spy(m);

        MatcherResult<List<Object>> res = sm.match(null);

        //must match
        assertTrue(res.isFailure());
        //this should suffice, as the interactions with the delegate matcher (and hence the reader are tested in  testDelegateMatcherIsCalledCorrectly)
    }


}
