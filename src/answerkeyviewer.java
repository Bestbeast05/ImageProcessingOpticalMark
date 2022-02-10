import FileIO.PDFHelper;
import Interfaces.PixelFilter;
import core.DImage;
import processing.core.PImage;

import java.util.ArrayList;

public class answerkeyviewer implements PixelFilter {

    @Override
    

    public DImage processImage(DImage img) {



        return img;
    }

    private static ArrayList<DImage> RunTheFilter(String filepath) {
        System.out.println("Loading pdf....");
        ArrayList<DImage> images = new ArrayList<>();


        ArrayList<PImage> inimage = PDFHelper.getPImagesFromPdf(filepath);
        for (PImage pImage : inimage) {
            images.add(new DImage(pImage));


        }
        return images;
    }
}
