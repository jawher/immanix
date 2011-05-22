package immanix;

import immanix.matchers.CondMatcher;
import immanix.readers.ReplayEventReader;
import immanix.utils.HungryPacman;
import org.junit.Test;

import javax.xml.stream.events.XMLEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static immanix.utils.XMLEventsUtils.c;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CondMatcherTest {

    @Test
    public void testDelegateMatcherIsCalled() throws Exception {
        List<XMLEvent> events = Arrays.<XMLEvent>asList();
        ReplayEventReader reader = new ReplayEventReader(events);

        StaxMatcher<Object> delegateMatcher = mock(StaxMatcher.class);
        when(delegateMatcher.match(null)).thenReturn(MatcherResult.success(null, reader, Collections.EMPTY_LIST));

        CondMatcher<Object> m = new CondMatcher<Object>(delegateMatcher) {
            @Override
            public boolean validate(Object data) {
                return false;
            }

            @Override
            public String conditionInEnglish() {
                return "Nothing";
            }
        };
        CondMatcher<Object> sm = spy(m);

        sm.match(null);

        verify(delegateMatcher).match(null);
    }


    @Test
    public void testValidateMethodIsCalled() throws Exception {
        List<XMLEvent> events = Arrays.<XMLEvent>asList();
        ReplayEventReader reader = new ReplayEventReader(events);

        StaxMatcher<String> delegateMatcher = mock(StaxMatcher.class);
        when(delegateMatcher.match(null)).thenReturn(MatcherResult.success("magic !", reader, Collections.EMPTY_LIST));

        CondMatcher<String> m = new CondMatcher<String>(delegateMatcher) {
            @Override
            public boolean validate(String data) {
                return false;
            }

            @Override
            public String conditionInEnglish() {
                return "Nothing";
            }
        };
        CondMatcher<String> sm = spy(m);

        sm.match(null);

        verify(sm).validate("magic !");
    }

    @Test
    public void testFailureWhenValidateReturnsFalseCalled() throws Exception {
        List<XMLEvent> events = Arrays.<XMLEvent>asList();
        ReplayEventReader reader = new ReplayEventReader(events);

        StaxMatcher<String> delegateMatcher = mock(StaxMatcher.class);
        when(delegateMatcher.match(null)).thenReturn(MatcherResult.success("magic !", reader, Collections.EMPTY_LIST));

        CondMatcher<String> m = new CondMatcher<String>(delegateMatcher) {
            @Override
            public boolean validate(String data) {
                return false;
            }

            @Override
            public String conditionInEnglish() {
                return "Nothing";
            }
        };
        CondMatcher<String> sm = spy(m);

        assertTrue(sm.match(null).isFailure());
    }

    @Test
    public void testSuccessWhenValidateReturnsTrueCalled() throws Exception {
        List<XMLEvent> events = Arrays.<XMLEvent>asList();
        ReplayEventReader reader = new ReplayEventReader(events);

        StaxMatcher<String> delegateMatcher = mock(StaxMatcher.class);
        when(delegateMatcher.match(null)).thenReturn(MatcherResult.success("magic !", reader, Collections.EMPTY_LIST));

        CondMatcher<String> m = new CondMatcher<String>(delegateMatcher) {
            @Override
            public boolean validate(String data) {
                return true;
            }

            @Override
            public String conditionInEnglish() {
                return "Nothing";
            }
        };
        CondMatcher<String> sm = spy(m);

        assertFalse(sm.match(null).isFailure());
    }

    @Test
    public void testReaderIsRestoredInCaseOfFailure() throws Exception {
        StaxMatcher<Object> delegateMatcher = new HungryPacman(4);

        CondMatcher<Object> m = new CondMatcher<Object>(delegateMatcher) {
            @Override
            public boolean validate(Object data) {
                return false;
            }

            @Override
            public String conditionInEnglish() {
                return "Nothing";
            }
        };

        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("_1"), c("_2"), c("c"), c("d"), c("e"), c("f"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<Object> res = m.match(reader);


        assertTrue(res.reader.hasNext());
        assertEquals(c("_1"), res.reader.next());
    }

    @Test
    public void testReaderStateInCaseOfSuccess() throws Exception {
        StaxMatcher<Object> delegateMatcher = new HungryPacman(3);

        CondMatcher<Object> m = new CondMatcher<Object>(delegateMatcher) {
            @Override
            public boolean validate(Object data) {
                return true;
            }

            @Override
            public String conditionInEnglish() {
                return "Nothing";
            }
        };
        List<XMLEvent> events = Arrays.<XMLEvent>asList(c("_1"), c("_2"), c("c"), c("d"), c("e"), c("f"));
        ReplayEventReader reader = new ReplayEventReader(events);
        MatcherResult<Object> res = m.match(reader);


        assertTrue(res.reader.hasNext());
        assertEquals(c("_2"), res.reader.next());
    }
}
