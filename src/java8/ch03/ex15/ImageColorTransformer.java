package java8.ch03.ex15;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

public class ImageColorTransformer {

    private List<UnaryOperator<Color>> pendingOperations;
    private Color[][] in;

    public static ImageColorTransformer from(Color[][] in) {
        return new ImageColorTransformer(in);
    }

    private ImageColorTransformer(Color[][] in) {
        this.in = in;
        this.pendingOperations = new ArrayList<>();
    }

    public ImageColorTransformer transform(UnaryOperator<Color> f) {
        pendingOperations.add(f);
        return this;
    }

    public Color[][] toColors() {
        int n = Runtime.getRuntime().availableProcessors();
        int height = in.length;
        int width = in[0].length;
        Color[][] out = new Color[height][width];
        try {
            ExecutorService pool = Executors.newCachedThreadPool();
            for (int i = 0; i < n; i++) {
                int fromY = i * height / n;
                int toY = (i + 1) * height / n;
                pool.submit(() -> {
                    for (int x = 0; x < width; x++) {
                        for (int y = fromY; y < toY; y++) {
                            Color c = in[x][y];
                            for (UnaryOperator<Color> operator: pendingOperations) {
                                c = operator.apply(c);
                            }
                            out[y][x] = c;
                        }
                    }
                });
            }
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return out;
    }

}
