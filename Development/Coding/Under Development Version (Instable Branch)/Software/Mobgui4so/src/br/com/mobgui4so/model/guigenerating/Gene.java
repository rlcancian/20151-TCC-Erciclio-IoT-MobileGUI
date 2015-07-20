/**
 * 
 */
package br.com.mobgui4so.model.guigenerating;

import java.io.Serializable;
import java.util.List;

import br.com.mobgui4so.model.guigenerating.codon.BaseCodon;
import br.com.mobgui4so.model.guigenerating.codon.ComboCodon;
import br.com.mobgui4so.model.guigenerating.codon.LayoutCodon;

/**
 * @author Ercilio Nascimento
 */
public class Gene implements Serializable {
	private int id;
	private ParamType paramType;
	private BaseCodon codon;
	private int lastChildRead;
	private boolean isParamLayout = false;
	private ChildrenList children = new ChildrenList();

	public enum ParamType {
		BUTTON,
		COMBO,
		LAYOUT,
		BOOLEAN,
		STRING,
		INT,
		DOUBLE,
		VOID,
		GET

	}

	public Gene() {
		this.lastChildRead = -1;
	}

	public Gene(ParamType att) {
		this(att, null, 0, 0, null);
	}

	public Gene(ParamType att, String name) {
		this(att, name, 0, 0, null);
	}

	public Gene(ParamType att, String name, int minValue, int maxValue, String options) {
		this.lastChildRead = -1;
		this.paramType = att;
		switch (att) {
		case LAYOUT:
			this.codon = new LayoutCodon(name);
			break;
		case COMBO:
			this.codon = new ComboCodon(name, options);
			break;
		case BOOLEAN:
			this.codon = new BaseCodon(name, minValue, maxValue);
			break;
		case DOUBLE:
			this.codon = new BaseCodon(name, minValue, maxValue);
			break;
		case INT:
			this.codon = new BaseCodon(name, minValue, maxValue);
			break;
		case GET:
			this.codon = new BaseCodon(name, minValue, maxValue);
			break;
		default:
			break;
		}
	}

	public void setBaseCodon(BaseCodon codon) {
		this.codon = codon;
	}

	public Gene clone() {
		Gene gene = new Gene();
		gene.setId(this.id);
		gene.setParamType(this.paramType);
		gene.setBaseCodon(this.codon.clone());
		gene.setLastChildRead(this.lastChildRead);
		gene.setParamLayout(this.isParamLayout);
		gene.setChildren(children.clone());

		return gene;
	}

	public boolean isParamLayout() {
		return isParamLayout;
	}

	public void setParamLayout(boolean isParamLayout) {
		this.isParamLayout = isParamLayout;
	}

	public void removeChild(int index) {
		this.children.remove(index);
	}

	public Gene getChild(int index) {
		return this.children.get(index);
	}

	public int getNumberOfChildren() {
		return this.children.size();
	}

	public ParamType getParamType() {
		return paramType;
	}

	public void setParamType(ParamType paramType) {
		this.paramType = paramType;
	}

	public BaseCodon getCodon() {
		return codon;
	}

	public void setCodon(BaseCodon codon) {
		this.codon = codon;
	}

	public void addChild(Gene son) {
		this.children.add(son);
	}

	public void removeChild(Gene son) {
		this.children.remove(son);
	}

	public Gene getNextChild() {
		return this.children.get(++lastChildRead);
	}

	public int getLastChildRead() {
		return lastChildRead;
	}

	public void setLastChildRead(int lastChildRead) {
		this.lastChildRead = lastChildRead;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Gene> getChildren() {
		return children;
	}

	public void setChildren(ChildrenList children) {
		this.children = children;
	}

	private boolean isLeaf() {
		boolean isLeaf = false;
		if (this.children.isEmpty()) {
			isLeaf = true;
		}

		return isLeaf;
	}
}
