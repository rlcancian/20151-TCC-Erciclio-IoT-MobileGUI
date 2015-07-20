/**
 * 
 */
package br.com.mobgui4so.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;
import android.widget.TextView;
import br.com.mobgui4so.model.guigenerating.Gene.ParamType;

/**
 * @author Ercilio Nascimento
 */
public class NegativeSeekBar extends SeekBar {

	private int minValue;
	private int maxValue;
	private TextView label;
	private String labelText;
	private float floatProgress;
	private int intProgress;
	private ParamType type;

	public NegativeSeekBar(Context context, int minValue, int maxValue, TextView label, ParamType type) {
		this(context);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.label = label;
		this.labelText = label.getText().toString();
		this.type = type;
		setIntervalAndListener();
	}

	public NegativeSeekBar(Context context) {
		super(context);
	}

	public NegativeSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NegativeSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public String getStringProgress() {
		String value = null;
		if (this.type == ParamType.DOUBLE) {
			value = String.valueOf(intProgress);
		} else {
			value = String.valueOf(intProgress);
		}

		return value;
	}

	private void setIntervalAndListener() {
		int max = 0;
		if (this.minValue < 0 && this.maxValue < 0) {
			max = 10 * (Math.abs(this.minValue) - Math.abs(this.maxValue));
			setOnSeekBarChangeListener(negativeLimitsListener());
		} else if (this.minValue < 0 && this.maxValue > 0) {
			max = this.maxValue + Math.abs(this.minValue);
			setOnSeekBarChangeListener(negativeLimitsListener());
		} else {
			max = this.maxValue - this.minValue;
			setOnSeekBarChangeListener(positiveLimitsListener());
		}
		setMax(max);
	}

	private OnSeekBarChangeListener negativeLimitsListener() {
		OnSeekBarChangeListener listener = new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				floatProgress = (float) (progress -= (10 * Math.abs(minValue))) / 10;
				label.setText(labelText + " : " + floatProgress);
			}
		};

		return listener;
	}

	private OnSeekBarChangeListener positiveLimitsListener() {
		OnSeekBarChangeListener listener = new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				progress += minValue;
				intProgress = progress;
				label.setText(labelText + " : " + progress);
			}
		};

		return listener;
	}

}
