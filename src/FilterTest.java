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

        RunTheFilter("assets/omrtest.pdf");
    }

    private static ArrayList<DImage> RunTheFilter(String filepath) {
        System.out.println("Loading pdf....");
        ArrayList<DImage> images =new ArrayList<DImage>();


       ArrayList<PImage> inimage  = PDFHelper.getPImagesFromPdf(filepath);
        for (int i = 0; i < inimage.size(); i++) {
            images.add(new DImage(inimage.get(i)));


        }
        return images;
                                   // that does the image processing an returns a DTO with
                                   // the information you want
    }

    public int blackcpixelsinregion (short [][] inputarray , int thresholdbwtnquestions, int thresholdbwtwnsolutions, int inputrow, int inputcol, int numOptions,int distanceToFirstQuestion) {
        int blackpixels = 0;
        int blackcounter = 0;
        int storedquadrant =0;
        int storedwor = 0;
                for (int k = inputrow; k < inputrow + thresholdbwtnquestions; k++) {


                    for (int x = inputcol; x < inputcol + thresholdbwtwnsolutions; x++) {
                        if (inputarray[k][x] < 150) {
                            blackcounter++;
                            storedwor=x;
                        }



                        }
                    if(blackcounter>blackpixels) {
                        blackpixels = blackcounter;

                        storedquadrant = (inputarray[0].length - distanceToFirstQuestion) / numOptions*storedwor;
                    }


                    }
                return storedquadrant;
            }


    public ArrayList<Integer> readQuestions(String filepath , int thresholdbwtnquestions, int thresholdbwtwnsolutions, int distanceToFirstQuestion, int numOptions){
        DImage getquestions=RunTheFilter("assets/omrtest.pdf").get(0);
        ArrayList<Integer> answerkey= new ArrayList<>();
        short[][]inputarray=getquestions.getBWPixelGrid();
        int storedquadrant=0;
        int blackpixels=0;
        for(int i=0;i<inputarray.length;i+=thresholdbwtnquestions) {


            for (int j = 0; j < inputarray[i].length - (j + thresholdbwtwnsolutions); j++) {
               int answer = blackcpixelsinregion(inputarray,thresholdbwtnquestions,thresholdbwtwnsolutions,i,j,numOptions,distanceToFirstQuestion);
               answerkey.add(answer);


            }
        }
        return answerkey;













    }


    private static void SaveAndDisplayExample() {
        PImage img = PDFHelper.getPageImage("assets/omrtest.pdf",1);
        img.save(currentFolder + "assets/page1.png");

        DisplayWindow.showFor("assets/page1.png");
    }
}