package java8.ch03.ex05;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import java8.ch03.ex05.PhotoEditor.ColorTransformer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PhotoEditorTest {
    @Test
    public void testTransform1() throws IOException {
        FileInputStream inputStream = new FileInputStream("test/java8/ch03/ex05/test_photo1.jpeg");
        Image image = new Image(inputStream);
        Image brightenedImage = PhotoEditor.transform(image, (x, y, color) -> {
            if (x <= 10 || y <= 10 || x >= image.getWidth() - 10 || y >= image.getHeight() - 10) {
                return Color.GRAY;
            } else {
                return color;
            }
        });
        BufferedImage buffer = SwingFXUtils.fromFXImage(brightenedImage, null);
        ImageIO.write(buffer, "jpeg", new File("test/java8/ch03/ex05/result_photo1.jpeg"));
    }

}
