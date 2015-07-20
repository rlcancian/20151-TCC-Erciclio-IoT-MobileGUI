/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.phenotype;

import android.widget.ScrollView;

/**
 * @author Ercilio Nascimento
 */
public class AndroidPhenotype extends BasePhenotype {

	private String text;
	private ScrollView layout;

	public ScrollView getLayout() {
		return layout;
	}

	public void setLayout(ScrollView layout) {
		this.layout = layout;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
