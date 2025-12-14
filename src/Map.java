
import java.io.Serializable;
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
        int w = Math.min(p.getWidth(),this.getWidth());
        int h = Math.min(p.getHeight(),this.getHeight());
        for (int i = 0; i < w; i++) {
            for( int j = 0; j < h; j++) {
                int val = this.getPixel(i, j);
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
        int startW = Math.max(0, (int)(center.getX() - rad));
        int endW = Math.min(this.getWidth() - 1, (int)(center.getX()+rad+1));
        int startH = Math.max(0, (int)(center.getY() - rad));
        int endH = Math.min(this.getHeight() - 1, (int)(center.getY()+rad+1));
        for (int i = startW; i <= endW; i++) {
            for (int j = startH; j <= endH; j++) {
                Pixel2D p = new Index2D(i, j);
                if (center.distance2D(p) < rad) {
                    this.setPixel(i, j, color);
                }
            }
        }
    }

    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {

    }

    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {

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
		int ans = -1;

		return ans;
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;  // the result.

		return ans;
	}
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;  // the result.

        return ans;
    }
	////////////////////// Private Methods ///////////////////////

}
