/**
 * SelectParents(population)
 */
package br.com.mobgui4so.model.guigenerating.matcher;

import java.util.HashMap;
import java.util.Map;

import br.com.mobgui4so.model.guigenerating.Genotype;
import br.com.mobgui4so.model.guigenerating.operator.BasicCrossover;
import br.com.mobgui4so.model.guigenerating.operator.BasicMutation;
import br.com.mobgui4so.model.guigenerating.operator.Crossover;
import br.com.mobgui4so.model.guigenerating.operator.Mutation;
import br.com.mobgui4so.model.guigenerating.pojo.Individual;
import br.com.mobgui4so.model.guigenerating.pojo.Population;

/**
 * @author Ercilio Nascimento
 */
public class Matcher {

	public Population selectParents(Population toSelect, int parentSize) {
		Map<Individual, Double> mapIndividualProb = new HashMap<Individual, Double>();
		Population parents = new Population();
		double reproductionProbability = 0.0;
		int i = 0;

		Population population = Population.clone(toSelect);
		double sumFitness = population.getFitnessesSum();
		for (Individual individual : population.getIndividuals()) {
			reproductionProbability = individual.getFitness() / sumFitness;
			mapIndividualProb.put(individual, reproductionProbability);
		}
		while (parents.size() < parentSize) {
			Individual individual = population.getIndividual(i);
			reproductionProbability = mapIndividualProb.get(individual);
			if (Math.random() < reproductionProbability) {
				parents.addIndividual(individual);
				population.removeIndividual(individual);
			}
			if (++i >= population.size()) {
				i = 0;
			}
		}

		return parents;
	}

	public Population match(Population parents, int offspringSize, double crossingoverRate, double mutationRate) {
		Population offspring = new Population();
		Crossover crossover = new Crossover(new BasicCrossover(), crossingoverRate);
		Mutation mutation = new Mutation(new BasicMutation(), mutationRate);
		for (int i = 0; i < offspringSize; i++) {
			int index = (int) Math.random() * parents.size();
			Individual father = parents.getIndividual(index);
			parents.removeIndividual(father);
			index = (int) Math.random() * parents.size();
			Individual mother = parents.getIndividual(index);
			parents.addIndividual(father);
			Genotype fatherGen = father.getGenotype();
			Genotype motherGen = mother.getGenotype();
			Genotype sonGen = crossover.crossover(fatherGen, motherGen);
			mutation.mutate(sonGen);
			Individual son = new Individual();
			son.setGenotype(sonGen);
			offspring.addIndividual(son);
		}

		return offspring;
	}
}
