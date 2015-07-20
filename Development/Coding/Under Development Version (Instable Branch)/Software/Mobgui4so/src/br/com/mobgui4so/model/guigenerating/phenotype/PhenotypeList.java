/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.phenotype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ercilio Nascimento
 */
public class PhenotypeList {

	private List<BasePhenotype> phenotypes = new ArrayList<BasePhenotype>();

	public List<BasePhenotype> getPhenotypes() {
		return phenotypes;
	}

	public void setPhenotypes(List<BasePhenotype> phenotypes) {
		this.phenotypes = phenotypes;
	}

	public void addPhenotype(BasePhenotype phenotype) {
		this.phenotypes.add(phenotype);
	}
}
