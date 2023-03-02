package mainApp;

import java.util.HashMap;

import org.junit.rules.Stopwatch;

public class BigOAnalysisCalculations {
	
	private HashMap<Integer, Integer> generationsToElapsedTimeMap = new HashMap<Integer, Integer>();

	public void calculate() {
		
		//Create an instance of the GeneticAlgorithmCalculator class to test its methods on the default settings
		GeneticAlgorithmCalculator testCalculator = new GeneticAlgorithmCalculator();
		
		StopWatch timer = new StopWatch();
		
		for(int numGenerations = 0; numGenerations <= 500; numGenerations += 10) {
			timer.start();
			testCalculator.runForNumberOfGenerationsTestMethodOnly(numGenerations);
			timer.stop();
			
			Long elapsedTime = timer.getElapsedTime();
			
			System.out.println("Created " + numGenerations + " generations in " + timer.getElapsedTime() + "ms.");
			this.generationsToElapsedTimeMap.put(numGenerations, elapsedTime.intValue());
		}
		

	}
	
	public HashMap<Integer, Integer> getGenerationsToElapsedTimeMap(){
		return this.generationsToElapsedTimeMap;
	}

}
