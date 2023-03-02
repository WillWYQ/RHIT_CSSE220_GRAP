package mainApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Class: Generation
 * 
 * 
 * Purpose: The Generation class represents a single Generation in a genetic
 * algorithm. <br>
 * <br>
 * A Generation is composed of a series of Chromosome. <br>
 * Contains an array of individuals (Chromosomes) and methods for creating the
 * next generation.<br>
 * <br>
 * Restrictions: NONE <br>
 * For example:
 * 
 * <pre>
 * 
 * Generation generation = new Generation(ArrayList<Gene>);
 * 
 * <pre>
 * 
 * @author Yueqiao Wang and Rebecca Testa
 * @see Chromosome
 */

public class Generation {

	/**
	 * Ratio of individuals to be selected as parents for next generation.
	 * 1/PARENTS_RATIO
	 */
	private static final int PARENTS_RATIO = 2;

	/** Flag for crossover operation */
	private static final boolean CROSSOVER = true;
	/** Flag for mutation operation */
	private static final boolean MUTATION = false;

	/** Flag of parent selection: Truncation */
	private static final int TRUNCATION = 0;
	/** Flag of parent selection: Roulette Wheel */
	private static final int ROULETTE_WHEEL = 1;
	/** Flag of parent selection: Rank */
	private static final int RANK = 2;
	/** constant for parent selection in Baldwinian evolution */
	private static final int BALDWINIAN_PARENT_SELECTION = 3;

	/** Flag of Fitness Method: The number of ones in the ideal genotype */
	private static final int NUM_1S = 11;
	/** Flag of Fitness Method: The target number in the ideal genotype */
	private static final int TARGET = 22;
	/** Flag of Fitness Method:The symmetry in the ideal genotype */
	private static final int SYMMETRY = 33;
	/**
	 * flag for the Baldwinism fitness in fitness evaluation
	 */
	private static final int BALDWINISM_FITNESS = 44;

	/**
	 * flag for the BinaryValue fitness in fitness evaluation
	 */
	private static final int BINARY_VALUE = 55;

	/**
	 * The index of the best chromosome in this generation.
	 */
	private static final int BEST_CHROMOSOME_INDEX = 0;

	/** The number of genes per chromosome */
	private int numberOfGenesPerChromosome;
	/**
	 * The size of the population: How many individuals (Chromosomes) are in one
	 * generation.
	 */
	private int populationSize;

	/**
	 * the average number of correct alleles in the generation
	 */
	private int aveCorrectAllele = 0;
	/**
	 * the average number of incorrect alleles in the generation
	 */
	private int aveIncorrectAllele = 0;
	/**
	 * the average number of unknown alleles in the generation
	 */
	private int aveUnknownAllele = 0;

	/**
	 * Flags for correct allele types
	 */
	private static final int CORRECT_ALLELE_FLAG = 1;
	/**
	 * Flags for incorrect allele types
	 */
	private static final int INCORRECT_ALLELE_FLAG = 0;
	/**
	 * Flags for unknown allele types
	 */
	private static final int UNKNOWN_ALLELE_FLAG = 2;

	/**
	 * flag to confirm the calculation process is finished.
	 */
	private boolean allelesCalculated = false;

	/** The array of individuals (Chromosomes) in the generation */
	private Chromosome[] individuals;
	/** The list of parents for the next generation */
	private ArrayList<Chromosome> parentsForNextGeneration = new ArrayList<Chromosome>();

	/**
	 * Constructs a generation with the specified parameters.
	 *
	 * @param populationSize             the size of the population
	 * @param numberOfGenesPerChromosome the number of genes per chromosome
	 * @param fitnessMethod              the type of fitness method to use
	 * @param idealGenotype              the ideal genotype for the genetic
	 *                                   algorithm
	 */
	public Generation(int populationSize, int numberOfGenesPerChromosome, int fitnessMethod,
			ArrayList<Gene> idealGenotype) {
		this.populationSize = populationSize;
		this.numberOfGenesPerChromosome = numberOfGenesPerChromosome;
		this.individuals = new Chromosome[this.populationSize];
		for (int i = 0; i < populationSize; i++) {
			individuals[i] = new Chromosome(numberOfGenesPerChromosome, fitnessMethod, idealGenotype);
		}
		FileIO.writeGenerationFile(this.toString(), "ChromosomeTextFiles/OriginalGeneration");
	}

	/**
	 * Constructs a new generation from the specified list of parents.
	 *
	 * @param parents             the list of parents for the new generation
	 * @param crossoverOrMutation flag indicating if the operation is crossover or
	 *                            mutation
	 * @param mutationRate        the rate of mutation, if mutation is chosen
	 */
	public Generation(ArrayList<Chromosome> parents, boolean crossoverOrMutation, double mutationRate,
			double elitismPercentage) {

		this.populationSize = parents.size();
		this.numberOfGenesPerChromosome = parents.get(0).getGenesAsArrayList().size();

		int numToPreserve = this.calculateNumberOfChromosomesToPreserveFromElitismPercentage(elitismPercentage);

		if (crossoverOrMutation == MUTATION) {
			this.individuals = this.createIndividualsFromParentsUsingMutation(parents, mutationRate, numToPreserve);
		} else if (crossoverOrMutation == CROSSOVER) {
			this.individuals = this.createIndividualsFromParentsUsingCrossover(parents, mutationRate, numToPreserve);
		}
		this.sortChromosomesByFitnessScore();
	}

	/**
	 * Sets the size of the population.
	 *
	 * @param populationSize the size of the population
	 */
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	/**
	 * Sets the number of genes per chromosome.
	 *
	 * @param numGenes the number of genes per chromosome
	 */
	public void setNumberOfGenesPerChromosome(int numGenes) {
		this.numberOfGenesPerChromosome = numGenes;
	}

	/**
	 * Chooses the next set of parents for generating the next Generation, based on
	 * the parentSelectionType provided as an argument.
	 * 
	 * @param parentSelectionType an int, which indicates the flag of method of
	 *                            selecting parents
	 *                            <p>
	 *                            0 = TRUNCATION, 1 = ROULETTE_WHEEL, 2 = RANK
	 * @return ArrayList of Chromosome objects, representing the next set of parents
	 */

	public ArrayList<Chromosome> chooseNextParents(int parentSelectionType, double elitismPercentage) {

		int numberOfChromosomesToPreserveAsInt = this
				.calculateNumberOfChromosomesToPreserveFromElitismPercentage(elitismPercentage);

		if (parentSelectionType == TRUNCATION) {
			return this.chooseNextParentsWithTruncation(numberOfChromosomesToPreserveAsInt);
		} else if (parentSelectionType == ROULETTE_WHEEL) {
			return this.chooseNextParentsWithRouletteWheel(numberOfChromosomesToPreserveAsInt);
		} else if (parentSelectionType == RANK) {
			return this.chooseParentsWithRanked(numberOfChromosomesToPreserveAsInt);
		} else if (parentSelectionType == BALDWINIAN_PARENT_SELECTION) {
			return this.chooseParentsWithBaldwinian(numberOfChromosomesToPreserveAsInt);
		} else {
			System.err.println("Invalid parent selection type chosen!! No parents have been generated.");
			return null;
		}
	}

	/**
	 * Creates a new set of individuals by mutating the parents.
	 * 
	 * @param parents      ArrayList of Chromosome objects, representing the parents
	 * @param mutationRate a double, representing the mutation rate
	 * @return Chromosome[] array, representing the new set of individuals created
	 *         from the parents
	 */
	private Chromosome[] createIndividualsFromParentsUsingMutation(ArrayList<Chromosome> parents, double mutationRate,
			int numberOfChromosomesToPreserve) {

		Chromosome[] newIndividuals = new Chromosome[this.populationSize];

		for (int i = 0; i < this.populationSize; i++) {
			Chromosome newChromosome = parents.get(i).makeDeepCopy();
			newIndividuals[i] = newChromosome;
		}

		Arrays.sort(newIndividuals);

		for (int i = numberOfChromosomesToPreserve; i < this.populationSize; i++) {
			newIndividuals[i].mutate(mutationRate);
		}

		Arrays.sort(newIndividuals);

		System.out.println("Individuals: ");
		for (int i = 0; i < newIndividuals.length; i++) {
			System.out.print(newIndividuals[i].reportFitness() + " ");
		}
		System.out.println("\n");
		return newIndividuals;
	}

	/**
	 * Creates a new set of individuals by performing crossover on the parents.
	 * 
	 * @param parents ArrayList of Chromosome objects, representing the parents
	 * @return Chromosome[] array, representing the new set of individuals created
	 *         from the parents
	 */
	private Chromosome[] createIndividualsFromParentsUsingCrossover(ArrayList<Chromosome> parents, double mutationRate,
			int numberOfChromosomesToPreserve) {

		Chromosome[] newIndividuals = new Chromosome[this.populationSize];

		for (int i = 0; i < this.populationSize; i++) {
			Chromosome newChromosome = parents.get(i).makeDeepCopy();
			newIndividuals[i] = newChromosome;
		}

		Arrays.sort(newIndividuals);

		Random randomNumberGenerator = new Random();
		int randomIndex1 = numberOfChromosomesToPreserve
				+ randomNumberGenerator.nextInt(this.populationSize - numberOfChromosomesToPreserve - 1);
		int randomIndex2 = numberOfChromosomesToPreserve
				+ randomNumberGenerator.nextInt(this.populationSize - numberOfChromosomesToPreserve - 1);

		if (this.populationSize - numberOfChromosomesToPreserve >= 2) {
			Chromosome[] crossoverChildren = this.makeAChildUsingCrossover(newIndividuals[randomIndex1],
					newIndividuals[randomIndex2]);
			newIndividuals[randomIndex1] = crossoverChildren[0];
			newIndividuals[randomIndex2] = crossoverChildren[1];
		}

		for (int i = numberOfChromosomesToPreserve; i < this.populationSize; i++) {
			newIndividuals[i].mutate(mutationRate);
		}

		Arrays.sort(newIndividuals);
		return newIndividuals;

	}

	/**
	 * Makes a child chromosome using a crossover operation between two parent
	 * chromosomes.
	 *
	 * The method performs a single-point crossover operation between two parent
	 * chromosomes by selecting a random crossover point and swapping the genes
	 * between the parent chromosomes. The resulting children chromosomes are stored
	 * in an array and returned. The parent and child chromosomes are also saved to
	 * text files for further analysis.
	 *
	 * @param chromosomeA one of the parent chromosomes
	 * @param chromosomeB the other parent chromosome
	 * @return an array of two child chromosomes
	 */
	public Chromosome[] makeAChildUsingCrossover(Chromosome chromosomeA, Chromosome chromosomeB) {

		Random randomNumberGenerator = new Random();
		int crossoverPoint = randomNumberGenerator.nextInt(this.numberOfGenesPerChromosome);

		ArrayList<Gene> parentAGenes = chromosomeA.getGenesAsArrayList();
		ArrayList<Gene> parentBGenes = chromosomeB.getGenesAsArrayList();
		ArrayList<Gene> genesForChild1 = new ArrayList<Gene>();
		ArrayList<Gene> genesForChild2 = new ArrayList<Gene>();

		for (int i = 0; i < crossoverPoint; i++) {
			genesForChild1.add(parentAGenes.get(i));
			genesForChild2.add(parentBGenes.get(i));
		}

		for (int i = crossoverPoint; i < this.numberOfGenesPerChromosome; i++) {
			genesForChild1.add(parentBGenes.get(i));
			genesForChild2.add(parentAGenes.get(i));
		}

		Chromosome templateForChild1 = new Chromosome(genesForChild1, chromosomeA.getFitnessMethod(),
				chromosomeA.getIdealGenotype());
		Chromosome child1 = templateForChild1.makeDeepCopy();

		Chromosome templateForChild2 = new Chromosome(genesForChild2, chromosomeA.getFitnessMethod(),
				chromosomeA.getIdealGenotype());
		Chromosome child2 = templateForChild2.makeDeepCopy();

		Chromosome[] crossoverChildren = new Chromosome[PARENTS_RATIO];
		crossoverChildren[0] = child1;
		crossoverChildren[1] = child2;

		return crossoverChildren;

	}

	/**
	 * ensure: Sorts the individuals in the current generation based on their
	 * fitness scores. The individuals with higher fitness scores will be sorted to
	 * the front of the array. After sorting, the updated individuals list is
	 * written to a file.
	 * 
	 * @return void
	 */
	public void sortChromosomesByFitnessScore() {
		Arrays.sort(this.individuals);
		FileIO.writeGenerationFile(this.toString(), "ChromosomeTextFiles/SortedGeneration");
	}

	/**
	 * 
	 * The {@code chooseNextParentsWithTruncation} method is used to select the next
	 * parents for the generation. The method sorts the individuals in the
	 * population by their fitness scores and uses truncation selection to choose
	 * the next parents.
	 * 
	 * @param numberOfChromosomesToPreserve the number of chromosomes to preserve in
	 *                                      the population
	 * @return an ArrayList of Chromosome objects representing the next parents for
	 *         the generation
	 */
	private ArrayList<Chromosome> chooseNextParentsWithTruncation(int numberOfChromosomesToPreserve) {
		this.sortChromosomesByFitnessScore();

		Chromosome[] temporaryArray = new Chromosome[this.populationSize];

		if (numberOfChromosomesToPreserve > populationSize / PARENTS_RATIO) {
			for (int i = 0; i < numberOfChromosomesToPreserve; i++) {
				temporaryArray[i] = individuals[i];
			}

			for (int i = 0; i < this.populationSize - numberOfChromosomesToPreserve; i++) {
				temporaryArray[i + numberOfChromosomesToPreserve] = individuals[i];
			}

			Arrays.sort(temporaryArray);

			for (int i = 0; i < this.populationSize; i++) {
				this.parentsForNextGeneration.add(temporaryArray[i]);
			}
		} else {

			for (int i = 0; i < individuals.length / PARENTS_RATIO; i++) {
				for (int j = 0; j < PARENTS_RATIO; j++) {
					this.parentsForNextGeneration.add(individuals[i]);
				}
			}
		}

		return this.parentsForNextGeneration;
	}

	/**
	 *
	 * chooseNextParentsWithRouletteWheel is a method that implements a roulette
	 * wheel selection strategy for choosing the next set of parents in the genetic
	 * algorithm.
	 * 
	 * @param numberOfChromosomesToPreserve the number of chromosomes to preserve
	 *                                      from the current generation to the next
	 *                                      generation.
	 * @return the ArrayList of Chromosomes that will serve as the parents for the
	 *         next generation.
	 */
	private ArrayList<Chromosome> chooseNextParentsWithRouletteWheel(int numberOfChromosomesToPreserve) {
		this.sortChromosomesByFitnessScore();

		for (int i = 0; i < numberOfChromosomesToPreserve; i++) {
			this.parentsForNextGeneration.add(individuals[i]);
		}

		ArrayList<Chromosome> probabilityBasedParentPool = new ArrayList<Chromosome>();

		for (int i = 0; i < this.populationSize; i++) {
			for (int j = 0; j < this.individuals[i].reportFitness(); j++) {
				probabilityBasedParentPool.add(individuals[i]);
			}
		}

		for (int i = numberOfChromosomesToPreserve; i < this.populationSize; i++) {
			Random randomNumberGenerator = new Random();
			int randomNumber = randomNumberGenerator.nextInt(probabilityBasedParentPool.size());
			this.parentsForNextGeneration.add(probabilityBasedParentPool.get(randomNumber));

		}

		return this.parentsForNextGeneration;
	}

	/**
	 * 
	 * The chooseParentsWithRanked method is used to select parents for the next
	 * generation of chromosomes in a genetic algorithm. This method uses the ranked
	 * parent selection method to choose the next parents.
	 * 
	 * @param numberOfChromosomesToPreserve The number of top-performing chromosomes
	 *                                      that should be preserved in the next
	 *                                      generation.
	 * @return An ArrayList of Chromosome objects that have been selected as the
	 *         parents for the next generation.
	 */
	private ArrayList<Chromosome> chooseParentsWithRanked(int numberOfChromosomesToPreserve) {
		this.sortChromosomesByFitnessScore();

		for (int i = 0; i < numberOfChromosomesToPreserve; i++) {
			this.parentsForNextGeneration.add(individuals[i]);
		}

		ArrayList<Chromosome> rankBasedParentPool = new ArrayList<Chromosome>();
		int decliningScoreValue = individuals.length;

		for (int i = 0; i < this.individuals.length; i++) {
			for (int j = 0; j < decliningScoreValue; j++) {
				rankBasedParentPool.add(individuals[i]);
			}
			decliningScoreValue -= 1;
		}

		for (int i = numberOfChromosomesToPreserve; i < this.populationSize; i++) {
			Random randomNumberGenerator = new Random();
			int randomNumber = randomNumberGenerator.nextInt(rankBasedParentPool.size());
			this.parentsForNextGeneration.add(rankBasedParentPool.get(randomNumber));
		}

		return this.parentsForNextGeneration;
	}

	private ArrayList<Chromosome> chooseParentsWithBaldwinian(int numberOfChromosomesToPreserve) {

		for (int i = 0; i < numberOfChromosomesToPreserve; i++) {
			this.parentsForNextGeneration.add(individuals[i]);
		}

		ArrayList<Chromosome> probabilityBasedParentPool = new ArrayList<Chromosome>();

		for (int i = 0; i < this.populationSize; i++) {
			for (int j = 0; j < this.individuals[i].reportFitness(); j++) {
				probabilityBasedParentPool.add(individuals[i]);
			}
		}

		for (int i = numberOfChromosomesToPreserve; i < this.populationSize; i++) {

			Random randomNumberGenerator = new Random();
			int randomIndex1 = randomNumberGenerator.nextInt(probabilityBasedParentPool.size());
			int randomIndex2 = randomNumberGenerator.nextInt(probabilityBasedParentPool.size());

			Chromosome parent1 = probabilityBasedParentPool.get(randomIndex1);
			Chromosome parent2 = probabilityBasedParentPool.get(randomIndex2);

			Chromosome[] crossoverChildren = this.makeAChildUsingCrossover(parent1, parent2);
			Chromosome selectedChild = crossoverChildren[0];

			this.parentsForNextGeneration.add(selectedChild);

		}
		this.calculateAverageAlleles();
		return this.parentsForNextGeneration;
	}

	/**
	 * 
	 * This method returns the fitness score of the best chromosome in the
	 * population. It sorts the chromosomes in the population based on their fitness
	 * score and returns the fitness score of the first chromosome, which is the
	 * chromosome with the highest fitness score.
	 * 
	 * @return int - the fitness score of the best chromosome in the population. If
	 *         there are no chromosomes in the population, it returns 0.
	 */
	public int returnBestChromosomeFitnessScore() {
		this.sortChromosomesByFitnessScore();
		if (this.individuals.length > 0) {
			return this.individuals[0].reportFitness();
		} else {
			return 0;
		}
	}

	/**
	 * Returns the fitness score of the worst chromosome in the population.
	 *
	 * @return the fitness score of the worst chromosome in the population
	 */
	public int returnWorstChromosomeFitnessScore() {
		this.sortChromosomesByFitnessScore();
		if (this.individuals.length > 0) {
			return this.individuals[individuals.length - 1].reportFitness();
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * Returns the average fitness score of the chromosomes in the population.
	 * 
	 * @return The average fitness score of the chromosomes in the population.
	 */
	public int returnAveChromosomeFitnessScore() {
		int totalScore = 0;
		for (int i = 0; i < individuals.length; i++) {
			totalScore += individuals[i].reportFitness();
		}
		return totalScore / individuals.length;
	}

	/**
	 * 
	 * Returns the Hamming distance of the population.
	 * 
	 * @return the Hamming distance of the population.
	 */
	public int returnHammingDistance() {

		int startIndex = 0;
		int totalHammingDistance = 0;
		int numberOfComparisons = 0;

		for (int i = 0; i < this.populationSize; i++) {
			for (int j = startIndex; j < this.populationSize; j++) {
				if (i != j) {
					totalHammingDistance += computeHammingDistanceForTwoChromosomes(this.individuals[i],
							this.individuals[j]);
					startIndex += 1;
					numberOfComparisons += 1;
				}
			}
		}

		totalHammingDistance = totalHammingDistance / numberOfComparisons;
		return totalHammingDistance;
	}

	/**
	 * ensureComputes the Hamming distance between two chromosomes.<br>
	 * The Hamming distance is the number of differing positions between two strings
	 * of equal length. In this context, each chromosome is considered as a string
	 * of genes, and the Hamming distance is the number of differing genes between
	 * two chromosomes.
	 * 
	 * @param chromosomeA the first chromosome
	 * @param chromosomeB the second chromosome
	 * @return the Hamming distance between the two chromosomes
	 */
	public int computeHammingDistanceForTwoChromosomes(Chromosome chromosomeA, Chromosome chromosomeB) {

		ArrayList<Gene> chromosomeAGenes = chromosomeA.getGenesAsArrayList();
		ArrayList<Gene> chromosomeBGenes = chromosomeB.getGenesAsArrayList();

		int hammingDistance = 0;

		for (int i = 0; i < chromosomeAGenes.size(); i++) {
			if (chromosomeAGenes.get(i).getCurrentValue() != chromosomeBGenes.get(i).getCurrentValue()) {
				hammingDistance += 1;
			}
		}

		return hammingDistance;
	}

	/**
	 * 
	 * Returns the number of unique individuals in the population.
	 * 
	 * @return int - The number of unique individuals in the population.
	 */
	public int returnNumUniqueIndividuals() {

		ArrayList<Chromosome> uniqueIndividualsList = new ArrayList<Chromosome>();

		for (Chromosome newChromosome : this.individuals) {
			boolean alreadyInList = false;

			if (uniqueIndividualsList.size() > 0) {
				for (Chromosome addedChromosome : uniqueIndividualsList) {
					if (this.computeHammingDistanceForTwoChromosomes(addedChromosome, newChromosome) == 0) {
						alreadyInList = true;
					}
				}
			}

			if (alreadyInList == false) {
				uniqueIndividualsList.add(newChromosome);
			}
		}
		System.out.println("Number of unique individuals: " + uniqueIndividualsList.size());
		return uniqueIndividualsList.size();
	}

	public int returnAverageCorrectAlleles() {
		if (this.allelesCalculated) {
			return this.aveCorrectAllele;
		} else {
			this.calculateAverageAlleles();
			return this.aveCorrectAllele;
		}
	}

	public int returnAverageIncorrectAlleles() {
		if (this.allelesCalculated) {
			return this.aveIncorrectAllele;
		} else {
			this.calculateAverageAlleles();
			return this.aveIncorrectAllele;
		}
	}

	public int returnAverageUnknownAlleles() {
		if (this.allelesCalculated) {
			return this.aveUnknownAllele;
		} else {
			this.calculateAverageAlleles();
			return this.aveUnknownAllele;
		}
	}

	public void calculateAverageAlleles() {
		int correctAllele = 0;
		int incorrectAllele = 0;
		int unknownAllele = 0;

		for (int i = 0; i < populationSize; i++) {

			correctAllele += this.individuals[i].returnCorrectAlleles();
			incorrectAllele += this.individuals[i].returnIncorrectAlleles();
			unknownAllele += this.individuals[i].returnUnknownAlleles();
		}
		this.aveCorrectAllele = correctAllele / populationSize;
		this.aveIncorrectAllele = incorrectAllele / populationSize;
		this.aveUnknownAllele = unknownAllele / populationSize;
		this.allelesCalculated = true;
	}

	/**
	 * 
	 * Calculates the number of chromosomes to preserve in the population based on
	 * the given elitism percentage.
	 * 
	 * @param elitismPercentage a value between 0 and 1 representing the percentage
	 *                          of the best individuals to preserve
	 * @return the number of chromosomes to preserve
	 */
	public int calculateNumberOfChromosomesToPreserveFromElitismPercentage(double elitismPercentage) {
		Integer numberOfIndividuals = this.populationSize;
		Double numberOfChromosomesToPreserveAsDouble = elitismPercentage * numberOfIndividuals.doubleValue();
		Integer numberOfChromosomesToPreserveAsInt = numberOfChromosomesToPreserveAsDouble.intValue();

		while (numberOfChromosomesToPreserveAsInt > this.numberOfGenesPerChromosome) {
			numberOfChromosomesToPreserveAsInt -= 1;
		}

		return numberOfChromosomesToPreserveAsInt;
	}

	/**
	 * return the Best Chromosome in this generation
	 * 
	 * @return Chromosome - the Best Chromosome in this generation
	 */
	public Chromosome getBestChromosome() {
		return this.individuals[BEST_CHROMOSOME_INDEX];
	}

	/**
	 * return all Chromosome in this generation
	 * 
	 * @return Chromosome[] - all Chromosome in this generation in list
	 */
	public Chromosome[] getAllChromosome() {
		return this.individuals;
	}

	@Override
	public String toString() {
		String generationAsString = "";
		generationAsString += "Beginning of generation";
		for (int i = 0; i < individuals.length; i++) {
			generationAsString += individuals[i].toString() + "\n";
		}
		generationAsString += "End of generation";
		return generationAsString;
	}
}
