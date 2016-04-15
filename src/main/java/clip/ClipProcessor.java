package clip;

import generator.Generator;
import storage.InvalidArgumentException;
import storage.InvalidStateException;
import storage.Storage;
import utils.ClipUrlValidator;
import utils.IConverter;
import utils.ShortClipUrlValidator;

import java.math.BigInteger;

/**
 * The <code>ClipProcessor</code> contains the main Clip logic.
 * It can:
 * - Generate a short URL from a long URL.
 * - Retrieve a long URL from a short URL.
 * - Make URL validation.
 *
 * @author Guenael Thiriet
 */

public class ClipProcessor {

    /**
     * Let the processor what it has to do.
     */
    private ClipArguments arguments;

    /**
     * The configuration which the processor can use to convert the URLs.
     */
    private ClipConfiguration configuration;

    /**
     * @param arguments     Some arguments to use for processing.
     * @param configuration The Clip configuration.
     */
    public ClipProcessor(ClipArguments arguments, ClipConfiguration configuration) {
        this.arguments = arguments;
        this.configuration = configuration;
    }

    /**
     * Formats a short URL.
     *
     * @param value A unique indentifier.
     * @return A short URL.
     */
    private String getShortUrl(String value) {
        return String.format("http://%s/%s", Clip.SHORT_URL_DOMAIN, value);
    }

    /**
     * Validate the instance arguments are correct.
     *
     * @return true if the URL validation passed.
     */
    public boolean validate() {
        ClipUrlValidator urlValidator = null;
        if (arguments.isEncodeSelected()) {
            // We will need a long url validator
            urlValidator = new ClipUrlValidator();
        }
        if (arguments.isDecodeSelected()) {
            // We will need a long url validator
            urlValidator = new ShortClipUrlValidator();
        }

        return urlValidator.validate(arguments.getUrl());
    }

    /**
     * The function will convert a short URL to a
     * long url and a long URL to a short URL.
     *
     * @return a URL, based on the current object state.
     * @throws ClipProcessingException when it was unable to process the URL
     */
    public String processUrl() throws ClipProcessingException {
        if (arguments.isEncodeSelected()) {
            try {
                return toShortUrl(arguments.getUrl());
            } catch (InvalidStateException | InvalidArgumentException e) {
                throw new ClipProcessingException("Unable to process short URL", e);
            }
        } else if (arguments.isDecodeSelected()) {
            return toLongUrl(arguments.getUrl());
        }

        throw new ClipProcessingException("Unknown encoding requested");
    }

    /**
     * The function will convert a short URL to a long URL.
     *
     * @return a long URL, based on arguments provided.
     */
    private String toLongUrl(String url) throws ClipProcessingException {
        Storage storage = configuration.getStorage();
        IConverter converter = configuration.getConverter();
        Generator idGenerator = configuration.getIdGenerator();

        // FIXME this does not seem super safe
        String encodedValue = url.substring(url.lastIndexOf('/') + 1);
        BigInteger id = converter.decode(encodedValue);
        if (!storage.hasKey(id.toString())) {
            throw new ClipProcessingException("Trying to decode a string not generated");
        }

        return storage.getValue(id.toString());
    }

    /**
     * The function will convert a long URL to a short URL.
     *
     * @return a short URL, based on arguments provided.
     * @throws InvalidStateException
     * @throws InvalidArgumentException
     */
    private String toShortUrl(String url) throws InvalidStateException, InvalidArgumentException {
        Storage storage = configuration.getStorage();
        IConverter converter = configuration.getConverter();
        Generator idGenerator = configuration.getIdGenerator();

        String identifier;
        if (storage.hasValue(url)) {
            identifier = storage.getKey(url);
        } else {
            // Need to generate it
            identifier = idGenerator.getNextId().toString();
            // And store it
            storage.store(identifier, url);
        }
        // Finally encode it and return it
        String encodedId = converter.encode(new BigInteger(identifier));
        return getShortUrl(encodedId);
    }

}
