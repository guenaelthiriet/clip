package utils;

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
public class LongClipUrlValidatorTest {

    private String url;
    private boolean validationResult;

    public LongClipUrlValidatorTest(String url, boolean validationResult) {
        this.url = url;
        this.validationResult = validationResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"http://www.google.com/abcd", true},
                {"htttp://www.google.com/abcd", false},
                {"http:///www.google.com/abcd", false},
        });
    }

    @Test
    public void testLongUrl() {
        ClipUrlValidator validator = new ClipUrlValidator();
        assertTrue(validator.validate(url) == validationResult);
    }
}