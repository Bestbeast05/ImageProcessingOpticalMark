import FileIO.PDFHelper;
import Filters.Blurfilterpipeline;

import java.io.*;
import java.util.ArrayList;


public class OpticalMarkReaderMain {
    public static void main(String[] args) {
        ArrayList[] studentanswers = new ArrayList[6];


        Blurfilterpipeline answers = new Blurfilterpipeline(answeranalysis.getimage("assets/omrtest.pdf", 1), 142, 520, 14, 37);
        answers.loopthroughpages(142, 520, answeranalysis.getimage("assets/omrtest.pdf", 1).getBWPixelGrid());
        ArrayList<Integer> answerkey = answers.getList();
        Blurfilterpipeline student1 = new Blurfilterpipeline(answeranalysis.getimage("assets/omrtest.pdf", 2), 142, 520, 14, 37);
        student1.loopthroughpages(142, 520, answeranalysis.getimage("assets/omrtest.pdf", 2).getBWPixelGrid());
        ArrayList<Integer> studentinput1 = student1.getList();
        studentanswers[0] = studentinput1;

        Blurfilterpipeline student2 = new Blurfilterpipeline(answeranalysis.getimage("assets/omrtest.pdf", 3), 142, 520, 14, 37);
        student2.loopthroughpages(142, 520, answeranalysis.getimage("assets/omrtest.pdf", 3).getBWPixelGrid());
        ArrayList<Integer> studentinput2 = student2.getList();
        studentanswers[1] = studentinput2;


        Blurfilterpipeline student3 = new Blurfilterpipeline(answeranalysis.getimage("assets/omrtest.pdf", 4), 142, 520, 14, 37);
        student3.loopthroughpages(142, 520, answeranalysis.getimage("assets/omrtest.pdf", 4).getBWPixelGrid());
        ArrayList<Integer> studentinput3 = student3.getList();
        studentanswers[2] = studentinput3;

        Blurfilterpipeline student4 = new Blurfilterpipeline(answeranalysis.getimage("assets/omrtest.pdf", 5), 142, 520, 14, 37);
        student4.loopthroughpages(142, 520, answeranalysis.getimage("assets/omrtest.pdf", 5).getBWPixelGrid());
        ArrayList<Integer> studentinput4 = student4.getList();
        studentanswers[3] = studentinput4;

        Blurfilterpipeline student5 = new Blurfilterpipeline(answeranalysis.getimage("assets/omrtest.pdf", 6), 142, 520, 14, 37);
        student5.loopthroughpages(142, 520, answeranalysis.getimage("assets/omrtest.pdf", 6).getBWPixelGrid());
        ArrayList<Integer> studentinput5 = student5.getList();
        studentanswers[4] = studentinput5;

        Blurfilterpipeline student6 = new Blurfilterpipeline(answeranalysis.getimage("assets/omrtest.pdf", 7), 142, 520, 14, 37);
        student6.loopthroughpages(142, 520, answeranalysis.getimage("assets/omrtest.pdf", 7).getBWPixelGrid());
        ArrayList<Integer> studentinput6 = student6.getList();
        studentanswers[5] = studentinput6;
        int[] itemanal1 = answeranalysis.itemanalysis(studentanswers, answerkey);
        Createfile();
        Createfiletwo();
        String x = "";
        for (int i = 0; i < itemanal1.length; i++) {
            x += itemanal1[i] + " students have gotten problem " + i +1+ " wrong \n";


        }
        writetofile(x, "itemanal.csv");


        String str = "";

        for (int i = 0; i < studentanswers.length; i++) {
            double percent = answeranalysis.percentcorrect(studentanswers[i], answerkey);
            str += "Student " + i + " got " + percent + " correct\n";


        }
        writetofile(str, "indivresults.csv");

        String str2 = " student  ";
        for (int i = 0; i < studentanswers.length; i++) {
            str2 += i + "\n";



                ArrayList<String> indvresults = answeranalysis.studentresults(answerkey, studentanswers[i]);
                for (int k = 0; k < indvresults.size(); k++) {
                    str2+= indvresults.get(k) + "\n";


                }
            }
            writetofile(str2, "indivresults.csv");

        }
            public static void Createfile () {
            try {
                File myObj = new File("itemanal.csv");
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
        public static void Createfiletwo () {
            try {
                File myObj = new File("indivresults.csv");
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

        public static void writetofile (String data, String filepath){
            try {
                FileWriter myWriter = new FileWriter(filepath);
                myWriter.write(data);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }

