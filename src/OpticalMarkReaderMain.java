import FileIO.PDFHelper;
import core.DImage;
import processing.core.PImage;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class OpticalMarkReaderMain {
    public static void main(String[] args) throws IOException {
        String pathToPdf = fileChooser("omrtest.pdf");
        System.out.println("Loading pdf at " + pathToPdf);

        ArrayList<PImage> pageImages = PDFHelper.getPImagesFromPdf("assets/omrtest.pdf");


        DImage answerkeyImage = new DImage(pageImages.get(0));
        



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
