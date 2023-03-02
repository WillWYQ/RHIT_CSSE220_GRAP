package mainApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Class: ChromosomeFileIO
 *
 * 
 * Purpose: The ChromosomeFileIO class provides methods for reading and writing
 * Chromosome objects to and from file. <br>
 * <br>
 * This class is useful for saving and loading Chromosome objects to and from
 * disk, allowing for persistence across multiple program runs. <br>
 * 
 * <br>
 * Restrictions: 1 is true, 0 is false
 * 
 * <br>
 * For example:
 * 
 * <pre>
// Writing a Chromosome object to file
*Chromosome chromosome = new Chromosome(ArrayList<Gene>);
*ChromosomeFileIO.writeChromosomeToFile(chromosome, "chromosome.txt");
// Reading a Chromosome object from file
*Chromosome readChromosome = ChromosomeFileIO.readChromosomeFromFile("chromosome.txt");
 * </pre>
 * 
 * @author Yueqiao Wang and Rebecca Testa
 * @see Chromosome
 */

public class FileIO {

	/** The number of genes per row in the file. */
	public static final int NUM_GENES_PER_ROW = 10;
	/** The probability of a gene being 1 in a newly generated file. */
	public static final double PROBABILITY_OF_GENE_BEING_ONE = 0.5;

	private static final String FILE_NOT_FOUND_EXCEPTION_NOTICE = "File does not exist! Please try a different file.";

	private static final String IO_EXCEPTION_NOTICE = "IO Exception! The file you selected may not exist. Please try a different file.";

	/**
	 * Update an existing text file with the contents of the given list of genes.
	 * 
	 * @param filePathName The file path of the file to update.
	 * @param listOfGenes  The list of genes to write to the file.
	 */
	public static void updateTextFile(String filePathName, ArrayList<Gene> listOfGenes) {

		System.out.println("Writing your file!");

		PrintWriter pw = null;

		try {
			pw = new PrintWriter(filePathName);

			String rowOfGenes = "";
			for (int i = 0; i < listOfGenes.size(); i++) {
				if ((listOfGenes.get(i).getCurrentValue()) == 1)
					pw.print(1);
				else if (listOfGenes.get(i).getCurrentValue() == 0)
					pw.print(0);
				else
					pw.print("X");
				if ((i + 1) % 10 == 0) {
					pw.println();
				}
			}

			pw.close();

		} catch (FileNotFoundException e) {
			System.err.println(FILE_NOT_FOUND_EXCEPTION_NOTICE);
			JOptionPane.showMessageDialog(null, FILE_NOT_FOUND_EXCEPTION_NOTICE, "File Not Found Exception",
					JOptionPane.ERROR_MESSAGE);
		}

	};

	/**
	 * Write a new file with random binary genes.
	 * 
	 * @param filename      The file path of the file to write.
	 * @param numberOfGenes The number of genes to write to the file.
	 */
	public static void writeNewFileWithRandomGenes(String filename, Integer numberOfGenes) {

		PrintWriter pw = null;

		try {
			pw = new PrintWriter(filename);
		} catch (FileNotFoundException e) {
			System.err.println(FILE_NOT_FOUND_EXCEPTION_NOTICE);
			JOptionPane.showMessageDialog(null, FILE_NOT_FOUND_EXCEPTION_NOTICE, "File Not Found Exception",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		for (int row = 0; row < numberOfGenes / NUM_GENES_PER_ROW; row++) {

			String rowOfGenes = "";

			for (int gene = 0; gene < NUM_GENES_PER_ROW; gene++) {
				double randomNumber = Math.random();

				if (randomNumber > PROBABILITY_OF_GENE_BEING_ONE) {
					rowOfGenes += "0";
				} else {
					rowOfGenes += "1";
				}
			}

			pw.println(rowOfGenes);
		}

		pw.close();

	}

	/**
	 * Read binary genes from a text file and return them as a list of Gene objects.
	 * 
	 * @param filename The file path of the file to read from.
	 * @return The list of binary genes as Gene objects.
	 * @throws InvalidChromosomeFormatException if the file contains an invalid
	 *                                          character.
	 */
	public static ArrayList<Gene> readFile(String filename) throws InvalidChromosomeFormatException {

		ArrayList<Gene> listOfGenes = new ArrayList<Gene>();

		Scanner scanner = null;

		try {
			scanner = new Scanner(new File(filename));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				// System.out.println(line);

				for (int i = 0; i < line.length(); i++) {
					Character currentCharacter = line.charAt(i);

					int current = Integer.parseInt(currentCharacter.toString());
					Gene currentGene;

					if (current == 1 || current == 0) {
						currentGene = new Gene(current);
					} else {
						currentGene = new Gene(0);
						throw new InvalidChromosomeFormatException(current);
					}
					listOfGenes.add(currentGene);
				}

			}
		} catch (InvalidChromosomeFormatException e) {
			System.err.println(e.returnInvalidCharacterWarning());
		} catch (IOException e) {
			System.err.println(IO_EXCEPTION_NOTICE);
			JOptionPane.showMessageDialog(null, IO_EXCEPTION_NOTICE, "IO Exception", JOptionPane.ERROR_MESSAGE);

		}
		return listOfGenes;
	}

	/**
	 * 
	 * Writes the given generation as a string to the specified file location.
	 * 
	 * @param generationAsString  the generation to be written to the file
	 * @param fileNameAndLocation the file location to write the generation to
	 */
	public static void writeGenerationFile(String generationAsString, String fileNameAndLocation) {
		PrintWriter pw = null;

		try {
			pw = new PrintWriter(fileNameAndLocation);
			pw.print(generationAsString);

			pw.close();

		} catch (FileNotFoundException e) {
			System.err.println(FILE_NOT_FOUND_EXCEPTION_NOTICE);
			JOptionPane.showMessageDialog(null, FILE_NOT_FOUND_EXCEPTION_NOTICE, "File Not Found Exception",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}
