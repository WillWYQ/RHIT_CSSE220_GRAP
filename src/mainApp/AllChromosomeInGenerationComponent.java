package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * Class: AllChromosomeInGenerationComponent<br>
 * The class AllChromosomeInGenerationComponent is a component for displaying
 * the information about the chromosomes in a given generation.<br>
 * <br>
 * Purpose: <br>
 * The purpose of this class is to represent an array of Chromosome objects in a
 * graphical form. It extends the JComponent class from the Java Swing library,
 * allowing it to be used in a GUI.<br>
 * 
 * Restrictions: <br>
 * Unable to adjust size of Gene<br>
 * 
 * For Example:
 * 
 * <pre>
 * AllChromosomeInGenerationComponent chromosomeDisplay = new
 * AllChromosomeInGenerationComponent();
 * 
 * 
 * <pre>
 * 
 * @see PopulationViewer
 * @see JComponent
 * @author Yueqiao Wang and Rebecca Testa
 */
public class AllChromosomeInGenerationComponent extends JComponent {
	/** The serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** The current chromosomes list */
	private Chromosome[] currentChromosomes;

	/** The color for the genes that have a false value */
	private static final Color FALSE_COLOR = Color.CYAN;
	/** The color for the genes that have a true value */
	private static final Color TRUE_COLOR = Color.BLACK;

	/** The initial width of the intervals */
	private static final int INTITIAL_INTERVAL_WIDTH = 100;
	/** The initial height of the intervals */
	private static final int INTITIAL_INTERVAL_HEIGHT = 100;

	/** The number of chromosomes to display per row */
	private static final int CHROMOSOMES_PER_ROW = 10;
	/** The interval between the chromosomes */
	private static final int CHROMOSOMES_INTERVAL = 1;
	/** The number of genes to display per row */
	private static final int GENES_PER_ROW = 10;

	/** The number of chromosomes */
	private int numberOFChromosomes = 0;
	/** The number of chromosome rows */
	private int numberChromosomesRow = 0;
	/** The width of a chromosome */
	private int widthPerChromosome = 0;
	/** The height of a chromosome */
	private int heightPerChromosome = 0;
	/** The number of genes */
	private int numberOfGenes = 0;
	/** The number of gene rows */
	private int numberGenesRow = 0;
	/** The width of a gene */
	private int widthOfGene = 0;
	/** The height of a gene */
	private int heightOfGene = 0;
	/** The width of the intervals */
	private int widthOFInterval = 0;
	/** The height of the intervals */
	private int heightOFInterval = 0;

	/**
	 * Overrides the paintComponent method to display the chromosomes and their
	 * information.
	 * 
	 * @param g2d the graphics object
	 */
	@Override
	protected void paintComponent(Graphics g2d) {
		super.paintComponent(g2d);
		Graphics2D g = (Graphics2D) g2d;
		g.translate(INTITIAL_INTERVAL_WIDTH, INTITIAL_INTERVAL_HEIGHT);
		int currentX = 0;
		int currentY = 0;
		int currentLineOFGene = 0;
		int currentRowOFGene = 0;
		int currentLineOFChromosomes = 0;
		int currentRowOFChromosomes = 0;

		for (int i = 0; i < numberOFChromosomes; i++) {
			currentLineOFGene = 0;
			currentRowOFGene = 0;
			ArrayList<Gene> thisChromosome = currentChromosomes[i].getGenesAsArrayList();
			for (int j = 0; j < numberOfGenes; j++) {
				currentX = currentLineOFChromosomes * (this.widthPerChromosome + this.widthOFInterval)
						+ currentLineOFGene * this.widthOfGene;
				currentY = currentRowOFChromosomes * (this.heightPerChromosome + this.heightOFInterval)
						+ currentRowOFGene * this.heightOfGene;
//				System.err.println(currentX + " " + currentY);
				g.drawRect(currentX, currentY, widthOfGene, heightOfGene);
				if (thisChromosome.get(j).getCurrentValue() == 1) {
					g.setColor(TRUE_COLOR);
				} else {
					g.setColor(FALSE_COLOR);
				}
				g.fillRect(currentX, currentY, widthOfGene, heightOfGene);
				g.setColor(Color.black);
//				System.err.println("Graphing");
				currentLineOFGene++;
				if (currentLineOFGene >= CHROMOSOMES_PER_ROW) {
					currentLineOFGene = 0;
					currentRowOFGene++;
				}
			}

			currentLineOFChromosomes++;
			if (currentLineOFChromosomes >= CHROMOSOMES_PER_ROW) {
				currentLineOFChromosomes = 0;
				currentRowOFChromosomes++;
			}

		}
		g.dispose();
	}

	/**
	 * Updates the current chromosomes.
	 * 
	 * @param currentChromosomes the new chromosomes
	 */
	public void update(Chromosome[] currentChromosomes) {
		this.currentChromosomes = currentChromosomes;
		this.numberOFChromosomes = this.currentChromosomes.length;
		this.numberChromosomesRow = this.currentChromosomes.length / CHROMOSOMES_PER_ROW;
		this.numberOfGenes = this.currentChromosomes[0].getGenesAsArrayList().size();
		this.numberGenesRow = this.numberOfGenes / GENES_PER_ROW;
//TODO: enable adjust
//		System.err.println("update Chromosomes!  WIDTH / (CHROMOSOMES_PER_ROW + CHROMOSOMES_INTERVAL);:"
//				+ WIDTH / (CHROMOSOMES_PER_ROW + CHROMOSOMES_INTERVAL));

//		this.widthPerChromosome = WIDTH / (CHROMOSOMES_PER_ROW + CHROMOSOMES_INTERVAL);
//		this.numberOfGenes = this.currentChromosomes[0].getGenesAsArrayList().size();
//		this.numberGenesRow = this.numberOfGenes / GENES_PER_ROW;
//		this.widthOfGene = this.widthPerChromosome / GENES_PER_ROW;
//		this.heightOfGene = this.widthOfGene;
//		this.widthOFInterval = this.widthPerChromosome / AllChromosomeInGenerationComponent.CHROMOSOMES_PER_ROW;
//		this.heightOFInterval = this.widthOFInterval;
//		repaint();

		// fixed size version:

		this.widthOfGene = 5;
		this.heightOfGene = 5;
		this.widthOFInterval = 20;
		this.heightOFInterval = 20;
		this.widthPerChromosome = widthOfGene * GENES_PER_ROW;
		this.heightPerChromosome = numberGenesRow * heightOfGene;
		repaint();
		revalidate();

	}
}
