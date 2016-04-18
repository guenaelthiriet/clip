package clip;

import generator.Generator;
import generator.SimpleGenerator;
import org.apache.commons.lang.StringUtils;
import storage.MemoryStorage;
import storage.Storage;
import utils.BaseConverter;
import utils.IConverter;

/**
 * The <code>Clip</code> is the Clip program entry point.
 *
 * @author Guenael Thiriet
 */

public class Clip {

    /**
     * The alphabet used to convert to short URL
     */
    public static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * The maximum number of alpha-num chars allowed in a short URL
     */
    public static final int MAX_CHARS_IN_SHORT_URL = 10;

    /**
     * Clip domain name
     */
    public static final String SHORT_URL_DOMAIN = "cl.ip";

    /**
     * @param args Arguments for the Clip program. Not used for now.
     */
    public static void main(String[] args) {
        // Let's configure clip!
        IConverter converter = BaseConverter.createBaseConverter(ALPHABET);
        String lastCharOfAlphabet = ALPHABET.substring(ALPHABET.length() - 1);
        String biggestEncodedNumber = StringUtils.repeat(lastCharOfAlphabet, MAX_CHARS_IN_SHORT_URL);

        Long upperLimit = converter.decode(biggestEncodedNumber);
        Generator idGenerator = SimpleGenerator.createGenerator(0L, upperLimit);

        // Get the storage
        Storage storage = new MemoryStorage();

        // Clip can be configured
        ClipConfiguration configuration = new ClipConfiguration(converter, idGenerator, storage);

        // Start the console
        ClipConsole console = new ClipConsole(configuration);
        console.startInteractiveConsole();
    }
}
