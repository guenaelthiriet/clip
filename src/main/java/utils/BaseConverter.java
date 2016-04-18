package utils;

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
    private final String alphabet;

    /**
     * The target base in which we wish to encode
     */
    private final Long base;

    /**
     * @param alphabet The target alphabet to use.
     */
    private BaseConverter(String alphabet) {
        this.alphabet = alphabet;
        this.base = Long.valueOf(this.alphabet.length());
    }

    /**
     * The method validates that the alphabet fits with the supported range of target alphabets.
     *
     * @param alphabet Alphabet to validate.
     * @throws IllegalArgumentException When parameters provided are invalid.
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
    public String encode(Long number) {
        StringBuilder str = new StringBuilder();
        while (number.longValue() > 0) {
            long modulus = number % base;
            if(modulus > Integer.MAX_VALUE) {
                // However unlikely, let's be prepared
                return null;
            }

            str.insert(0, alphabet.charAt((int) modulus));
            number = number / base;
        }
        return str.toString();
    }


    /**
     * The function decodes from the destination base to base 10. Base depends on the alphabet size.
     *
     * @param encodedValue A string representation in the destination base.
     * @return A Long value in base 10 of the encodedValue.
     */
    public Long decode(String encodedValue) {
        Long num = Long.valueOf(0);
        for (int i = 0; i < encodedValue.length(); i++) {
            Long alphabetValue = (long) alphabet.indexOf(encodedValue.charAt(i));
            num = (num * base) + alphabetValue;
        }
        return num;
    }

}
