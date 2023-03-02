package mainApp;

import java.util.ArrayList;

/**
 * Class: Chromosome
 * 
 * 
 * Purpose: The Chromosome class represents a single chromosome in a genetic
 * algorithm. <br>
 * <br>
 * A chromosome is composed of a series of genes. <br>
 * <br>
 * Restrictions: NONE <br>
 * For example:
 * 
 * <pre>
 * 
 * Chromosome chromosome = new Chromosome(ArrayList<Gene>);
 * 
 * <pre>
 * 
 * @author Yueqiao Wang and Rebecca Testa
 * @see Gene
 */
public class Chromosome implements Comparable<Chromosome> {
	/**
	 * ArrayList of genes that make up the chromosome
	 */
	private ArrayList<Gene> genes = new ArrayList<Gene>();
	/**
	 * the fitness score of the chromosome
	 */
	private int fitnessScore;
	/**
	 * the number of correct alleles in the chromosome
	 */
	private int correctAllele = 0;
	/**
	 * the number of incorrect alleles in the chromosome
	 */
	private int incorrectAllele = 0;
	/**
	 * the number of unknown alleles in the chromosome
	 */
	private int unknownAllele = 0;

	/**
	 * flag to confirm the calculation process is finished.
	 */
	private boolean allelesCalculated = false;

	/**
	 * flag for the NUM_1S fitness in fitness evaluation
	 */
	private static final int NUM_1S = 11;
	/**
	 * flag for the target fitness in fitness evaluation
	 */
	private static final int TARGET = 22;
	/**
	 * flag for the symmetry fitness in fitness evaluation
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
	 * constant for the lifespan of a chromosome in generations in Baldwinism
	 */
	private static final int LIFE_OF_CHROMOSOME = 1000;

	/**
	 * constant for the display/calculation of a chromosome fitness in Binary Value
	 */
	private static final int DEFAULT_UNIT = 10;

	/**
	 * the fitness evaluation method used
	 */
	private int fitnessMethod;

	/**
	 * the target Binary Score for Binary value fitness method;
	 */
	private int targetBinaryScore;

	/**
	 * flag indicating whether Baldwinism evolution is being used
	 */
	private boolean baldwinianEvolution;
	/**
	 * flag for an correct allele
	 */
	private static final int CORRECT_ALLELE_FLAG = 1;
	/**
	 * flag for an incorrect allele
	 */
	private static final int INCORRECT_ALLELE_FLAG = 0;
	/**
	 * flag for an unknown allele
	 */
	private static final int UNKNOWN_ALLELE_FLAG = 2;

	private ArrayList<Gene> idealGenotype;

	public Chromosome(ArrayList<Gene> genes) {
		// SET THIS.GENES TO CHROMOSOMEFILEIO.READFILE AND THIS.FILE TO THE PARAMETER
		// "FILENAME"
		this.genes = genes;
		this.calculateFitnessScore();
	}

	/**
	 * Constructor for the Chromosome class.
	 * 
	 * @param genes ArrayList of genes to be included in the chromosome.
	 */
	public Chromosome(ArrayList<Gene> genes, int fitnessMethod, ArrayList<Gene> idealGenotype) {

		// SET THIS.GENES TO CHROMOSOMEFILEIO.READFILE AND THIS.FILE TO THE PARAMETER
		// "FILENAME"
		this.genes = genes;
		this.fitnessMethod = fitnessMethod;
		this.idealGenotype = idealGenotype;
		this.calculateFitnessScore();
	}

	public Chromosome(int numberOfGenes, int fitnessMethod, ArrayList<Gene> idealGenotype) {

		if (fitnessMethod == BALDWINISM_FITNESS) {
			for (int i = 0; i < numberOfGenes; i++) {
				this.baldwinianEvolution = true;
				this.genes.add(new Gene(baldwinianEvolution));
			}

		} else {
			for (int i = 0; i < numberOfGenes; i++) {
				this.genes.add(new Gene());
			}
		}
		this.fitnessMethod = fitnessMethod;
		this.idealGenotype = idealGenotype;
		this.calculateFitnessScore();
	}

	private void calculateFitnessScore() {
		if (this.fitnessMethod == NUM_1S) {
			this.calculateFitnessScoreByNumOnes();
		} else if (this.fitnessMethod == TARGET) {
			this.calculateFitnessComparedToIdealImage(this.idealGenotype);
		} else if (this.fitnessMethod == SYMMETRY) {
			this.calculateFitnessBySymmetry();
		} else if (this.fitnessMethod == BALDWINISM_FITNESS) {
			this.calculateFitnessAccordingToBaldwinism();
		} else if (this.fitnessMethod == BINARY_VALUE) {
			this.calculateFitnessAccordingToBinaryValue();
		} else {
			System.err.println("Error: Choosing Fitness Method Problem");
		}
	}

	/**
	 * Calculates and returns the fitness score of the chromosome based on the
	 * number of 1's in its genotype.
	 * 
	 */
	private void calculateFitnessScoreByNumOnes() {
		for (Gene gene : genes) {
			if (gene.getCurrentValue() == 1) {
				this.fitnessScore++;
			}

		}
	}

	/**
	 * ensures: calculates and returns the fitness score of the chromosome based on
	 * an ideal image
	 * 
	 * @param idealGenotype the list of genes that characterize the most desirable
	 *                      genotype
	 */
	private void calculateFitnessComparedToIdealImage(ArrayList<Gene> idealGenotype) {
		this.fitnessScore = 0;
		for (int i = 0; i < this.genes.size(); i++) {
			if (this.genes.get(i).getCurrentValue() == idealGenotype.get(i).getCurrentValue()) {
				this.fitnessScore += 1;
			}
		}
	}

	/**
	 * ensures: calculates and returns the fitness score of the chromosome based on
	 * how symmetrical its array of genes is.
	 */
	private void calculateFitnessBySymmetry() {
		this.fitnessScore = 0;
		for (int i = 0; i < this.genes.size() / 2; i++) {
			if (this.genes.get(i).getCurrentValue() == this.genes.get(this.genes.size() - (i + 1)).getCurrentValue()) {
				this.fitnessScore += 1;
			}
		}
	}

	/**
	 * 
	 * Calculates fitness according to Baldwinian .
	 * 
	 * @return void
	 */
	private void calculateFitnessAccordingToBaldwinism() {
		int daysLeftWhenReachedBestFitness = 0;

		for (int daysRemaining = LIFE_OF_CHROMOSOME; daysRemaining > 0; daysRemaining--) {
			boolean allCorrect = true;
			for (Gene gene : this.genes) {
				Gene temp;
				if (gene.getCurrentValue() == CORRECT_ALLELE_FLAG) {
					temp = (new Gene(CORRECT_ALLELE_FLAG));
				} else if (gene.getCurrentValue() == INCORRECT_ALLELE_FLAG) {
					temp = (new Gene(INCORRECT_ALLELE_FLAG));
				} else {
					double randomNumber = Math.random();
					if (randomNumber < 0.5) {
						temp = (new Gene(CORRECT_ALLELE_FLAG));
					} else {
						temp = (new Gene(INCORRECT_ALLELE_FLAG));
					}
				}
				if (temp.getCurrentValue() == INCORRECT_ALLELE_FLAG) {
					allCorrect = false;
				}

			}

			if (allCorrect == true) {
				daysLeftWhenReachedBestFitness = daysRemaining;
				System.out.println("Reached best fitness! Days remaining: " + daysRemaining);
				break;
			}

		}

		this.fitnessScore = 1 + 19 * daysLeftWhenReachedBestFitness / 1000;

	}

	/**
	 * Calculates the fitness score of the chromosome based on its binary value.<br>
	 * The fitness score is the sum of the absolute differences between the target
	 * binary score and the chromosome's binary value, modulo DEFAULT_UNIT. <br>
	 * The binary value is calculated by concatenating the binary values of each
	 * gene in the chromosome's ArrayList, and splitting the resulting string into
	 * groups of size DEFAULT_UNIT. <br>
	 * The binary value of each group is converted to an integer, and the sum of
	 * these integers is used to calculate the fitness score.
	 */

	private void calculateFitnessAccordingToBinaryValue() {
		this.targetBinaryScore = (int) Math.pow(2, DEFAULT_UNIT) / 2;
		this.fitnessScore = 0;
		String numlists = "";
		int binary_value = 0;
		for (int i = 0; i < genes.size(); i++) {
			if (i % DEFAULT_UNIT == 0 && i != 0) {
				binary_value += (int) Long.parseLong(numlists, 2);
				this.fitnessScore += Math.abs(targetBinaryScore - binary_value) % DEFAULT_UNIT;
				numlists = "";
			}
			numlists += genes.get(i).getCurrentValue();
		}

	}

	/**
	 * Returns the number of correct alleles in the chromosome. If the alleles have
	 * not been calculated yet, calculates them and returns the correct allele
	 * count.
	 *
	 * @return The number of correct alleles in the chromosome.
	 */
	public int returnCorrectAlleles() {
		if (this.allelesCalculated) {
			return this.correctAllele;
		} else {
			this.calculateAlleles();
			return this.correctAllele;
		}

	}

	/**
	 * Returns the number of incorrect alleles in the chromosome. If the alleles
	 * have not been calculated yet, calculates them and returns the incorrect
	 * allele count.
	 *
	 * @return The number of incorrect alleles in the chromosome.
	 */
	public int returnIncorrectAlleles() {
		if (this.allelesCalculated) {
			return this.incorrectAllele;
		} else {
			this.calculateAlleles();
			return this.incorrectAllele;
		}

	}

	/**
	 * Returns the number of unknown alleles in the chromosome. If the alleles have
	 * not been calculated yet, calculates them and returns the unknown allele
	 * count.
	 *
	 * @return The number of unknown alleles in the chromosome.
	 */
	public int returnUnknownAlleles() {
		if (this.allelesCalculated) {
			return this.unknownAllele;
		} else {
			this.calculateAlleles();
			return this.unknownAllele;
		}
	}

	/**
	 * Calculates the number of correct, incorrect, and unknown alleles in the
	 * chromosome, and sets the allelesCalculated flag to true.
	 */
	private void calculateAlleles() {
		for (Gene g : genes) {
			int currentValue = g.getCurrentValue();
			if (currentValue == CORRECT_ALLELE_FLAG) {
				this.correctAllele++;
			} else if (currentValue == INCORRECT_ALLELE_FLAG) {
				this.incorrectAllele++;
			} else if (currentValue == UNKNOWN_ALLELE_FLAG) {
				this.unknownAllele++;
			}
		}
		this.allelesCalculated = true;
	}

	/**
	 * Reports the fitness score of the chromosome.
	 * 
	 * @return Integer value representing the fitness score of the chromosome.
	 */
	public int reportFitness() {
		return this.fitnessScore;
	}

	/**
	 * Returns the fitness method used by the chromosome.
	 *
	 * @return The fitness method used by the chromosome.
	 */
	public int getFitnessMethod() {
		return this.fitnessMethod;
	}

	/**
	 * Returns the ideal genotype for the chromosome.
	 *
	 * @return The ideal genotype for the chromosome.
	 */
	public ArrayList<Gene> getIdealGenotype() {
		return this.idealGenotype;
	}

	/**
	 * Mutates the chromosome by randomly flipping the value of certain genes.
	 * 
	 * @param mutateRate Double value representing the probability of a gene being
	 *                   flipped.
	 * @return ArrayList of mutated genes.
	 */
	public ArrayList<Gene> mutate(Double mutateRate) {
		for (Gene g : this.genes) {
			if (Math.random() < mutateRate / this.genes.size()) {
				g.changeValue();
			}
		}

		return this.genes;
	}

	/**
	 * Retrieves the genes of the chromosome from a file.
	 * 
	 * @param fileNameAndLocation String value representing the file name and
	 *                            location.
	 */
	public void getGenesFromFile(String fileNameAndLocation) {

		try {
			this.genes = FileIO.readFile(fileNameAndLocation);
		} catch (InvalidChromosomeFormatException e) {
			System.err.println(e.returnInvalidCharacterWarning());
		}
	}

	/**
	 * Returns the genes of this chromosome as an ArrayList.
	 * 
	 * @return: the list of genes in this chromosome.
	 */
	public ArrayList<Gene> getGenesAsArrayList() {
		return this.genes;
	}

	/**
	 * Creates a deep copy of this chromosome. The genes of the copy are new objects
	 * with the same values as the original genes. The fitness method and ideal
	 * genotype of the copy are the same as the original chromosome.
	 *
	 * @return a deep copy of this chromosome
	 */
	public Chromosome makeDeepCopy() {
		ArrayList<Gene> newGenes = new ArrayList<Gene>();
		for (Gene gene : this.genes) {
			newGenes.add(new Gene(gene.getCurrentValue()));
		}
		return new Chromosome(newGenes, this.fitnessMethod, this.idealGenotype);
	}

	/**
	 * Returns a string representation of the chromosome's genes and fitness score.
	 * Each gene is represented as a 0 or 1 or 2 character. The genes are displayed
	 * in rows of 10 separated by a newline character. The fitness score is also
	 * displayed.
	 *
	 * @return a string representation of the chromosome
	 */
	@Override
	public String toString() {
		String genesAsString = "";
		for (int i = 0; i < genes.size(); i++) {
			genesAsString += genes.get(i).getCurrentValue();
			if ((i + 1) % 10 == 0) {
				genesAsString += "\n";
			}
		}
		genesAsString += "Fitness score: " + this.fitnessScore + "\n";
		return genesAsString;
	}

	/**
	 * Compares this chromosome with another chromosome based on their fitness
	 * scores. Returns a negative integer, zero, or a positive integer as this
	 * chromosome's fitness score is less than, equal to, or greater than the other
	 * chromosome's fitness score, respectively.
	 *
	 * @param otherChromosome the chromosome to compare with
	 * @return a negative integer, zero, or a positive integer as this chromosome's
	 *         fitness score is less than, equal to, or greater than the other
	 *         chromosome's fitness score, respectively
	 */
	@Override
	public int compareTo(Chromosome otherChromosome) {
		if (this.fitnessScore > otherChromosome.reportFitness()) {
			return -1;
		} else if (this.fitnessScore < otherChromosome.reportFitness()) {
			return 1;
		} else {
			return 0;
		}
	}

}