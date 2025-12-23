
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 *
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D, Serializable{

    // edit this class below
    private int[][] _map;

	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w - width of the map
	 * @param h - height of the map
	 * @param v - init value of all the entries in the map
	 */
	public Map(int w, int h, int v) {init(w, h, v);}
	/**
	 * Constructs a square map (size*size). all the entries are initialized to 0.
	 * @param size - the size for the width and height of the map
	 */
	public Map(int size) {this(size,size, 0);}
	
	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}

    /**
     * Construct a 2D w*h matrix of integers.
     * v is the init value of all the entries in the 2D array.
     * @param w the width of the underlying 2D array.
     * @param h the height of the underlying 2D array.
     * @param v the init value of all the entries in the 2D array.
     */
	@Override
	public void init(int w, int h, int v) {
        this._map = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                this._map[i][j] = v;
	}
        }
    }
    /**
     * Constructs a new 2D raster map from a given 2D int array (deep copy).
     * @throws RuntimeException if arr == null or if the array is empty or a ragged 2D array.
     * @param arr a 2D int array.
     */
	@Override
	public void init(int[][] arr) {
        if (arr == null || arr.length == 0 || arr[0].length == 0) {
            throw new RuntimeException("Invalid array");
        }
        int height = arr[0].length;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].length != height) {
                throw new RuntimeException("Ragged array");
            }
        }
        this._map = new int[arr.length][height];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < height; j++) {
                this._map[i][j] = arr[i][j];
            }
        }
    }

    /**
     * @return a deep copy of the underline matrix.
     */
	@Override
	public int[][] getMap() {
		int[][] ans = new int[this._map.length][this._map[0].length];
        for (int i = 0; i < this._map.length; i++) {
            for (int j = 0; j < this._map[0].length; j++) {
                ans[i][j] = this._map[i][j];
            }
        }
		return ans;
	}

    /**
     * @return the width of this 2D map (first coordinate).
     */
	@Override
	public int getWidth() {
        int ans = this._map.length;
        return ans;
    }

    /**
     * @return the height of this 2D map (second coordinate).
     */
	@Override
	public int getHeight() {
        int ans = this._map[0].length;
        return ans;
    }
    /**
     * @param x the x coordinate (first coordinate) - width
     * @param y the y coordinate (second coordinate) - height
     * @return the [x][y] (int) value of the map[x][y].
     */
	@Override
	public int getPixel(int x, int y) {
        int ans = this._map[x][y];
        return ans;
    }
    /**
     * @param p the x,y coordinate
     * @return the [p.x][p.y] (int) value of the map.
     */
	@Override
	public int getPixel(Pixel2D p) {
        int x = p.getX();
        int y = p.getY();
        int ans = this._map[x][y];
        return ans;
	}

    /**
     * Set the [x][y] coordinate of the map to v.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param v the value that the entry at the coordinate [x][y] is set to.
     */
	@Override
	public void setPixel(int x, int y, int v) {
        Pixel2D p = new Index2D(x, y);
        if(!isInside(p)){
            throw new RuntimeException("Coordinate out of bounds");
        }
        this._map[x][y] = v;
    }

    /**
     * Set the [x][y] coordinate of the map to v.
     * @param p the coordinate in the map.
     * @param v the value that the entry at the coordinate [p.x][p.y] is set to.
     */
	@Override
	public void setPixel(Pixel2D p, int v) {
        int x = p.getX();
        int y = p.getY();
        this._map[x][y] = v;
	}

    /**
     * checks if the given 2D coordinate is inside this map.
     * @param p the 2D coordinate.
     * @return true if p is inside this map.
     */
    @Override
    public boolean isInside(Pixel2D p) {
        boolean ans = true;
        if (p == null) {return false;}
        int x = p.getX();
        int y = p.getY();
        if (x < 0 || y < 0 || x >= this.getWidth() || y >= this.getHeight()) {
            ans = false;
        }
        return ans;
    }
    /**
     * This method returns true if and only if this Map has the same dimensions as p.
     * @param p - other map to compare to.
     * @return true if and only if this Map has the same dimensions as p.
     */

    @Override
    public boolean sameDimensions(Map2D p){
        boolean ans = false;
        if (this.getWidth() == p.getWidth() && this.getHeight() == p.getHeight()){
            ans = true;
        }
        return ans;
    }
    /**
     * This method adds the map p to this map - assuming they have the same dimensions (
     * else do nothing).
     * @param p - the map that should be added to this map (just in case they have the same dimensions).
     */
    @Override
    public void addMap2D(Map2D p) {
        if (this.sameDimensions(p)) {
            for (int i = 0; i < this.getWidth(); i++) {
                for (int j = 0; j < this.getHeight(); j++) {
                    int sum = this.getPixel(i, j) + p.getPixel(i, j);
                    this.setPixel(i, j, sum);
                }
            }
        }

    }

    /**
     * This method multiplies all the entries in this map by the given scalar.
     * @param scalar
     */
    @Override
    public void mul(double scalar) {
        for( int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                int val = (int)(this.getPixel(i, j) * scalar);
                this.setPixel(i, j, val);
            }
        }
    }

    /**
     * Rescale the dimensions of this map, a map of size [100][200]
     * rescaled with (1.2,0.5) will change to [120][100].
     * @param sx rescale factor for width
     * @param sy rescale factor for height
     */
    @Override
    public void rescale(double sx, double sy) {
        int width = (int)(this.getWidth() * sx);
        int height = (int)(this.getHeight() * sy);
        Map p = new Map(width, height, 0);
        double wx = 1/sx;      // ratio of new to old width
        double hy = 1/sy;      // ratio of new to old height
        for (int i = 0; i < p.getWidth(); i++) {
            for( int j = 0; j < p.getHeight(); j++) {
                int i2 = (int)(i * wx);
                int j2 = (int)(j * hy);
                int val = this.getPixel(i2, j2);
                p.setPixel(i, j, val);
            }
        }
        this._map = p.getMap();
    }

    /**
     * Draw a circle on this map.
     * @param center - the center of the circle "(x,y)".
     * @param rad - the radius of the circle.
     * @param color - the (new) color to be used in the drawing.
     */
    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        if (rad >= 0 || isInside(center)) {
            int startW = Math.max(0, (int) (center.getX() - rad));
            int endW = Math.min(this.getWidth() - 1, (int) (center.getX() + rad + 1));
            int startH = Math.max(0, (int) (center.getY() - rad));
            int endH = Math.min(this.getHeight() - 1, (int) (center.getY() + rad + 1));
            for (int i = startW; i <= endW; i++) {
                for (int j = startH; j <= endH; j++) {
                    Pixel2D p = new Index2D(i, j);
                    if (center.distance2D(p) < rad) {
                        this.setPixel(i, j, color);
                    }
                }
            }
        }
    }

    /**
     * Draw a line on this map.
     * @param p1 start point of the line "(x,y)".
     * @param p2 end point of the line "(x,y)".
     * @param color - the (new) color to be used in the drawing.
     */
    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
        if(isInside(p1)&&isInside(p2)){
            if(p1.equals(p2)){this.setPixel(p1,color);}
            else{
                Pixel2D left, right;
                if (p1.getX() < p2.getX()){
                    left = (Index2D) p1;
                    right = (Index2D) p2;
                }
                else{
                    left = (Index2D) p2;
                    right = (Index2D) p1;
                }
                int dx = right.getX() - left.getX(); // dx >=0
                int dy = right.getY() - left.getY();
                int steps = Math.max(Math.abs(dx), Math.abs(dy));
                if (dx == 0) { // vertical line
                    for (int j = Math.min(left.getY(), right.getY()); j <= Math.max(left.getY(), right.getY()); j++) {
                        this.setPixel(left.getX(), j, color);
                    }
                    return;
                }
                double xInc = (double) dx / (double) steps; // increment in x for each step
                double a = (double) dy/(double) dx; // represent a from y = ax + b
                double b = left.getY() - a * left.getX(); // represent b from y = ax + b
                for (int i = 0; i <= steps; i++) {
                    int newX = (int) (left.getX() + xInc * i);
                    int newY = (int) (a * newX + b);
                    this.setPixel(newX,newY,color);
                }
            }
        }
    }

    /**
     * Draw a rectangle on this map with corners p1 and p2.
     * @param p1 the first corner of the rectangle "(x,y)".
     * @param p2 the second corner of the rectangle "(x,y)".
     * @param color - the (new) color to be used in the drawing.
     */
    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {
        if(isInside(p1)&&isInside(p2)){
            int startX = Math.min(p1.getX(), p2.getX());
            int endX = Math.max(p1.getX(), p2.getX());
            int startY = Math.min(p1.getY(), p2.getY());
            int endY = Math.max(p1.getY(), p2.getY());
            for (int i = startX; i <= endX; i++) {
                for (int j = startY; j <= endY; j++) {
                    this.setPixel(i, j, color);
                }
            }
        }
    }

    /**
     * check if this map is equal to another map.
     * @param ob the reference object with which to compare.
     * @return true if the maps are equal (same dimensions and same values in all entries).
     */
    @Override
    public boolean equals(Object ob) {
        boolean ans = true;
        if(!(ob instanceof Map2D)) {
            return false;
        }
        Map2D p = (Map2D) ob;
        if(!this.sameDimensions(p)) {
            ans = false;
        }
        else {
            for (int i = 0; i < this.getWidth(); i++) {
                for (int j = 0; j < this.getHeight(); j++) {
                    if (this.getPixel(i, j) != p.getPixel(i, j)) {
                        ans = false;
                        break;
                    }
                }
                if (!ans) {
                    break;
                }
            }
        }
        return ans;
    }
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
		int ans = 0;
        if(xy == null || !isInside(xy)){return ans;}
        int old_v = this.getPixel(xy);
        if(old_v == new_v){return ans;}
        Pixel2D up = new Index2D(xy.getX(), xy.getY()-1);
        Pixel2D down = new Index2D(xy.getX(), xy.getY()+1);
        Pixel2D left = new Index2D(xy.getX()-1, xy.getY());
        Pixel2D right = new Index2D(xy.getX()+1, xy.getY());
        this.setPixel(xy, new_v);
        ans++;
        if(isInside(up) && this.getPixel(up) == old_v){
            ans += fill(up, new_v, cyclic);
        }
        if(isInside(down) && this.getPixel(down) == old_v){
            ans += fill(down, new_v, cyclic);
        }
        if(isInside(left) && this.getPixel(left) == old_v){
            ans += fill(left, new_v, cyclic);
        }
        if(isInside(right) && this.getPixel(right) == old_v){
            ans += fill(right, new_v, cyclic);
        }
        if(cyclic){
            if(xy.getX() == 0){
                Pixel2D cLeft = new Index2D(this.getWidth()-1, xy.getY());
                if(this.getPixel(cLeft) == old_v){
                    ans += fill(cLeft, new_v, cyclic);
                }
            }
            if(xy.getX() == this.getWidth()-1){
                Pixel2D cRight = new Index2D(0, xy.getY());
                if(this.getPixel(cRight) == old_v){
                    ans += fill(cRight, new_v, cyclic);
                }
            }
            if(xy.getY() == 0){
                Pixel2D cUp = new Index2D(xy.getX(), this.getHeight()-1);
                if(this.getPixel(cUp) == old_v){
                    ans += fill(cUp, new_v, cyclic);
                }
            }
            if(xy.getY() == this.getHeight()-1){
                Pixel2D cDown = new Index2D(xy.getX(), 0);
                if(this.getPixel(cDown) == old_v){
                    ans += fill(cDown, new_v, cyclic);
                }
            }
        }
		return ans;
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D start, Pixel2D target, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;
        if (start == null || target == null || !isInside(start) || !isInside(target)){return ans;}
        if (start.equals(target)){
            ans = new Pixel2D[1];
            ans[0] = start;
            return ans;
        }
        Map tempMap = new Map(this.getMap());
        tempMap.resetMap(obsColor);
        tempMap.setPixel(start, 0);
        //tempMap.printMap();
        tempMap.markSteps(start, target, cyclic, obsColor);
        //tempMap.printMap();
        int howManySteps = tempMap.getPixel(target);
        if (howManySteps < 0) {return null;} // no path found
        ans = new Pixel2D[howManySteps+1];      // from start to target is steps +1 pixels
        Pixel2D curr = target;
        for(int i=howManySteps; i>=0; i--){
            ans[i] = curr;
            curr = tempMap.goBack(curr, cyclic);
        }
		return ans;
	}
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;  // the result.

        return ans;
    }

    /**
     * Resets the map for the shortest path computation.
     * all obstacle pixels (obstColor) are set to -2.
     * all other pixels are set to -1.
     */
    ////////////////////// Private Methods ///////////////////////
    private void resetMap(int obstColor){
        for(int i=0;i<this.getWidth();i++){
            for(int j=0;j<this.getHeight();j++){
                if(this.getPixel(i,j) == obstColor){
                    this.setPixel(i,j,-2);
                }
                else{
                    this.setPixel(i,j,-1);
                }
            }
        }
    }
    private int markNieghbors(Pixel2D start, boolean cyclic){
        int howManyMarked =0;
        Pixel2D up = new Index2D(start.getX(), start.getY()-1);
        Pixel2D down = new Index2D(start.getX(), start.getY()+1);
        Pixel2D left = new Index2D(start.getX()-1, start.getY());
        Pixel2D right = new Index2D(start.getX()+1, start.getY());
        if(cyclic && up.getY()<0){
            up = new Index2D(start.getX(), this.getHeight()-1);
        }
        if(cyclic && down.getY()>=this.getHeight()) {
            down = new Index2D(start.getX(), 0);
        }
        if(cyclic && left.getX()<0){
            left = new Index2D(this.getWidth()-1, start.getY());
        }
        if(cyclic && right.getX()>=this.getWidth()){
            right = new Index2D(0, start.getY());
        }
        int myValue = this.getPixel(start);

        if(isInside(up) && (this.getPixel(up) == -1 || this.getPixel(up) > myValue +1)){
            this.setPixel(up, myValue +1);
            howManyMarked++;
        }
        if(isInside(down) && (this.getPixel(down) == -1 || this.getPixel(down) > myValue +1)){
            this.setPixel(down, myValue +1);
            howManyMarked++;
        }
        if(isInside(left) && (this.getPixel(left) == -1 || this.getPixel(left) > myValue +1)) {
            this.setPixel(left, myValue + 1);
            howManyMarked++;
        }
        if(isInside(right) && (this.getPixel(right) == -1 || this.getPixel(right) > myValue +1)){
            this.setPixel(right, myValue +1);
            howManyMarked++;
        }
        return howManyMarked;
    }

    private void markSteps(Pixel2D start,Pixel2D target, boolean cyclic, int obsColor){
        if(!isInside(start) || !isInside(target)){return;}
        if(start.equals(target)){return;}

        int howMany = markNieghbors(start, cyclic);
        if (howMany == 0) {
            return;
        }
        Pixel2D up = new Index2D(start.getX(), start.getY()-1);
        Pixel2D down = new Index2D(start.getX(), start.getY()+1);
        Pixel2D left = new Index2D(start.getX()-1, start.getY());
        Pixel2D right = new Index2D(start.getX()+1, start.getY());

        if(cyclic && up.getY()<0){
            up = new Index2D(start.getX(), this.getHeight()-1);
        }
        if(cyclic && down.getY()>=this.getHeight()) {
            down = new Index2D(start.getX(), 0);
        }
        if(cyclic && left.getX()<0){
            left = new Index2D(this.getWidth()-1, start.getY());
        }
        if(cyclic && right.getX()>=this.getWidth()){
            right = new Index2D(0, start.getY());
        }
        if (isInside(up) && getPixel(up) == getPixel(start) +1) {
            markSteps(up, target, cyclic, obsColor);
        }
        if (isInside(down)&& getPixel(down) == getPixel(start) +1) {
            markSteps(down, target, cyclic, obsColor);
        }
        if (isInside(left)&& getPixel(left) == getPixel(start) +1) {
            markSteps(left, target, cyclic, obsColor);
        }
        if (isInside(right)&& getPixel(right) == getPixel(start) +1) {
            markSteps(right, target, cyclic, obsColor);
        }
    }
    private Pixel2D goBack(Pixel2D target, boolean cyclic){
        Pixel2D ans = null;
        int myValue = this.getPixel(target);
        Pixel2D up = new Index2D(target.getX(), target.getY()-1);
        Pixel2D down = new Index2D(target.getX(), target.getY()+1);
        Pixel2D left = new Index2D(target.getX()-1, target.getY());
        Pixel2D right = new Index2D(target.getX()+1, target.getY());
        if(cyclic && up.getY()<0){
            up = new Index2D(target.getX(), this.getHeight()-1);
        }
        if(cyclic && down.getY()>=this.getHeight()) {
            down = new Index2D(target.getX(), 0);
        }
        if(cyclic && left.getX()<0){
            left = new Index2D(this.getWidth()-1, target.getY());
        }
        if(cyclic && right.getX()>=this.getWidth()){
            right = new Index2D(0, target.getY());
        }

        if(isInside(up) && this.getPixel(up) == myValue -1){
            ans = up;
        }
        else if(isInside(down) && this.getPixel(down) == myValue -1){
            ans = down;
        }
        else if(isInside(left) && this.getPixel(left) == myValue -1){
            ans = left;
        }
        else if(isInside(right) && this.getPixel(right) == myValue -1){
            ans = right;
        }
        return ans;
    }
    private void printMap(){
        for(int i=0; i< this.getWidth(); i++){
            for(int j=0; j< this.getHeight(); j++){
                System.out.print(this.getPixel(i,j)+" ");
            }
            System.out.println();
        }
        System.out.println();
        return;
    }
    static void main(String[] args) {
        Map map = new Map(100,100,0);
        Pixel2D p1 = new Index2D(8,3);
        Pixel2D p2 = new Index2D(8,12);
        Pixel2D p3 = new Index2D(1,12);
        Pixel2D p4 = new Index2D(1,3);
        Pixel2D p5 = new Index2D(6,3);
        Pixel2D p6 = new Index2D(6,10);
        Pixel2D p7 = new Index2D(3,10);
        Pixel2D p8 = new Index2D(3,5);


        map.drawLine(p1,p2, -2);
        map.drawLine(p2,p3, -2);
        map.drawLine(p3,p4, -2);
        map.drawLine(p4,p5, -2);
        map.drawLine(p5,p6, -2);
        map.drawLine(p6,p7, -2);
        map.drawLine(p7,p8, -2);
        map.printMap();

        Pixel2D start = new Index2D(0,0);
        Pixel2D target = new Index2D(5,7);
        Pixel2D[] ans = map.shortestPath(start, target, -2, false);
        for(int i=0; i<ans.length; i++){
            map.setPixel(ans[i], 1);
        }
        map.printMap();
        System.out.println("how many steps: " + (ans.length -1));


    }

}
