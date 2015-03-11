package ch.heigvd.res.lab01.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    
    // Loop through string
    for (int i = 0; i < lines.length(); ++i) {
        // If reach end of line
        if (lines.charAt(i) == '\n' && (i == 0 || lines.charAt(i - 1) == '\r')) { // Windows
            return new String[] {lines.substring(0, i + 1), lines.substring(i + 1)};
        } else if (lines.charAt(i) == '\n' && (i == 0 || lines.charAt(i - 1) != '\r')) { // Unix
            return new String[] {lines.substring(0, i + 1), lines.substring(i + 1)};
        } else if (lines.charAt(i) == '\r' && (i == lines.length() - 1 || lines.charAt(i + 1) != '\n')) { // MacOS9
            return new String[] {lines.substring(0, i + 1), lines.substring(i + 1)};
        }
    }
    return new String[] {"", lines};
  }

}
