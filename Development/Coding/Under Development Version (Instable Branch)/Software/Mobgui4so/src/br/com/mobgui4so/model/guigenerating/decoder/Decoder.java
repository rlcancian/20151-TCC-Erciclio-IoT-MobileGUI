/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.decoder;

import br.com.mobgui4so.model.guigenerating.Genotype;
import br.com.mobgui4so.model.guigenerating.phenotype.BasePhenotype;
import br.com.mobgui4so.model.guigenerating.pojo.Individual;
import br.com.mobgui4so.model.guigenerating.pojo.Population;

/**
 * @author Ercilio Nascimento
 */
public class Decoder {

	private IDecodable decoderImpl;

	public Decoder(IDecodable decoder) {
		this.decoderImpl = decoder;
	}

	public void decode(Population population, Object context) {
		for (Individual individual : population.getIndividuals()) {
			decode(individual, context);
		}
	}

	private void decode(Individual individual, Object context) {
		Genotype genotype = individual.getGenotype();
		BasePhenotype phenotype = decoderImpl.decode(genotype, context);
		individual.setPhenotype(phenotype);
	}
}
