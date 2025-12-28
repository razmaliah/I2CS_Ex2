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
        Map2D map =new Map(30,30,0);
        for (int i=1;i<map.getHeight();i++) {
            map.setPixel(10, i, 1);
        }
        Index2D start = new Index2D(0,9);
        Index2D target = new Index2D(29,29);
        Pixel2D[] p = map.shortestPath(start, target , 1, false);
        for (Pixel2D pixel : p) {
            map.setPixel(pixel, 3);
        }
        start = new Index2D(0,6);
        map.fill(start,4,true);
        //saveMap(map, mapFile);
        //Map2D map2 = loadMap(mapFile);
        //map2.setPixel(7,7,100);
        //saveMap(map2, "map2.txt");
        Index2D center = new Index2D(20,15);
        map.drawCircle(center, 6,4);
        Index2D p11 = new Index2D(20,20);
        Index2D p12 = new Index2D(29,29);
        map.drawRect(p12,p11,5);
        drawMap(map);

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
