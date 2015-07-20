/**
 * 
 */
package br.com.mobgui4so.model.guigenerating;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.util.Log;
import br.com.mobgui4so.model.guigenerating.creator.Creator;
import br.com.mobgui4so.model.guigenerating.decoder.AndroidDecoder;
import br.com.mobgui4so.model.guigenerating.decoder.Decoder;
import br.com.mobgui4so.model.guigenerating.evaluator.AreaEvaluator;
import br.com.mobgui4so.model.guigenerating.evaluator.Evaluator;
import br.com.mobgui4so.model.guigenerating.matcher.Matcher;
import br.com.mobgui4so.model.guigenerating.phenotype.BasePhenotype;
import br.com.mobgui4so.model.guigenerating.pojo.Individual;
import br.com.mobgui4so.model.guigenerating.pojo.Population;
import br.com.mobgui4so.model.pojo.SmartObject;

/**
 * @author Ercilio Nascimento
 */
public class GUIGeneratingMain {

	private ExecutorService executor = Executors.newFixedThreadPool(1);
	private final int TIMEOUTSECS = 900;
	private final int POPULATIONSIZE = 16;
	private final double MUTATIONRATE = 0.1;
	private final double CROSSINGOVERRATE = 0.99;
	private final int PARENTSSIZE = 8;
	private final int ARCHIEVESIZE = 5;
	private final int OFFSPRINGSIZE = 8;
	private final double DELTAFITNESSTHRESHOLD = 0.001;
	private SmartObject so;
	private Individual bestIndividual;
	private int screenWidth;
	private int screenHeight;
	private Object context;
	private Population bestOfAllGenerations;
	private FileOutputStream saveStream;

	public GUIGeneratingMain(SmartObject so, int screenWidth, int screenHeight, Object context) {
		this.so = so;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.context = context;
	}

	public BasePhenotype generateGUI() {
		final Future<?> future = executor.submit(new Runnable() {
			public void run() {
				try {
					applyGeneticAlgorythm();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		try {
			future.get(this.TIMEOUTSECS, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			future.cancel(true);
		} catch (InterruptedException e) {
		} catch (ExecutionException e) {
		}

		return this.bestIndividual.getPhenotype();
	}

	private void applyGeneticAlgorythm() {
		Population parents = null, offspring = null;
		double deltaFitness = 1e6, nextAverageFitness = 1e6, averageFitness = 0;

		Decoder decoder = new Decoder(new AndroidDecoder());
		Evaluator evaluator = new Evaluator(new AreaEvaluator(this.screenHeight, this.screenWidth));
		Creator creator = new Creator();
		Matcher matcher = new Matcher();
		Population population = creator.create(POPULATIONSIZE, this.so);
		decoder.decode(population, this.context);
		while (!Thread.interrupted() && deltaFitness > DELTAFITNESSTHRESHOLD) {
			nextAverageFitness = evaluator.evaluate(population);
			deltaFitness = nextAverageFitness - averageFitness;
			averageFitness = nextAverageFitness;
			parents = matcher.selectParents(population, PARENTSSIZE);
			offspring = matcher.match(parents, OFFSPRINGSIZE, CROSSINGOVERRATE, MUTATIONRATE);
			decoder.decode(offspring, this.context);
			evaluator.evaluate(offspring);
			population = generateNewPopulation(population, offspring);
			this.bestIndividual = population.getBestIndividual();
			saveToArchieve(population);
		}
		Log.i("best deltafitness", " " + this.bestIndividual.getFitness());
	}

	private Population generateNewPopulation(Population population, Population offspring) {
		removeLessEffectiveIndividuals(population, offspring);

		return population;
	}

	private void removeLessEffectiveIndividuals(Population population, Population offspring) {
		population.addPopulation(offspring);
		List<Individual> individuals = population.getIndividualsOrderedByWorstFitnesses();
		for (int i = offspring.size() - 1; i >= 0; i--) {
			population.removeIndividual(individuals.get(i));
		}
	}

	private void saveToArchieve(Population population) {
		NavigableMap<Double, Genotype> map = null;
		if (this.screenWidth > this.screenHeight) {
			map = so.getLandscapeGenotypes();
		} else {
			map = so.getPortraitGenotypes();
		}
		for (Individual individual : population.getIndividuals()) {
			map.put(individual.getFitness(), individual.getGenotype());
		}
		List<Double> keys = new ArrayList<Double>();
		keys.addAll(map.keySet());
		for (Double key : keys) {
			if (map.size() > ARCHIEVESIZE) {
				map.remove(key);
			} else {
				break;
			}
		}
	}

}
