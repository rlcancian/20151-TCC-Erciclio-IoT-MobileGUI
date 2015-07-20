/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.operator;

import java.util.Random;

import br.com.mobgui4so.model.guigenerating.Gene;
import br.com.mobgui4so.model.guigenerating.Gene.ParamType;
import br.com.mobgui4so.model.guigenerating.Genotype;
import br.com.mobgui4so.model.guigenerating.codon.BaseCodon;
import br.com.mobgui4so.model.guigenerating.codon.LayoutCodon;

/**
 * @author Ercilio Nascimento
 */
public class BasicMutation implements IMutable {

	@Override
	public void mutate(Genotype son, double mutationRate) {
		preOrderTree(son.getRoot(), mutationRate);

	}

	private void preOrderTree(Gene root, double mutationRate) {
		BaseCodon codon = root.getCodon();
		if (codon.isMutable() && root.getNumberOfChildren() > 1) {
			if (Math.random() <= mutationRate) {
				raffleGenesToMutate(root);
			}
		}
		root.setLastChildRead(-1);
		while (root.getLastChildRead() != root.getChildren().size() - 1) {
			preOrderTree(root.getNextChild(), mutationRate);
		}

		return;
	}

	private void raffleGenesToMutate(Gene root) {
		Random r = new Random();
		int numberOfChildren = r.nextInt(root.getNumberOfChildren() - 1) + 2;
		Gene toMutate = new Gene();
		for (int i = 0; i < numberOfChildren; i++) {
			int sorteio = r.nextInt(root.getNumberOfChildren());
			toMutate.addChild(root.getChild(sorteio));
			root.removeChild(root.getChild(sorteio));
		}
		int numberOfLines = r.nextInt(toMutate.getNumberOfChildren() - 1) + 1;
		mutate(numberOfLines, toMutate, root);

	}

	private void mutate(int numberOfLines, Gene toMutate, Gene root) {
		int size = toMutate.getNumberOfChildren();
		Gene group = new Gene(ParamType.LAYOUT, "");
		LayoutCodon groupCodon = (LayoutCodon) group.getCodon();
		groupCodon.setWeight(1f);
		group.setId(-1);
		for (int i = 0; i < size;) {
			Gene gene = new Gene(ParamType.LAYOUT, "");
			gene.setId(-1);
			LayoutCodon codon = (LayoutCodon) gene.getCodon();
			codon.setOrientation(1);
			codon.setWeight((float) (1 / (Math.ceil((double) size / numberOfLines))));
			for (int j = 0; j < numberOfLines; j++) {
				if (i < size) {
					gene.addChild(toMutate.getChild(0));
					toMutate.removeChild(0);
					i++;
				}
			}
			group.addChild(gene);
		}
		root.addChild(group);
	}

}
