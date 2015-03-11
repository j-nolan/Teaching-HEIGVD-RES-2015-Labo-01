package ch.heigvd.res.lab01.impl.explorers;

import ch.heigvd.res.lab01.interfaces.IFileExplorer;
import ch.heigvd.res.lab01.interfaces.IFileVisitor;
import java.io.File;
import java.util.ArrayList;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
      // We will store subdirectories in a list
      ArrayList<File> directories = new ArrayList<File>();
      
      // Visit current directory
      vistor.visit(rootDirectory);
      
      // Get files in root directory
      File[] files = rootDirectory.listFiles();
      
      // Loop through root directory files
      if (files != null) {
          for (File file : files) {
              // If looped file is a file (and not a folder)
              if (file.isFile()) {
                  // Visit it
                  vistor.visit(file);
              } else {              
                  // If it's a directory, remember it
                  directories.add(file);
              }
          }
      }
      
      // When all file are visited, do the same task for each sub
      for (File directory : directories) {
          explore(directory, vistor);
      }
  }

}
