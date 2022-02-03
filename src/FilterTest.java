import FileIO.PDFHelper;
import Filters.DisplayInfoFilter;
import Interfaces.PixelFilter;
import core.DImage;
import core.DisplayWindow;
import processing.core.PImage;

import java.util.ArrayList;

public class FilterTest {
    public static String currentFolder = System.getProperty("user.dir") + "/";

    public static void main(String[] args) {
        System.out.println("rohan beats children");

        // SaveAndDisplayExample();

        RunTheFilter();
    }

    private static void RunTheFilter() {
        System.out.println("Loading pdf....");
        ArrayList<DImage> images =new ArrayList<DImage>();


       ArrayList<PImage> inimage  = PDFHelper.getPImagesFromPdf("assets/omrtest.pdf");
        for (int i = 0; i < inimage.size(); i++) {
            images.add(new DImage(inimage.get(i)));
            DisplayInfoFilter filter = new DisplayInfoFilter();

            filter.processImage(images.get(i));  // if you want, you can make a different method


        }
                                   // that does the image processing an returns a DTO with
                                   // the information you want
    }


    private static void SaveAndDisplayExample() {
        PImage img = PDFHelper.getPageImage("assets/omrtest.pdf",1);
        img.save(currentFolder + "assets/page1.png");

        DisplayWindow.showFor("assets/page1.png");
    }
}