package storage;

/**
 * The <code>Storage</code> is an interface to store and retrieve key, value pairs.
 *
 * @author Guenael Thiriet
 */

public interface Storage {
    /**
     * Persists a pair of key,value in the underlying container.
     *
     * @param key   A key to persist in the container.
     * @param value A value to persist in the container
     * @throws InvalidStateException    when n invalid state is detected.
     * @throws InvalidArgumentException when argument provided are invalid.
     */
    public void store(String key, String value) throws InvalidStateException, InvalidArgumentException;

    /**
     * Tells if the key exists in the container.
     *
     * @param key A key to persist in the container.
     * @return true is the key was found
     */
    public boolean hasKey(String key);

    /**
     * Tells if the value exists in the container.
     *
     * @param value The value to test for.
     * @return true is the value was found
     */
    public boolean hasValue(String value);

    /**
     * Get the key from the container.
     *
     * @param value A value for which the key needs to be retrieved.
     * @return The key is returned as a string, of null is returned if not found.
     */
    public String getKey(String value);

    /**
     * Get the value from the container.
     *
     * @param key A key for which the value needs to be retrieved.
     * @return The value is returned as a string, of null is returned if not found.
     */
    public String getValue(String key);
}
