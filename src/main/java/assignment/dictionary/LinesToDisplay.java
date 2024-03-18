package assignment.dictionary;

import java.util.Iterator;


/**
 * A class that will be used to display the lines of text that are corrected.
 */

public class LinesToDisplay {

    public static final int LINES = 20;     // Display 20 lines
    private AList<Wordlet>[] lines;
    private int currentLine;

    /**
     * Constructor for objects of class LinesToDisplay
     */
    public LinesToDisplay() {
        lines = new AList[LINES];
        for (int i = 0; i < LINES; i++) {
            lines[i] = new AList<Wordlet>();
        }
        currentLine = 0;
    }

    /**
     * Add a new wordlet to the current line.
     */
    public void addWordlet(Wordlet w) {
        lines[currentLine].add(w);
    }

    /**
     * Go to the next line, if the number of lines has exceeded LINES, shift
     * them all up by one
     */
    public void nextLine() {
        // If the current line is the last line in the array, shift all lines up
        if (currentLine == LINES - 1) {
            for (int i = 0; i < LINES - 1; i++) {
                lines[i] = lines[i + 1];
            }
            lines[LINES - 1] = new AList<>();
        } else {
            // Otherwise, just increment the current line
            currentLine++;
        }
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public AList<Wordlet>[] getLines() {
        return lines;
    }
}
