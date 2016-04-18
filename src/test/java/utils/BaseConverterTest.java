package utils;

import clip.Clip;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Created by Guenael Thiriet on 2016-04-11.
 */

@RunWith(Parameterized.class)
public class BaseConverterTest {

    final private String binaryAlphabet = "01";
    final private String octalAlphabet = "01234567";
    final private String baseTenAlphabet = "0123456789";
    final private String hexAlphabet = "0123456789abcdef";
    final private String thirtySixCharsAlphabet = "0123456789abcdefghijklmnopqrstuvwxyz";
    private final String input;
    @Rule
    public ExpectedException exception = ExpectedException.none();


    public BaseConverterTest(String input) {
        this.input = input;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"65535"},
                {"4294234098"},
                {"2535252"},
                {"1285619"},
                {"97341254"},
        });
    }

    // Unfortunately the test is run 5 times.
    @Test
    public void testConstructor() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(String.format("Unsupported alphabet length: %d", BaseConverter.MIN_ALPHABET_LEN - 1));
        BaseConverter.createBaseConverter("0");
    }

    // Unfortunately the test is run 5 times.
    @Test
    public void testConstructor2() {
        // Test with 63 character alphabet
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(String.format("Unsupported alphabet length: %d", BaseConverter.MAX_ALPHABET_LEN + 1));
        BaseConverter.createBaseConverter(Clip.ALPHABET + "_");
    }

    // Unfortunately the test is run 5 times.
    @Test
    public void maxValue() throws Exception {
        IConverter converter = BaseConverter.createBaseConverter(Clip.ALPHABET);
        String maxValue = "ZZZZZZZZZZ";
        Long decodedValue = converter.decode(maxValue);
        assertTrue(decodedValue < Long.MAX_VALUE);
        assertTrue(decodedValue > 0);
    }

    @Test
    public void testBinaryEncode() throws Exception {
        IConverter converter = BaseConverter.createBaseConverter(binaryAlphabet);
        // Let's test against the Java implementation, this will give some confidence in the algorithm
        String javaImplementationEncodedValue = Long.toString(Long.valueOf(input), binaryAlphabet.length());
        assertTrue(converter.encode(new Long(input)).equals(javaImplementationEncodedValue));
    }

    @Test
    public void testOctalEncode() throws Exception {
        IConverter converter = BaseConverter.createBaseConverter(octalAlphabet);
        // Let's test against the Java implementation, this will give some confidence in the algorithm
        String javaImplementationEncodedValue = Long.toString(Long.valueOf(input), octalAlphabet.length());
        assertTrue(converter.encode(new Long(input)).equals(javaImplementationEncodedValue));
    }

    @Test
    public void testBaseTen() throws Exception {
        IConverter converter = BaseConverter.createBaseConverter(baseTenAlphabet);
        // In base 10, the encode and decode should yield the same as input
        assertTrue(converter.encode(new Long(input)).equals(input));
        assertTrue(converter.decode(input).toString().equals(input));
    }

    @Test
    public void testHexEncode() throws Exception {
        IConverter hexConverter = BaseConverter.createBaseConverter(hexAlphabet);
        // Let's test against the Java implementation, this will give some confidence in the algorithm
        String javaImplementationEncodedValue = Long.toString(Long.valueOf(input), hexAlphabet.length());
        assertTrue(hexConverter.encode(new Long(input)).equals(javaImplementationEncodedValue));
    }

    @Test
    public void testThirtySixCharsEncode() throws Exception {
        IConverter converter = BaseConverter.createBaseConverter(thirtySixCharsAlphabet);
        // Let's test against the Java implementation, this will give some confidence in the algorithm
        String javaImplementationEncodedValue = Long.toString(Long.valueOf(input), thirtySixCharsAlphabet.length());
        assertTrue(converter.encode(new Long(input)).equals(javaImplementationEncodedValue));
    }

    @Test
    public void testEncodeDecodeHex() throws Exception {
        IConverter hexConverter = BaseConverter.createBaseConverter(hexAlphabet);
        String encodedValue = hexConverter.encode(new Long(input));
        Long decodedValue = hexConverter.decode(encodedValue);
        assertTrue(decodedValue.toString().equals(input));
    }


    @Test
    public void testEncodeDecodeSixtyTwo() throws Exception {
        IConverter converter = BaseConverter.createBaseConverter(Clip.ALPHABET);
        String encodedValue = converter.encode(new Long(input));
        Long decodedValue = converter.decode(encodedValue);
        assertTrue(decodedValue.toString().equals(input));
    }
}