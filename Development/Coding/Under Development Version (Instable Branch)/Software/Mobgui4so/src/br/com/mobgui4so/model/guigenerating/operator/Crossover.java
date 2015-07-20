/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.operator;

import br.com.mobgui4so.model.guigenerating.Genotype;

/**
 * @author Ercilio Nascimento
 */
public class Crossover {

	private ICrossovable crossoverImpl;
	private double crossingoverRate;

	public Crossover(ICrossovable crossover, double crossingoverRate) {
		this.crossoverImpl = crossover;
		this.crossingoverRate = crossingoverRate;
	}

	public Genotype crossover(Genotype father, Genotype mother) {
		return this.crossoverImpl.crossover(father, mother, crossingoverRate);
	}

}
