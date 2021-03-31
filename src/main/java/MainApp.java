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
            input = ImageIO.read(new File("C:\\Users\\DNS\\IdeaProjects\\ImageEvolvingGA\\src\\main\\resources\\sample1.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int eval(Genotype<IntegerGene> genotype) {

        BufferedImage image = drawPicture(genotype);

        int fitness;


    }

    private static BufferedImage drawPicture(Genotype<IntegerGene> genotype) {

        Triangle[] triangles = getTrianglesFromGenotype(genotype);

        BufferedImage image = createEmptyImage();

        Graphics graphics = image.getGraphics();

        graphics.clearRect(0, 0, RANK, RANK);

        for (Triangle triangle : triangles) {

            graphics.setColor(triangle.getColor());
            graphics.drawPolygon(triangle.getArrayX(), triangle.getArrayY(), 3);

        }

        graphics.dispose();

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

    public static void main(String[] args) {


    }

    private static BufferedImage createEmptyImage() {
        return new BufferedImage(RANK, RANK, BufferedImage.TYPE_INT_ARGB);
    }


}