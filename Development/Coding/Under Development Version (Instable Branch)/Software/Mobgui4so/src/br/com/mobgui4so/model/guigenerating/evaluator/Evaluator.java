/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.evaluator;

import br.com.mobgui4so.model.guigenerating.pojo.Individual;
import br.com.mobgui4so.model.guigenerating.pojo.Population;

/**
 * @author Ercilio Nascimento
 */
public class Evaluator {

	private IEvaluable evaluatorImpl;

	public Evaluator(IEvaluable evaluator) {
		this.evaluatorImpl = evaluator;
	}

	public double evaluate(Population population) {
		double sumFitness = 0.0;
		for (Individual individual : population.getIndividuals()) {
			sumFitness += evaluate(individual);
		}
		double average = sumFitness / population.size();
		population.setFitnessAverage(average);

		return average;
	}

	private double evaluate(Individual ind) {
		double fitness = this.evaluatorImpl.evaluate(ind.getPhenotype());
		ind.setFitness(fitness);
		return fitness;
	}
}
