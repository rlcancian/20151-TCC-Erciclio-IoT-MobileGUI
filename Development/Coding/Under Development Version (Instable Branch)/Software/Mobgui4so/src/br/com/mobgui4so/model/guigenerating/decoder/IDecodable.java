/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.decoder;

import br.com.mobgui4so.model.guigenerating.Genotype;
import br.com.mobgui4so.model.guigenerating.phenotype.BasePhenotype;

/**
 * @author Ercilio Nascimento
 */
public interface IDecodable {

	public BasePhenotype decode(Genotype gen, Object context);

}
