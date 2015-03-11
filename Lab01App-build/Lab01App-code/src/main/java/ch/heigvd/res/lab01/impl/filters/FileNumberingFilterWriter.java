package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  
  // Store the line number and previous char
  private int lineNumber = 0;
  private int prevC = -1;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    // Call the write(int c) method for each char
    for (int i = off; i < off + len; ++i) {
        write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    // Call the write(int c) method for each char
    for (int i = off; i < off + len; ++i) {
        write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
      
    // Check if it's the first inserted char
    if (lineNumber == 0) {
        // If so, write the first line number
        insertLineNumber();
        super.write('\t');
    }
    
    // Check if new line char is inserted
    if (c == '\n' && prevC == '\r') {   // Windows
    
        super.write('\r');  // Write the buffered \r
        super.write(c);     // Write the \n
        insertLineNumber(); // Write the line number
        super.write('\t');  // Write the tab
        
    } else if (c == '\n' && prevC != '\r') { // Linux
    
        super.write(c);     // Write the \n
        insertLineNumber(); // Write the line number
        super.write('\t');  // Write the tab
        
    } else if (c != '\n' && prevC == '\r') { // MacOS9
    
        super.write('\r');  // Write the buffered \r
        insertLineNumber(); // Write the line number
        super.write('\t');  // Write the tab
        super.write(c);     // Write the character
        
    } else if (c == '\r') { // Maybe MacOS9, maybe Windows
        // Wait for next char to decide
        
    } else { // Not a line break
        super.write(c);     // Write the character as usual
    }
      
      // Store char as previous char
      prevC = c;
  }
  
  private void insertLineNumber() throws IOException {
    // Send line number to decorated writer
    lineNumber++;
    for (char chr : String.valueOf(lineNumber).toCharArray()) {
        super.write((int)chr);
    }
  }

}
