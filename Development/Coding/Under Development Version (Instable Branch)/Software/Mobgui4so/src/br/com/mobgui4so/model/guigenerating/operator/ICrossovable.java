/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.operator;

import br.com.mobgui4so.model.guigenerating.Genotype;

/**
 * @author Ercilio Nascimento
 */
public interface ICrossovable {

	public Genotype crossover(Genotype father, Genotype mother, double crossingoverRate);

}
