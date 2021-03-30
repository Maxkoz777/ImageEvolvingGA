package model;

import io.jenetics.Genotype;
import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import io.jenetics.util.Factory;

import java.util.ArrayList;

public class ModelFactory {

    public static Factory<Genotype<IntegerGene>> of (int n) {

        ArrayList<IntegerChromosome> chromosomes = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            ArrayList<IntegerGene> genes = new ArrayList<>();

            for (int j = 0; j < 6; j++) {

                genes.add(IntegerGene.of(0, 512));

            }

            for (int j = 0; j < 4; j++) {

                genes.add(IntegerGene.of(0, 255));

            }

            chromosomes.add(IntegerChromosome.of(genes));

        }

        return Genotype.of(chromosomes);

    }

}
