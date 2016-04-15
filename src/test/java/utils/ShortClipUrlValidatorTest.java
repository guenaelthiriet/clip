package utils;

import clip.Clip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Created by Guenael Thiriet on 2016-04-13.
 */

@RunWith(Parameterized.class)
public class ShortClipUrlValidatorTest {

    private String url;
    private boolean validationResult;

    public ShortClipUrlValidatorTest(String url, boolean validationResult) {
        this.url = url;
        this.validationResult = validationResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {String.format("http://%s", Clip.SHORT_URL_DOMAIN), false},
                {String.format("http://%s/", Clip.SHORT_URL_DOMAIN), false},
                {String.format("http://%s/10123456789", Clip.SHORT_URL_DOMAIN), false},
                {"http://ip.cl/1", false},
                {String.format("http://%s/%s/aaaddd", Clip.SHORT_URL_DOMAIN, Clip.SHORT_URL_DOMAIN), false},
                {String.format("http://%s/aaaddd", Clip.SHORT_URL_DOMAIN), true},
                {String.format("http://%s/1", Clip.SHORT_URL_DOMAIN), true},
                {String.format("http://%s/0123456789", Clip.SHORT_URL_DOMAIN), true},
        });
    }

    @Test
    public void testShortUrl() {
        ShortClipUrlValidator validator = new ShortClipUrlValidator();
        if (validator.validate(url) != validationResult)
            System.out.println(url);
        assertTrue(validator.validate(url) == validationResult);
    }
}