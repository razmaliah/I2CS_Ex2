
public class Index2D implements Pixel2D {
    private int _x;
    private int _y;
    /**
     * Constructor that creates a 2D index (w,h).
     * @param w - reprtesents the width (x coordinate)
     * @param h - represents the height (y coordinate)
     */
    public Index2D(int w, int h) {
        this._x = w;
        this._y = h;
    }

    /**
     * Copy constructor, creates a deep copy of other Index2D.
     * @param other
     */
    public Index2D(Pixel2D other) {
        this._x = other.getX();
        this._y = other.getY();
    }

    @Override
    public int getX() {
        return this._x;
    }

    @Override
    public int getY() {
        return this._y;
    }

    /**
     * This function computes the 2D distance between this pixel and p2 pixel.
     * @param p2 - another Pixel2D
     * @return - the distance between the two pixels
     */
    @Override
    public double distance2D(Pixel2D p2) {
        double dx = this._x - p2.getX();
        double dy = this._y - p2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * String represent this coordinate "(x,y)".
     * @return - string represent this coordinate
     */
    @Override
    public String toString() {
        String ans = "(" + this._x + "," + this._y + ")";
        return ans;
    }

    /**
     * check if this pixel is equal to p.
     * Note: will return true only if p is instance of Pixel2D and the x and y coordinates are equal.
     * @param p the reference object with which to compare.
     * @return
     */
    @Override
    public boolean equals(Object p) {
        boolean ans = false;
        if (p instanceof Pixel2D) {
            Pixel2D other = (Pixel2D) p;
            if (this._x == other.getX() && this._y == other.getY()) {
                ans = true;
            }
        }
        return ans;
    }
}
