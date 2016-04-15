package clip;

/**
 * The <code>ClipArguments</code> is used to store arguments
 * for the Clip program.
 *
 * @author Guenael Thiriet
 */

public class ClipArguments {
    /**
     * A URL
     */
    private String url;

    /**
     * The algorith used
     */
    private Algorithm algorithm;

    /**
     * @param algorithm Algorithm to use.
     * @param url       A URL.
     */
    public ClipArguments(final Algorithm algorithm, final String url) {
        this.url = url;
        this.algorithm = algorithm;
    }

    /**
     * @return true if the class was configure to use the following alogorithm : {@link Algorithm#ENCODE}.
     */
    public boolean isEncodeSelected() {
        return algorithm == Algorithm.ENCODE;
    }

    /**
     * @return true if the class was configure to use the following alogorithm : {@link Algorithm#DECODE}.
     */
    public boolean isDecodeSelected() {
        return algorithm == Algorithm.DECODE;
    }

    /**
     * @return The url contained by this object instance.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Algorithms that can by used by Clip
     */
    public enum Algorithm {
        /**
         * Use this to encode
         */
        ENCODE,
        /**
         * Use this to decode
         */
        DECODE
    }
}
