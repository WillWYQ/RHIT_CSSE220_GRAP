package mainApp;

import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JComponent;

public class BigOAnalysisComponent extends JComponent {
	
	private static final int BAR_WIDTH = 10;
	private static final int BAR_WIDTH_DIVIDED_BY_2 = BAR_WIDTH/2;
	
	HashMap<Integer, Integer> pointsToPlot;
	
	public void setPointsToPlot(HashMap<Integer, Integer> newPoints) {
		this.pointsToPlot = newPoints;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(Integer numGenerations : pointsToPlot.keySet()) {
			g.translate(numGenerations - BAR_WIDTH_DIVIDED_BY_2, pointsToPlot.get(numGenerations));
			g.drawRect(0, 0, BAR_WIDTH, pointsToPlot.get(numGenerations));
			g.translate(-(numGenerations - BAR_WIDTH_DIVIDED_BY_2), -pointsToPlot.get(numGenerations));
		}
	}

}
