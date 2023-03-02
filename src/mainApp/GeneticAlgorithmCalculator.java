package mainApp;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * Class:GeneticAlgorithmCalculator<br>
 * This class represents a Genetic Algorithm Calculator.
 * 
 * Purpose:It performs the genetic algorithm calculations, such as selecting
 * parents, creating new generations, and evaluating fitness scores.
 * 
 * Restrictions: <br>
 * The parent selection type must be one of the three defined constants
 * "Truncation", "Roulette Wheel", and "Rank Selection". <br>
 * The fitness method must be one of the three defined constants "Number of 1",
 * "Target Fitness", and "Symmetry".<br>
 * The elitism percentage must be a value between 0 and 100. <br>
 * The number of generations, population size, and number of genes per
 * chromosome must be positive integers.<br>
 * The mutation rate must be a positive integer.<br>
 * 
 * For Example:
 * 
 * <pre>
 * GeneticAlgorithmCalculator calculator = new GeneticAlgorithmCalculator();
 * 
 * <pre>
 * 
 * @see EvolutionAnalysisViewer
 * @see Generation
 * @author Yueqiao Wang and Rebecca Testa
 */

public class GeneticAlgorithmCalculator {

	/**
	 * An ArrayList containing all generations of the genetic algorithm.
	 */
	private ArrayList<Generation> allGenerationsList;
	/**
	 * A PopulationViewer object that displays the population of the current run.
	 */
	PopulationViewer populationViewerOfCurrentRun;

	/**
	 * A constant boolean flag to indicate the use of crossover.
	 */
	private static final boolean CROSSOVER = true;
	/**
	 * A constant boolean flag to indicate the use of mutation.
	 */
	private static final boolean MUTATION = false;
	/**
	 * A flag to indicate whether to use crossover or mutation.
	 */
	private boolean crossOverOrMutation = MUTATION;

	/**
	 * A constant integer flag to indicate the use of Truncation parent selection.
	 */
	private static final int TRUNCATION = 0;
	/**
	 * A constant integer flag to indicate the use of Roulette Wheel parent
	 * selection.
	 */
	private static final int ROULETTE_WHEEL = 1;
	/**
	 * A constant integer flag to indicate the use of Rank parent selection.
	 */
	private static final int RANK = 2;

	/**
	 * constant to indicate that parents will be selected using the method from the
	 * Baldwinism study
	 */
	private static final int BALDWINIAN_PARENT_SELECTION = 3;

	/**
	 * An integer flag to indicate the type of parent selection to use.
	 */
	private int parentSelectionType = TRUNCATION;

	/**
	 * A constant integer flag to indicate the fitness method "Number of 1".
	 */
	private static final int NUM_1S = 11;
	/**
	 * A constant integer flag to indicate the fitness method "Target Fitness".
	 */
	private static final int TARGET = 22;
	/**
	 * A constant integer flag to indicate the fitness method "Symmetry".
	 */
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
	 * constant to indicate use of the fitness method for the Baldwin tests
	 */
	private static final int BALDWINIAN_FITNESS = 44;

	/**
	 * An integer flag to indicate the fitness method to use.
	 */
	private int fitnessMethod = NUM_1S;

	/**
	 * the target Binary Score for Binary value fitness method;
	 */
	private int targetBinaryScore;

	/**
	 * A double value representing the elitism percentage.
	 */
	private double elitismPercentage = 0.0;
	/**
	 * A constant integer value used to scale the elitism percentage.
	 */
	private static final int SCALE_FOR_PERCENT = 100;

	/**
	 * An integer value representing the mutation rate.
	 */
	private int nofmutationRate = 1;

	/**
	 * An integer value representing the number of generations in current genetic
	 * algorithm run.
	 */
	private int numberOfGenerations = 200;
	/**
	 * An integer value representing the number of genes per chromosome.
	 */
	private int numberOfGenesPerChromosome = 100;
	/**
	 * An integer value representing the size of the population.
	 */
	private int populationSize = 100;

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
	 * A Generation object representing the current generation.
	 */
	Generation currentGeneration;

	/**
	 * constructor ensure: This constructor initializes the list of all generations.
	 */
	public GeneticAlgorithmCalculator() {
		this.allGenerationsList = new ArrayList<Generation>();

	}

	/**
	 * ensure: This method sets the mutation rate for the genetic algorithm.
	 * 
	 * @param nofmutationRate the rate at which mutations occur
	 */
	public void setMutationRate(int nofmutationRate) {
		this.nofmutationRate = nofmutationRate;
		System.out.println(this.nofmutationRate);
	}

	/**
	 * ensure: This method sets the parent selection type for the genetic algorithm.
	 * 
	 * @param parentSelectionType the type of parent selection
	 */
	public void setParentSelectionType(String parentSelectionType) {
		if (parentSelectionType.equals("Truncation")) {
			this.parentSelectionType = TRUNCATION;
		} else if (parentSelectionType.equals("Roulette Wheel")) {
			this.parentSelectionType = ROULETTE_WHEEL;
		} else if (parentSelectionType.equals("Rank Selection")) {
			this.parentSelectionType = RANK;
		} else if (parentSelectionType.equals("Baldwinism")) {
			this.baldwinismDefaults();
		} else {
			System.err.println("Error exists when choosing Parent Selection Type in Viewer!!!");
		}
		System.out.println("Parent Selection Type: " + this.parentSelectionType);

	}

	/**
	 * This method sets the fitness method for the genetic algorithm.
	 * 
	 * @param fitnessMethodString the method used to calculate fitness
	 */
	public void setFitnessMethod(String fitnessMethodString) {
		if (fitnessMethodString.equals("Number of 1")) {
			this.fitnessMethod = NUM_1S;
		} else if (fitnessMethodString.equals("Target Fitness")) {
			this.fitnessMethod = TARGET;
		} else if (fitnessMethodString.equals("Symmetry")) {
			this.fitnessMethod = SYMMETRY;
		} else if (fitnessMethodString.equals("Baldwinism")) {
			this.baldwinismDefaults();
		} else if (fitnessMethodString.equals("Binary Value")) {
			this.fitnessMethod = BINARY_VALUE;

		} else {
			System.err.println("Error exists when choosing Fitness Calculation Type in Viewer!!!");
		}
		System.out.println("Fitness Method: " + this.fitnessMethod);
	}

	private void baldwinismDefaults() {
		this.fitnessMethod = BALDWINIAN_FITNESS;
		this.parentSelectionType = BALDWINIAN_PARENT_SELECTION;
		this.crossOverOrMutation = MUTATION;
		this.nofmutationRate = 0;
		this.elitismPercentage = 0;

	}

	/**
	 * This method sets the size of the population for the genetic algorithm.
	 * 
	 * @param populationSize the size of the population
	 */
	public void setSizeOfPopulation(int populationSize) {
		this.populationSize = populationSize;
		System.out.println("Population size: " + this.populationSize);
	}

	/**
	 * ensure: Sets the number of genes per chromosome.
	 *
	 * @param numGenes the number of genes per chromosome
	 */
	public void setNumberOfGenesPerChromosome(int numGenes) {
		this.numberOfGenesPerChromosome = numGenes;
		System.out.println("Genes per chromosome: " + this.numberOfGenesPerChromosome);
	}

	/**
	 * ensure: Sets the elitism percentage of the population.
	 *
	 * @param percentageOfPopulation the percentage of the population that will be
	 *                               considered elite
	 */
	public void setElitismPercentage(double percentageOfPopulation) {
		this.elitismPercentage = percentageOfPopulation / SCALE_FOR_PERCENT;
		System.out.println("Elitism: " + this.elitismPercentage + "%");
	}

	/**
	 * Sets the choice between crossover or mutation.
	 *
	 * @param crossoverOrMutation true if crossover is to be performed, false if
	 *                            mutation is to be performed
	 */
	public void setCrossoverOrMutation(boolean crossoverOrMutation) {
		this.crossOverOrMutation = crossoverOrMutation;
		System.out.println("Crossover: " + this.crossOverOrMutation);
	}

	/**
	 * Runs the algorithm for a specified number of generations.
	 *
	 * This method updates the current generation and tracks the best, average, and
	 * worst fitness score, diversity, and number of unique individuals for each
	 * generation.
	 * 
	 *
	 * @param numberOfGenerations the number of generations the algorithm should run
	 *                            for
	 */
	public void runForNumberOfGenerations(int numberOfGenerations) {

		this.createInitialGeneration();

		for (int i = 0; i < numberOfGenerations - 1; i++) {
			this.createNewGeneration();
			
		}
	}

	public void runForNumberOfGenerationsTestMethodOnly(int numberOfGenerations) {

		this.createInitialGenerationTestMethodOnly();

		for (int i = 0; i < numberOfGenerations - 1; i++) {
			this.createNewGenerationTestMethodOnly();
			
		}
	}

	/**
	 * Creates the initial generation.
	 *
	 * 
	 * This method sets the current generation and sorts the chromosomes by their
	 * fitness score.
	 * 
	 */
	public void createInitialGeneration() {
		System.out.println("Fitness method: " + this.fitnessMethod);
		this.currentGeneration = new Generation(this.populationSize, this.numberOfGenesPerChromosome,
				this.fitnessMethod, this.setIdealGenotype());
		this.currentGeneration.sortChromosomesByFitnessScore();
		System.out.println(this.currentGeneration.toString());
		this.allGenerationsList.add(this.currentGeneration);
		this.updateNewChromosomesToPopulationViewer();
	}

	public void createInitialGenerationTestMethodOnly() {
		this.currentGeneration = new Generation(this.populationSize, this.numberOfGenesPerChromosome,
				this.fitnessMethod, this.setIdealGenotype());
		this.currentGeneration.sortChromosomesByFitnessScore();
		this.allGenerationsList.add(this.currentGeneration);
	}

	/**
	 * Creates a new generation.
	 *
	 *
	 * This method selects the parents of the next generation, creates a new
	 * generation, and updates the list of all generations.
	 * 
	 */
	public void createNewGeneration() {

		ArrayList<Chromosome> parentsOfNewGeneration = this.currentGeneration
				.chooseNextParents(this.parentSelectionType, this.elitismPercentage);
		this.currentGeneration = new Generation(parentsOfNewGeneration, this.crossOverOrMutation, this.nofmutationRate,
				this.elitismPercentage);
		this.allGenerationsList.add(this.currentGeneration);
		this.updateNewChromosomesToPopulationViewer();
	}

	public void createNewGenerationTestMethodOnly() {

		ArrayList<Chromosome> parentsOfNewGeneration = this.currentGeneration
				.chooseNextParents(this.parentSelectionType, this.elitismPercentage);
		this.currentGeneration = new Generation(parentsOfNewGeneration, this.crossOverOrMutation, this.nofmutationRate,
				this.elitismPercentage);
		this.allGenerationsList.add(this.currentGeneration);
	}

	/**
	 * Returns the best fitness score of the current generation.
	 *
	 * @return the best fitness score of the current generation
	 */
	public int returnCurrentBest() {
		return this.currentGeneration.returnBestChromosomeFitnessScore();
	}

	/**
	 * Returns the average fitness score of the current generation.
	 *
	 * @return the average fitness score of the current generation
	 */
	public int returnCurrentAverage() {
		return this.currentGeneration.returnAveChromosomeFitnessScore();
	}

	/**
	 * Returns the worst fitness score of the current generation.
	 *
	 * @return the worst fitness score of the current generation
	 */
	public int returnCurrentWorst() {
		return this.currentGeneration.returnWorstChromosomeFitnessScore();
	}

	/**
	 * Returns the Hamming distance diversity of the current generation.
	 *
	 * @return the Hamming distance diversity of the current generation
	 */
	public int returnCurrentHammingDistanceDiversity() {
		return currentGeneration.returnHammingDistance();
	}

	/**
	 * Returns the number of unique individuals in the current generation.
	 *
	 * @return the number of unique individuals in the current generation
	 */
	public int returnCurrentNumberOfUniqueIndividuals() {
		return currentGeneration.returnNumUniqueIndividuals();
	}

	/**
	 * Returns the best chromosome of the current generation.
	 *
	 * @return the best chromosome of the current generation
	 */

	public Chromosome getBestChromosome() {
		return currentGeneration.getBestChromosome();
	}

	/**
	 * 
	 * Returns the average number of correct alleles in the current generation. If
	 * the parent selection type is not Baldwinian, returns 0.
	 * 
	 * @return The average number of correct alleles in the current generation, or 0
	 *         if the parent selection type is not Baldwinian.
	 */
	public int returnAverageCorrectAlleles() {
		if (this.parentSelectionType == BALDWINIAN_PARENT_SELECTION) {
			return currentGeneration.returnAverageCorrectAlleles();
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * Returns the average number of incorrect alleles in the current generation. If
	 * the parent selection type is not Baldwinian, returns 0.
	 * 
	 * @return The average number of incorrect alleles in the current generation, or
	 *         0 if the parent selection type is not Baldwinian.
	 */
	public int returnAverageIncorrectAlleles() {
		if (this.parentSelectionType == BALDWINIAN_PARENT_SELECTION) {
			return currentGeneration.returnAverageIncorrectAlleles();
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * Returns the average number of unknown alleles in the current generation. If
	 * the parent selection type is not Baldwinian, returns 0.
	 * 
	 * @return The average number of unknown alleles in the current generation, or 0
	 *         if the parent selection type is not Baldwinian.
	 */
	public int returnAverageUnknownAlleles() {
		if (this.parentSelectionType == BALDWINIAN_PARENT_SELECTION) {
			return currentGeneration.returnAverageUnknownAlleles();
		} else {
			return 0;
		}
	}

	/**
	 * Sets the ideal genotype from Files.
	 *
	 * @return the ideal genotype
	 */

	public ArrayList<Gene> setIdealGenotype(String idealGenotypeFileLocation) {
		ArrayList<Gene> idealGenotype = null;
		try {
			idealGenotype = FileIO.readFile(idealGenotypeFileLocation);
		} catch (InvalidChromosomeFormatException e1) {
			System.err.println(
					"The file for the ideal genotype was not found! Please create a file for ideal genotype or choose a different fitness function.");
		} catch (IOException e2) {
			System.err.println("There has been a file error (IO Exception)! Please choose a different file.");
		}
		return idealGenotype;
	}

	/**
	 * Sets the ideal genotype from default Files.
	 *
	 * @return the ideal genotype
	 */
	public ArrayList<Gene> setIdealGenotype() {
		ArrayList<Gene> idealGenotype = null;
		try {
			idealGenotype = FileIO.readFile("ChromosomeTextFiles/IdealGenotype");
		} catch (InvalidChromosomeFormatException e1) {
			System.err.println(
					"The file for the ideal genotype was not found! Please create a file for ideal genotype or choose a different fitness function.");
		}
		return idealGenotype;
	}

	/**
	 * Updates the population viewer with the chromosomes of the current generation.
	 */

	public void updateNewChromosomesToPopulationViewer() {
		populationViewerOfCurrentRun.updateChromosomes(currentGeneration.getAllChromosome());
	}

	/**
	 * Starts the population viewer.
	 */

	public void startPopulationViewer() {
		if (populationViewerOfCurrentRun == null)
			populationViewerOfCurrentRun = new PopulationViewer();
		else {
			populationViewerOfCurrentRun.open();
		}
	}

	/**
	 * Closes the population viewer.
	 */
	public void closePopulationViewer() {
		if (populationViewerOfCurrentRun != null)
			populationViewerOfCurrentRun.close();
	}

}
