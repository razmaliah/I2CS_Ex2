import org.junit.jupiter.api.Test;
import java.awt.*;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 *
 */
public class Ex2_GUI {

    public static void drawMap(Map2D map) {
        if (map == null) {
            return;
        }
        int width = map.getWidth();
        int height = map.getHeight();
        double ratio = (double) height / (double) width;
        int canvasHeight = 750;
        int canvasWidth = Math.min(1500 ,(int) (canvasHeight / ratio));
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(StdDraw.BLACK);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = map.getPixel(i, j);
                StdDraw.setPenColor(intToColor(pixel));
                StdDraw.filledSquare(i + 0.5, j + 0.5, 0.49);
            }
        }
    }

    /**
     * this function loads a map from a text file. the format is:
     * first line: width height (with space between)
     * next lines: the pixel values row by row (with space between)
     * @param mapFileName
     * @return
     */
    public static Map2D loadMap(String mapFileName) {
        Map2D ans = null;
        try {
            File myObj = new File(mapFileName);
            Scanner myReader = new Scanner(myObj);
            if (myReader.hasNextLine()) {
                String firstLine = myReader.nextLine();
                String[] dims = firstLine.split(" ");
                int width = Integer.parseInt(dims[0]);
                int height = Integer.parseInt(dims[1]);
                ans = new Map(width, height, 0);
                int j = 0;
                while (myReader.hasNextLine() && j < height) {
                    String line = myReader.nextLine();
                    String[] pixels = line.split(" ");
                    for (int i = 0; i < width && i < pixels.length; i++) {
                        int pixelValue = Integer.parseInt(pixels[i]);
                        ans.setPixel(i, j, pixelValue);
                    }
                    j++;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * this function saves the given map to a text file. the format is:
     * first line: width height
     * next lines: the pixel values row by row.
     * @param map given map
     * @param mapFileName given file name
     */
    public static void saveMap(Map2D map, String mapFileName) {
        try{
            FileWriter fw = new FileWriter(mapFileName);
            fw.write(map.getWidth() + " " + map.getHeight() + "\n");
            for (int j = 0; j < map.getHeight(); j++) {
                for (int i = 0; i < map.getWidth(); i++) {
                    fw.write(map.getPixel(i, j) + " ");
                }
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] a) {
        String mapFile = "map1.txt";
        Map2D map =new Map(20,20,0);
        for (int i=1;i<map.getHeight();i++) {
            map.setPixel(10, i, 1);
        }
        drawMap(map); // draw the map with obstacles
        StdDraw.pause(2000);
        Index2D start = new Index2D(5,5);
        Index2D target = new Index2D(19,19);
        map.setPixel(start,6);
        map.setPixel(target,6);
        drawMap(map); // add  2 pixels
        StdDraw.pause(2000);
        Pixel2D[] p = map.shortestPath(start, target , 1, false);
        for (Pixel2D pixel : p) {
            map.setPixel(pixel, 3); // change the shortest path to RED color
        }
        map.setPixel(start,6);
        map.setPixel(target,6);
        drawMap(map); // add the shortest path between the pixels
        StdDraw.pause(2000);
        start = new Index2D(0,6);
        map.fill(start,4,false); // fill the left side of the obstacle to GREEN color
        drawMap(map); // add the filled area
        StdDraw.pause(2000);
        saveMap(map, mapFile);
        Map2D map2 = loadMap(mapFile);
        Index2D center = new Index2D(15,12);
        map2.drawCircle(center, 3.5,2);
        drawMap(map2); // draw the new loaded map with added circle
    }

    /// ///////////// Private functions ///////////////

    private static Color intToColor(int c) {
        if(c==0){return StdDraw.WHITE;}
        if(c==1){return StdDraw.YELLOW;}
        if(c==2){return StdDraw.BLUE;}
        if(c==3){return StdDraw.RED;}
        if(c==4){return StdDraw.GREEN;}
        if(c==5){return StdDraw.PINK;}
        return StdDraw.GRAY;
    }
}
