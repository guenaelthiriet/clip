package clip;

import generator.Generator;
import generator.SimpleGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import storage.Storage;
import storage.persistent.PersistentStorage;
import storage.persistent.ShortUrlRepository;
import utils.BaseConverter;
import utils.IConverter;

import java.math.BigInteger;

/**
 * The <code>Clip</code> is the Clip program entry point.
 *
 * @author Guenael Thiriet
 */

@SpringBootApplication
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
        SpringApplication.run(Clip.class, args);
    }


    @Bean
    public CommandLineRunner clip(ShortUrlRepository repository) {
        return (args) -> {
            // Let's configure clip!
            IConverter converter = BaseConverter.createBaseConverter(ALPHABET);
            String lastCharOfAlphabet = ALPHABET.substring(ALPHABET.length() - 1);
            String biggestEncodedNumber = StringUtils.repeat(lastCharOfAlphabet, MAX_CHARS_IN_SHORT_URL);

            BigInteger upperLimit = converter.decode(biggestEncodedNumber);
            Generator idGenerator = SimpleGenerator.createGenerator(BigInteger.ZERO, upperLimit);

            // Get the storage
            // TODO : Parse command line and decide which one to create from there
//            Storage storage = new MemoryStorage();
            Storage storage = new PersistentStorage(repository);

            // Clip can be configured
            ClipConfiguration configuration = new ClipConfiguration(converter, idGenerator, storage);

            // Start the console
            ClipConsole console = new ClipConsole(configuration);
            console.startInteractiveConsole();
        };
    }
}
