package java8.ch03.ex08;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PhotoEditor {
    public static Image transform(Image in, ColorTransformer f) {
        int width = (int)in.getWidth();
        int height = (int)in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(x, y, f.apply(x, y, in.getPixelReader().getColor(x, y)));
            }
        }
        return out;
    }
    public static ColorTransformer addFrame(Color color, int size, int imageWidth, int imageHeight) {
        return (x, y, col) -> {
            if (x <= size || y <= size || x >= imageWidth - size || y >= imageHeight - size) {
                return color;
            } else {
                return col;
            }
        };
    }
    @FunctionalInterface 
    interface ColorTransformer {
        Color apply(int x, int y, Color colorAtXY);
    }
}
