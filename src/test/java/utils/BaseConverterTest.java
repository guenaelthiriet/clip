package utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Created by Guenael Thiriet on 2016-04-11.
 */

@RunWith(Parameterized.class)
public class BaseConverterTest {

    final String binaryAlphabet = "01";
    final String octalAlphabet = "01234567";
    final String baseTenAlphabet = "0123456789";
    final String hexAlphabet = "0123456789abcdef";
    final String thirtySixCharsAlphabet = "0123456789abcdefghijklmnopqrstuvwxyz";
    final String sixtyTwoCharsAlphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private String input;


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
        BaseConverter.createBaseConverter(sixtyTwoCharsAlphabet + "_");
    }

    @Test
    public void testBinaryEncode() throws Exception {
        IConverter hexConverter = BaseConverter.createBaseConverter(binaryAlphabet);
        // Let's test against the Java implementation, this will give some confidence in the algorithm
        String javaImplementationEncodedValue = Long.toString(Long.valueOf(input), binaryAlphabet.length());
        assertTrue(hexConverter.encode(new BigInteger(input)).equals(javaImplementationEncodedValue));
    }

    @Test
    public void testOctalEncode() throws Exception {
        IConverter hexConverter = BaseConverter.createBaseConverter(octalAlphabet);
        // Let's test against the Java implementation, this will give some confidence in the algorithm
        String javaImplementationEncodedValue = Long.toString(Long.valueOf(input), octalAlphabet.length());
        assertTrue(hexConverter.encode(new BigInteger(input)).equals(javaImplementationEncodedValue));
    }

    @Test
    public void testBaseTen() throws Exception {
        IConverter hexConverter = BaseConverter.createBaseConverter(baseTenAlphabet);
        // In base 10, the encode and decode should yield the same as input
        assertTrue(hexConverter.encode(new BigInteger(input)).equals(input));
        assertTrue(hexConverter.decode(input).toString().equals(input));
    }

    @Test
    public void testHexEncode() throws Exception {
        IConverter hexConverter = BaseConverter.createBaseConverter(hexAlphabet);
        // Let's test against the Java implementation, this will give some confidence in the algorithm
        String javaImplementationEncodedValue = Long.toString(Long.valueOf(input), hexAlphabet.length());
        assertTrue(hexConverter.encode(new BigInteger(input)).equals(javaImplementationEncodedValue));
    }

    @Test
    public void testThirtySixCharsEncode() throws Exception {
        IConverter hexConverter = BaseConverter.createBaseConverter(thirtySixCharsAlphabet);
        // Let's test against the Java implementation, this will give some confidence in the algorithm
        String javaImplementationEncodedValue = Long.toString(Long.valueOf(input), thirtySixCharsAlphabet.length());
        assertTrue(hexConverter.encode(new BigInteger(input)).equals(javaImplementationEncodedValue));
    }

    @Test
    public void testEncodeDecodeHex() throws Exception {
        IConverter hexConverter = BaseConverter.createBaseConverter(hexAlphabet);
        String encodedValue = hexConverter.encode(new BigInteger(input));
        BigInteger decodedValue = hexConverter.decode(encodedValue);
        assertTrue(decodedValue.toString().equals(input));
    }

    @Test
    public void testEncodeDecodeSixtyTwo() throws Exception {
        IConverter hexConverter = BaseConverter.createBaseConverter(sixtyTwoCharsAlphabet);
        String encodedValue = hexConverter.encode(new BigInteger(input));
        BigInteger decodedValue = hexConverter.decode(encodedValue);
        assertTrue(decodedValue.toString().equals(input));
    }

}