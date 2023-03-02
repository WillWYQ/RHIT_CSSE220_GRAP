package mainApp;

import java.util.ArrayList;

public class TestCrossoverMethod {

	public static void main(String[] args) {

		ArrayList<Gene> idealGenotype = new ArrayList<Gene>();
		final int FITNESS_METHOD = 1;
		final int POPULATION_SIZE = 2;
		final int NUMBER_OF_GENES_PER_CHROMOSOME = 100;

		Generation generation = new Generation(POPULATION_SIZE, NUMBER_OF_GENES_PER_CHROMOSOME, FITNESS_METHOD,
				idealGenotype);

		ArrayList<Gene> parentAGenes = null;
		ArrayList<Gene> parentBGenes = null;

		try {
			parentAGenes = FileIO.readFile("ChromosomeTextFiles/ParentA");
		} catch (InvalidChromosomeFormatException e) {

			e.printStackTrace();
		}

		try {
			parentBGenes = FileIO.readFile("ChromosomeTextFiles/ParentB");
		} catch (InvalidChromosomeFormatException e) {
			
			e.printStackTrace();
		}

		Chromosome parentA = new Chromosome(parentAGenes, FITNESS_METHOD, idealGenotype);
		Chromosome parentB = new Chromosome(parentBGenes, FITNESS_METHOD, idealGenotype);

		generation.makeAChildUsingCrossover(parentA, parentB);
		System.out.println(generation.computeHammingDistanceForTwoChromosomes(parentA, parentB));

	}

}
