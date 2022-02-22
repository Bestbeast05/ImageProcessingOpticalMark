package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;
import java.util.Arrays;

public class Blurfilterpipeline implements PixelFilter {


    ArrayList<PixelFilter> filters = new ArrayList<>();
    private int columndiameter, rowDiam;
    public String studentID = "";
    ArrayList<Integer> answerkey= new ArrayList<>() ;
    // colddiam rowdiam 37
    public Blurfilterpipeline(DImage img, int row, int col, int columbdiameter, int rowdiameter) {
        PixelFilter blur = new ConvolutionFilter1();
        columndiameter=14;
        rowDiam=37;

        filters.add(blur);
        processImage(img);
        short[][] pixelinput = img.getBWPixelGrid();
        this.columndiameter = columndiameter;
        this.rowDiam = rowdiameter;

        studentID = getStudentID(340, 90, pixelinput, 53, 25);
    }

    public ArrayList<Integer> getList() {
        return answerkey;
    }

    @Override
    public DImage processImage(DImage img) {
        for (PixelFilter filter : filters) {
            img = filter.processImage(img);
        }
        return img;
    }
// student id diameterhsit 24,55
    public String getStudentID(int row, int col, short[][] grid, int columndiameter, int rowdiameter) {
        String ID = " ";
        for (int r = row; r < row + (rowdiameter * 4); r += rowdiameter) {
            ID = returnID(col, columndiameter, grid, r, ID);
        }
        return ID;
    }


    public String returnID ( int col, int colD, short[][] grid, int row, String ID){
                    int max = Integer.MIN_VALUE;
                    int maxLoc = 0;
                    ArrayList<Integer> values = new ArrayList<>();
                    ArrayList<String> numbervalues = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
                    for (int c = col; c < col + (colD * 10); c += colD) {
                        int result = loopthroughbubbles(row, c, grid);
                        values.add(result);
                    }
                    for (int i = 0; i < values.size(); i++) {
                        if (values.get(i) > max) {
                            max = values.get(i);
                            maxLoc = i;
                        }
                    }
                    ID += numbervalues.get(maxLoc);
                    return ID;
                }

                public void loopthroughpages ( int row, int col, short[][] grid){
                    int count = 0;
                    col = loopthroughrows(row, col, grid, count);
                    count += 25;
                    col = loopthroughrows(row, col + 133, grid, count);
                    count += 25;
                    col = loopthroughrows(row, col + 133, grid, count);
                    count += 25;
                    col = loopthroughrows(row, col + 133, grid, count);
                }
    public int loopthroughbubbles(int row, int col, short[][] grid) {
        int counter = 0;
        for (int i = -8; i <8; i++) {
            for (int j = -8; j < 8; j++) {
                if(grid[row+i][col+j] < 200){
                    counter++;
                }
            }
        }
        return counter;
    }
    public int loopthroughrows(int row, int col, short[][] grid, int count) {
        int lastLoc = 0;
        for (int r = row; r < row + (rowDiam * 25); r += rowDiam) {
            count++;
            System.out.println(count);



            lastLoc = loopthroughcols(r, col, grid);
        }
        return lastLoc;
    }
    public int loopthroughcols(int row, int col, short[][] grid) {
        int max = 0;
        int maxLoc = 0;
        int column = 0;
        ArrayList<Integer> vals = new ArrayList<>();
        ArrayList<Integer> answer = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        for (int c = col; c < col+(columndiameter*5); c+=columndiameter) {
            int result = loopthroughbubbles(row,c,grid);
            vals.add(result);
            column = c;

        }
        for (int i = 0; i < vals.size(); i++) {
            if(vals.get(i) > max){
                max = vals.get(i);
                maxLoc=i;
            }
        }
        answerkey.add(answer.get(maxLoc));



        return column;
    }





}