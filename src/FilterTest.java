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
        ArrayList<DImage> images = new ArrayList<>();


       ArrayList<PImage> inimage  = PDFHelper.getPImagesFromPdf(filepath);
        for (PImage pImage : inimage) {
            images.add(new DImage(pImage));


        }
        return images;
                                   // that does the image processing an returns a DTO with
                                   // the information you want
    }

    public int blackcpixelsinregion (short [][] inputarray , int thresholdbwtnquestions, int thresholdbwtwnsolutions, int inputrow, int inputcol, int numOptions,int distanceToFirstQuestionhorizontal) {
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

                        storedquadrant = (inputarray[0].length - distanceToFirstQuestionhorizontal) / numOptions*storedwor;
                    }


                    }
                return storedquadrant;
            }


    public ArrayList<Integer> readQuestions(String filepath , int thresholdbwtnquestions, int thresholdbwtwnsolutions, int distanceToFirstQuestion, int numOptions){
        DImage getquestions=RunTheFilter(filepath).get(0);
        ArrayList<Integer> answerkey= new ArrayList<>();
        short[][]inputarray=getquestions.getBWPixelGrid();
        for(int i=0;i<inputarray.length;i+=thresholdbwtnquestions) {


            for (int j = 0; j < inputarray[i].length - (j + thresholdbwtwnsolutions); j++) {
               int answer = blackcpixelsinregion(inputarray,thresholdbwtnquestions,thresholdbwtwnsolutions,i,j,numOptions,distanceToFirstQuestion);
               answerkey.add(answer);


            }
        }
        return answerkey;













    }

}