/**
 * 
 */
package br.com.mobgui4so.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author Ercilio Nascimento
 */
public class SOLinearLayout extends LinearLayout {

	private boolean isFrameLooping = false;
	private long loopingInterval;
	private boolean isGetLayout = false;

	public SOLinearLayout(Context context) {
		super(context);
	}

	public SOLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SOLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean isFrameLooping() {
		return isFrameLooping;
	}

	public void setFrameLooping(boolean isFrameLooping) {
		this.isFrameLooping = isFrameLooping;
	}

	public long getLoopingInterval() {
		return loopingInterval;
	}

	public void setLoopingInterval(long loopingInterval) {
		this.loopingInterval = loopingInterval;
	}

	public boolean isGetLayout() {
		return isGetLayout;
	}

	public void setGetLayout(boolean isGetLayout) {
		this.isGetLayout = isGetLayout;
	}

}
