import core.DImage;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OpticalMarkReaderMain {
    public static void main(String[] args) throws IOException {
        String pathToPdf = fileChooser("omrtest.pdf");
        System.out.println("Loading pdf at " + pathToPdf);



        /*
        Your code here to...
        (1).  Load the pdf

        (2).  Loop over its pages
        (3).  Create a DImage from each page and process its pixels

        (4).  Output 2 csv files
         */

    }


    private static String fileChooser(String s) {
        String userDirLocation = System.getProperty("user.dir");
        File userDir = new File(userDirLocation);
        JFileChooser fc = new JFileChooser(userDir);
        int returnVal = fc.showOpenDialog(null);
        File file = fc.getSelectedFile();
        return file.getAbsolutePath();


    }
}
