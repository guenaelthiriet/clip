package utils;

import java.math.BigInteger;

/**
 * The <code>BaseConverter</code> is a base 10 to any base converter
 * It implements the <code>{@link IConverter}</code> interface.
 *
 * @author Guenael Thiriet
 */

public final class BaseConverter implements IConverter {

    /**
     * Maximum alphabet length supported
     */
    public static final int MAX_ALPHABET_LEN = 62;

    /**
     * Minimum alphabet length supported
     */
    public static final int MIN_ALPHABET_LEN = 2;

    /**
     * An alphabet in which we wish to encode
     */
    private String alphabet;

    /**
     * The target base in which we wish to encode
     */
    private BigInteger base;

    /**
     * @param alphabet The target alphabet to use.
     */
    private BaseConverter(String alphabet) {
        this.alphabet = alphabet;
        this.base = new BigInteger(Integer.valueOf(this.alphabet.length()).toString());
    }

    /**
     * The method validates that the alphabet fits with the supported range of target alphabets.
     *
     * @param alphabet Alphabet to validate.
     * @return IllegalArgumentException
     */
    private static void validateParameters(String alphabet) throws IllegalArgumentException {
        // We assume that the alphabet size always fits in a int int this class.
        boolean isAlphabetValid = (alphabet.length() <= MAX_ALPHABET_LEN) &&
                (alphabet.length() >= MIN_ALPHABET_LEN);
        if (!isAlphabetValid) {
            String message = String.format("Unsupported alphabet length: %d", alphabet.length());
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Factory method to create a <code>BaseConverter</code>.
     *
     * @param alphabet Alphabet used to create the <code>BaseConverter</code>.
     * @return A BaseConverter object instance.
     */
    public static IConverter createBaseConverter(String alphabet) {
        validateParameters(alphabet);
        return new BaseConverter(alphabet);
    }

    /**
     * The function encodes from base 10 to the destination base. Base depends on the alphabet size.
     *
     * @param number An number to encode into destination base.
     * @return A string containing the encoded value of number in destination base.
     */
    public String encode(BigInteger number) {
        StringBuilder str = new StringBuilder();
        while (number.compareTo(BigInteger.ZERO) > 0) {
            str.insert(0, alphabet.charAt(number.mod(base).intValue()));
            number = number.divide(base);
        }
        return str.toString();
    }


    /**
     * The function decodes from the destination base to base 10. Base depends on the alphabet size.
     *
     * @param encodedValue A string representation in the destination base.
     * @return A BigInteger value in base 10 of the encodedValue.
     */
    public BigInteger decode(String encodedValue) {
        BigInteger num = BigInteger.ZERO;
        for (int i = 0; i < encodedValue.length(); i++) {
            BigInteger alphabetValue = BigInteger.valueOf(alphabet.indexOf(encodedValue.charAt(i)));
            num = num.multiply(base).add(alphabetValue);
        }
        return num;
    }

}
