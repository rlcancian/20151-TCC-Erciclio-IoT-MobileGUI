/**
 * 
 */
package br.com.mobgui4so.model.guigenerating;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Ercilio Nascimento
 */
@SuppressWarnings("serial")
public class ChildrenList extends ArrayList<Gene> implements Serializable {

	public ChildrenList clone() {
		ChildrenList list = new ChildrenList();
		for (Gene gene : this) {
			list.add(gene.clone());
		}

		return list;
	}
}
