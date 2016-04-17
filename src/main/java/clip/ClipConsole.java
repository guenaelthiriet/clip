package clip;

import java.io.Console;

/**
 * The <code>ClipConsole</code> is the interactive console used by Clip.
 *
 * @author Guenael Thiriet
 */

final class ClipConsole {

    /**
     * The new line separator
     */
    private static final String NEW_LINE = System.getProperty("line.separator");

    /**
     * This flag indicates if the console loop should exit
     */
    private boolean exit;

    /**
     * The <code>ClipConfiguration</code> used by Clip
     */
    private final ClipConfiguration configuration;

    /**
     * @param configuration A configuration object instance used to convert URLs.
     */
    ClipConsole(ClipConfiguration configuration) {
        exit = false;
        this.configuration = configuration;
    }

    /**
     * Parse a string and look for an "encode" string
     *
     * @param cmd A string to parse.
     * @return true if the string contains "encode"
     */
    private boolean shouldEncode(String cmd) {
        return cmd.toLowerCase().contains("encode");
    }

    /**
     * Parse a string and look for a "decode" string
     *
     * @param cmd A string to parse.
     * @return true if the string contains "decode"
     */
    private boolean shouldDecode(String cmd) {
        return cmd.toLowerCase().contains("decode");
    }

    /**
     * Parse a string and look for a "y" string
     *
     * @param cmd A string to parse.
     * @return true if the string contains "y"
     */
    private boolean shouldExit(String cmd) {
        return cmd.toLowerCase().contains("y");
    }

    /**
     * Prints a URL.
     *
     * @param url the URL to print.
     */
    private void printUrl(String url) {
        System.out.println("The URL is: " + url);
    }

    /**
     * Prints an error message.
     *
     * @param msg The error message to print.
     */
    private void printErrorMsg(String msg) {
        System.out.println(String.format("I did not understand the command (%s)", msg));
    }

    /**
     * The method starts the interactive console.
     */
    void startInteractiveConsole() {

        Console console = System.console();
        if (console == null) {
            printErrorMsg("Seems like the terminal you are using is not supported by the JVM");
            System.exit(0);
        }

        while (!exit) {
            // Prepare the list of parameters
            ClipArgumentsBuilder builder = new ClipArgumentsBuilder();

            // URL
            String url = console.readLine("URL? ");
            builder.url(url);

            // What should I do with it
            String encodeDecode = console.readLine("Encode/Decode? ");
            if (shouldEncode(encodeDecode)) {
                builder.algorithm(ClipArguments.Algorithm.ENCODE);
            } else if (shouldDecode(encodeDecode)) {
                builder.algorithm(ClipArguments.Algorithm.DECODE);
            } else {
                // Something is very wrong, print some error message
                printErrorMsg("Encode/Decode");
                // Go back to typing the URL...
                continue;
            }

            // Great we have some arguments!
            ClipArguments arguments = builder.createClipArgument();
            ClipProcessor processor = new ClipProcessor(arguments, configuration);

            // Let's validate them first
            if (!processor.validate()) {
                // Ouch, something went wrong during the validation
                printErrorMsg("Bad arguments");
                // Go back to typing the URL...
                continue;
            }

            // Alright, let's make it fly!
            try {
                String result = processor.processUrl();
                printUrl(result);
            } catch (ClipProcessingException e) {
                // The URL does not exists
                printErrorMsg("I don't have this URL in my database");
                // Should the user see this?
                e.printStackTrace();
            }

            // Anything else?
            String exitCmd = console.readLine("Exit(y/n)? ");
            exit = shouldExit(exitCmd);

            console.printf(NEW_LINE);
        }

        // We are done
        console.printf("Bye.");
    }
}
