package immanix;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CharsMatcherTest.class, StartElementMatcherTest.class, EndElementMatcherTest.class,
        NamedStartElementMatcherTest.class, NamedEndElementMatcherTest.class, AtLeastButFiniteMatcherTest.class,
        AtMostMatcherTest.class, NTimesMatcherTest.class})
public class AllTests {
}
