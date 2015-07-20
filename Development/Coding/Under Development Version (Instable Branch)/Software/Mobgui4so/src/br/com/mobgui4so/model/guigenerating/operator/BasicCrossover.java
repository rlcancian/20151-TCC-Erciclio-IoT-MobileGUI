/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.mobgui4so.model.guigenerating.ChildrenList;
import br.com.mobgui4so.model.guigenerating.Gene;
import br.com.mobgui4so.model.guigenerating.Genotype;

/**
 * @author Ercilio Nascimento
 */
public class BasicCrossover implements ICrossovable {

	private Gene toErase;
	private List<Integer> paramLayoutsRead = new ArrayList<Integer>();

	@Override
	public Genotype crossover(Genotype father, Genotype mother, double crossingoverRate) {
		Genotype son = father.clone();
		if (Math.random() <= crossingoverRate) {
			Genotype fatherClone = father.clone();
			Genotype motherClone = mother.clone();
			ChildrenList sonChildren = new ChildrenList();
			sonChildren.addAll(fatherClone.getRoot().getChildren());
			Random r = new Random();
			for (int i = 0; i < motherClone.getRoot().getNumberOfChildren(); i++) {
				int index = r.nextInt(sonChildren.size() + 1);
				sonChildren.add(index, motherClone.getRoot().getChild(i));
			}
			son = new Genotype();
			son.createRoot();
			son.getRoot().setChildren(sonChildren);
			preOrderTree(son.getRoot());
			paramLayoutsRead.clear();
		}

		return son;
	}

	private void preOrderTree(Gene root) {
		if (root.isParamLayout()) {
			if (paramLayoutsRead.contains(root.getId())) {
				toErase = root;
			} else {
				paramLayoutsRead.add(root.getId());
			}
		} else {
			for (int i = 0; i < root.getNumberOfChildren(); i++) {
				preOrderTree(root.getChild(i));
				if (toErase != null) {
					root.removeChild(toErase);
					i--;
					toErase = null;
				}
			}
			if (root.getNumberOfChildren() == 0) {
				toErase = root;
			}
		}

		return;
	}

}
