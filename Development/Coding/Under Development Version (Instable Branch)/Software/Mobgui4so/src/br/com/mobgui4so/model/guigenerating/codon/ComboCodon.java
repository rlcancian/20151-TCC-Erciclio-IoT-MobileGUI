/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.codon;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ercilio Nascimento
 */
public class ComboCodon extends BaseCodon {

	private List<String> options;

	public ComboCodon() {

	}

	public ComboCodon(String text, String options) {
		super(text, 0, 0);
		this.options = Arrays.asList(options.split("\\|"));
	}

	public ComboCodon clone() {
		ComboCodon codon = new ComboCodon();
		codon.setText(text);
		codon.setWeight(weight);
		codon.setMutable(isMutable);
		codon.setWidth(width);
		codon.setHeight(height);
		codon.setMinValue(minValue);
		codon.setMaxValue(maxValue);
		codon.setOptions(options);

		return codon;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

}
