package generator;

import java.math.BigInteger;

/**
 * The <code>Generator</code> is an interface for
 * a unique identifier generator.
 *
 * @author Guenael Thiriet
 */

public interface Generator {
    /**
     * @return A unique identifier.
     * @throws IndexOutOfBoundsException when the generator has reached its limit.
     */
    public BigInteger getNextId() throws IndexOutOfBoundsException;
}
