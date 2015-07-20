/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.mobgui4so.model.guigenerating.phenotype.BasePhenotype;

/**
 * @author Ercilio Nascimento
 */
public class Population {
	private List<Individual> individuals = new ArrayList<Individual>();
	private Double fitnessAverage; // média dos fit dos individuos

	public int size() {
		return this.individuals.size();
	}

	public List<Individual> getIndividualsOrderedByWorstFitnesses() {
		List<Individual> individuals = new ArrayList<Individual>();
		individuals.addAll(this.individuals);
		Collections.sort(individuals, new Comparator<Individual>() {

			@Override
			public int compare(Individual ind1, Individual ind2) {
				return ind1.getFitness() < ind2.getFitness() ? -1 : (ind1.getFitness() > ind2.getFitness() ? +1 : 0);
			}
		});

		return individuals;
	}

	public List<Individual> getIndividualsOrderedByBestFitnesses() {
		List<Individual> individuals = new ArrayList<Individual>();
		individuals.addAll(this.individuals);
		Collections.sort(individuals, new Comparator<Individual>() {

			@Override
			public int compare(Individual ind1, Individual ind2) {
				return ind1.getFitness() < ind2.getFitness() ? +1 : (ind1.getFitness() > ind2.getFitness() ? -1 : 0);
			}
		});

		return individuals;
	}

	public void setIndividuals(List<Individual> individuals) {
		this.individuals = individuals;
	}

	public double getFitnessesSum() {
		double sumFitness = 0.0;
		for (Individual individual : this.individuals) {
			sumFitness += individual.getFitness();
		}

		return sumFitness;
	}

	public Double getFitnessAverage() {
		return fitnessAverage;
	}

	public void setFitnessAverage(Double fitnessAverage) {
		this.fitnessAverage = fitnessAverage;
	}

	public void removeIndividual(int index) {
		this.individuals.remove(index);
	}

	public void removeIndividual(Individual individual) {
		this.individuals.remove(individual);
	}

	public void addPopulation(Population population) {
		this.individuals.addAll(population.getIndividuals());
	}

	public List<Individual> getIndividuals() {
		return this.individuals;
	}

	public void addIndividual(Individual individual) {
		this.individuals.add(individual);
	}

	public Individual getIndividual(int index) {
		return this.individuals.get(index);
	}

	public static Population clone(Population toClone) {
		Population population = new Population();
		population.setFitnessAverage(toClone.getFitnessAverage());
		population.addPopulation(toClone);

		return population;
	}

	public Individual getBestIndividual() {
		List<Individual> individuals = getIndividualsOrderedByWorstFitnesses();
		return individuals.get(individuals.size() - 1);
	}

	public BasePhenotype getBestPhenotype() {
		return getBestIndividual().getPhenotype();

	}
}
