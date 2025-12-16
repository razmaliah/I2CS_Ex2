

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
    void tesInit() {
        int[][] bigarr = new int [500][500];
        _m1 = new Map(bigarr);
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3,2);
        //_m1.fill(p1,1, true);
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
}