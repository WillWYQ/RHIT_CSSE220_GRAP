package mainApp;

import java.io.IOException;

public class InvalidChromosomeFormatException extends IOException {
	
	private static final long serialVersionUID = 1L;
	private int wrongValue;
	
	public InvalidChromosomeFormatException(int wrongValue) {
		this.wrongValue = wrongValue;
	}
	
	public String returnInvalidCharacterWarning() {
		String warning = "This file contains an invalid gene value! The invalid value is "
				+ this.wrongValue + ". Please load another file that only contains the values 1 and 0.";
		return warning;
	}

}
