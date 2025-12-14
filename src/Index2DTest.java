import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Index2DTest {
    @Test
    void testIndex2D() {
        Index2D index1 = new Index2D(3, 5);
        Index2D index2 = new Index2D(index1);
        assertTrue(index1.getX() == index2.getX());
        assertTrue(index1.getY() == index2.getY());
        assertTrue(index1.equals(index2));

        Index2D index3 = new Index2D(1,1);
        assertFalse(index1.equals(index3));

        Index2D index4 = null;
        assertFalse(index1.equals(index4));
    }

    @Test
    void testGetters() {
        Index2D index = new Index2D(1, 2);
        assertEquals(1, index.getX());
        assertEquals(2, index.getY());
    }

    @Test
    void testDistance() {
        Index2D index1 = new Index2D(1, 1);
        Index2D index2 = new Index2D(4, 5);
        assertEquals(5.0, index1.distance2D(index2));
        Index2D index3 = new Index2D(-2, -3);
        assertEquals(5.0, index1.distance2D(index3));
    }
    @Test
    void testToString() {
        Index2D index1 = new Index2D(2, 3);
        assertEquals("(2,3)", index1.toString());
        Index2D index2 = new Index2D(index1);
        assertEquals(index1.toString(), index2.toString());
    }
}
