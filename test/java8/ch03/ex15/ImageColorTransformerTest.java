package java8.ch03.ex15;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.scene.paint.Color;

public class ImageColorTransformerTest {

    @Test
    public void test() {
        Color[][] target = generateColors(100, 100, 0, 0);
        ImageColorTransformer transformer = ImageColorTransformer.from(target);
        Color[][] actual = transformer.transform(c -> {
            return new Color(c.getRed() + 0.3, c.getGreen() + 0.1, c.getBlue(), c.getOpacity());
        }).transform(c -> {
            return new Color(c.getRed() + 0.2, c.getGreen() + 0.2, c.getBlue(), c.getOpacity());
        }).transform(c -> {
            return new Color(c.getRed() + 0.1, c.getGreen() + 0.1, c.getBlue(), c.getOpacity());
        }).toColors();
        Color[][] expected = generateColors(100, 100, 0.6, 0.4);

        assertTrue(expected.length == actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertTrue(expected[i].length == actual[i].length);
            for (int j = 0; j < actual[i].length; j++) {
                assertTrue(expected[i][j].equals(actual[i][j]));
            }
        }
    }

    private Color[][] generateColors(int x, int y, double adjustmentX, double adjustmentY) {
        Color[][] target = new Color[x][y];
        for (int i = 0; i < target.length; i++) {
            for (int j = 0; j < target[i].length; j++) {
                target[i][j] = new Color((double)(i / 255) + adjustmentX, (double)(j / 255) + adjustmentY, 0, 1.0);
            }
        }
        return target;
    }

}
