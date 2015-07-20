/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.operator;

import br.com.mobgui4so.model.guigenerating.Genotype;

/**
 * @author Ercilio Nascimento
 */
public class Mutation {

	private IMutable mutationImpl;
	private double mutationRate;

	public Mutation(IMutable mutation, double mutationRate) {
		this.mutationImpl = mutation;
		this.mutationRate = mutationRate;
	}

	public void mutate(Genotype son) {
		this.mutationImpl.mutate(son, this.mutationRate);
	}

}
