package Filters;
import java.io.*;


import FileIO.PDFHelper;
import core.DImage;
import org.apache.pdfbox.io.IOUtils;
import processing.core.PImage;

import java.util.ArrayList;

public class ScantronInfo {
    private int numstudents = 6;
    private int thresholdbtwnquestions = 513-476;
    private int thresholdbtwnsolutions=210-172;
    private int distancetofirstquestionhorizontal =172;
    private int distancetofirstquestionvertical = 476;
    private int numoptions=5;

    public static void main(String[] args) {

        filecreater();
        for (int i = 0; i < itemanalysis(readanswers(6,513-476,210-172,172,5,476),513-476,210-172,172,5,476).size(); i++) {
            writeDataToFile("itemanalysis.txt",""+itemanalysis(readanswers(6,513-476,210-172,172,5,476),513-476,210-172,172,5,476).get(i));


        }
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
        for (int k = inputrow; k < inputrow + thresholdbwtnquestions-31; k++) {


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
        return RunTheFilter(filepath).get(index);

    }


    public static ArrayList<Integer> readQuestions(int currenthorquestionhorizontal, int numquestionspersheethorizontal , DImage getquestions, int thresholdbwtnquestions, int thresholdbwtwnsolutions, int distanceToFirstQuestionhorizontal, int numOptions, int distancetofirstquestionvertical) {
        ArrayList<Integer> answerkey = new ArrayList<>();
        short[][] inputarray = getquestions.getBWPixelGrid();
        for (int i = distancetofirstquestionvertical; i < inputarray.length; i += thresholdbwtnquestions) {


            for (int j = distanceToFirstQuestionhorizontal; j < distanceToFirstQuestionhorizontal+currenthorquestionhorizontal*(576-410); j += thresholdbwtnquestions) {
                int answer = blackcpixelsinregion(inputarray, thresholdbwtnquestions, thresholdbwtwnsolutions, i, j, numOptions, distanceToFirstQuestionhorizontal);
                answerkey.add(answer);


            }
        }
        return answerkey;


    }

    public static ArrayList<ArrayList<Integer>> readanswers(int numstudents, int thresholdbwtnquestions, int thresholdbwtwnsolutions, int distanceToFirstQuestionhorizontal, int numOptions, int distancetofirstquestionvertical) {
        ArrayList<ArrayList<Integer>> studentanswer = new ArrayList<>();
        for (int i = 1; i < numstudents; i++) {
            DImage currentstudent = getimage("assets\\omrtest.pdf", i);
            ArrayList<Integer> currentstu = new ArrayList<>();

            for (int j = 0; j < 4; j++) {
                for (int k = 0; k <readQuestions(j,4,currentstudent, thresholdbwtnquestions, thresholdbwtwnsolutions, distanceToFirstQuestionhorizontal, numOptions, distancetofirstquestionvertical).size(); k++) {

                    currentstu.add(readQuestions(j,4,currentstudent, thresholdbwtnquestions, thresholdbwtwnsolutions, distanceToFirstQuestionhorizontal, numOptions, distancetofirstquestionvertical).get(i));
                }

            }
            studentanswer.add(currentstu);

        }
        return studentanswer;

    }
    public static ArrayList<String> studentresults (int numstudents, int thresholdbwtnquestions, int thresholdbwtwnsolutions, int distanceToFirstQuestionhorizontal, int numOptions, int distancetofirstquestionvertical){
        ArrayList<String>sturesults = new ArrayList<>();
        ArrayList<Integer> anwerkey = new ArrayList<>();

        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < readQuestions(j, 4, getimage("assets/omrtest.pdf", 0), thresholdbwtnquestions, thresholdbwtwnsolutions, distanceToFirstQuestionhorizontal, numOptions, distancetofirstquestionvertical).size(); k++) {

                anwerkey.add(readQuestions(j, 4, getimage("assets/omrtest.pdf", 0), thresholdbwtnquestions, thresholdbwtwnsolutions, distanceToFirstQuestionhorizontal, numOptions, distancetofirstquestionvertical).get(j));
            }
        }
        ArrayList<ArrayList<Integer>> studentanswer = readanswers(numstudents, thresholdbwtnquestions, thresholdbwtwnsolutions, distanceToFirstQuestionhorizontal, numOptions, distancetofirstquestionvertical);
        for (ArrayList<Integer> integers : studentanswer) {
            for (int j = 0; j < integers.size(); j++) {
                if (!integers.get(j).equals(anwerkey.get(j))) {
                    sturesults.add(j + " is wrong");

                } else {
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
            if (x.get(i).equals(answerkey.get(i))){
                sum++;
            }

        }
        return (sum);

    }
    public static ArrayList<Integer> itemanalysis (ArrayList<ArrayList<Integer>>studentanswers, int thresholdbwtnquestions, int thresholdbwtwnsolutions, int distanceToFirstQuestionhorizontal, int numOptions, int distancetofirstquestionvertical ){
        ArrayList<Integer> anwerkey = new ArrayList<>();

        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < readQuestions(j, 4, getimage("assets/omrtest.pdf", 0), thresholdbwtnquestions, thresholdbwtwnsolutions, distanceToFirstQuestionhorizontal, numOptions, distancetofirstquestionvertical).size(); k++) {

                anwerkey.add(readQuestions(j, 4, getimage("assets/omrtest.pdf", 0), thresholdbwtnquestions, thresholdbwtwnsolutions, distanceToFirstQuestionhorizontal, numOptions, distancetofirstquestionvertical).get(j));
            }
        }
        ArrayList<Integer> incorrectsum =new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            incorrectsum.add(0);

        }

        for (ArrayList<Integer> studentanswer : studentanswers) {
            for (int j = 0; j < incorrectsum.size(); j++) {
                if (!studentanswer.get(j).equals(anwerkey.get(j))) {
                    //unsure of this (j-1)
                    incorrectsum.add(j, incorrectsum.get(j) + 1);
                    incorrectsum.remove(j + 1);
                }
            }
        }
        return incorrectsum;
    }


    public static void filecreater() {
            try {
                File myObj = new File("itemanalysis.txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    public static void writeDataToFile(String filePath, String data) {
        try (FileWriter f = new FileWriter(filePath);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b);
             ) {


            writer.println(data);


        } catch (IOException error) {
            System.err.println("There was a problem writing to the file: " + filePath);
            error.printStackTrace();
        }
    }





}
