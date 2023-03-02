package mainApp;

/**
 * Class: MainApp<br>
 * Purpose: Top level class for CSSE220 Project containing main method <br>
 * Restrictions: None
 * 
 * @author Yueqiao Wang and Rebecca Testa
 * @see EvolutionAnalysisViewer
 */

public class MainApp {

	private void runApp() {
		new EvolutionAnalysisViewer();

	} // runApp

	/**
	 * ensures: runs the application
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();
	} // main

}