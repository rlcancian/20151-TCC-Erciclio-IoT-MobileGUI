/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.decoder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.mobgui4so.R;
import br.com.mobgui4so.model.guigenerating.Gene;
import br.com.mobgui4so.model.guigenerating.Genotype;
import br.com.mobgui4so.model.guigenerating.codon.BaseCodon;
import br.com.mobgui4so.model.guigenerating.codon.ComboCodon;
import br.com.mobgui4so.model.guigenerating.codon.LayoutCodon;
import br.com.mobgui4so.model.guigenerating.phenotype.AndroidPhenotype;
import br.com.mobgui4so.model.guigenerating.phenotype.BasePhenotype;
import br.com.mobgui4so.utils.BlockingOnUIRunnable;
import br.com.mobgui4so.utils.IBlockingOnUIRunnableListener;
import br.com.mobgui4so.view.NegativeSeekBar;
import br.com.mobgui4so.view.SOLinearLayout;
import br.com.mobgui4so.view.SmartObjectGUIActivity;

/**
 * @author Ercilio Nascimento
 */
public class AndroidDecoder implements IDecodable {

	private Context context;
	private LinearLayout layout;
	private LinearLayout layoutAux;
	private Spinner spinner;
	private Thread thread;
	private int framesArea;
	private ScrollView sv;
	private int lastIdRead;
	private boolean badPhenotype;

	/* (non-Javadoc)
	 * @see br.com.mobgui4so.model.guigenerating.IDecodable#decode(br.com.mobgui4so.model.guigenerating.Genotype)
	 */
	@Override
	public BasePhenotype decode(Genotype gen, Object context) {
		this.context = (Context) context;
		sv = new ScrollView(this.context);
		LinearLayout ll = new LinearLayout(this.context);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setPadding(0, 0, 10, 10);
		sv.addView(ll);

		preOrderTree(gen.getRoot(), ll);
		setLayoutOnUIThread();

		AndroidPhenotype phen = new AndroidPhenotype();
		phen.setMaxFrameHeight(ll.getHeight());
		phen.setMaxFrameWidth(ll.getWidth());
		this.framesArea = 0;
		this.badPhenotype = false;
		calculateFramesArea(sv.getChildAt(0));
		phen.setBadPhenotype(this.badPhenotype);
		phen.setFramesArea(this.framesArea);
		phen.setLayout(sv);

		return phen;
	}

	private void calculateFramesArea(View layoutRoot) {
		if (layoutRoot instanceof TextView && !(layoutRoot instanceof EditText)) {
			if (((TextView) layoutRoot).getLineCount() > 1) {
				this.badPhenotype = true;
			}

			return;
		}
		if (layoutRoot instanceof LinearLayout) {
			LinearLayout root = (LinearLayout) layoutRoot;
			if (root.getId() == R.id.templateFrame) {
				this.framesArea += layoutRoot.getHeight() * layoutRoot.getWidth();
			}

			for (int i = 0; i < root.getChildCount() && this.badPhenotype == false; i++) {
				calculateFramesArea(root.getChildAt(i));
			}
		}

		return;
	}

	private void preOrderTree(Gene root, LinearLayout view) {
		if (root.getId() != 0) {
			switch (root.getParamType()) {
			case LAYOUT:
				view = createLayout(root, view);
				break;
			case BOOLEAN:
				createCheckBox(root, view);
				break;
			case COMBO:
				if (((ComboCodon) root.getCodon()).getOptions().size() <= 2) {
					createRadioButton(root, view);
				} else {
					createComboBox(root, view);
				}
				break;
			case DOUBLE:
				if (root.getCodon().getMinValue() != 0 && root.getCodon().getMaxValue() != 0) {
					createSeekBar(root, view);
				} else {
					createInput(root, view);
				}
				break;
			case INT:
				createInput(root, view);
				break;
			case GET:
				createButton(root, view);
				break;
			default:
				break;
			}
		}
		root.setLastChildRead(-1);
		while (root.getLastChildRead() != root.getChildren().size() - 1) {
			this.preOrderTree(root.getNextChild(), view);
		}
		this.layout = view;

		return;
	}

	private void createRadioButton(Gene root, LinearLayout view) {
		this.layoutAux = view;
		final ComboCodon codon = (ComboCodon) root.getCodon();
		TextView label = new TextView(this.context);
		LayoutParams params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, codon.getWeight());
		label.setText(codon.getText());
		this.layoutAux.addView(label, params);

		final SmartObjectGUIActivity activity = (SmartObjectGUIActivity) this.context;
		BlockingOnUIRunnable actionRunnable = new BlockingOnUIRunnable(activity, new IBlockingOnUIRunnableListener()
		{
			public void runOnUIThread()
			{
				RadioGroup group = new RadioGroup(activity);
				group.setOrientation(RadioGroup.VERTICAL);
				for (String option : codon.getOptions()) {
					RadioButton rb = new RadioButton(activity);
					rb.setText(option);
					group.addView(rb);
				}
				((RadioButton) group.getChildAt(0)).setChecked(true);
				LayoutParams params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1 - codon.getWeight());
				layoutAux.addView(group, params);
			}
		});
		actionRunnable.startOnUiAndWait();
	}

	private void createSeekBar(final Gene root, LinearLayout view) {
		this.layoutAux = view;
		final BaseCodon codon = root.getCodon();
		final TextView label = new TextView(this.context);
		LayoutParams params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, codon.getWeight());
		label.setText(codon.getText());
		this.layoutAux.addView(label, params);

		final SmartObjectGUIActivity activity = (SmartObjectGUIActivity) this.context;
		BlockingOnUIRunnable actionRunnable = new BlockingOnUIRunnable(activity, new IBlockingOnUIRunnableListener()
		{
			public void runOnUIThread()
			{
				NegativeSeekBar seek = new NegativeSeekBar(activity, codon.getMinValue(), codon.getMaxValue(), label, root.getParamType());
				LayoutParams params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1 - codon.getWeight());
				layoutAux.addView(seek, params);
			}
		});
		actionRunnable.startOnUiAndWait();

	}

	private void setLayoutOnUIThread() {
		final SmartObjectGUIActivity activity = (SmartObjectGUIActivity) this.context;

		Runnable uiRunnable = new Runnable() {
			public void run() {
				activity.setLayout(sv);

				final Runnable r = this;
				sv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
						synchronized (r) {
							r.notify();
						}
					}
				});

			}
		};
		synchronized (uiRunnable) {
			activity.runOnUiThread(uiRunnable);
			try {
				uiRunnable.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void createInput(Gene root, LinearLayout view) {
		this.layoutAux = view;
		final BaseCodon codon = root.getCodon();
		TextView label = new TextView(this.context);
		LayoutParams params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, codon.getWeight());
		label.setText(codon.getText());
		this.layoutAux.addView(label, params);

		final SmartObjectGUIActivity activity = (SmartObjectGUIActivity) this.context;
		BlockingOnUIRunnable actionRunnable = new BlockingOnUIRunnable(activity, new IBlockingOnUIRunnableListener()
		{
			public void runOnUIThread()
			{
				EditText edit = new EditText(activity);
				LayoutParams params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1 - codon.getWeight());
				edit.setEms(5);
				layoutAux.addView(edit, params);
			}
		});
		actionRunnable.startOnUiAndWait();
	}

	private void createComboBox(Gene root, LinearLayout view) {
		this.layoutAux = view;
		final ComboCodon codon = (ComboCodon) root.getCodon();
		TextView label = new TextView(this.context);
		LayoutParams params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, codon.getWeight());
		label.setText(codon.getText());
		this.layoutAux.addView(label, params);

		final SmartObjectGUIActivity activity = (SmartObjectGUIActivity) this.context;
		BlockingOnUIRunnable actionRunnable = new BlockingOnUIRunnable(activity, new IBlockingOnUIRunnableListener()
		{
			public void runOnUIThread()
			{
				LayoutParams params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1 - codon.getWeight());
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, codon.getOptions());
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				Spinner spinner = new Spinner(activity);
				spinner.setAdapter(adapter);
				layoutAux.addView(spinner, params);
			}
		});
		actionRunnable.startOnUiAndWait();
	}

	private void createCheckBox(Gene root, LinearLayout view) {
		BaseCodon codon = root.getCodon();
		TextView label = new TextView(this.context);
		LayoutParams params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, codon.getWeight());
		label.setText(codon.getText());
		view.addView(label, params);

		CheckBox cb = new CheckBox(this.context);
		params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1 - codon.getWeight());
		view.addView(cb, params);
	}

	private LinearLayout createLayout(Gene root, LinearLayout view) {
		LayoutCodon codon = (LayoutCodon) root.getCodon();
		SOLinearLayout layout = new SOLinearLayout(this.context);
		LayoutParams params = null;
		if (codon.getWeight() == 1) {
			params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		} else {
			params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, codon.getWeight());
		}
		if (codon.isFrame()) {
			LayoutInflater li = LayoutInflater.from(context);

			layout = (SOLinearLayout) li.inflate(R.layout.templateframe, null);
			layout.setLoopingInterval(1000);
			layout.setId(R.id.templateFrame);
			if (codon.isGetLayout()) {
				layout.setGetLayout(true);
			}
			TextView tv = (TextView) layout.findViewById(R.id.textFrame);
			tv.setText(codon.getText().toUpperCase());
			params.setMargins(10, 10, 0, 0);
		} else {
			if (codon.getOrientation() == 0) {
				layout.setOrientation(LinearLayout.HORIZONTAL);
			} else {
				layout.setOrientation(LinearLayout.VERTICAL);
			}
		}
		codon.setWidth(layout.getWidth());
		codon.setHeight(layout.getHeight());
		view.addView(layout, params);
		view = layout;

		return view;
	}

	private void createButton(Gene root, LinearLayout view) {
		BaseCodon codon = root.getCodon();
		TextView label = new TextView(this.context);
		LayoutParams params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, codon.getWeight());
		label.setText(codon.getText());
		view.addView(label, params);

		TextView label2 = new TextView(this.context);
		label2.setText("18");
		params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1 - codon.getWeight());
		view.addView(label2, params);
	}

}
