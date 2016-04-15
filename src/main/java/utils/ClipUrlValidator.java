package utils;

import org.apache.commons.validator.routines.UrlValidator;

/**
 * The <code>ClipUrlValidator</code> is a basic http URL validator.
 *
 * @author Guenael Thiriet
 */

public class ClipUrlValidator {

    /**
     * Default constructor
     */
    public ClipUrlValidator() {
    }

    /**
     * Validation method for http and https URLs.
     *
     * @param url The url to validate.
     * @return true if the validation succeeds
     */
    public boolean validate(String url) {
        if (url == null) {
            return false;
        }

        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }

}
