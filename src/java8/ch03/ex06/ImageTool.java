package java8.ch03.ex06;

import java.util.function.BiFunction;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageTool {

    public static <T> Image transform(Image in, BiFunction<Color, T, Color> f, T arg) {
        int width = (int)in.getWidth();
        int height = (int)in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color newColor = f.apply(in.getPixelReader().getColor(x, y), arg);
                out.getPixelWriter().setColor(x, y, newColor);
            }
        }
        return out;
    }

}
