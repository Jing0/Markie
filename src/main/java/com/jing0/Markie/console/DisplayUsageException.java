package com.jing0.Markie.console;

/**
 * Thrown if the program arguments were invalid.
 *
 * @author Jing0
 * @since 1.0
 */
public final class DisplayUsageException extends Exception {
    private static final long serialVersionUID = -7009963711233684636L;

    /**
     * Displays the program usage.
     */
    @Override
    public String getMessage() {
        return "usage: java -jar Markie.jar [markdownFile] [- header headerFile.html] [-footer footerFile.html] [-out [file.html]]";
    }
}