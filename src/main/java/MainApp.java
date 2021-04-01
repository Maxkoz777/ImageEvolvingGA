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

    static BufferedImage input;
    static Color[][] pixels;

    static {
        try {
            input = ImageIO.read(new File("C:\\Users\\DNS\\IdeaProjects\\ImageEvolvingGA\\src\\main\\resources\\sample1.png"));

            pixels = getPixelsFromImage(input);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Color[][] getPixelsFromImage(BufferedImage image) {

        Color[][] colors = new Color[RANK][RANK];

        for (int y = 0; y < RANK; y++) {

            for (int x = 0; x < RANK; x++) {

                int p = image.getRGB(x, y);

                colors[x][y] = new Color(p, true);

            }

        }

        return colors;

    }

    private static int eval(Genotype<IntegerGene> genotype) throws IOException {

        BufferedImage image = drawPicture(genotype);

        return difference(getPixelsFromImage(image));

    }

    private static int difference(Color[][] matrix) {

        int fitness = 0;

        for (int y = 0; y < RANK; y++) {

            for (int x = 0; x < RANK; x++) {

                fitness += Math.abs(pixels[x][y].getBlue() - matrix[x][y].getBlue());
                fitness += Math.abs(pixels[x][y].getRed() - matrix[x][y].getRed());
                fitness += Math.abs(pixels[x][y].getGreen() - matrix[x][y].getGreen());
                fitness += Math.abs(pixels[x][y].getAlpha() - matrix[x][y].getAlpha());

            }

        }

        return fitness;

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



    }

    private static BufferedImage createEmptyImage() {
        return new BufferedImage(RANK, RANK, BufferedImage.TYPE_INT_ARGB);
    }


}