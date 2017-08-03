package java8.ch03.ex06;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import static java8.ch03.ex06.ImageTool.transform;

import org.junit.Test;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImageToolTest {

    @Test
    public void testTransform1() throws IOException {
        FileInputStream inputStream = new FileInputStream("test/java8/ch03/ex05/test_photo1.jpeg");
        Image image = new Image(inputStream);
        Image brightenedImage = transform(image, (c, factor) -> c.deriveColor(1, 1, factor, 1), 1.2);
        BufferedImage buffer = SwingFXUtils.fromFXImage(brightenedImage, null);
        ImageIO.write(buffer, "jpeg", new File("test/java8/ch03/ex06/result_photo1.jpeg"));
    }

}
