import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainApp {

    static class Colour {
        int a;
        int r;
        int g;
        int b;

        public Colour(int a, int r, int g, int b) {
            this.a = a;
            this.r = r;
            this.g = g;
            this.b = b;
        }

    }

    static BufferedImage output;
    static BufferedImage input;

    static {

        output = createEmptyImage();
        try {
            input = ImageIO.read(new File("C:\\Users\\DNS\\IdeaProjects\\ImageEvolvingGA\\src\\main\\resources\\sample1.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {



    }

    private static BufferedImage createEmptyImage() {
        return new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
    }



}