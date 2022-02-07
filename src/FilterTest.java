import FileIO.PDFHelper;
import Filters.DisplayInfoFilter;
import Interfaces.PixelFilter;
import core.DImage;
import core.DisplayWindow;
import processing.core.PImage;

import java.lang.reflect.Array;
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


        ArrayList<PImage> inimage = PDFHelper.getPImagesFromPdf(filepath);
        for (PImage pImage : inimage) {
            images.add(new DImage(pImage));


        }
        return images;
        // that does the image processing an returns a DTO with
        // the information you want
    }

    public static int blackcpixelsinregion(short[][] inputarray, int thresholdbwtnquestions, int thresholdbwtwnsolutions, int inputrow, int inputcol, int numOptions, int distanceToFirstQuestionhorizontal) {
        int blackpixels = 0;
        int blackcounter = 0;
        int storedquadrant = 0;
        int storedwor = 0;
        for (int k = inputrow; k < inputrow + thresholdbwtnquestions; k++) {


            for (int x = inputcol; x < inputcol + thresholdbwtwnsolutions; x++) {
                if (inputarray[k][x] < 150) {
                    blackcounter++;
                    storedwor = x;
                }


            }
            if (blackcounter > blackpixels) {
                blackpixels = blackcounter;

                storedquadrant = (inputarray[0].length - distanceToFirstQuestionhorizontal) / numOptions * storedwor;
            }


        }
        return storedquadrant;
    }

    public static DImage getimage(String filepath, int index) {
        DImage getquestions = RunTheFilter(filepath).get(index);
        return getquestions;

    }


    public static ArrayList<Integer> readQuestions(DImage getquestions, int thresholdbwtnquestions, int thresholdbwtwnsolutions, int distanceToFirstQuestionhorizontal, int numOptions, int distancetofirstquestionvertical) {
        ArrayList<Integer> answerkey = new ArrayList<>();
        short[][] inputarray = getquestions.getBWPixelGrid();
        for (int i = distancetofirstquestionvertical; i < inputarray.length; i += thresholdbwtnquestions) {


            for (int j = distanceToFirstQuestionhorizontal; j < inputarray[i].length - (j + thresholdbwtwnsolutions); j += thresholdbwtnquestions) {
                int answer = blackcpixelsinregion(inputarray, thresholdbwtnquestions, thresholdbwtwnsolutions, i, j, numOptions, distanceToFirstQuestionhorizontal);
                answerkey.add(answer);


            }
        }
        return answerkey;


    }

    public static ArrayList<ArrayList<Integer>> readanswers(int numstudents, int thresholdbwtnquestions, int thresholdbwtwnsolutions, int distanceToFirstQuestionhorizontal, int numOptions, int distancetofirstquestionvertical) {
        ArrayList<ArrayList<Integer>> studentanswer = new ArrayList<>();
        for (int i = 1; i < numstudents; i++) {
            DImage currentstudent = getimage("C:\\Users\\adame\\IdeaProjects\\ImageProcessingOpticalMark\\assets\\omrtest.pdf", i);
            ArrayList<Integer> currentstu = readQuestions(currentstudent, thresholdbwtnquestions, thresholdbwtwnsolutions, distanceToFirstQuestionhorizontal, numOptions, distancetofirstquestionvertical);
            studentanswer.add(currentstu);

        }
        return studentanswer;

    }


    public static double percentcorrect(ArrayList<Integer> x, ArrayList<Integer> answerkey) {
       int sum =0;
       if(x.size()!=answerkey.size() )return 0;
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i)==answerkey.get(i)){
                sum++;
            }

        }
        return (double)(sum)/ x.size();

    }
}