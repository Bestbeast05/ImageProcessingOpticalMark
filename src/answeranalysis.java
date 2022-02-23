import java.io.*;


import FileIO.PDFHelper;
import core.DImage;
import core.DisplayWindow;
import org.apache.pdfbox.io.IOUtils;
import processing.core.PImage;

import java.util.ArrayList;

public class answeranalysis {




    // that does the image processing an returns a DTO with
    // the information you want



    public static DImage getimage(String filepath, int index) {
        PImage in = PDFHelper.getPageImage(filepath,index);
        DImage img = new DImage(in);
        return img;

    }



    public static ArrayList<String> studentresults (ArrayList<Integer> Answerkey, ArrayList<Integer> studentinput){
ArrayList<String> sturesults = new ArrayList<>();

for (int j = 0; j <studentinput.size(); j++){
if (!studentinput.get(j).equals(Answerkey.get(j))) {
                    sturesults.add(j+1 + " is wrong");

                } else {
                    sturesults.add(j+1 + " is correct");
                }

            }


        return sturesults;


    }


    public static double percentcorrect(ArrayList<Integer> x, ArrayList<Integer> answerkey) {
        int sum =0;
        if(x.size()!=answerkey.size() )return 0;
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i).equals(answerkey.get(i))){
                sum++;
            }

        }
        return (double) (sum)/x.size();

    }
    public static int [] itemanalysis(ArrayList<Integer>[] studentanswers,ArrayList<Integer>key){


        int[] incorrectsum =new int[key.size()];
        for (int i = 0; i < studentanswers.length; i++) {
            for(int j=0;j< key.size();j++){
                if (studentanswers[i].get(j)!= key.get(j)){
                    incorrectsum[j]++;
                }
            }

        }
        return incorrectsum;


    }






}