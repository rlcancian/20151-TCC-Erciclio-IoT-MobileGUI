/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.codon;

/**
 * @author Ercilio Nascimento
 */
public class LayoutCodon extends BaseCodon {

	private int orientation;
	private boolean isFrame;
	private boolean isGetLayout = false;

	public LayoutCodon() {

	}

	public LayoutCodon(String text) {
		super(text, 0, 0);
		this.isFrame = false;
	}

	public LayoutCodon clone() {
		LayoutCodon codon = new LayoutCodon();
		codon.setText(text);
		codon.setWeight(weight);
		codon.setMutable(isMutable);
		codon.setWidth(width);
		codon.setHeight(height);
		codon.setMinValue(minValue);
		codon.setMaxValue(maxValue);
		codon.setOrientation(orientation);
		codon.setFrame(isFrame);

		return codon;
	}

	public boolean isGetLayout() {
		return isGetLayout;
	}

	public void setGetLayout(boolean isGetLayout) {
		this.isGetLayout = isGetLayout;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public boolean isFrame() {
		return isFrame;
	}

	public void setFrame(boolean isFrame) {
		this.isFrame = isFrame;
	}

}
