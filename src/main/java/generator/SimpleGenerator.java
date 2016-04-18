package generator;

/**
 * The <code>SimpleGenerator</code> is class that implements <code>SimpleGenerator</code>.
 * The generator is just an incremental generator. Increments are of 1.
 * When the upper limit is reached, the generator cannot generate any more value.
 *
 * @author Guenael Thiriet
 */

public final class SimpleGenerator implements Generator {

    /**
     * The current identifier
     */
    private Long currentId = null;

    /**
     * The upper limit of the generator
     */
    private final Long upperLimit;

    /**
     * @param origin     The starting point of the generator.
     * @param upperLimit The upper limit boundary, after which this generator will stop generating values.
     */
    private SimpleGenerator(Long origin, Long upperLimit) {
        this.currentId = origin;
        this.upperLimit = upperLimit;
    }

    /**
     * Factory method to create a Generator
     *
     * @param origin     The starting point of the generator.
     * @param upperLimit The upper limit boundary, after which this generator will stop generating values.
     * @return A <code>SimpleGenerator</code> object instance.
     */
    public static Generator createGenerator(Long origin, Long upperLimit) {
        validateParameters(origin, upperLimit);
        return new SimpleGenerator(origin, upperLimit);
    }

    /**
     * Method to validate parameters. It should be used before creating a new Generator.
     *
     * @param origin     The starting point of the generator.
     * @param upperLimit The upper limit boundary, after which this generator will stop generating values.
     * @throws IllegalArgumentException
     */
    private static void validateParameters(Long origin, Long upperLimit) throws IllegalArgumentException {
        if (origin < 0) {
            throw new IllegalArgumentException("Origin is negative");
        }
        if (upperLimit < 0) {
            throw new IllegalArgumentException("UpperLimit is negative");
        }
        if (origin >= upperLimit) {
            throw new IllegalArgumentException("Cannot specify origin higher than upperLimit");
        }
    }

    /**
     * Generate the next identifier. After this method is executed, the generated identifier becomes current.
     *
     * @return Increments the identifier by one and return it.
     * @throws IndexOutOfBoundsException when the generator has reached its upper limit.
     */
    @Override
    public Long getNextId() throws IndexOutOfBoundsException {
        if (currentId.equals(upperLimit)) {
            throw new IndexOutOfBoundsException("Upper limit reached.");
        }
        return currentId += 1;
    }
}
