package mainApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * Class: EvolutionAnalysisViewer <br>
 * Purpose:provide a graphical user interface for viewing the results of a
 * genetic algorithm simulation. <br>
 * <br>
 * Each Gene has a currentValue, expectedValue and isCorrect property. <br>
 * <br>
 * Restrictions: <br>
 * the number of different types of selection methods and fitness functions that
 * can be used, <br>
 * The size of the viewer cannot be too small. The Generation cannot be too
 * high. These will influence the view <br>
 * For example:
 * 
 * <pre>
 * EvolutionAnalysisViewer evolutionAnalysisViewer = new
 * EvolutionAnalysisViewer();
 * 
 * <pre>
 * 
 * @see GeneticAlgorithmCalculator
 * @see ChromosomeViewer
 * @see EvolutionAnalysisComponent
 * @author Yueqiao Wang and Rebecca Testa
 */

public class EvolutionAnalysisViewer {
	/**
	 * DEFAULT_FRAME_SIZE_X is the default x-axis size of the
	 * EvolutionAnalysisViewer frame.
	 */
	private static final int DEFAULT_FRAME_SIZE_X = 2000;
	/**
	 * DEFAULT_FRAME_SIZE_Y is the default y-axis size of the
	 * EvolutionAnalysisViewer frame.
	 */
	private static final int DEFAULT_FRAME_SIZE_Y = 700;
	/**
	 * TEXTFEILD_RATIO is the ratio of the text field size in the control panel.
	 */
	private static final int TEXTFEILD_RATIO = 180;
	/**
	 * the delayed time by timer in ms
	 */
	private static final int DELAY = 100;

	/**
	 * differentSelectionType is an array of strings that represent the different
	 * types of parent selection for the genetic algorithm.
	 */
	private static final String[] differentSelectionType = { "Truncation", "Roulette Wheel", "Rank Selection",
			"Baldwinism" };
	/**
	 * differentFitnessType is an array of strings that represent the different
	 * types of fitness for the genetic algorithm.
	 */
	private static final String[] differentFitnessType = { "Number of 1", "Target Fitness", "Symmetry", "Baldwinism",
			"Binary Value" };
	/**
	 * defaultColor is the default Color for Color Chooser
	 */
	private static final Color defaultColor = Color.black;

	/**
	 * evolutionAnalysisViewer is the main JFrame for the EvolutionAnalysisViewer.
	 */
	private JFrame evolutionAnalysisViewer;
	/**
	 * colorChooser is the JFrame to choose color.
	 */
	private JFrame colorChooser = new JFrame("Choose Color for Line");
	/**
	 * graphTitle is a JLabel for the title of the graph.
	 */
	private JLabel graphTitle;
	/**
	 * controlPanel is a JPanel that contains all of the control panels for the
	 * genetic algorithm.
	 */
	private JPanel controlPanel;
	/**
	 * controlPanelMutation is a JPanel that contains controls for the mutation rate
	 * of the genetic algorithm.
	 */
	private JPanel controlPanelMutation;
	/**
	 * controlPanelFitness is a JPanel that contains controls for the fitness type
	 * of the genetic algorithm.
	 */
	private JPanel controlPanelFitness;
	/**
	 * controlPanelGeneral is a JPanel that contains general controls for the
	 * genetic algorithm.
	 */
	private JPanel controlPanelGeneral;
	/**
	 * controlPanelRuner is a JPanel that contains the start and pause button for
	 * the genetic algorithm.
	 */
	private JPanel controlPanelRuner;
	/**
	 * controlPanelDisplay is a JPanel that contains the buttons to control the
	 * display choices
	 */
	private JPanel controlPanelDisplay;
	/**
	 * controlPanelDisplay is a JPanel that contains the buttons to control the
	 * Alleles display choices
	 */
	private JPanel controlPanelDisplayAlleles;
	/**
	 * startAndPause is a JButton that starts and pauses the genetic algorithm.
	 */
	JButton startAndPause = new JButton("Start");
	/**
	 * nofMutateRate is the n (n/population) of mutation rate.
	 */
	private int nofMutateRate = 0;
	/**
	 * population is the size of the population in the genetic algorithm.
	 */
	private int population = 100;
	/**
	 * numberOfGenerations is the number of generations in the genetic algorithm.
	 */
	private int numberOfGenerations = 100;
	/**
	 * genomeLength is the length of the genome of each chromosome in the
	 * population.
	 */
	private int genomeLength = 100;
	/**
	 * parentSelectionTypeString is a string that represents the type of parent
	 * selection to use in the genetic algorithm. <br>
	 * The possible values are "Truncation", "Roulette Wheel", and "Rank Selection".
	 */
	private String parentSelectionTypeString = "Truncation";
	/**
	 * fitnessTypeString is a string that represents the type of fitness function to
	 * use in the genetic algorithm.<br>
	 * The possible values are "Number of 1", "Target Fitness", and "Symmetry".
	 */
	private String fitnessTypeString = "Number of 1";
	/**
	 * elitismNPerPop represents the number of chromosomes to preserve from one
	 * generation to the next using elitism.
	 */
	private int elitismNPerPop = 0;

	/**
	 * 
	 * The default location of the file containing the ideal genotype.
	 */
	private String idealGenotypeFileLocation = "ChromosomeTextFiles/IdealGenotype";

	/**
	 * a boolean flag indicating whether the evolution should be the same every time
	 * it is run.
	 */
	private boolean sameEvolution = false;
	/**
	 * keeps track of the current generation count in the evolution.
	 */
	private int generationCount;
	/**
	 * the target fitness score to reach in the evolution.
	 */
	private int targetFitnessScore = 100;

	/**
	 * an instance of the GeneticAlgorithmCalculator class that is used to run the
	 * genetic algorithm.
	 */
	private GeneticAlgorithmCalculator geneticAlgorithmCalculator = new GeneticAlgorithmCalculator();
	/**
	 * an instance of the ChromosomeViewer class that is used to display the
	 * chromosomes in the population.
	 */
	private ChromosomeViewer chromosomeViewer;

	/**
	 * an instance of the EvolutionAnalysisComponent class that displays the results
	 * of the evolution.
	 */
	private EvolutionAnalysisComponent evolutionAnalysisComponent = new EvolutionAnalysisComponent();

	/**
	 * a long integer representing the time (in milliseconds) when the evolution
	 * started.
	 */
	private long timeAgo;
	/**
	 * a long integer representing the current time (in milliseconds).
	 */
	private long timeGoes;
	/**
	 * an instance of the JLabel class that displays the time elapsed since the
	 * evolution started.
	 */
	private JLabel runningTime;

	/**
	 * a long integer representing the past time (in milliseconds).
	 */
	private long timeHasGone;

	public EvolutionAnalysisViewer() {
		this.chromosomeViewer = new ChromosomeViewer();
		evolutionAnalysisViewer = new JFrame("Evolution Analysis Viewer");
		evolutionAnalysisViewer.setSize(DEFAULT_FRAME_SIZE_X, DEFAULT_FRAME_SIZE_Y);

		graphTitle = new JLabel();
		graphTitle.setText("Fitness Over Generation");
		evolutionAnalysisViewer.add(graphTitle, BorderLayout.NORTH);
		evolutionAnalysisViewer.add(evolutionAnalysisComponent, BorderLayout.CENTER);

		controlPanel = new JPanel();
		controlPanelMutation = new JPanel();
		controlPanelMutation.setLayout(new BoxLayout(controlPanelMutation, BoxLayout.Y_AXIS));
		controlPanel.add(controlPanelMutation);

		controlPanelFitness = new JPanel();
		controlPanelFitness.setLayout(new BoxLayout(controlPanelFitness, BoxLayout.Y_AXIS));
		controlPanel.add(controlPanelFitness);

		controlPanelGeneral = new JPanel();
		controlPanelGeneral.setLayout(new BoxLayout(controlPanelGeneral, BoxLayout.Y_AXIS));
		controlPanel.add(controlPanelGeneral);

		controlPanelRuner = new JPanel();
		controlPanelRuner.setLayout(new BoxLayout(controlPanelRuner, BoxLayout.Y_AXIS));
		controlPanel.add(controlPanelRuner);

		controlPanelDisplay = new JPanel();
		controlPanelDisplay.setLayout(new BoxLayout(controlPanelDisplay, BoxLayout.Y_AXIS));
		controlPanel.add(controlPanelDisplay);

		controlPanelDisplayAlleles = new JPanel();
		controlPanelDisplayAlleles.setLayout(new BoxLayout(controlPanelDisplayAlleles, BoxLayout.Y_AXIS));
		controlPanel.add(controlPanelDisplayAlleles);

		// mutatation Rate
		JPanel mutateRatePanel = new JPanel();
		JLabel mutateRateLabel = new JLabel("M Rate:");
		mutateRatePanel.add(mutateRateLabel);
		JTextField mutateRateField = new JTextField(evolutionAnalysisViewer.getWidth() / TEXTFEILD_RATIO);
		mutateRateField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				sameEvolution = false;

				nofMutateRate = Integer.parseInt(mutateRateField.getText());
				if (nofMutateRate > genomeLength) {
					JOptionPane.showMessageDialog(null, "The N cannot be higher than the genome Length", "Error",
							JOptionPane.ERROR_MESSAGE);
					nofMutateRate = genomeLength;
					mutateRateField.setText("" + genomeLength);
				}
				geneticAlgorithmCalculator.setMutationRate(nofMutateRate);

			}

		});
		mutateRatePanel.add(mutateRateField);
		JLabel slashNLabel = new JLabel("/" + this.population);
		mutateRatePanel.add(slashNLabel);
		controlPanelMutation.add(mutateRatePanel);

		// selectionType
		JComboBox<String> selectionType = new JComboBox<String>(differentSelectionType);
		selectionType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				sameEvolution = false;
				parentSelectionTypeString = (String) selectionType.getSelectedItem();
				geneticAlgorithmCalculator.setParentSelectionType(parentSelectionTypeString);

			}

		});
		controlPanelMutation.add(selectionType);

		// crossover
		JPanel crossoverPanel = new JPanel();
		JLabel crossoverLabel = new JLabel("Crossover?");
		JRadioButton crossover = new JRadioButton();
		crossover.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				geneticAlgorithmCalculator.setCrossoverOrMutation(crossover.isSelected());
			}
		});
		crossoverPanel.add(crossoverLabel);
		crossoverPanel.add(crossover);
		controlPanelMutation.add(crossoverPanel);

		// Fitness Part

		// Fitness
		JComboBox<String> fitnessType = new JComboBox<String>(differentFitnessType);
		fitnessType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sameEvolution = false;
				fitnessTypeString = (String) fitnessType.getSelectedItem();
				geneticAlgorithmCalculator.setFitnessMethod(fitnessTypeString);
			}
		});
		controlPanelFitness.add(fitnessType);

		JPanel extraFitnessControlPanel = new JPanel();
		JButton loadButton = new JButton("Load Ideal Genotype File");
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String locationOfChromosomeTextFiles = "ChromosomeTextFiles";
				JFileChooser filePicker = new JFileChooser(locationOfChromosomeTextFiles);
				if (filePicker.showSaveDialog(evolutionAnalysisViewer) == JFileChooser.APPROVE_OPTION) {
					idealGenotypeFileLocation = filePicker.getSelectedFile().getPath();
					geneticAlgorithmCalculator.setIdealGenotype(idealGenotypeFileLocation);
				}

			}
		});
		extraFitnessControlPanel.add(loadButton);

		controlPanelFitness.add(extraFitnessControlPanel);
		// targetFitness
		JPanel targetFitnessPanel = new JPanel();
		JLabel targetFitnessLable = new JLabel("Target Fitness:");
		targetFitnessPanel.add(targetFitnessLable);
		JTextField targetFitnessField = new JTextField(evolutionAnalysisViewer.getWidth() / TEXTFEILD_RATIO);
		targetFitnessField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				sameEvolution = false;
				targetFitnessScore = Integer.parseInt(targetFitnessField.getText());

			}

		});
		targetFitnessPanel.add(targetFitnessField);
		controlPanelFitness.add(targetFitnessPanel);

		// Elitism
		JPanel elitismPanel = new JPanel();
		JLabel elitismLable = new JLabel("Elitism");
		elitismPanel.add(elitismLable);
		JTextField elitismField = new JTextField(evolutionAnalysisViewer.getWidth() / TEXTFEILD_RATIO);
		elitismField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				sameEvolution = false;
				elitismNPerPop = Integer.parseInt(elitismField.getText());
				if (nofMutateRate > genomeLength) {
					JOptionPane.showMessageDialog(null, "The elitism cannot be higher than the genome Length", "Error",
							JOptionPane.ERROR_MESSAGE);
					elitismNPerPop = genomeLength;
					mutateRateField.setText("" + elitismNPerPop);
				}
				geneticAlgorithmCalculator.setElitismPercentage(elitismNPerPop);
			}

		});
		elitismPanel.add(elitismField);
		JLabel percentageLable = new JLabel("%");
		elitismPanel.add(percentageLable);
		controlPanelFitness.add(elitismPanel);

		// General Setting

		// PopulationSize
		JPanel populationSizePanel = new JPanel();
		JLabel populationSizeLable = new JLabel("Population Size:");
		populationSizePanel.add(populationSizeLable);
		JTextField populationSizeField = new JTextField(evolutionAnalysisViewer.getWidth() / TEXTFEILD_RATIO);
		populationSizeField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				sameEvolution = false;
				population = Integer.parseInt(populationSizeField.getText());

				geneticAlgorithmCalculator.setSizeOfPopulation(population);
			}

		});
		populationSizePanel.add(populationSizeField);
		controlPanelGeneral.add(populationSizePanel);

		// Generation
		JPanel generationPanel = new JPanel();
		JLabel generationLable = new JLabel("Generations");
		generationPanel.add(generationLable);
		JTextField generationField = new JTextField(evolutionAnalysisViewer.getWidth() / TEXTFEILD_RATIO);
		generationField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				sameEvolution = false;
				numberOfGenerations = Integer.parseInt(generationField.getText());
				evolutionAnalysisComponent.setGenerationNum(numberOfGenerations);
				System.out.println("Num generations: " + Integer.parseInt(generationField.getText()));
			}

		});
		generationPanel.add(generationField);
		controlPanelGeneral.add(generationPanel);

		// Genome Length
		JPanel genomeLengthPanel = new JPanel();
		JLabel genomeLengthLable = new JLabel("Genome Length");
		genomeLengthPanel.add(genomeLengthLable);
		JTextField genomeLengthField = new JTextField(evolutionAnalysisViewer.getWidth() / TEXTFEILD_RATIO);
		genomeLengthField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				sameEvolution = false;
				genomeLength = Integer.parseInt(genomeLengthField.getText());
				geneticAlgorithmCalculator.setNumberOfGenesPerChromosome(genomeLength);
				evolutionAnalysisComponent.setMaximumFitnessScore(genomeLength);
				slashNLabel.setText("/" + genomeLength);

			}

		});
		genomeLengthPanel.add(genomeLengthField);
		controlPanelGeneral.add(genomeLengthPanel);

		// Program Run

		startAndPause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (timer.isRunning()) {
					timer.stop();
					timeHasGone += System.currentTimeMillis() - timeAgo;
					timeAgo = System.currentTimeMillis();
					startAndPause.setText("Continue");
				} else {

					timer.start();
					chromosomeViewer.repaint();
					timeAgo = System.currentTimeMillis();
					if (!sameEvolution) {
						geneticAlgorithmCalculator.startPopulationViewer();
						timeGoes = 0;
						geneticAlgorithmCalculator.createInitialGeneration();
						evolutionAnalysisComponent.clearFitnessScoreLists();
						updateComponent();
						startAndPause.setText("Start");
						evolutionAnalysisComponent.repaint();
						sameEvolution = true;
						generationCount = 0;

					}

					startAndPause.setText("Pause");

				}

			}
		});
		controlPanelRuner.add(startAndPause);

		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				reset();
			}
		});
		controlPanelRuner.add(resetButton);

		runningTime = new JLabel("The Program has run:" + this.timeGoes + "ms");
		controlPanelRuner.add(runningTime);

		// Dispaly

		JPanel displayBestPanel = new JPanel();
		JLabel displayBestLabel = new JLabel("Display Best Line");
		displayBestPanel.add(displayBestLabel);
		JRadioButton displayBest = new JRadioButton();
		displayBest.setSelected(true);
		displayBest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				evolutionAnalysisComponent.setDisplayBest(displayBest.isSelected());
			}
		});
		displayBestPanel.add(displayBest);
		JButton displayBestColor = new JButton("Color");
		displayBestColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					evolutionAnalysisComponent.setColorOfBest(
							JColorChooser.showDialog(colorChooser, "Choose Color for Line", defaultColor));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		displayBestPanel.add(displayBestColor);
		controlPanelDisplay.add(displayBestPanel);

		JPanel displayAvePanel = new JPanel();
		JLabel displayAveLabel = new JLabel("Display Average Line");
		displayAvePanel.add(displayAveLabel);
		JRadioButton displayAve = new JRadioButton();
		displayAve.setSelected(true);
		displayAve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				evolutionAnalysisComponent.setDisplayAve(displayAve.isSelected());
			}
		});

		displayAvePanel.add(displayAve);
		JButton displayAveColor = new JButton("Color");
		displayAveColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					evolutionAnalysisComponent.setColorOfAve(
							JColorChooser.showDialog(colorChooser, "Choose Color for Line", defaultColor));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		displayAvePanel.add(displayAveColor);
		controlPanelDisplay.add(displayAvePanel);

		JPanel displayLowPanel = new JPanel();
		JLabel displayLowLabel = new JLabel("Display Low Line");
		displayLowPanel.add(displayLowLabel);
		JRadioButton displayLow = new JRadioButton();
		displayLow.setSelected(true);
		displayLow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				evolutionAnalysisComponent.setDisplayLow(displayLow.isSelected());
			}
		});
		displayLowPanel.add(displayLow);
		JButton displayLowColor = new JButton("Color");
		displayLowColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					evolutionAnalysisComponent.setColorOfWorst(
							JColorChooser.showDialog(colorChooser, "Choose Color for Line", defaultColor));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		displayLowPanel.add(displayLowColor);
		controlPanelDisplay.add(displayLowPanel);

		JPanel displayHDPanel = new JPanel();
		JLabel displayHDLabel = new JLabel("Display Hamming Distance Line");
		displayHDPanel.add(displayHDLabel);
		JRadioButton displayHD = new JRadioButton();
		displayHD.setSelected(true);
		displayHD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				evolutionAnalysisComponent.setDisplayHD(displayHD.isSelected());
			}
		});
		displayHDPanel.add(displayHD);
		JButton displayHDColor = new JButton("Color");
		displayHDColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					evolutionAnalysisComponent.setColorOfHD(
							JColorChooser.showDialog(colorChooser, "Choose Color for Line", defaultColor));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		displayHDPanel.add(displayHDColor);
		controlPanelDisplay.add(displayHDPanel);

		JPanel displayUNIPanel = new JPanel();
		JLabel displayUNILabel = new JLabel("Display Unique Individuals # Line");
		displayUNIPanel.add(displayUNILabel);
		JRadioButton displayUNI = new JRadioButton();
		displayUNI.setSelected(true);
		displayUNI.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				evolutionAnalysisComponent.setDisplayUNI(displayUNI.isSelected());
			}
		});
		displayUNIPanel.add(displayUNI);
		JButton displayUNIColor = new JButton("Color");
		displayUNIColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					evolutionAnalysisComponent.setColorOfUNI(
							JColorChooser.showDialog(colorChooser, "Choose Color for Line", defaultColor));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		displayUNIPanel.add(displayUNIColor);
		controlPanelDisplay.add(displayUNIPanel);

		JPanel displayPopulationViwerPanel = new JPanel();
		JLabel displayPopulationViwerLabel = new JLabel("Display Population Viewer");
		displayPopulationViwerPanel.add(displayPopulationViwerLabel);
		JButton displayPopulationViwerON = new JButton("ON");
		displayPopulationViwerON.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				geneticAlgorithmCalculator.startPopulationViewer();
			}
		});
		displayPopulationViwerPanel.add(displayPopulationViwerON);
		JButton displayPopulationViwerOFF = new JButton("OFF");
		displayPopulationViwerOFF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				geneticAlgorithmCalculator.closePopulationViewer();
			}
		});

		displayPopulationViwerPanel.add(displayPopulationViwerOFF);
		controlPanelDisplay.add(displayPopulationViwerPanel);

		// Correct Alleles
		JPanel displayCorrectAellelesPanel = new JPanel();
		JLabel displayCorrectAellelesLabel = new JLabel("Display Correct Alleles");
		displayCorrectAellelesPanel.add(displayCorrectAellelesLabel);
		JRadioButton displayCorrectAelleles = new JRadioButton();
		displayCorrectAelleles.setSelected(false);
		displayCorrectAelleles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				evolutionAnalysisComponent.setDisplayAveCorrect(displayCorrectAelleles.isSelected());
			}
		});
		displayCorrectAellelesPanel.add(displayCorrectAelleles);
		JButton displayCorrectAellelesColor = new JButton("Color");
		displayCorrectAellelesColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					evolutionAnalysisComponent.setColorOfAveCorrect(
							JColorChooser.showDialog(colorChooser, "Choose Color for Line", defaultColor));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		displayCorrectAellelesPanel.add(displayCorrectAellelesColor);
		controlPanelDisplayAlleles.add(displayCorrectAellelesPanel);

		// Incorrect Alleles
		JPanel displayIncorrectAellelesPanel = new JPanel();
		JLabel displayIncorrectAellelesLabel = new JLabel("Display Incorrect Alleles");
		displayIncorrectAellelesPanel.add(displayIncorrectAellelesLabel);
		JRadioButton displayIncorrectAelleles = new JRadioButton();
		displayIncorrectAelleles.setSelected(false);
		displayIncorrectAelleles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				evolutionAnalysisComponent.setDisplayAveIncorrect(displayIncorrectAelleles.isSelected());

			}
		});
		displayIncorrectAellelesPanel.add(displayIncorrectAelleles);
		JButton displayIncorrectAellelesColor = new JButton("Color");
		displayIncorrectAellelesColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					evolutionAnalysisComponent.setColorOfAveIncorrect(
							JColorChooser.showDialog(colorChooser, "Choose Color for Line", defaultColor));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		displayIncorrectAellelesPanel.add(displayIncorrectAellelesColor);
		controlPanelDisplayAlleles.add(displayIncorrectAellelesPanel);

		// Incorrect Alleles
		JPanel displayUnknownAellelesPanel = new JPanel();
		JLabel displayUnknownAellelesLabel = new JLabel("Display Unknown Alleles");
		displayUnknownAellelesPanel.add(displayUnknownAellelesLabel);
		JRadioButton displayUnknownAelleles = new JRadioButton();
		displayUnknownAelleles.setSelected(false);
		displayUnknownAelleles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				evolutionAnalysisComponent.setDisplayAveUnknown(displayUnknownAelleles.isSelected());

			}
		});
		displayUnknownAellelesPanel.add(displayUnknownAelleles);
		JButton displayUnknownAellelesColor = new JButton("Color");
		displayUnknownAellelesColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					evolutionAnalysisComponent.setColorOfAveUnknown(
							JColorChooser.showDialog(colorChooser, "Choose Color for Line", defaultColor));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		displayUnknownAellelesPanel.add(displayUnknownAellelesColor);
		controlPanelDisplayAlleles.add(displayUnknownAellelesPanel);

		evolutionAnalysisViewer.add(controlPanel, BorderLayout.SOUTH);

		evolutionAnalysisViewer.setVisible(true);
		evolutionAnalysisViewer.validate();

	}

	/**
	 * This method is used to reset the timer that controls the execution of the
	 * genetic algorithm.<br>
	 * Resets the values of timeHasGone, timeGoes and timeAgo to 0, 0, and
	 * Long.MIN_VALUE, respectively.
	 * 
	 * @return void
	 */
	private void timerReset() {
		this.timeHasGone = 0;
		this.timeGoes = 0;
		this.timeAgo = Long.MIN_VALUE;
	}

	/**
	 * used to control the execution of the genetic algorithm.
	 */
	private Timer timer = new Timer(DELAY, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			updateRunningTimeLable();
			if (generationCount < numberOfGenerations) {

				if (geneticAlgorithmCalculator.returnCurrentBest() >= targetFitnessScore) {
					timer.stop();
					startAndPause.setText("Start");
					sameEvolution = false;
					timerReset();

				} else {
					geneticAlgorithmCalculator.createNewGeneration();
					System.out.println("best: " + geneticAlgorithmCalculator.returnCurrentBest() + " ave: "
							+ geneticAlgorithmCalculator.returnCurrentAverage() + " worst: "
							+ geneticAlgorithmCalculator.returnCurrentWorst());
					updateComponent();
//					evolutionAnalysisComponent.updateTimeGoes(System.currentTimeMillis() - timeAgo);
					timeGoes = System.currentTimeMillis() - timeAgo - 100;
					geneticAlgorithmCalculator.updateNewChromosomesToPopulationViewer();
					generationCount++;
				}
				chromosomeViewer.setChromosome(geneticAlgorithmCalculator.getBestChromosome());
			} else {

				timer.stop();
				startAndPause.setText("Start");
			}

		}
	});

	/**
	 * 
	 * The updateRunningTimeLable method updates the text displayed on the
	 * runningTime label, which shows the total running time of the program.
	 * 
	 * @return void
	 */
	private void updateRunningTimeLable() {
		runningTime.setText("The Program has run:" + (this.timeGoes + this.timeHasGone) + "milliseconds");
	}

	/**
	 * 
	 * Resets the genetic algorithm and the graphical user interface. This method is
	 * mainly called when the user clicks the "Reset" button.
	 * 
	 * @return void
	 */
	private void reset() {
		timerReset();
		this.sameEvolution = false;
		this.timer.stop();
		this.startAndPause.setText("Start");
		this.evolutionAnalysisComponent.graphRest();
		this.geneticAlgorithmCalculator.closePopulationViewer();
	}

	/**
	 * 
	 * Updates the graphical user interface to display the current state of the
	 * genetic algorithm. This method is called after a new generation is created.
	 * 
	 * @return void
	 */
	private void updateComponent() {
		evolutionAnalysisComponent.updataGeneration(geneticAlgorithmCalculator.returnCurrentBest(),
				geneticAlgorithmCalculator.returnCurrentAverage(), geneticAlgorithmCalculator.returnCurrentWorst(),
				geneticAlgorithmCalculator.returnCurrentHammingDistanceDiversity(),
				geneticAlgorithmCalculator.returnCurrentNumberOfUniqueIndividuals(),
				geneticAlgorithmCalculator.returnAverageCorrectAlleles(),
				geneticAlgorithmCalculator.returnAverageIncorrectAlleles(),
				geneticAlgorithmCalculator.returnAverageUnknownAlleles());
	}

}
