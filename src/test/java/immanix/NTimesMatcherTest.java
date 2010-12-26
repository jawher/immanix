package immanix;

import org.junit.Test;
import immanix.matchers.NTimesMatcher;
import immanix.readers.ReplayEventReader;
import immanix.utils.FullPacman;
import immanix.utils.HungryPacman;

import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static immanix.utils.XMLEventsUtils.c;

public class NTimesMatcherTest {
    @Test
    public void testDelegateMatcherIsCalledCorrectly() throws Exception {
        StaxMatcher<Object> delegateMatcher = mock(StaxMatcher.class);
        when(delegateMatcher.match(null)).thenReturn(MatcherResult.success(null, null, Collections.EMPTY_LIST));

        NTimesMatcher<Object> m = new NTimesMatcher<Object>(delegateMatcher, 4);
        NTimesMatcher<Object> sm = spy(m);

        sm.match(null);

        verify(delegateMatcher, times(5)).match(null);
    }

    @Test
    public void testReaderIsRestoredInCaseOfFailure() throws Exception {
        StaxMatcher<Object> delegateMatcher = new HungryPacman(4);

        NTimesMatcher<Object> m = new NTimesMatcher<Object>(delegateMatcher, 3);
        NTimesMatcher<Object> sm = spy(m);
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("_1"), c("_2"), c("c"), c("d"), c("e"), c("f"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<List<Object>> res = sm.match(reader);


        assertTrue(res.reader.hasNext());
        assertEquals(c("_1"), res.reader.next());
    }

    @Test
    public void testReaderStateInCaseOfSuccess() throws Exception {
        StaxMatcher<Object> delegateMatcher = new HungryPacman(3);

        NTimesMatcher<Object> m = new NTimesMatcher<Object>(delegateMatcher, 3);
        NTimesMatcher<Object> sm = spy(m);
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("_1"), c("_2"), c("c"), c("d"), c("e"), c("f"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<List<Object>> res = sm.match(reader);


        assertTrue(res.reader.hasNext());
        assertEquals(c("d"), res.reader.next());
    }


    @Test
    public void testSuccessWhenWantedNumberOfMatchingsIsReached() throws Exception {
        StaxMatcher<Object> delegateMatcher = new FullPacman(4);

        NTimesMatcher<Object> m = new NTimesMatcher<Object>(delegateMatcher, 4);
        NTimesMatcher<Object> sm = spy(m);

        MatcherResult<List<Object>> res = sm.match(null);

        //must match
        assertFalse(res.isFailure());
        assertEquals(4, res.data.size());

        //this should suffice, as the interactions with the delegate matcher (and hence the reader are tested in  testDelegateMatcherIsCalledCorrectly)
    }

    @Test
    public void testFailureWhenLessThanTheWantedNumberOfMatchingsAreFound() throws Exception {
        StaxMatcher<Object> delegateMatcher = new FullPacman(2);


        NTimesMatcher<Object> m = new NTimesMatcher<Object>(delegateMatcher, 4);
        NTimesMatcher<Object> sm = spy(m);

        MatcherResult<List<Object>> res = sm.match(null);

        //must fail
        assertTrue(res.isFailure());

        //this should suffice, as the interactions with the delegate matcher (and hence the reader are tested in  testDelegateMatcherIsCalledCorrectly)
    }

    @Test
    public void testFailureWhenMoreThanTheWantedNumberOfMatchingsIsFound() throws Exception {
        StaxMatcher<Object> delegateMatcher = new FullPacman(4);

        NTimesMatcher<Object> m = new NTimesMatcher<Object>(delegateMatcher, 3);
        NTimesMatcher<Object> sm = spy(m);

        MatcherResult<List<Object>> res = sm.match(null);

        //must match
        assertTrue(res.isFailure());
        //this should suffice, as the interactions with the delegate matcher (and hence the reader are tested in  testDelegateMatcherIsCalledCorrectly)
    }


}
