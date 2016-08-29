package immanix;

import immanix.matchers.Mapper;
import immanix.matchers.UntilMatcher;
import immanix.readers.StaxEventReader;
import immanix.tuples.Tuple2;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import java.io.IOException;
import java.io.InputStream;

import static immanix.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class CharacterisationTest {

    @Test
    public void worksAsPerTheDocumentation() throws XMLStreamException, IOException {
        // validate we have well-formed XML
        try (InputStream is = getInputStream("/example.xml");) {
            XMLEventReader reader = createReader(is);
            reader.forEachRemaining(d -> {
            });
        }

        // Got this far, we have well-formed XML
        StaxMatcher<String> value = wsl(start("value")).thenr(chars()).thenl(wsr(end("value")));
        StaxMatcher<String> extra = wsl(start("extra")).thenr(chars()).thenl(wsr(end("extra"))).optional();

        StaxMatcher<Tuple2<String, String>> entry = wsl(start("entry")).thenr(value).then(extra).thenl(wsr(end("entry")));

        final int[] entryCount = {0};

        Mapper<Tuple2<String, String>, Void> execEntry = new Mapper<Tuple2<String, String>, Void>(entry) {
            @Override
            public Void process(Tuple2<String, String> data) {
                entryCount[0]++;
                return null;
            }

            public String toString() {
                return entry.toString();
            }
        };

        StaxMatcher<Void> entries = execEntry.zeroOrMore();

        StaxMatcher<Void> almighty = startDoc().then(start("root")).thenr(entries).thenl(end("root")).thenl(endDoc());

        try (InputStream is = getInputStream("/example.xml");) {
            XMLEventReader reader = createReader(is);
            MatcherResult<Void> result = almighty.match(new StaxEventReader(reader));

            if (result.isFailure()) {
                //handle errors
                fail(result.errorMessage);
            }

            assertThat("3 <entry/> elements were processed", entryCount[0], is(equalTo(3)));
        }
    }

    private XMLEventReader createReader(InputStream inputStream) throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
        xmlInputFactory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, true);
        xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
        return xmlInputFactory.createXMLEventReader(inputStream);
    }

    private InputStream getInputStream(String name) {
        InputStream resourceAsStream = getClass().getResourceAsStream(name);
        assertThat("We have loaded the expected resource " +name, resourceAsStream, is(not(nullValue())));
        return resourceAsStream;
    }

}
