

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Intro2CS, 2026A, this is a very
 */
class MapTest {
    /**
     */
    private int[][] _map_3_3 = {{0,1,0}, {1,0,1}, {0,1,0}};
    private Map2D _m0, _m1, _m3_3;
    @BeforeEach
    public void setuo() {
        _m3_3 = new Map(_map_3_3);
    }
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testInit() {
        int[][] bigarr = new int [500][500];
        _m1 = new Map(bigarr);
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        assertThrowsExactly(RuntimeException.class, () -> {
            _m0 = new Map(0,5,0);
        });
        int[][] arr2 = new int[5][0];
        assertThrowsExactly(RuntimeException.class, () -> {
            _m0 = new Map(arr2);
        });
        int [] ar1 = new int[5];
        int [] ar2 = new int[4];
        int [][] arr3 = {ar1, ar2};
        assertThrowsExactly(RuntimeException.class, () -> {
            _m0 = new Map(arr3);
        });
    }

    @Test
    void testInit2() {
        _m0 = new Map(_map_3_3);
        _m1 = new Map(_map_3_3);
        assertEquals(_m0,_m1);
        _m0 = new Map(100);
        assertEquals(100, _m0.getWidth());
        assertEquals(100, _m0.getHeight());
    }
    @Test
    void testGetMap() {
        _m0 = new Map(_map_3_3);
        int[][] arr = _m0.getMap();
        assertArrayEquals(_map_3_3, arr);
    }

    @Test
    void testGetPixel() {
        _m0 = new Map(_map_3_3); // {{0,1,0}, {1,0,1}, {0,1,0}}
        Pixel2D p = new Index2D(1,1);
        assertEquals(0, _m0.getPixel(p));
        p = new Index2D(0,1);
        assertEquals(1, _m0.getPixel(p));
        assertEquals(1, _m0.getPixel(2,1));
    }
    @Test
    void testSetPixel() {
        _m0 = new Map(_map_3_3); // {{0,1,0}, {1,0,1}, {0,1,0}}
        Pixel2D p = new Index2D(1,1);
        _m0.setPixel(p, -5);
        assertEquals(-5, _m0.getPixel(p));
        _m0.setPixel(0,1,-1);
        assertEquals(-1, _m0.getPixel(0,1));
    }

    @Test
    void testEquals() {
        assertEquals(_m0,_m1);
        _m0 = new Map(_map_3_3);
        _m1 = new Map(_map_3_3);
        assertTrue(_m0.equals(_m1));
    }
    @Test
    void testIsInside() {
        _m0 = new Map(100); // {{0,1,0}, {1,0,1}, {0,1,0}}
        Pixel2D p = new Index2D(50,50);
        assertTrue(_m0.isInside(p));
        p = new Index2D(99,99);
        assertTrue(_m0.isInside(p));
        p = new Index2D(0,0);
        assertTrue(_m0.isInside(p));
        p = new Index2D(100,100);
        assertFalse(_m0.isInside(p));
        p = new Index2D(-5,3);
        assertFalse(_m0.isInside(p));
        p = new Index2D(5,500);
        assertFalse(_m0.isInside(p));
        p = new Index2D(20,-1);
        assertFalse(_m0.isInside(p));
    }

    @Test
    void testSameDimensions() {
        _m0 = new Map(50);
        _m1 = new Map(50);
        assertTrue(_m0.sameDimensions(_m1));
        _m1 = new Map(60,50,5);
        assertFalse(_m0.sameDimensions(_m1));
    }

    @Test
    void testAddMap2D() {
        _m0 = new Map(_map_3_3);
        _m1 = new Map(_map_3_3);
        _m0.addMap2D(_m1);
        int[][] expected = {{0,2,0}, {2,0,2}, {0,2,0}};
        assertArrayEquals(expected, _m0.getMap());
    }

    @Test
    void testMul() {
        _m0 = new Map(5,5,5); // {{0,1,0}, {1,0,1}, {0,1,0}}
        _m0.mul(2.0);
        int[][] expected = {{10,10,10,10,10}, {10,10,10,10,10}, {10,10,10,10,10},{10,10,10,10,10},{10,10,10,10,10}};
        assertArrayEquals(expected, _m0.getMap());
        _m0.mul(0.55);
        _m1 = new Map(5,5,5);
        assertArrayEquals(_m1.getMap() , _m0.getMap());
    }

    @Test
    void testRescale() {
        _m0 = new Map(10);
        for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                _m0.setPixel(i,j,i);
            }
            System.out.println(Arrays.toString(_m0.getMap()[i]));
        }

        _m0.rescale(2,1.5);
        assertEquals(15, _m0.getHeight());
        for(int i=0;i<20;i++){
            for(int j=0;j<15;j++){
                assertEquals(i/2, _m0.getPixel(i,j));
            }
            System.out.println(Arrays.toString(_m0.getMap()[i]));
        }
    }
    @Test
    void testDrawCircle() {
        _m0 = new Map(11,11,0);
        Pixel2D center = new Index2D(5,5);
        Pixel2D center2 = new Index2D(100,5);
        _m0.drawCircle(center,-1,4);
        _m0.drawCircle(center2,3,11);
        _m0.drawCircle(center,3,1);
        boolean ans = true;
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                Pixel2D p = new Index2D(i,j);
                if (p.distance2D(center)<3 && _m0.getPixel(p) !=1){
                    ans = false;
                }
                if (p.distance2D(center)>=3 && _m0.getPixel(p) !=0){
                    ans = false;
                }
            }
        }
        assertTrue(ans);
    }
    @Test
    void testDrawLine() {
        _m0 = new Map(11);
        Pixel2D p1 = new Index2D(0,0);
        Pixel2D p2 = new Index2D(10,10);
        _m0.drawLine(p1,p2,1);
        boolean ans = true;
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                Pixel2D p = new Index2D(i,j);
                if (i==j && _m0.getPixel(p) !=1){
                    ans = false;
                }
                if (i!=j && _m0.getPixel(p) !=0){
                    ans = false;
                }
            }
        }
        assertTrue(ans);
    }
    @Test
    void testDrawLine2(){
        _m0 = new Map(10);
        Pixel2D p1 = new Index2D(1,5);
        Pixel2D p2 = new Index2D(7,3);
        _m0.drawLine(p1,p2,1);
        boolean ans = true;
        for(int i=0;i<10;i++) {
            System.out.println(Arrays.toString(_m0.getMap()[i]));
        }
    }
    @Test
    void testDrawRect() {
        _m0 = new Map(10);
        Pixel2D topLeft = new Index2D(2,2);
        Pixel2D bottomRight = new Index2D(7,5);
        _m0.drawRect(topLeft,bottomRight,1);
        boolean ans = true;
        for(int w=0;w<10;w++){
            for(int h=0;h<10;h++){
                Pixel2D p = new Index2D(w,h);
                if ((w>=2 && w<=7) && (h>=2 && h<=5) && _m0.getPixel(p) !=1){
                    ans = false;
                }
                if ((w<2 || w>7 || h<2 || h>5) && _m0.getPixel(p) !=0){
                    ans = false;
                }
            }
            System.out.println(Arrays.toString(_m0.getMap()[w]));
        }
        assertTrue(ans);
    }

    /**
     * test fill function by filling a 5x5 map from the center point (2,2) with value 1 and check if all the map is 1
     */
    @Test
    void testFill() {
        _m0 = new Map(5);
        Pixel2D p = new Index2D(2,2);
        int res = _m0.fill(p,1,true);
        boolean ans = true;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if (_m0.getPixel(i,j) !=1){
                    ans = false;
                }
            }
        }
        assertTrue(ans);
        assertEquals(25, res);
    }
    /**
     * test shortestPath function in 4 different cases:
     * 1. 11X11 map with vertical obstacle without one Pixel, non-cyclic
     * 2. 11X11 map with vertical obstacle without one Pixel, cyclic
     * 3. when start pixel equals to target pixel
     * 4. blocking the path completely (no valid path)
     */
    @Test
    void testShortestPath() {
        _m0 = new Map(11);
        for(int i=1;i<_m0.getHeight();i++){
            _m0.setPixel(5,i,1);
        }
        Pixel2D start = new Index2D(4,10);
        Pixel2D target = new Index2D(6,10);
        Pixel2D[] ans = _m0.shortestPath(start, target, 1, false);

        System.out.println("counter: " + Map.getCounter());

        int res = _m0.getHeight()*2 +1;     //the path going all the way up 2 steps right and all the way down
        assertEquals(res,ans.length);
        ans = _m0.shortestPath(start, target, 1, true);

        System.out.println("counter: " + Map.getCounter());

        res = 5;     // 4 steps + 1 (down,right*2,up)
        assertEquals(res,ans.length);
        ans = _m0.shortestPath(start, start, 1, false);

        System.out.println("counter: " + Map.getCounter());

        assertEquals(1,ans.length);         // check start equals target
        _m0.setPixel(5,0,1);    // blocking the path
        ans = _m0.shortestPath(start, target, 1, false);

        System.out.println("counter: " + Map.getCounter());

        assertNull(ans);
    }

    /**
     * test allDistance function in 3 different cases:
     * 1. small map with center point without obstacles
     * 2. larger map with vertical obstacle, non-cyclic
     * 3. larger map with vertical obstacle, cyclic
     */
    @Test
    void testAllDistance() {
        _m0 = new Map(5);
        Pixel2D p = new Index2D(2,2);
        _m0 = _m0.allDistance(p,1,false);
        int[][] dists = _m0.getMap();
        int[][] expected = {
                {4,3,2,3,4},
                {3,2,1,2,3},
                {2,1,0,1,2},
                {3,2,1,2,3},
                {4,3,2,3,4}
        };
        for(int i=0;i<5;i++){
            assertArrayEquals(expected[i], dists[i]);
        }
        _m0 = new Map(11);
        for(int i=0;i<_m0.getHeight();i++){
            _m0.setPixel(5,i,1);
        }
        p = new Index2D(0,0);
        _m1 = _m0.allDistance(p,1,true);
        _m0 = _m0.allDistance(p,1,false);
        boolean ans0 = true, ans1 = true;
        for(int i=0;i<_m0.getWidth();i++) {
            for (int j = 0; j < _m0.getHeight(); j++) {
                Pixel2D curr = new Index2D(i, j);
                if (i<5 && _m0.getPixel(curr) != (i + j)) {
                    ans0 = false;
                    ans1 = false;
                }
                if (i==5 && _m0.getPixel(curr) != 1) {
                    ans0 = false;
                    ans1 = false;
                }
                if (i>5) {
                    if(_m0.getPixel(curr) != -1) {
                        ans0 = false;
                    }
                    if (j<=5 && _m1.getPixel(curr) != ((_m1.getWidth()-i) + j)) {
                        ans1 = false;
                    }
                    if(j>5 && _m1.getPixel(curr) != ((_m1.getWidth()-i) + ( _m1.getHeight()-j))) {
                        ans1 = false;
                    }
                }
            }
        }
        assertTrue(ans0);
        assertTrue(ans1);
    }


}