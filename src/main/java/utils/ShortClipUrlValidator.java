package utils;

import clip.Clip;

/**
 * The <code>ShortClipUrlValidator</code> is a short URL validator.
 *
 * @author Guenael Thiriet
 */

public class ShortClipUrlValidator extends ClipUrlValidator {

    /**
     * Default constructor
     */
    public ShortClipUrlValidator() {
        super();
    }

    /**
     * Validation method for Clip short URLs.
     *
     * @param url The url to validate.
     * @return true if the validation succeeds
     */
    @Override
    public boolean validate(String url) {
        // FIXME super.validate(url) fails always for hort clip URLs
        // FIXME use regex here
        String base = String.format("http://%s/", Clip.SHORT_URL_DOMAIN);
        return url.startsWith(base) &&
                url.length() > base.length() && // at least one character more
                (url.length() <= (Clip.MAX_CHARS_IN_SHORT_URL + base.length()));
    }
}
