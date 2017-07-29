package java8.ch03.ex08;

import static org.junit.Assert.*;
import static java8.ch03.ex08.PhotoEditor.transform;
import static java8.ch03.ex08.PhotoEditor.addFrame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PhotoEditorTest {

    @Test
    public void testTransform1() throws IOException {
        FileInputStream inputStream = new FileInputStream("test/java8/ch03/ex05/test_photo1.jpeg");
        Image image = new Image(inputStream);
        Image brightenedImage = PhotoEditor.transform(image, addFrame(Color.RED, 30, (int)image.getWidth(), (int)image.getHeight()));
        BufferedImage buffer = SwingFXUtils.fromFXImage(brightenedImage, null);
        ImageIO.write(buffer, "jpeg", new File("test/java8/ch03/ex08/result_photo1.jpeg"));
    }

}
