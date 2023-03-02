package mainApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ChromosomeViewer {

	/**
	 * Width of the frame
	 */
	public static final int FRAME_WIDTH = 900;
	/**
	 * Height of the frame
	 */
	public static final int FRAME_HEIGHT = 900;
	/**
	 * Number of genes to be displayed per row
	 */
	private static final int PER_ROW = 10;
	/**
	 * Background color of a gene
	 */
	private static final Color BACKGROUND_GENE_COLOR = new Color(129, 216, 208);

	/**
	 * Default color of a gene
	 */
	private static final Color DEFAULT_COLOR = Color.pink;
	/**
	 * The ratio of the frame width to the width of a gene button
	 */
	private static final int FRAME_WIDTH_TO_BUTTON_WIDTH_RATIO = 20;
	/**
	 * The ratio of the frame width to the width of a text field
	 */
	private static final int TEXT_FIELD_RATIO = 60;
	/**
	 * Panel for displaying the genes
	 */
	private JPanel genePanel = new JPanel();
	/**
	 * The main frame for the application
	 */
	private JFrame chromosomeFrame = new JFrame("Chromosome Viewer");
	/**
	 * The file name and location of the chromosome
	 */
	private String fileNameAndLocation;
	/**
	 * The chromosome to be viewed
	 */
	private Chromosome chromosome = new Chromosome(new ArrayList<Gene>());

	public void setChromosome(Chromosome chromosome) {
		this.chromosome = chromosome;
		this.listOfGenes = chromosome.getGenesAsArrayList();
		genesButton(listOfGenes);
	}

	/**
	 * List of genes in the chromosome
	 */
	private ArrayList<Gene> listOfGenes;

	/**
	 * Mutation rate of the chromosome
	 */
	private double mutateRate;

	/**
	 * Main method that launches the application during testing
	 * 
	 * @param args command line arguments
	 */
	public static void main(String args[]) {

		ChromosomeViewer test = new ChromosomeViewer();

	}

	/**
	 * Returns the color for the given Gene based on its current value.
	 * 
	 * @param currentGene the current Gene being evaluated
	 * @return the color for the Gene. If the Gene's current value is true, return
	 *         Color.BLACK. If the Gene's current value is false, return
	 *         BACKGROUND_GENE_COLOR.
	 */
	private Color setColor(Gene currentGene) {
		if (currentGene.getCurrentValue() == 1) {
			return (Color.BLACK);
		} else if (currentGene.getCurrentValue() == 0) {
			return (BACKGROUND_GENE_COLOR);
		} else {
			return (DEFAULT_COLOR);
		}
	}

	/**
	 * Creates buttons for each Gene in the given listOfGenes and adds them to the
	 * genePanel. The background color of each button is determined by the current
	 * value of the Gene. The buttons have an ActionListener that changes the Gene's
	 * value and updates the button's background color.
	 * 
	 * @param listOfGenes the list of Genes to be displayed as buttons in the
	 *                    genePanel.
	 */
	public void genesButton(ArrayList<Gene> listOfGenes) {
		if (genePanel.getComponentCount() > 0) {
			genePanel.removeAll();
		}
		int rows = 1;
		int lines[];

		if (listOfGenes.size() > PER_ROW) {
			rows = listOfGenes.size() / PER_ROW;
			if (listOfGenes.size() % PER_ROW != 0) {
				rows++;
			}
			lines = new int[rows];
			for (int i = 0; i < rows; i++) {
				lines[i] = PER_ROW;

			}
			if (listOfGenes.size() % PER_ROW != 0) {
				lines[rows - 1] = listOfGenes.size() % PER_ROW;
			}

		} else {
			rows = 1;
			lines = new int[1];
			lines[0] = listOfGenes.size();
		}

		for (int i = 0; i < rows; i++) {

			JPanel geneRowPanel = new JPanel();
			for (int j = 0; j < lines[i]; j++) {

				Gene currentGene = listOfGenes.get(i * PER_ROW + j);
				int index = i * PER_ROW + j;
				JButton currentButton = new JButton("" + (index));
				currentButton.setBackground(this.setColor(currentGene));
				currentButton.setContentAreaFilled(false);
				currentButton.setOpaque(true);
				currentButton.setBorderPainted(false);
				currentButton.setSize(chromosomeFrame.getWidth() / FRAME_WIDTH_TO_BUTTON_WIDTH_RATIO,
						chromosomeFrame.getHeight() / FRAME_WIDTH_TO_BUTTON_WIDTH_RATIO);
				currentButton.setForeground(Color.red);
				currentButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						currentGene.changeValue();
						currentButton.setBackground(setColor(currentGene));
					}

				});

				geneRowPanel.add(currentButton);
			}
			genePanel.add(geneRowPanel);
		}

		chromosomeFrame.add(genePanel, BorderLayout.CENTER);
		chromosomeFrame.validate();
	}

	/**
	 * 
	 * Repaints the chromosome frame by packing the frame and resizing it to the
	 * constant FRAME_WIDTH and FRAME_HEIGHT, then validating the changes.
	 */
	public void repaint() {
		chromosomeFrame.pack();
		chromosomeFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		chromosomeFrame.validate();
	}

	/**
	 * Creates a new instance of the ChromosomeViewer. Initializes the
	 * chromosomeFrame, fileNameAndLocationLabel, controlButtonPanel, and
	 * topButtonPanel. Adds a mutateButton with an ActionListener that mutates the
	 * chromosome and updates the genesButton. Adds a JTextField for mutate rate and
	 * an ActionListener to update the mutateRate.
	 */
	public ChromosomeViewer() {

		JLabel fileNameAndLocationLabel = new JLabel();
		chromosomeFrame.add(fileNameAndLocationLabel, BorderLayout.NORTH);

		JPanel controlButtonPanel = new JPanel();

		JPanel topButtonPanel = new JPanel();
		JButton mutateButton = new JButton("Mutate");
		mutateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listOfGenes = chromosome.mutate(mutateRate);
				genesButton(listOfGenes);
			}
		});
		JLabel mutateRateLabel = new JLabel("M Rate: _/N");
		JTextField mutateRateField = new JTextField(20);
		mutateRateField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				mutateRate = 1.0 * Integer.parseInt(mutateRateField.getText());
			}

		});
		topButtonPanel.add(mutateButton);
		topButtonPanel.add(mutateRateLabel);
		topButtonPanel.add(mutateRateField);
		controlButtonPanel.add(topButtonPanel, BorderLayout.NORTH);

		JPanel bottomButtonPanel = new JPanel();
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (genePanel.getComponentCount() > 0) {
					genePanel.removeAll();
				}

				String locationOfChromosomeTextFiles = "ChromosomeTextFiles";
				JFileChooser filePicker = new JFileChooser(locationOfChromosomeTextFiles);

				if (filePicker.showOpenDialog(chromosomeFrame) == JFileChooser.APPROVE_OPTION) {

					fileNameAndLocation = filePicker.getSelectedFile().getPath();
					fileNameAndLocationLabel.setText(fileNameAndLocation);

					try {
						listOfGenes = FileIO.readFile(fileNameAndLocation);
						chromosome = new Chromosome(listOfGenes);
						genesButton(listOfGenes);
						genePanel.repaint();
					} catch (InvalidChromosomeFormatException e1) {
						System.err.println(e1.returnInvalidCharacterWarning());
					} catch (IOException e2) {
						System.err
								.println("There has been a file error (IO Exception)! Please choose a different file.");
					}

				}

			}
		});

		bottomButtonPanel.add(loadButton);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String locationOfChromosomeTextFiles = "ChromosomeTextFiles";
				JFileChooser filePicker = new JFileChooser(locationOfChromosomeTextFiles);

				if (filePicker.showSaveDialog(chromosomeFrame) == JFileChooser.APPROVE_OPTION) {

					fileNameAndLocation = filePicker.getSelectedFile().getPath();
					FileIO.updateTextFile(fileNameAndLocation, listOfGenes);
				}
			}
		});
		bottomButtonPanel.add(saveButton);
		controlButtonPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
		chromosomeFrame.add(controlButtonPanel, BorderLayout.SOUTH);

		chromosomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chromosomeFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		chromosomeFrame.setVisible(true);
	}

}
