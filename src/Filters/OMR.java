package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

public class OMR implements PixelFilter, Interactive {

    private static final int ID_BOX_SIZE =469;
    private static final int ID_START_ROW=317;
    private static final int ID_START_COL=130;
    private static int Blackthreshold=150;


    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {

    }

    @Override
    public void keyPressed(char key) {
        if(key=='+'){
            Blackthreshold+=10;
        }
        if(key=='-'){
            Blackthreshold-=10;
        }
        System.out.println(Blackthreshold);
    }
    private short[][] THRESHOLD(short[][]grid){
        for (int row = 0; row <grid.length; row++) {
            for (int col = 0; col < grid[0].length ; col++) {
                if(grid[row][col]<Blackthreshold){
                    grid[row][col]=0;
                }
                else{
                    grid[row][col]=255;
                }
            }
        }
        return grid;
    }
    @Override
    public DImage processImage(DImage img) {
        short[][] image = img.getBWPixelGrid();

        image = THRESHOLD(image);

        img.setPixels(image);
        return img;
    }
}
