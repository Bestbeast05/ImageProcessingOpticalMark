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

        //SaveAndDisplay();

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

    private static void SaveAndDisplay(){
        //for (int i = 0; i <7 ; i++) {
            PImage image = PDFHelper.getPageImage("assets/omrtest.pdf",1);
            image.save(currentFolder+"asset/page1.png");
            DisplayWindow.showFor("asset/page1.png");
       // }

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
    public static ArrayList<String> studentresults (int numstudents, int thresholdbwtnquestions, int thresholdbwtwnsolutions, int distanceToFirstQuestionhorizontal, int numOptions, int distancetofirstquestionvertical){
        ArrayList<String>sturesults = new ArrayList<>();
        ArrayList<Integer> anwerkey = readQuestions(getimage("assets/omrtest.pdf",0),thresholdbwtnquestions,thresholdbwtwnsolutions,distanceToFirstQuestionhorizontal,numOptions,distancetofirstquestionvertical);

        ArrayList<ArrayList<Integer>> studentanswer = readanswers(numstudents, thresholdbwtnquestions, thresholdbwtwnsolutions, distanceToFirstQuestionhorizontal, numOptions, distancetofirstquestionvertical);
        for (int i = 0; i < studentanswer.size(); i++) {
            for (int j = 0; j < studentanswer.get(i).size(); j++) {
                if(studentanswer.get(i).get(j)!= anwerkey.get(j)){
                    sturesults.add(j +" is wrong");

                }else{
                    sturesults.add(j + " is correct");
                }

            }

        }
        return sturesults;


    }


    public static int numcorrect(ArrayList<Integer> x, ArrayList<Integer> answerkey) {
       int sum =0;
       if(x.size()!=answerkey.size() )return 0;
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i)==answerkey.get(i)){
                sum++;
            }

        }
        return (sum);

    }
    public static ArrayList<Integer> itemanalysis (ArrayList<ArrayList<Integer>>studentanswers, int thresholdbwtnquestions, int thresholdbwtwnsolutions, int distanceToFirstQuestionhorizontal, int numOptions, int distancetofirstquestionvertical ){
        ArrayList<Integer> incorrectsum =new ArrayList<>(studentanswers.get(0).size());
        for (int i = 0; i < studentanswers.size(); i++) {
            for (int j = 0; j < studentanswers.get(i).size(); j++) {
                if(studentanswers.get(i).get(j)!=readQuestions(getimage("assets/omrtest.pdf", 0),thresholdbwtnquestions,thresholdbwtwnsolutions,distanceToFirstQuestionhorizontal,numOptions,distancetofirstquestionvertical).get(j)){
                    incorrectsum.add( j, incorrectsum.get(j)+1);
                    incorrectsum.remove(j+1);
                }
            }


        }
        return incorrectsum;
    }
}