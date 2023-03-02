package mainApp;

import javax.swing.JFrame;

public class BigOAnalysisViewer {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		BigOAnalysisComponent graphComponent = new BigOAnalysisComponent();
		BigOAnalysisCalculations calculator = new BigOAnalysisCalculations();
		
		calculator.calculate();
		graphComponent.setPointsToPlot(calculator.getGenerationsToElapsedTimeMap());
		
		frame.add(graphComponent);
		frame.setVisible(true);

	}

}
