package assignment.dictionary;

import java.io.*;
import java.util.*;
import javafx.application.Platform;

/**
 * A Thread that contains the application we are going to animate
 *
 */

public class MisSpellActionThread implements Runnable {

    DictionaryController controller;
    private final String textFileName;
    private final String dictionaryFileName;

    private LinesToDisplay myLines;
    private DictionaryInterface<String, String> myDictionary;
    private boolean dictionaryLoaded;

    /**
     * Constructor for objects of class MisspellActionThread
     *
     * @param controller
     */
    public MisSpellActionThread(DictionaryController controller) {
        super();

        this.controller = controller;
        textFileName = "src/main/resources/assignment/dictionary/check.txt";
        dictionaryFileName = "src/main/resources/assignment/dictionary/sampleDictionary.txt";

        myDictionary = new HashedMapAdaptor<String, String>();
        myLines = new LinesToDisplay();
        dictionaryLoaded = false;

    }

    @Override
    public void run() {

        dictionaryLoaded = loadDictionary(dictionaryFileName, myDictionary);

        System.out.printf("TESTING AFTER LOADING\nTried to find 'right' was in there? %b\n\n", myDictionary.contains("Right"));


        Platform.runLater(() -> {
            if (dictionaryLoaded) {
               controller.SetMsg("The Dictionary has been loaded"); 
            } else {
               controller.SetMsg("No Dictionary is loaded"); 
            }
        });
        
        checkWords(textFileName, myDictionary);

    }

    /**
     * Load the words into the dictionary.
     *
     * @param theFileName The name of the file holding the words to put in the
     * dictionary.
     * @param theDictionary The dictionary to load.
     */
    public boolean loadDictionary(String theFileName, DictionaryInterface<String, String> theDictionary) {
// ADD CODE HERE
// >>>>>>>>>>> ADDED CODE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        Scanner input;
        try (BufferedReader br = new BufferedReader(new FileReader(theFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                theDictionary.add(line, line);
            }

            return true;
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: " + theFileName);
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Get the words to check, check them, then put Wordlets into myLines. When
     * a single line has been read do an animation step to wait for the user.
     *
     */
    public void checkWords(String theFileName, DictionaryInterface<String, String> theDictionary) {
        Scanner input;
//        try {
// ADD CODE HERE
// >>>>>>>>>>> ADDED CODE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            try (BufferedReader br = new BufferedReader(new FileReader(theFileName))) {
                String line;
                String checkPunctuation = "\"'?.!,;()";
                while ((line = br.readLine()) != null) {
                    String subsection = "";
                    for (char c : line.toCharArray()) {
                        if (c == ' ') {
                            subsection += c;
                            Wordlet newWordlet = new Wordlet(subsection, checkWord(subsection.trim(), theDictionary));
                            myLines.addWordlet(newWordlet);
                            subsection = "";
                        } else if (checkPunctuation.indexOf(c) != -1) {
                            if (!subsection.isEmpty()) {
                                Wordlet newWordlet = new Wordlet(subsection, checkWord(subsection, theDictionary));
                                myLines.addWordlet(newWordlet);
                            }
                            Wordlet newWordlet = new Wordlet("" + c, true);
                            myLines.addWordlet(newWordlet);
                            subsection = "";
                        } else {
                            subsection += c;
                        }
                    }

//                System.out.printf("\n\n");
                    myLines.nextLine();
                    showLines(myLines);
//                    Thread.sleep(500);
                }
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            } catch (IOException e) {
                System.out.println("There was an error in reading or opening the file: " + theFileName);
                System.out.println(e.getMessage());
            }
//            } catch (InterruptedException e) {
//                System.out.println("There was an error in threading sleep");
//                System.out.println(e.getMessage());
//            }

    }

    /**
     * Check the spelling of a single word.
     *
     */
    public boolean checkWord(String word, DictionaryInterface<String, String> theDictionary) {
        return theDictionary.contains(word);
    }

    private void showLines(LinesToDisplay lines) {
        try {
            Thread.sleep(500);
            Platform.runLater(() -> {
                if (myLines != null) {
                    controller.UpdateView(lines);
                }
            });
        } catch (InterruptedException ex) {
        }
    }

} // end class MisspellActionThread

