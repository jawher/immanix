Immanix
=======================

A Java library for processing (large) xml files using parser combinators-like approach.
Please see below for a more detailed explanation on how to use Immanix.

Building
--------

You need a Java 5 (or newer) environment and Maven 3 installed:

    $ mvn --version

    Apache Maven 2.2.1 (r801777; 2009-08-06 20:16:01+0100)
    Java version: 1.6.0_20
    Java home: /usr/lib/jvm/java-6-openjdk/jre
    Default locale: en_US, platform encoding: UTF-8
    OS name: "linux" version: "2.6.35-24-generic" arch: "amd64" Family: "unix"

You should now be able to do a full build of `ojaas`:

    $ git clone git://github.com/jawher/immanix.git
    $ cd immanix
    $ mvn clean install

To use this library in your projects, add the following to the `dependencies` section of your
`pom.xml`:

    <dependency>
      <groupId>jawher</groupId>
      <artifactId>immanix</artifactId>
      <version>0.9-SNAPSHOT</version>
    </dependency>

Troubleshooting
---------------

Please consider using [Github issues tracker](http://github.com/jawher/immanix/issues) to submit bug reports or feature requests.


Using this library
------------------

I've compiled [some simple and elemetary use cases in this gist](http://gist.github.com/522028), and [some more complex and advanced use cases in this gist](http://gist.github.com/522037).

Here's a quick sample of how `moulder` can be used to manipulate html:

Given an xml file that looks like the following:

    <root>
        <entry>
            <value>[...]</value>
        </entry>
        <entry>
            <value>[...]</value>
            <extra>[...]</extra>
        </entry>
        <entry>
            <value>[...]</value>
        </entry>
        :
        :
    </root>

Where the entry element can appear any number of times, and the extra element is optional (may be present or not), a
parser able to process this xml by combining simple and elemetary parsers:

Start by matching the <value> element, which in StAX language can be thought of as a sequence of 3 events: element start,
characters and element end.

An element start event can be matched by the StartElementParser. but here, we actually need to match an element start
with a precise name "value", so we're going to use NamedStartElementMatcher instead:

    StaxMatcher<StartElement> s = new NamedStartElementMatcher("value");

We can also use a factory method to save ourselves a couple of keystrokes:

    StaxMatcher<StartElement> s = start("value");

The produced object s is an elementary matcher (or parser) that'll read an event from the XML stream, and check if it's
a start element event, and if the element has the supplied name. If this is the case, it succeeds and returns the start
element event as a result, if not, it'll fail and no events will be consumed from the stream.

Afterwards, we need to match the textual content of the <value> element. The CharsMatcher can be used to this end:

    StaxMatcher<String> t = new CharsMatcher();

Or by using the factory method:

    StaxMatcher<String> t = chars();

Notice that the produced matcher is parametrized with the String type, which indicates that if the matcher succeeds, a
String is returned.

We need now to combine these 2 matchers (s and t) into a new matcher that'll match a start element named "value", then
a textual content. the SuccMatcher can be used to this end:

    StaxMatcher<Tuple2<StartElement, String>> st = new SuccMatcher<StartElement, String>(s, t);

There's a shorter syntax to achieve the same:

    StaxMatcher<Tuple2<StartElement, String>> st = s.then(t);

The combined matcher returns a Tuple2 (a pair of objects) of a StartElement event and a String if it succeeds.

Usuallay, we won't be interested in the start element event, but only in the textual content of the element for the
processing we intend to perform. In such cases, we can use another variant of the succession matcher which only keeps
the result of one of its child matchers (left or right):

    StaxMatcher<String> st = new RightSuccMatcher<StartElement, String>(s, t);

Or with the shorter syntax:

    StaxMatcher<String> st = s.thenr(t);

Notice that the result matcher is parametrized with the String type only, as the StartElement event is discarded.

We then combine the st matcher with another matcher that accepts the "value" end element event, and as we won't need
this latter in the processing we intend to perform later, we're going to use a LeftSuccMatcher:

    StaxMatcher<String> ste = st.thenl(end("value");

Combining all these steps, we end up with this code:

    StaxMatcher<StartElement> s = start("value");
    StaxMatcher<String> t = chars();
    StaxMatcher<String> st = s.thenr(t);
    StaxMatcher<String> ste = st.thenl(end("value");

Or in one line:

    StaxMatcher<String> value = start("value").thenr(chars()).thenl(end("value"));

Using the same process for the <extra> element, we end up with:

    StaxMatcher<String> extra = start("extra").thenr(chars()).thenl(end("extra"));

Except that the <extra> element is optional. If it doesn't appear in the XML file, the parsing will fail.
To handle such cases, a matcher can be marked as optional by simply calling a method on it:

    extra = extra.optional();

Or in one line:

    StaxMatcher<String> extra = start("extra").thenr(chars()).thenl(end("extra")).optional();

We need now to combine the value matcher with the extra matcher. As we need both of their results, we combine the using
then:

    StaxMatcher<Tuple2<String, String>>  valueExtra = value.then(extra);

Augmenting this matcher to handle the <entry> element can be achieved this way :

    StaxMatcher<Tuple2<String, String>>  entry =  start("entry").thenr(valueExtra).thenl(end("entry"));

Once an entry is matched, we usually need to perform an operation on it, inserting it in a database for example. To
inject code to be executed when a matcher succeeds, the Mapper matcher can be used:

    Mapper<Void> execEntry = new Mapper<Tuple2<String, String>, T>(entry) {
        public Void process(Tuple2<String, String> data) {
            // insert data into the database
            return null;
        }
    }

Mapper is actually a matcher that transforms the results of a matcher into another type/value. In this case, and since
we won't need the entry matcher's result in the following, we return null.

As said earlier, the <entry> element can appear any number of times in the file.
To handle this case, the entry matcher can be augmented to handle repetition, by calling one of the repetition methods
on it (zeroOrMore, zeroOrMoreButFinite, oneOrMore, oneOrMorebutfinite, atLeast, atLeastBuFinite, atMost):

    StaxMatcher<Void> entries = execEntry.zeroOrMore();

Finally, it remains to handle the root element and the document start/end events:

    StaxMatcher<Void> almighty = startDoc().then(start("root")).thenr(entries).thenl(end("root")).thenl(endDoc());

We ended up with a combined matcher that can match the whole XML file by calling its match method on a XMLEventReader:

    XMLInputFactory inputFactory = createInputFactory();
    XMLEventReader evr = inputFactory.createXMLEventReader(new FileInputStream("/some/file.xml"));

    MatcherResult<Void> result = almighty.match(evr);
    if(result.isFailure()){
        //handle errors
    } else {
        //everything went well
    }


License
-------

See `LICENSE` for details.
