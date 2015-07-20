/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.phenotype;

/**
 * @author Ercilio Nascimento
 */
public class BasePhenotype {

	protected double fitness;
	private int framesArea;
	private int maxFrameHeight;
	private int maxFrameWidth;
	private boolean badPhenotype = false;

	public boolean isBadPhenotype() {
		return badPhenotype;
	}

	public void setBadPhenotype(boolean badPhenotype) {
		this.badPhenotype = badPhenotype;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int getFramesArea() {
		return framesArea;
	}

	public void setFramesArea(int framesArea) {
		this.framesArea = framesArea;
	}

	public int getMaxFrameHeight() {
		return maxFrameHeight;
	}

	public void setMaxFrameHeight(int maxFrameHeight) {
		this.maxFrameHeight = maxFrameHeight;
	}

	public int getMaxFrameWidth() {
		return maxFrameWidth;
	}

	public void setMaxFrameWidth(int maxFrameWidth) {
		this.maxFrameWidth = maxFrameWidth;
	}

}
