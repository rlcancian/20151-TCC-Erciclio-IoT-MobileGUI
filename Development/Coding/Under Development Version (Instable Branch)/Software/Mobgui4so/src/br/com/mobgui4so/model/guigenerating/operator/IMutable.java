/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.operator;

import br.com.mobgui4so.model.guigenerating.Genotype;

/**
 * @author Ercilio Nascimento
 */
public interface IMutable {

	public void mutate(Genotype son, double mutationRate);

}
