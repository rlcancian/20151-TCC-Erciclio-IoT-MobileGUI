/**
 * 
 */
package br.com.mobgui4so.model.pojo;

import java.io.Serializable;

import br.com.mobgui4so.model.guigenerating.Gene.ParamType;

/**
 * @author Ercilio Nascimento
 */
public class SOServiceParam implements Serializable {

	private static final long serialVersionUID = 3457815774849515058L;
	private String name;
	private ParamType type;
	private int minValue;
	private int maxValue;
	private String options;

	public SOServiceParam() {

	}

	public SOServiceParam(String name, String type) {
		this.name = name;
		this.type = ParamType.valueOf(type.toUpperCase());
	}

	public String getName() {
		return name;
	}

	public ParamType getType() {
		return type;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		if (minValue != null && !minValue.equals("") && !minValue.equals("null")) {
			this.minValue = Integer.parseInt(minValue);
		}
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		if (maxValue != null && !maxValue.equals("") && !maxValue.equals("null")) {
			this.maxValue = Integer.parseInt(maxValue);
		}
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(ParamType type) {
		this.type = type;
	}

}
