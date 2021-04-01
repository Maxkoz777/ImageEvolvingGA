import io.jenetics.Chromosome;
import io.jenetics.Genotype;
import io.jenetics.IntegerGene;
import model.Triangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

public class MainApp {

    private static final int RANK = 512;

    static BufferedImage output;
    static BufferedImage input;

    static {

        output = createEmptyImage();
        try {
            input = ImageIO.read(new File("C:\\Users\\DNS\\IdeaProjects\\ImageEvolvingGA\\src\\main\\resources\\sample1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int eval(Genotype<IntegerGene> genotype) throws IOException {

        BufferedImage image = drawPicture(genotype);

        int fitness;

        return 0;


    }

    private static BufferedImage drawPicture(Genotype<IntegerGene> genotype) throws IOException {

        Triangle[] triangles = getTrianglesFromGenotype(genotype);

        BufferedImage image = createEmptyImage();

        Graphics graphics = image.getGraphics();

        graphics.setColor(Color.white);

        graphics.fillRect(0, 0, RANK, RANK);

        for (Triangle triangle : triangles) {

            graphics.setColor(triangle.getColor());

            Polygon polygon = new Polygon(triangle.getArrayX(), triangle.getArrayY(), 3);

            graphics.drawPolygon(polygon);

            graphics.fillPolygon(polygon);

        }

        ImageIO.write(image, "png", new File("C:\\Users\\DNS\\IdeaProjects\\ImageEvolvingGA\\src\\main\\resources\\output.png"));

        graphics.dispose();




        return ImageIO.read(new File("C:\\Users\\DNS\\IdeaProjects\\ImageEvolvingGA\\src\\main\\resources\\output.png"));


    }

    private static Triangle[] getTrianglesFromGenotype(Genotype<IntegerGene> genotype) {

        Triangle[] triangles = new Triangle[genotype.length()];

        int index = 0;

        for (Chromosome<IntegerGene> chromosome : genotype) {

            ListIterator<IntegerGene> iterator = chromosome.listIterator();

            ArrayList<Integer> list = new ArrayList<>();

            while (iterator.hasNext()) {
                list.add(iterator.next().intValue());
            }

            Triangle triangle = new Triangle(new Triangle.Point(list.get(0), list.get(1)),
                    new Triangle.Point(list.get(2), list.get(3)),
                    new Triangle.Point(list.get(4), list.get(5)),
                    new Color(list.get(6), list.get(7), list.get(8), list.get(9))
            );

            triangles[index++] = triangle;

        }

        return triangles;

    }

    public static void main(String[] args) throws IOException {

        BufferedImage image = createEmptyImage();

        Graphics graphics = image.getGraphics();

        graphics.setColor(Color.WHITE);

        graphics.fillRect(0, 0, RANK, RANK);

        graphics.setColor(new Color(200, 200, 200, 200));

        int[] x = {30, 90, 60};
        int[] y = {30, 30, 90};

        Polygon polygon = new Polygon(x, y, x.length);

        graphics.drawPolygon(polygon);
        graphics.fillPolygon(polygon);

        graphics.setColor(new Color(250, 100, 100, 200));

        int[] x1 = {30, 90, 60};
        int[] y1 = {90, 90, 30};

        Polygon polygon1 = new Polygon(x1, y1, x1.length);

        graphics.drawPolygon(polygon1);
        graphics.fillPolygon(polygon1);

        ImageIO.write(image, "png", new File("C:\\Users\\DNS\\IdeaProjects\\ImageEvolvingGA\\src\\main\\resources\\output.png"));

        graphics.dispose();


    }

    private static BufferedImage createEmptyImage() {
        return new BufferedImage(RANK, RANK, BufferedImage.TYPE_INT_ARGB);
    }


}