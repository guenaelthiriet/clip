package storage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Guenael Thiriet on 2016-04-14.
 */
public class MemoryStorageTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private MemoryStorage storage;

    @Before
    public void setUp() throws Exception {
        storage = new MemoryStorage();
    }

    @Test
    public void testMemoryStorage() throws Exception {
        String key = "someKey";
        String value = "someValue";

        // First time it's ok
        storage.store(key, value);
        // Value can be retrieved
        assertTrue(storage.getKey(value).equals(key));
        assertTrue(storage.getValue(key).equals(value));
        // And the existence API works...
        assertTrue(storage.hasKey(key));
        assertTrue(storage.hasValue(value));
        assertTrue(!storage.hasValue(key));
        assertTrue(!storage.hasKey(value));

        // Next time throw an exception
        exception.expect(InvalidStateException.class);
        storage.store(key, value);

        // Map state has not change
        assertTrue(storage.hasKey(key));
        assertTrue(storage.hasValue(value));

        // Try to override something in the map
        exception.expect(InvalidStateException.class);
        storage.store(key, value + value);

        // Try to override something in the map
        exception.expect(InvalidStateException.class);
        storage.store(key + key, value);

        // Map state has not change
        assertTrue(storage.hasKey(key));
        assertTrue(storage.hasValue(value));
    }
}