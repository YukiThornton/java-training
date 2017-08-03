package java8.ch03.ex12;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class LatentImage {

    private Image in;
    private List<ColorTransformer> pendingOperation;

    private LatentImage(Image in) {
        this.in = in;
        pendingOperation = new ArrayList<>();
    }
    
    public static LatentImage from(Image in) {
        LatentImage latentImage = new LatentImage(in);
        return latentImage;
    }
    public LatentImage transform(ColorTransformer f) {
        pendingOperation.add(f);
        return this;
    }
    
    public LatentImage transform(UnaryOperator<Color> f) {
        pendingOperation.add(toColorTransformer(f));
        return this;
    }
    
    public Image toImage() {
        int width = (int)in.getWidth();
        int height = (int)in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = in.getPixelReader().getColor(x, y);
                for (ColorTransformer f: pendingOperation) {
                    c = f.apply(x, y, c);
                }
                out.getPixelWriter().setColor(x, y, c);
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
    private static ColorTransformer toColorTransformer(UnaryOperator<Color> operator) {
        return (x, y, col) -> {
            return operator.apply(col);
        };
    }
    @FunctionalInterface 
    interface ColorTransformer {
        Color apply(int x, int y, Color colorAtXY);
    }
}
