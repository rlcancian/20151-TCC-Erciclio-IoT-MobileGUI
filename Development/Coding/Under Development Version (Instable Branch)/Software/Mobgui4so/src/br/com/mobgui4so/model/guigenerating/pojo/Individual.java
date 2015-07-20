/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.pojo;

import br.com.mobgui4so.model.guigenerating.Genotype;
import br.com.mobgui4so.model.guigenerating.phenotype.BasePhenotype;

/**
 * @author Ercilio Nascimento
 */
public class Individual {
	private Genotype genotype;
	private BasePhenotype phenotype;
	private double fitness;

	public Genotype getGenotype() {
		return genotype;
	}

	public void setGenotype(Genotype genotype) {
		this.genotype = genotype;
	}

	public BasePhenotype getPhenotype() {
		return phenotype;
	}

	public void setPhenotype(BasePhenotype phenotype) {
		this.phenotype = phenotype;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

}
