package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * Class: EvolutionAnalysisComponent <br>
 * a JComponent for visualizing and tracking the statistics of a genetic
 * algorithm.<br>
 * Purpose:provide a graphical user interface for viewing the results of a
 * genetic algorithm simulation. <br>
 * <br>
 * 
 * <br>
 * Restrictions: <br>
 * The number of Generation cannot be too large <br>
 * <br>
 * For example:
 * 
 * <pre>
 * EvolutionAnalysisComponent evolutionAnalysis = new
 * EvolutionAnalysisComponent();
 * 
 * 
 * <pre>
 * 
 * @see EvolutionAnalysisViewer
 * @see JComponent
 * @author Yueqiao Wang and Rebecca Testa
 */
public class EvolutionAnalysisComponent extends JComponent {
	/**
	 * A constant used to ensure that the class is compatible with serialization.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * STATISTIC_COMPONENT_IN_Y represents the number of parts on the Y axis.
	 */
	private static final int STATISTIC_COMPONENT_IN_Y = 11; // 11 part on Y axis
	/**
	 * STATISTIC_COMPONENT_IN_X represents the number of parts on the X axis.
	 */
	private static final int STATISTIC_COMPONENT_IN_X = 12;// 12 part on X axis

	/**
	 * LOCATION_OF_X_SCALE is the location of X scale.
	 */
	private static final int LOCATION_OF_X_SCALE = 10;

	/**
	 * ORIGIN_X represents the origin on the X axis.
	 */
	private static final int ORIGIN_X = 0;
	/**
	 * ORIGIN_Y represents the origin on the Y axis.
	 */
	private static final int ORIGIN_Y = 0;
	/**
	 * NUM_OF_INTERVAL_ON_X_FOR_DATA is the number of intervals on the X axis for
	 * data.
	 */
	private static final int NUM_OF_INTERVAL_ON_X_FOR_DATA = 10;
	/**
	 * NUM_OF_INTERVAL_ON_Y_FOR_DATA is the number of intervals on the Y axis for
	 * data.
	 */
	private static final int NUM_OF_INTERVAL_ON_Y_FOR_DATA = 10;
	/**
	 * INTERVAL_Y is the interval on the Y axis.
	 */
	private static final int INTERVAL_Y = 10;
	/**
	 * INTERVAL_DISTANCE_Y is the interval distance on the Y axis.
	 */
	private static final int INTERVAL_DISTANCE_Y = 10;
	/**
	 * HEIGHT_FOR_TITLE is the height for the title.
	 */
	private static final int HEIGHT_FOR_TITLE = 20;
	/**
	 * TICK_MARK_LENGTH_FACTOR is the length factor of tick mark.
	 */
	private static final int TICK_MARK_LENGTH_FACTOR = 4;

	/**
	 * KEY_OF_BEST is the key for best fitness.
	 */
	private static final String KEY_OF_BEST = "Best Fitness";
	/**
	 * KEY_OF_AVE is the key for average fitness.
	 */
	private static final String KEY_OF_AVE = "Ave Fitness";
	/**
	 * KEY_OF_LOW is the key for low fitness.
	 */
	private static final String KEY_OF_LOW = "Low Fitness";
	/**
	 * KEY_OF_HAMMING_DISTANCE is the key for hamming distance.
	 */
	private static final String KEY_OF_HAMMING_DISTANCE = "Hamming Distance";
	/**
	 * KEY_OF_N_OF_UNIQUE_INDIVIDUALS is the key for number of unique individuals.
	 */
	private static final String KEY_OF_N_OF_UNIQUE_INDIVIDUALS = "Number of Unique Individuals";
	/**
	 * KEY_OF_AVERAGE_CORRECT_ALLELES is the key for Average Correct Alleles.
	 */
	private static final String KEY_OF_AVERAGE_CORRECT_ALLELES = "Average Correct Alleles";
	/**
	 * KEY_OF_AVERAGE_INCORRECT_ALLELS is the key for Average Incorrect Alleles
	 */
	private static final String KEY_OF_AVERAGE_INCORRECT_ALLELES = "Average Incorrect Alleles";
	/**
	 * KEY_OF_AVERAGE_UNKNOWN_ALLELS is the key for Average Unknown Alleles
	 */
	private static final String KEY_OF_AVERAGE_UNKNOWN_ALLELES = "Average Unknown Alleles";

	private static final int LOCATION_RATIO_OF_KEY_X = 9;
	private static final int LOCATION_RATIO_OF_KEY_Y = 3;

	private static final int KEY_REC_SIZE_X = 10;
	private static final int KEY_REC_SIZE_Y = 10;
	private static final int KEY_OFFSET_X = 15;

	/**
	 * generationNum is the number of generations.
	 */
	private int generationNum = 100;

	/**
	 * maximumFitnessScore is the maximum fitness score.
	 */
	private int maximumFitnessScore = 100;
	/**
	 * 
	 * intervalDistanceX is the interval distance on the X axis.
	 */
	private int intervalDistanceX = generationNum / NUM_OF_INTERVAL_ON_X_FOR_DATA;
	/**
	 * currentGeneration is the current generation.
	 */
	private int currentGeneration;

	/**
	 * bestFitness is a list of best fitness scores.
	 */
	private ArrayList<Integer> bestFitness = new ArrayList<Integer>();
	/**
	 * aveFitness is a list of average fitness scores.
	 */
	private ArrayList<Integer> aveFitness = new ArrayList<Integer>();
	/**
	 * lowFitness is a list of low fitness scores.
	 */
	private ArrayList<Integer> lowFitness = new ArrayList<Integer>();
	/**
	 * hammingDistance is a list of hamming distances.
	 */
	private ArrayList<Integer> hammingDistance = new ArrayList<Integer>();
	/**
	 * nOfUniqueIndividuals is a list of the number of unique individuals.
	 */
	private ArrayList<Integer> nOfUniqueIndividuals = new ArrayList<Integer>();
	/**
	 * averageCorrectAlleles is a list of the number of Average Correct Alleles.
	 */
	private ArrayList<Integer> averageCorrectAlleles = new ArrayList<Integer>();
	/**
	 * averageIncorrectAlleles is a list of the number of Average Incorrect Alleles.
	 */
	private ArrayList<Integer> averageIncorrectAlleles = new ArrayList<Integer>();

	/**
	 * averageUnknownAlleles is a list of the number of Average Unknown Alleles.
	 */
	private ArrayList<Integer> averageUnknownAlleles = new ArrayList<Integer>();

	/**
	 * displayBest is a flag for displaying the best fitness scores.
	 */
	private boolean displayBest = true;
	/**
	 * displayAve is a flag for displaying the average fitness scores.
	 */
	private boolean displayAve = true;
	/**
	 * displayLow is a flag for displaying the low fitness scores.
	 */
	private boolean displayLow = true;
	/**
	 * displayHD is a flag for displaying the hamming distances.
	 */
	private boolean displayHD = true;
	/**
	 * displayUNI is a flag for displaying the number of unique individuals.
	 */
	private boolean displayUNI = true;
	/**
	 * displayAveCorrect is a flag for displaying the Average Correct Alleles.
	 */
	private boolean displayAveCorrect = false;
	/**
	 * displayAveIncorrect is a flag for displaying the Average Incorrect Alleles.
	 */
	private boolean displayAveIncorrect = false;
	/**
	 * displayAveUnknown is a flag for displaying the Average Unknown Alleles.
	 */
	private boolean displayAveUnknown = false;

	/**
	 * colorOfBest is the color for Best Fitness Line
	 */
	private Color colorOfBest = Color.GREEN;

	/**
	 * colorOfAve is the color for average Fitness Line
	 */
	private Color colorOfAve = Color.YELLOW;

	/**
	 * colorOfWorst is the color for worst Fitness Line
	 */
	private Color colorOfWorst = Color.RED;

	/**
	 * colorOfHD is the color for Hamming distances
	 */
	private Color colorOfHD = Color.PINK;

	/**
	 * colorOfUNI is the color for the number of unique individuals.
	 */
	private Color colorOfUNI = Color.CYAN;

	/**
	 * colorOfAveCorrect is the color for the Average Correct Alleles.
	 */
	private Color colorOfAveCorrect = Color.MAGENTA;

	/**
	 * colorOfAveIncorrect is the color for the Average Incorrect Alleles.
	 */
	private Color colorOfAveIncorrect = new Color((int) (Math.random() * 256), (int) (Math.random() * 256),
			(int) (Math.random() * 256));

	/**
	 * colorOfAveUnknown is the color for the Average Unknown Alleles.
	 */
	private Color colorOfAveUnknown = new Color((int) (Math.random() * 256), (int) (Math.random() * 256),
			(int) (Math.random() * 256));

	@Override
	protected void paintComponent(Graphics g2d) {

		super.paintComponent(g2d);
		Graphics2D g = (Graphics2D) g2d;

		int unitX = this.getWidth() / STATISTIC_COMPONENT_IN_X;
		int unitY = this.getHeight() / STATISTIC_COMPONENT_IN_Y;
		int dx = unitX * this.NUM_OF_INTERVAL_ON_X_FOR_DATA / this.generationNum;
		int dy = unitY * this.NUM_OF_INTERVAL_ON_Y_FOR_DATA / this.maximumFitnessScore;

		g2d.translate(0, HEIGHT_FOR_TITLE);
		// y-axis
		// y-axis
		g.drawLine(this.ORIGIN_X + unitX, ORIGIN_Y, this.ORIGIN_X + unitX, dy * this.maximumFitnessScore);

		for (int i = 0; i <= maximumFitnessScore; i += INTERVAL_DISTANCE_Y) {
			int currentY = ORIGIN_Y + i * dy;

			g.drawString("" + (maximumFitnessScore - i), ORIGIN_X, currentY);
			g.drawLine(unitX - unitX / TICK_MARK_LENGTH_FACTOR, currentY, unitX + unitX / TICK_MARK_LENGTH_FACTOR,
					currentY);
		}

		// x-axis
		g.drawLine(ORIGIN_X + unitX, dy * this.maximumFitnessScore, ORIGIN_X + unitX + dx * this.generationNum,
				dy * this.maximumFitnessScore);

		for (int i = 0; i <= generationNum; i += intervalDistanceX) {
			int currentX = ORIGIN_X + unitX + i * dx;

			int yforXScale = unitY * LOCATION_OF_X_SCALE;
			int yForAxisLine = dy * this.maximumFitnessScore;

			g.drawString("" + i, currentX, yforXScale);
			g.drawLine(currentX, yForAxisLine + unitY / TICK_MARK_LENGTH_FACTOR, currentX,
					yForAxisLine - unitY / TICK_MARK_LENGTH_FACTOR);
		}

		// TODO: Optimize!!!!
		// key
		g.translate(LOCATION_RATIO_OF_KEY_X * unitX, LOCATION_RATIO_OF_KEY_Y * unitY);

		// Green - Best
		if (displayBest) {
			g.setColor(colorOfBest);
			g.fillRect(0, 0, KEY_REC_SIZE_X, KEY_REC_SIZE_Y);
			g.drawString(KEY_OF_BEST, KEY_OFFSET_X, 10);
		}
		// Yellow - Ave
		if (displayAve) {
			g.setColor(colorOfAve);
			g.fillRect(0, 20, KEY_REC_SIZE_X, KEY_REC_SIZE_Y);
			g.drawString(KEY_OF_AVE, KEY_OFFSET_X, 30);
		}
		// Yellow - Ave
		if (displayLow) {
			g.setColor(colorOfWorst);
			g.fillRect(0, 40, KEY_REC_SIZE_X, KEY_REC_SIZE_Y);
			g.drawString(KEY_OF_LOW, KEY_OFFSET_X, 50);
		}
		// Pink - Hamming Distance
		if (displayHD) {
			g.setColor(colorOfHD);
			g.fillRect(0, 60, KEY_REC_SIZE_X, KEY_REC_SIZE_Y);
			g.drawString(KEY_OF_HAMMING_DISTANCE, KEY_OFFSET_X, 70);
		}
		// CYAN - Hamming Distance
		if (displayUNI) {
			g.setColor(colorOfUNI);
			g.fillRect(0, 80, KEY_REC_SIZE_X, KEY_REC_SIZE_Y);
			g.drawString(KEY_OF_N_OF_UNIQUE_INDIVIDUALS, KEY_OFFSET_X, 90);
		}

		if (displayAveCorrect) {
			g.setColor(colorOfAveCorrect);
			g.fillRect(0, 100, KEY_REC_SIZE_X, KEY_REC_SIZE_Y);
			g.drawString(KEY_OF_AVERAGE_CORRECT_ALLELES, KEY_OFFSET_X, 110);
		}
		if (displayAveIncorrect) {
			g.setColor(colorOfAveIncorrect);
			g.fillRect(0, 120, KEY_REC_SIZE_X, KEY_REC_SIZE_Y);
			g.drawString(KEY_OF_AVERAGE_INCORRECT_ALLELES, KEY_OFFSET_X, 130);
		}
		if (displayAveUnknown) {
			g.setColor(colorOfAveUnknown);
			g.fillRect(0, 140, KEY_REC_SIZE_X, KEY_REC_SIZE_Y);
			g.drawString(KEY_OF_AVERAGE_UNKNOWN_ALLELES, KEY_OFFSET_X, 150);
		}

		g.translate(-LOCATION_RATIO_OF_KEY_X * unitX, -LOCATION_RATIO_OF_KEY_Y * unitY);

		if (displayBest) {
			g.setColor(colorOfBest);
			for (int i = 1; i <= currentGeneration; i++) {
				g.drawLine(unitX + (i - 1) * dx, dy * maximumFitnessScore - (bestFitness.get(i - 1) * dy),
						unitX + dx * i, dy * maximumFitnessScore - bestFitness.get(i) * dy);
				System.out.println("currentGeneration --- " + i);
			}
		}
		if (displayAve) {
			g.setColor(colorOfAve);
			for (int i = 1; i <= currentGeneration; i++) {
				g.drawLine(unitX + (i - 1) * dx, dy * maximumFitnessScore - aveFitness.get(i - 1) * dy, unitX + dx * i,
						dy * maximumFitnessScore - aveFitness.get(i) * dy);
			}
		}
		if (displayLow) {
			g.setColor(colorOfWorst);
			for (int i = 1; i <= currentGeneration; i++) {
				g.drawLine(unitX + (i - 1) * dx, dy * maximumFitnessScore - lowFitness.get(i - 1) * dy, unitX + dx * i,
						dy * maximumFitnessScore - lowFitness.get(i) * dy);
			}
		}
		if (displayHD) {
			g.setColor(colorOfHD);
			for (int i = 1; i <= currentGeneration; i++) {
				g.drawLine(unitX + (i - 1) * dx, dy * maximumFitnessScore - hammingDistance.get(i - 1) * dy,
						unitX + dx * i, dy * maximumFitnessScore - hammingDistance.get(i) * dy);
			}
		}
		if (displayUNI) {
			g.setColor(colorOfUNI);
			for (int i = 1; i <= currentGeneration; i++) {
				g.drawLine(unitX + (i - 1) * dx, dy * maximumFitnessScore - nOfUniqueIndividuals.get(i - 1) * dy,
						unitX + dx * i, dy * maximumFitnessScore - nOfUniqueIndividuals.get(i) * dy);
			}
		}

		if (displayAveCorrect) {
			g.setColor(colorOfAveCorrect);
			for (int i = 1; i <= currentGeneration; i++) {
				g.drawLine(unitX + (i - 1) * dx, dy * maximumFitnessScore - averageCorrectAlleles.get(i - 1) * dy,
						unitX + dx * i, dy * maximumFitnessScore - averageCorrectAlleles.get(i) * dy);
			}
		}
		if (displayAveIncorrect) {
			g.setColor(colorOfAveIncorrect);
			for (int i = 1; i <= currentGeneration; i++) {
				g.drawLine(unitX + (i - 1) * dx, dy * maximumFitnessScore - averageIncorrectAlleles.get(i - 1) * dy,
						unitX + dx * i, dy * maximumFitnessScore - averageIncorrectAlleles.get(i) * dy);
			}
		}
		if (displayAveUnknown) {
			g.setColor(colorOfAveUnknown);
			for (int i = 1; i <= currentGeneration; i++) {
				g.drawLine(unitX + (i - 1) * dx, dy * maximumFitnessScore - averageUnknownAlleles.get(i - 1) * dy,
						unitX + dx * i, dy * maximumFitnessScore - averageUnknownAlleles.get(i) * dy);
			}
		}

	}

	/**
	 * The method updates the values of best fitness score, average fitness score,
	 * low fitness score, hamming distance and number of unique individuals. It also
	 * calls the repaint method to update the display.
	 * 
	 * @param newBest              The new best fitness score
	 * @param newAve               The new average fitness score
	 * @param newLow               The new low fitness score
	 * @param hammingDistance      The new hamming distance
	 * @param nOfUniqueIndividuals The new number of unique individuals
	 */
	public void updataGeneration(int newBest, int newAve, int newLow, int hammingDistance, int nOfUniqueIndividuals,
			int aveCorrect, int aveIncorrect, int aveUnknown) {
		this.currentGeneration = bestFitness.size();
		this.bestFitness.add(newBest);
		this.aveFitness.add(newAve);
		this.lowFitness.add(newLow);
		this.hammingDistance.add(hammingDistance);
		this.nOfUniqueIndividuals.add(nOfUniqueIndividuals);
		this.averageCorrectAlleles.add(aveCorrect);
		this.averageIncorrectAlleles.add(aveIncorrect);
		this.averageUnknownAlleles.add(aveUnknown);
		this.repaint();
	}

	/**
	 * The method updates the value of hamming distance.
	 * 
	 * @param hammingDistance The new hamming distance
	 */
	public void updateHammingDistance(int hammingDistance) {
		this.hammingDistance.add(hammingDistance);
	}

	/**
	 * The method updates the value of number of unique individuals.
	 * 
	 * @param nOfUniqueIndividuals The new number of unique individuals
	 */
	public void updatenOfUniqueIndividuals(int nOfUniqueIndividuals) {
		this.nOfUniqueIndividuals.add(nOfUniqueIndividuals);
	}

	/**
	 * The method sets the display of best fitness score graph.
	 * 
	 * @param displayBest The boolean value to display or hide the graph
	 */
	public void setDisplayBest(boolean displayBest) {
		this.displayBest = displayBest;
		repaint();
	}

	/**
	 * The method sets the display of average fitness score graph.
	 * 
	 * @param displayAve The boolean value to display or hide the graph
	 */
	public void setDisplayAve(boolean displayAve) {
		this.displayAve = displayAve;
		repaint();
	}

	/**
	 * The method sets the display of low fitness score graph.
	 * 
	 * @param displayLow The boolean value to display or hide the graph
	 */
	public void setDisplayLow(boolean displayLow) {
		this.displayLow = displayLow;
		repaint();
	}

	/**
	 * The method sets the display of hamming distance graph.
	 * 
	 * @param displayHD The boolean value to display or hide the graph
	 */
	public void setDisplayHD(boolean displayHD) {
		this.displayHD = displayHD;
		repaint();
	}

	/**
	 * The `setDisplayUNI` method sets the displayUNI instance variable to the
	 * specified value.
	 * 
	 * @param displayUNI - a boolean value indicating whether to display the number
	 *                   of unique individuals or not.
	 */
	public void setDisplayUNI(boolean displayUNI) {
		this.displayUNI = displayUNI;
		repaint();
	}

	/**
	 * The method sets the display of Average Correct Alleles graph.
	 * 
	 * @param displayAveCorrect The boolean value to display or hide the graph
	 */
	public void setDisplayAveCorrect(boolean displayAveCorrect) {
		this.displayAveCorrect = displayAveCorrect;
		repaint();
	}

	/**
	 * The method sets the display of Average Incorrect Alleles graph.
	 * 
	 * @param displayAveIncorrect The boolean value to display or hide the graph
	 */

	public void setDisplayAveIncorrect(boolean displayAveIncorrect) {
		this.displayAveIncorrect = displayAveIncorrect;
		repaint();
	}

	/**
	 * The method sets the display of Average Unknown Alleles graph.
	 * 
	 * @param displayAveUnknown The boolean value to display or hide the graph
	 */

	public void setDisplayAveUnknown(boolean displayAveUnknown) {
		this.displayAveUnknown = displayAveUnknown;
		repaint();
	}

	/**
	 * 
	 * Sets the current generation.
	 * 
	 * @param currentGeneration an int representing the current generation.
	 * @return void
	 */
	public void setCurrentGeneration(int currentGeneration) {
		this.currentGeneration = currentGeneration;
	}

	/**
	 * 
	 * Sets the color of the best chromosome Line.
	 * 
	 * @param colorOfBest a Color object representing the color of the best
	 *                    chromosome Line.
	 * @return void
	 */
	public void setColorOfBest(Color colorOfBest) {
		this.colorOfBest = colorOfBest;
		repaint();
	}

	/**
	 * 
	 * Sets the color of the average chromosome Line.
	 * 
	 * @param colorOfAve a Color object representing the color of the average
	 *                   chromosome.
	 * @return void
	 */
	public void setColorOfAve(Color colorOfAve) {
		this.colorOfAve = colorOfAve;
		repaint();
	}

	/**
	 * 
	 * Sets the color of the worst chromosome line.
	 * 
	 * @param colorOfWorst a Color object representing the color of the worst
	 *                     chromosome.
	 * @return void
	 */
	public void setColorOfWorst(Color colorOfWorst) {
		this.colorOfWorst = colorOfWorst;
		repaint();
	}

	/**
	 * 
	 * Sets the color of the Hamming Distance Line.
	 * 
	 * @param colorOfHD a Color object representing the color of the Hamming
	 *                  Distance.
	 * @return void
	 */
	public void setColorOfHD(Color colorOfHD) {
		this.colorOfHD = colorOfHD;
		repaint();
	}

	/**
	 * 
	 * Sets the color of the Unique Chromosome Line.
	 * 
	 * @param colorOfUNI a Color object representing the color of the Unique
	 *                   Chromosome Line
	 * @return void
	 */
	public void setColorOfUNI(Color colorOfUNI) {
		this.colorOfUNI = colorOfUNI;
		repaint();
	}

	/**
	 * 
	 * Sets the color of the average correct alleles.
	 * 
	 * @param colorOfAveCorrect a Color object representing the color of average
	 *                          correct alleles
	 * @return void
	 */
	public void setColorOfAveCorrect(Color colorOfAveCorrect) {
		this.colorOfAveCorrect = colorOfAveCorrect;
		repaint();
	}

	/**
	 * 
	 * Sets the color of the average incorrect alleles.
	 * 
	 * @param colorOfAveIncorrect a Color object representing the color of the
	 *                            average incorrect alleles.
	 * @return void
	 */
	public void setColorOfAveIncorrect(Color colorOfAveIncorrect) {
		this.colorOfAveIncorrect = colorOfAveIncorrect;
		repaint();
	}

	/**
	 * 
	 * Sets the color of the average unknown alleles.
	 * 
	 * @param colorOfAveUnknown a Color object representing the color of the average
	 *                          unknown alleles.
	 * @return void
	 */
	public void setColorOfAveUnknown(Color colorOfAveUnknown) {
		this.colorOfAveUnknown = colorOfAveUnknown;
		repaint();
	}

	/**
	 * 
	 * Resets the graph by clearing the best fitness, average fitness, low fitness,
	 * hamming distance and number of unique individuals data. Also sets the current
	 * generation back to 0. Finally, calls the repaint method to redraw the graph.
	 */
	public void graphRest() {
		this.bestFitness.clear();
		this.aveFitness.clear();
		this.lowFitness.clear();
		this.hammingDistance.clear();
		this.nOfUniqueIndividuals.clear();
		this.averageCorrectAlleles.clear();
		this.averageIncorrectAlleles.clear();
		this.averageUnknownAlleles.clear();
		this.currentGeneration = 0;
		repaint();
	}

	/**
	 * The `clearFitnessScoreLists` method clears the bestFitness, aveFitness, and
	 * lowFitness lists.
	 */
	public void clearFitnessScoreLists() {
		this.bestFitness.clear();
		this.aveFitness.clear();
		this.lowFitness.clear();
	}

	/**
	 * The `setGenerationNum` method sets the generationNum instance variable to the
	 * specified value.
	 * 
	 * @param generationNum - an integer value representing the current generation
	 *                      number.
	 */
	public void setGenerationNum(int generationNum) {
		this.generationNum = generationNum;

	}

	/**
	 * The `setMaximumFitnessScore` method sets the maximumFitnessScore instance
	 * variable to the specified value.
	 * 
	 * @param gemoLength - an integer value representing the maximum fitness score.
	 */
	public void setMaximumFitnessScore(int gemoLength) {
		this.maximumFitnessScore = gemoLength;
	}
}
