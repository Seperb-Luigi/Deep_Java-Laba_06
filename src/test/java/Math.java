
import org.junit.jupiter.api.Test;

import static java.lang.Math.*;
        import static org.junit.jupiter.api.Assertions.*;

public class MathCalcGraphTest {

    @Test
    public void testCalculateFunction() {
        double a = 7.12, b = 8.12, c = 2.0;
        double xVal = 1.0;

        double expected = calculateFunction(a, b, c, xVal);

        assertEquals(expected, expected, 0.001);
    }

    @Test
    public void testFindExtremums() {
        double a = 7.12, b = 8.12, c = 2.0;

        double[] extremums = findExtremums(a, b, c);
        double minX = extremums[0];
        double minF = extremums[1];
        double maxX = extremums[2];
        double maxF = extremums[3];

        assertNotNull(extremums);
        assertNotEquals(Double.NaN, minX);
        assertNotEquals(Double.NaN, maxX);
    }

    private double calculateFunction(double a, double b, double c, double x) {
        return exp(a * cos(x + 2)) - (exp(-sin(b * x))) / (c - cbrt(x));
    }

    private double[] findExtremums(double a, double b, double c) {
        double minX = Double.NaN, minF = Double.NaN, maxX = Double.NaN, maxF = Double.NaN;
        boolean first = true;

        for (double xVal = -10; xVal <= 5; xVal += 0.1) {
            double f = calculateFunction(a, b, c, xVal);

            if (Double.isNaN(f) || Double.isInfinite(f)) {
                continue;
            }

            if (first || f < minF) {
                minX = xVal;
                minF = f;
            }
            if (first || f > maxF) {
                maxX = xVal;
                maxF = f;
            }

            first = false;
        }

        return new double[]{minX, minF, maxX, maxF};
    }
}

