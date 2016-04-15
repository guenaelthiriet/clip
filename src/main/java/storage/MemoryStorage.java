package storage;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

/**
 * The <code>MemoryStorage</code> implements the <code>{@link Storage}</code> interface.
 * This is a memory only, non-persistent implementation.
 *
 * @author Guenael Thiriet
 */

public class MemoryStorage implements Storage {

    /**
     * The storage container
     */
    private BidiMap container;

    /**
     * Default constructor instantiates the container
     */
    public MemoryStorage() {
        container = new DualHashBidiMap();
    }

    /**
     * Persists a pair of key,value in the container.
     *
     * @param key   A key to persist in the container.
     * @param value A value to persist in the container
     * @throws InvalidStateException    when n invalid state is detected.
     * @throws InvalidArgumentException when argument provided are invalid.
     */
    @Override
    public void store(String key, String value) throws InvalidStateException, InvalidArgumentException {

        if ((key == null) || (value == null)) {
            throw new InvalidArgumentException("key or value are null");
        }
        // DualHashBidiMap will overwrite the content if entries exist.
        if (container.containsKey(key)) {
            throw new InvalidStateException("Identifier already in container");
        }
        if (container.containsValue(value)) {
            throw new InvalidStateException("URL already in container");
        }
        container.put(key, value);
    }

    /**
     * Tells if the value exists in the container.
     *
     * @param value The value to test for.
     * @return true is the value was found
     */
    @Override
    public boolean hasValue(String value) {
        return container.containsValue(value);
    }

    /**
     * Get the value from the container.
     *
     * @param key A key for which the value needs to be retrieved.
     * @return The value is returned as a string, of null is returned if not found.
     */
    @Override
    public String getValue(String key) {
        return (String) container.get(key);
    }

    /**
     * Tells if the key exists in the container.
     *
     * @param key A key to persist in the container.
     * @return true is the key was found
     */
    @Override
    public boolean hasKey(String key) {
        return container.containsKey(key);
    }

    /**
     * Get the key from the container.
     *
     * @param value A value for which the key needs to be retrieved.
     * @return The key is returned as a string, of null is returned if not found.
     */
    @Override
    public String getKey(String value) {
        return (String) container.getKey(value);
    }
}
