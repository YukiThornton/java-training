package java8.ch03.ex05;

import java.util.function.BiPredicate;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PhotoEditor {
    public static Image transform(Image in, BiPredicate<Integer, Integer> condition, ColorTransformer f) {
        int width = (int)in.getWidth();
        int height = (int)in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (condition.test(x, y)) {
                    out.getPixelWriter().setColor(x, y, f.apply(in.getPixelReader().getColor(x, y)));
                } else {
                    out.getPixelWriter().setColor(x, y, in.getPixelReader().getColor(x, y));
                }
            }
        }
        return out;
    }
    interface ColorTransformer {
        Color apply(Color color);
    }
}
