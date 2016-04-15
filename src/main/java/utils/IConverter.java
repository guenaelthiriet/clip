package utils;

import java.math.BigInteger;

/**
 * The <code>IConverter</code> is an interface for a base converter.
 *
 * @author Guenael Thiriet
 */

public interface IConverter {

    /**
     * The function encodes from base origin to the destination base. Base depends on the alphabet size.
     *
     * @param number An number to encode into destination base.
     * @return A string containing the encoded value of number in destination base.
     */
    public String encode(BigInteger number);

    /**
     * The function decodes from the destination base to base origin. Base depends on the alphabet size.
     *
     * @param encodedValue A string representation in the destination base.
     * @return A BigInteger value in base origin of the encodedValue.
     */
    public BigInteger decode(String encodedValue);
}
