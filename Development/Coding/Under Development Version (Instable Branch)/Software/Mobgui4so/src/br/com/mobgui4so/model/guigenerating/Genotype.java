/**
 * 
 */
package br.com.mobgui4so.model.guigenerating;

import java.io.Serializable;

import br.com.mobgui4so.model.guigenerating.Gene.ParamType;
import br.com.mobgui4so.model.guigenerating.codon.LayoutCodon;

/**
 * @author Ercilio Nascimento
 */
public class Genotype implements Serializable {
	private Gene root;

	public Gene getRoot() {
		return root;
	}

	public void setRoot(Gene root) {
		this.root = root;
	}

	public Gene createRoot() {
		Gene root = new Gene(ParamType.LAYOUT, "");
		LayoutCodon codon = (LayoutCodon) root.getCodon();
		codon.setMutable(true);
		root.setId(0);
		this.root = root;

		return this.root;
	}

	public Genotype clone() {
		Genotype genotype = new Genotype();
		genotype.setRoot(root.clone());

		return genotype;
	}
}
