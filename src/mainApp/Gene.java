package mainApp;

/**
 * Class: Gene <br>
 * Purpose: The Gene class represents a single gene in a Chromosome. <br>
 * <br>
 * Each Gene has a currentValue, expectedValue and isCorrect property. <br>
 * <br>
 * Restrictions <br>
 * For example:
 * 
 * <pre>
 * Gene gene1 = new Gene(true, false); Gene gene2 = new Gene(false);
 * 
 * <pre>
 * 
 * @author Yueqiao Wang and Rebecca Testa
 */
class Gene {

	/**
	 * represents the current value of a Gene object.
	 */
	private int currentValue;

	private static final int CORRECT_ALLELE = 1;
	private static final int INCORRECT_ALLELE = 0;
	private static final int UNKNOWN_ALLELE = 2;

	public Gene() {
		double randomValue = Math.random();
		if (randomValue < 0.5) {
			this.currentValue = CORRECT_ALLELE;
		} else {
			this.currentValue = INCORRECT_ALLELE;
		}
	}

	/**
	 * Constructor for creating a Gene with only the currentValue. ensures: create a
	 * Gene and set its current Value
	 * 
	 * @param currentValue The current value of the Gene.
	 */
	public Gene(int currentValue) {
		this.currentValue = currentValue;
	}

	public Gene(boolean baldwinianEvolution) {
		if (baldwinianEvolution == true) {
			double randomValue = Math.random();
			if (randomValue < 0.5) {
				this.currentValue = UNKNOWN_ALLELE;
			} else if (randomValue >= 0.5 && randomValue < 0.75) {
				this.currentValue = CORRECT_ALLELE;
			} else {
				this.currentValue = INCORRECT_ALLELE;
			}
		}
	}

	/**
	 * Gets the current value of the Gene.
	 * 
	 * @return The current value of the Gene.
	 */
	public int getCurrentValue() {
		return this.currentValue;
	}

	/**
	 * Returns a string representation of the Gene.
	 * 
	 * @return A string representation of the Gene.
	 */
	public String toString() {
		return ("Gene value is " + this.currentValue);
	}

	/**
	 * Changes the current value of the Gene. update isCorrect by check whether it
	 * match with expectedValue
	 */
	public void changeValue() {
		if (this.currentValue == INCORRECT_ALLELE) {
			this.currentValue = CORRECT_ALLELE;
		} else {
			this.currentValue = INCORRECT_ALLELE;
		}
	}

}
