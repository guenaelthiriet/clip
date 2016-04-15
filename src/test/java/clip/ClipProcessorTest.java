package clip;

import generator.Generator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import storage.Storage;
import utils.IConverter;

import java.math.BigInteger;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Guenael Thiriet on 2016-04-14.
 */
public class ClipProcessorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Storage storage;
    private IConverter converter;
    private Generator idGenerator;
    private ClipConfiguration config;

    @Before
    public void setUp() throws Exception {
        storage = mock(Storage.class);
        converter = mock(IConverter.class);
        idGenerator = mock(Generator.class);
        config = new ClipConfiguration(converter, idGenerator, storage);
    }

    @Test
    public void retrieveLongUrl() throws Exception {
        String shortUrl = "http://cl.ip/1234";
        String longUrl = "http://www.google.com/";
        String identifier = "1234";

        ClipArguments arguments = new ClipArguments(ClipArguments.Algorithm.DECODE, shortUrl);

        // Configure storage
        when(storage.hasKey(identifier)).thenReturn(true);
        when(storage.getValue(identifier)).thenReturn(longUrl);

        // Configure converter
        when(converter.decode(identifier)).thenReturn(new BigInteger(identifier));

        config = new ClipConfiguration(converter, idGenerator, storage);
        ClipProcessor processor = new ClipProcessor(arguments, config);
        assertTrue(processor.processUrl().equals(longUrl));

        // Verify
        verify(storage).hasKey(identifier);
        verify(storage).getValue(identifier);
        verify(converter).decode(identifier);
        verify(idGenerator, never()).getNextId();
    }

    @Test
    public void inexistentShortUrl() throws Exception {
        String shortUrl = "http://cl.ip/1234";
        String longUrl = "http://www.google.com/";
        String identifier = "1234";

        ClipArguments arguments = new ClipArguments(ClipArguments.Algorithm.DECODE, shortUrl);

        // Configure storage
        when(storage.hasKey(identifier)).thenReturn(false);

        // Configure converter
        when(converter.decode(identifier)).thenReturn(new BigInteger(identifier));

        config = new ClipConfiguration(converter, idGenerator, storage);
        ClipProcessor processor = new ClipProcessor(arguments, config);

        exception.expect(ClipProcessingException.class);
        processor.processUrl();

        // Verify
        verify(storage).hasKey(identifier);
        verify(storage, never()).getValue(anyString());
        verify(converter, never()).decode(anyString());
        verify(idGenerator, never()).getNextId();
    }

    @Test
    public void retrieveShortUrl() throws Exception {
        String shortUrl = "http://cl.ip/1234";
        String longUrl = "http://www.google.com/";
        String identifier = "1234";

        ClipArguments arguments = new ClipArguments(ClipArguments.Algorithm.ENCODE, longUrl);

        // Configure storage
        when(storage.hasValue(longUrl)).thenReturn(true);
        when(storage.getKey(longUrl)).thenReturn(identifier);

        // Configure converter
        when(converter.encode(new BigInteger(identifier))).thenReturn(identifier);

        config = new ClipConfiguration(converter, idGenerator, storage);
        ClipProcessor processor = new ClipProcessor(arguments, config);
        assertTrue(processor.processUrl().equals(shortUrl));

        // Verify
        verify(storage).hasValue(longUrl);
        verify(storage).getKey(longUrl);
        verify(converter).encode(new BigInteger(identifier));
        verify(idGenerator, never()).getNextId();
    }

    @Test
    public void generateShortUrl() throws Exception {
        String shortUrl = "http://cl.ip/1234";
        String longUrl = "http://www.google.com/";
        String identifier = "1234";

        ClipArguments arguments = new ClipArguments(ClipArguments.Algorithm.ENCODE, longUrl);

        // Configure storage
        when(storage.hasValue(longUrl)).thenReturn(false);

        // Configure converter
        when(converter.encode(new BigInteger(identifier))).thenReturn(identifier);

        // Configure generator
        when(idGenerator.getNextId()).thenReturn(new BigInteger(identifier));

        config = new ClipConfiguration(converter, idGenerator, storage);
        ClipProcessor processor = new ClipProcessor(arguments, config);
        assertTrue(processor.processUrl().equals(shortUrl));

        // Verify
        verify(storage).hasValue(longUrl);
        verify(storage).store(identifier, longUrl);
        verify(converter).encode(new BigInteger(identifier));
        verify(idGenerator).getNextId();
    }

    @Test
    public void validateLongUrl() throws Exception {
        String longUrl = "http://www.google.com/";

        ClipArguments arguments = new ClipArguments(ClipArguments.Algorithm.ENCODE, longUrl);
        ClipProcessor processor = new ClipProcessor(arguments, config);

        assertTrue(processor.validate());
    }

    @Test
    public void validateShortUrl() throws Exception {
        String shortUrl = "http://cl.ip/1234";

        ClipArguments arguments = new ClipArguments(ClipArguments.Algorithm.DECODE, shortUrl);
        ClipProcessor processor = new ClipProcessor(arguments, config);

        assertTrue(processor.validate());
    }

}