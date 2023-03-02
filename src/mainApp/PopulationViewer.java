package mainApp;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Class:PopulationViewer<br>
 * display the population analysis of chromosomes in a graphical user interface
 * (GUI) using Java Swing.<br>
 * <br>
 * Purpose: <br>
 * The purpose of this class is to provide a visual representation of the
 * chromosomes in current generation. The class creates a JFrame to display the
 * population and updates it whenever the chromosomes are updated.<br>
 * 
 * Restrictions: <br>
 * NONE<br>
 * 
 * For Example:
 * 
 * <pre>
 * PopulationViewer viewer = new PopulationViewer();
 * 
 * <pre>
 * 
 * @see GeneticAlgorithmCalculator
 * @see AllChromosomeInGenerationComponent
 * @author Yueqiao Wang and Rebecca Testa
 */
public class PopulationViewer {
	/** The default width of the population viewer. */
	private static final int DEFAULT_WIDTH = 1000;
	/** The default height of the population viewer. */
	private static final int DEFAULT_HEIGHT = 1000;

	/** The JFrame object that displays the population viewer. */
	private JFrame populationViewer = new JFrame("Population Analysis");
	/** The array of chromosomes in the current generation. */
	private Chromosome[] currentChromosomes;
	/**
	 * The `AllChromosomeInGenerationComponent` component that displays all the
	 * chromosomes in a single generation.
	 */
	private AllChromosomeInGenerationComponent allChromosome;

	/**
	 * Constructs a new `PopulationViewer` object. This creates a JFrame object with
	 * the specified title and adds the `AllChromosomeInGenerationComponent`
	 * component to it.
	 */
	public PopulationViewer() {
		allChromosome = new AllChromosomeInGenerationComponent();
		allChromosome.setVisible(true);
		JScrollPane allChromosomePanel = new JScrollPane(allChromosome);

		allChromosomePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		allChromosomePanel.setPreferredSize(allChromosome.getPreferredSize());
		allChromosomePanel.createHorizontalScrollBar();

		populationViewer.add(allChromosomePanel);
		populationViewer.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		populationViewer.setVisible(true);
		populationViewer.validate();

	}

	/**
	 * Updates the chromosomes in the current generation.
	 *
	 * @param newChromosomes The new chromosomes in the current generation.
	 */
	public void updateChromosomes(Chromosome[] newChromosomes) {

		this.currentChromosomes = newChromosomes;
		allChromosome.update(this.currentChromosomes);
		allChromosome.repaint();
	}

	/**
	 * Closes the population viewer.
	 */
	public void close() {
		populationViewer.setVisible(false);
	}

	/**
	 * Opens the population viewer.
	 */
	public void open() {
		populationViewer.setVisible(true);
	}

}
