/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.evaluator;

import br.com.mobgui4so.model.guigenerating.phenotype.BasePhenotype;

/**
 * @author Ercilio Nascimento
 */
public class AreaEvaluator implements IEvaluable {

	private int screenHeight, screenWidth;

	public AreaEvaluator(int screenHeight, int screenWidth) {
		super();
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}

	/* (non-Javadoc)
	 * @see br.com.mobgui4so.model.guigenerating.evaluator.IEvaluable#evaluate(br.com.mobgui4so.model.guigenerating.phenotype.BasePhenotype)
	 */
	@Override
	public double evaluate(BasePhenotype phenotype) {
		double areaBoundingFrames = (phenotype.getMaxFrameHeight() * phenotype.getMaxFrameWidth());
		double blankArea = (areaBoundingFrames - phenotype.getFramesArea());
		double value = 0.0;
		int screenArea = this.screenHeight * this.screenWidth;
		if (!phenotype.isBadPhenotype() && (blankArea < 0.20 * areaBoundingFrames)) {
			value = phenotype.getMaxFrameHeight();
			double area = phenotype.getFramesArea();
			value = area < screenArea ? area / screenArea : screenArea / area;

		}

		return value;
	}

}
