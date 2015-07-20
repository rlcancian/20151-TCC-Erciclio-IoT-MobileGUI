/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.codon;

import java.io.Serializable;

/**
 * @author Ercilio Nascimento
 */
public class BaseCodon implements Serializable {

	protected String text;
	protected float weight;
	protected boolean isMutable = false;
	protected int width;
	protected int height;
	protected int minValue;
	protected int maxValue;

	public BaseCodon() {

	}

	public BaseCodon(String text, int minValue, int maxValue) {
		this.text = text;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public BaseCodon clone() {
		BaseCodon codon = new BaseCodon();
		codon.setText(text);
		codon.setWeight(weight);
		codon.setMutable(isMutable);
		codon.setWidth(width);
		codon.setHeight(height);
		codon.setMinValue(minValue);
		codon.setMaxValue(maxValue);

		return codon;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isMutable() {
		return isMutable;
	}

	public void setMutable(boolean isMutable) {
		this.isMutable = isMutable;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

}
