package com.questandglory.language.script;

import lombok.Getter;
import org.springframework.util.Assert;

/**
 * Contains a script, accessible as lines.
 *
 * @author Guy Raz Nir
 * @since 2025/08/26
 */
public class Script {

    /**
     * Holds the full script.
     */
    @Getter
    private final String script;

    /**
     * Holds all the script's lines.
     */
    private final String[] scriptLines;

    /**
     * Class constructor.
     *
     * @param scriptLines Script lines.
     */
    private Script(String script, String[] scriptLines) {
        this.script = script;
        this.scriptLines = scriptLines;
    }

    /**
     * Parse a given text as a script.
     *
     * @param script Text to treat as the script.
     * @return A new {@code Script} object.
     * @throws IllegalArgumentException If <i>script</i> is {@code null}.
     */
    public static Script from(String script) throws IllegalArgumentException {
        Assert.notNull(script, "Script cannot be null.");
        String[] lines = script.split("\\r?\\n|\\r");
        return new Script(script, lines);
    }

    /**
     * Fetch a line from the script.
     *
     * @param lineNumber Line number, starting from 1 to {@link #linesCount()}.
     * @return Line specified by <i>lineNumber</i>.
     * @throws IllegalArgumentException If line number is out of range.
     */
    public String getLine(int lineNumber) throws IllegalArgumentException {
        if (lineNumber < 1 || lineNumber > scriptLines.length) {
            throw new IllegalArgumentException("Line number of out of range: "
                    + lineNumber
                    + " (range: 1.."
                    + scriptLines.length + ").");
        }
        return scriptLines[lineNumber - 1];
    }

    /**
     * @return The number of lines in the script.
     */
    public int linesCount() {
        return scriptLines.length;
    }


}
