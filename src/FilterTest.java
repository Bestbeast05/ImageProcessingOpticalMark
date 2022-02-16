import FileIO.PDFHelper;
import Filters.DisplayInfoFilter;
import Interfaces.PixelFilter;
import core.DImage;
import core.DisplayWindow;
import org.w3c.dom.ls.LSOutput;
import processing.core.PImage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static jogamp.common.os.elf.SectionArmAttributes.Tag.File;

public class FilterTest {
    public static String currentFolder = System.getProperty("user.dir") + "/";

    public static void main(String[] args) throws IOException {
       // System.out.println( itemanalysis(readanswers(6,513-476,210-172,172-(210-172),5,172),513-476,210-172,172-(210-172),5,172));

       SaveAndDisplay();
        //writeDataToFile("omrtest.pdf", );
       // String data = readFile("omrtest.pdf");
       // System.out.println("File contains: " + data);
            }

    private static ArrayList<DImage> RunTheFilter(String filepath) {
        System.out.println("Loading pdf....");
        ArrayList<DImage> images = new ArrayList<>();


        ArrayList<PImage> inimage = PDFHelper.getPImagesFromPdf(filepath);
        for (PImage pImage : inimage) {
            images.add(new DImage(pImage));


        }
        return images;


        // that does the image processing an returns a DTO with
        // the information you want
    }

    private static void SaveAndDisplay(){
            PImage image = PDFHelper.getPageImage("assets/omrtest.pdf",1);
            image.save(currentFolder+"asset/page1.png");
            DisplayWindow.showFor("asset/page1.png");


    }

    public static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void writeDataToFile(String filePath, String data) {
        try (FileWriter f = new FileWriter(filePath);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b);) {


            writer.println(data);


        } catch (IOException error) {
            System.err.println("There was a problem writing to the file: " + filePath);
            error.printStackTrace();
        }
    }




}
