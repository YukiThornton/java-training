package java8.ch03.ex12;

import static org.junit.Assert.*;
import static java8.ch03.ex12.LatentImage.addFrame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class LatentImageTest {

    @Test
    public void testTransform1() throws IOException {
        FileInputStream inputStream = new FileInputStream("test/java8/ch03/ex05/test_photo1.jpeg");
        Image img = new Image(inputStream);
        Image brightenedImage = LatentImage.from(img).transform(Color::brighter).transform(addFrame(Color.RED, 30, (int)img.getWidth(), (int)img.getHeight())).toImage();
        BufferedImage buffer = SwingFXUtils.fromFXImage(brightenedImage, null);
        ImageIO.write(buffer, "jpeg", new File("test/java8/ch03/ex12/result_photo1.jpeg"));
    }

}
