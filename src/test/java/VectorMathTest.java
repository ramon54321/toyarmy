import org.joml.Vector2f;
import org.junit.Test;
import org.toyarmy.engine.math.VectorMath;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ramon Brand on 5/6/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class VectorMathTest {

    @Test
    public void testAngleBetween() {
        System.out.println("Angle Between");
        System.out.println(VectorMath.getAngleBetween(new Vector2f(0,4), new Vector2f(3, 0)));
        System.out.println(VectorMath.getAngleBetween(new Vector2f(0,4), new Vector2f(-3, 0)));
        System.out.println(VectorMath.getAngleBetween(new Vector2f(0,4), new Vector2f(-1, -10)));
        System.out.println(VectorMath.getAngleBetween(new Vector2f(0,4), new Vector2f(1, -10)));
        assertTrue(VectorMath.getAngleBetween(new Vector2f(0,4), new Vector2f(3, 0)) == 90f);
    }

    @Test
    public void testBearing() {
        System.out.println("Bearing");
        System.out.println(VectorMath.getBearingOf(new Vector2f(4,4)));
        System.out.println(VectorMath.getBearingOf(new Vector2f(4,-4)));
        System.out.println(VectorMath.getBearingOf(new Vector2f(-4,-4)));
        System.out.println(VectorMath.getBearingOf(new Vector2f(-4,4)));
        System.out.println(VectorMath.getBearingOf(new Vector2f(-4,0)));
        assertTrue(VectorMath.getBearingOf(new Vector2f(0,4)) == 0f);
        assertTrue(VectorMath.getBearingOf(new Vector2f(0,-4)) == 180f);
        assertTrue(VectorMath.getBearingOf(new Vector2f(4,0)) == 90f);
        assertTrue(VectorMath.getBearingOf(new Vector2f(-4,0)) == 270f);
        assertTrue(VectorMath.getBearingOf(new Vector2f(0,4)) == 0f);
        assertTrue(VectorMath.getBearingOf(new Vector2f(4,4)) == 45f);
        assertTrue(VectorMath.getBearingOf(new Vector2f(4,0)) == 90f);
        assertTrue(VectorMath.getBearingOf(new Vector2f(4,-1)) > 90f);
        assertTrue(VectorMath.getBearingOf(new Vector2f(0.5f,-7)) < 180f);
        assertTrue(VectorMath.getBearingOf(new Vector2f(-0.5f,-7)) > 180f);
        assertTrue(VectorMath.getBearingOf(new Vector2f(-4,-0.5f)) < 270f);
        assertTrue(VectorMath.getBearingOf(new Vector2f(-4,0.5f)) > 270f);
    }

    @Test
    public void testBearingFrom() {
        System.out.println("Bearing From");
        assertTrue(VectorMath.getBearingFrom(new Vector2f(0,0), new Vector2f(4, 4)) == 45f);
        assertTrue(VectorMath.getBearingFrom(new Vector2f(4,4), new Vector2f(8, 8)) == 45f);
        assertTrue(VectorMath.getBearingFrom(new Vector2f(4,4), new Vector2f(0, 0)) == 225f);
    }
}
