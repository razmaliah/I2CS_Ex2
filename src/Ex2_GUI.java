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
        StdDraw.setCanvasSize(700, 700);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = map.getPixel(i, j);
                if (pixel == 1) {
                    StdDraw.setPenColor(StdDraw.BLACK);
                } else {
                    StdDraw.setPenColor(StdDraw.BLUE);
                }
                StdDraw.filledCircle(i + 0.5, j + 0.5, 0.25);
            }
        }
    }

    /**
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
        Map2D map =new Map(30,15,0);
        for (int i=0;i<map.getWidth();i++) {
            map.setPixel(i, 7, 1);
        }
        saveMap(map, mapFile);
        Map2D map2 = loadMap(mapFile);
        map2.setPixel(7,7,100);
        saveMap(map2, "map2.txt");
        //drawMap(map);
    }

    /// ///////////// Private functions ///////////////
}
