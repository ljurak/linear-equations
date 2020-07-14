package number;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexParserTest {

    private final ComplexParser parser = new ComplexParser();

    @Test
    public void shouldParseRealNumbers() {
        assertEquals("5.0", parser.parse("5").toString());
        assertEquals("0.0", parser.parse("0").toString());
        assertEquals("-5.0", parser.parse("-5").toString());
        assertEquals("0.454", parser.parse("0.454").toString());
        assertEquals("3.45", parser.parse("3.45").toString());
        assertEquals("-9.21", parser.parse("-9.21").toString());
    }

    @Test
    public void shouldParseImaginaryNumbers() {
        assertEquals("1.0i", parser.parse("i").toString());
        assertEquals("-1.0i", parser.parse("-i").toString());
        assertEquals("1.0i", parser.parse("1i").toString());
        assertEquals("-1.0i", parser.parse("-1i").toString());
        assertEquals("5.0i", parser.parse("5i").toString());
        assertEquals("-5.0i", parser.parse("-5i").toString());
        assertEquals("-3.46i", parser.parse("-3.46i").toString());
        assertEquals("0.763i", parser.parse("0.763i").toString());
        assertEquals("8.45i", parser.parse("8.45i").toString());
    }

    @Test
    public void shouldParseComplexNumbers() {
        assertEquals("5.0+2.0i", parser.parse("5+2i").toString());
        assertEquals("-1.0-1.0i", parser.parse("-1-i").toString());
        assertEquals("-7.65+3.65i", parser.parse("-7.65+3.65i").toString());
        assertEquals("-3.45-2.0i", parser.parse("-3.45-2i").toString());
        assertEquals("4.0-3.457i", parser.parse("4-3.457i").toString());
        assertEquals("3.1+8.0i", parser.parse("3.1+8i").toString());
        assertEquals("9.27+1.0i", parser.parse("9.27+i").toString());
        assertEquals("7.65+4.5i", parser.parse("7.65+4.5i").toString());
        assertEquals("-5.4-0.98i", parser.parse("-5.4-0.98i").toString());
    }
}
