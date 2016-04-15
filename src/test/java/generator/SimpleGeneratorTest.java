package generator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigInteger;

/**
 * Created by Guenael Thiriet on 2016-04-12.
 */
public class SimpleGeneratorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getNextIdUpperLimitTest() throws Exception {
        final long upperLimit = 10;
        Generator generator = SimpleGenerator.createGenerator(BigInteger.ZERO, BigInteger.valueOf(upperLimit));

        // Call getNextId 10 times then throw an exception
        for (int i = 0; i < upperLimit + 1; i++) {
            // An exception will be thrown quickly enough
            BigInteger smallValue = generator.getNextId();
            if (smallValue.longValue() == upperLimit) {
                exception.expect(IndexOutOfBoundsException.class);
                exception.expectMessage("Upper limit reached.");
            }
        }
    }

    @Test
    public void constructorTest() throws Exception {
        final long upperLimit = 10;

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Cannot specify origin higher than upperLimit");

        Generator generator = SimpleGenerator.createGenerator(BigInteger.valueOf(upperLimit),
                BigInteger.valueOf(upperLimit));
    }

    @Test
    public void constructorTest2() throws Exception {
        final long upperLimit = 10;

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Origin is negative");

        Generator generator = SimpleGenerator.createGenerator(BigInteger.ONE.negate(),
                BigInteger.valueOf(upperLimit));
    }

    @Test
    public void constructorTest3() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("UpperLimit is negative");

        Generator generator = SimpleGenerator.createGenerator(BigInteger.valueOf(10),
                BigInteger.ONE.negate());
    }

}