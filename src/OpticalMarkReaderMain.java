import core.DImage;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OpticalMarkReaderMain {
    public static void main(String[] args) {
        String pathToPdf = fileChooser("omrtest.pdf");
        System.out.println("Loading pdf at " + pathToPdf);
        String fileContents = readFile("omrtest.pdf");


       readFile("C:\Users\adame\IdeaProjects\ImageProcessingOpticalMark\assets\omrtest.pdf");
        /*
        Your code here to...
        (1).  Load the pdf

        (2).  Loop over its pages
        (3).  Create a DImage from each page and process its pixels

        (4).  Output 2 csv files
         */

    }
    public static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void writeToFile(String filePath, String data) {
        try (FileWriter f = new FileWriter(filePath);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b);) {

            writer.print(data);

        } catch (Exception errorObj) {
            System.out.println("There was an error with the file");
            errorObj.printStackTrace();
        }
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
