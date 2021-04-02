import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Limits;
import io.jenetics.util.Factory;
import model.ModelFactory;
import model.Triangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

public class MainApp {

    private static final int RANK = 512;

    static BufferedImage input;
    static Color[][] pixels;

    static {
        try {
            input = ImageIO.read(new File("C:\\Users\\DNS\\IdeaProjects\\ImageEvolvingGA\\src\\main\\resources\\sample2.png"));

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

    private static synchronized int eval(Genotype<IntegerGene> genotype) {

        try {
            BufferedImage image = drawPicture(genotype);

            return difference(getPixelsFromImage(image));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Integer.MAX_VALUE;

    }

    private static int difference(Color[][] matrix) {

        int fitness = 0;

        for (int y = 0; y < RANK; y++) {

            for (int x = 0; x < RANK; x++) {

                fitness += Math.abs(pixels[x][y].getBlue() - matrix[x][y].getBlue());
                fitness += Math.abs(pixels[x][y].getRed() - matrix[x][y].getRed());
                fitness += Math.abs(pixels[x][y].getGreen() - matrix[x][y].getGreen());
                //fitness += Math.abs(pixels[x][y].getAlpha() - matrix[x][y].getAlpha());

            }

        }

        return fitness;

    }


    private static BufferedImage drawPicture(Genotype<IntegerGene> genotype) throws IOException {

        // retrieves triangles from genotype
        // Triangle.class contains 3 Point.class instances(int x, int y) and 1 Color.class
        // for keeping rgba values of each triangle

        Triangle[] triangles = getTrianglesFromGenotype(genotype);

        BufferedImage image = new BufferedImage(RANK, RANK, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = image.createGraphics();

        // simply fill background with white, where RANK is static final int constant = 512

        graphics.setColor(Color.white);

        graphics.fillRect(0, 0, RANK, RANK);

        // for each triangle we get its color and arrays
        // of x-coordinates and y-coordinates to create a Polygon.class instance
        // we draw that polygon and fill it with triangle color

        for (Triangle triangle : triangles) {

            graphics.setColor(triangle.getColor());

            Polygon polygon = new Polygon(triangle.getArrayX(), triangle.getArrayY(), 3);

            graphics.drawPolygon(polygon);

            graphics.fillPolygon(polygon);

        }

        // here I save results to png image

        ImageIO.write(image, "png", new File("C:\\Users\\DNS\\IdeaProjects\\ImageEvolvingGA\\src\\main\\resources\\output.png"));

        graphics.dispose();

        return image;

//        return ImageIO.read(new File("C:\\Users\\DNS\\IdeaProjects\\ImageEvolvingGA\\src\\main\\resources\\output.png"));


    }

    private static Triangle[] getTrianglesFromGenotype(Genotype<IntegerGene> genotype) {

        Triangle[] triangles = new Triangle[genotype.length() / 2];

        int index = 0;

        boolean coordinates = true;

        Triangle triangle = null;

        for (Chromosome<IntegerGene> chromosome : genotype) {

            ListIterator<IntegerGene> iterator = chromosome.listIterator();

            ArrayList<Integer> list = new ArrayList<>();

            while (iterator.hasNext()) {
                list.add(iterator.next().intValue());
            }

            if (coordinates) {

                triangle = new Triangle(new Triangle.Point(list.get(0), list.get(1)),
                        new Triangle.Point(list.get(2), list.get(3)),
                        new Triangle.Point(list.get(4), list.get(5))
                );

                coordinates = false;

            }

            else {

                coordinates = true;

                triangle.setColor(new Color(list.get(0), list.get(1), list.get(2), list.get(3)));

                triangles[index++] = triangle;
            }



        }

        return triangles;

    }

    public static void main(String[] args) throws IOException {

//        drawPicture(ModelFactory.of(512).newInstance());
//
//        System.out.println(ImageIO.read(new File("C:\\Users\\DNS\\IdeaProjects\\ImageEvolvingGA\\src\\main\\resources\\output.png")));
//
//        System.out.println(ImageIO.read(new File("C:\\Users\\DNS\\IdeaProjects\\ImageEvolvingGA\\src\\main\\resources\\sample1.png")));

        Factory<Genotype<IntegerGene>> factory = ModelFactory.of(50);

        Engine<IntegerGene, Integer> engine = Engine.builder(MainApp::eval, factory)
                .populationSize(50)
                .optimize(Optimize.MINIMUM)
                .build();

        final EvolutionStatistics<Integer, ?> statistics = EvolutionStatistics.ofNumber();

        final Phenotype<IntegerGene, Integer> result = engine.stream()
                .limit(Limits.byExecutionTime(Duration.ofSeconds(2000)))
                .peek(statistics)
                .collect(EvolutionResult.toBestPhenotype());

        System.out.println(statistics);

    }

    private static BufferedImage createEmptyImage() {
        return new BufferedImage(RANK, RANK, BufferedImage.TYPE_INT_ARGB);
    }


}