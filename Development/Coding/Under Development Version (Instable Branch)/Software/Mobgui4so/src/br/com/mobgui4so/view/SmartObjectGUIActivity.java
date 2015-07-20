package br.com.mobgui4so.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.mobgui4so.R;
import br.com.mobgui4so.controller.ApplicationFacade;
import br.com.mobgui4so.model.guigenerating.Genotype;
import br.com.mobgui4so.model.guigenerating.decoder.AndroidDecoder;
import br.com.mobgui4so.model.guigenerating.phenotype.AndroidPhenotype;
import br.com.mobgui4so.model.pojo.SmartObject;
import br.com.mobgui4so.model.pojo.SmartObjectList;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;

public class SmartObjectGUIActivity extends BaseActivity implements OnItemClickListener, ITransaction {

	private ListView listView;
	private SmartObjectList soList;
	private int idSOClicked;
	private SmartObject so;
	private ApplicationFacade facade;
	private ScrollView soGUILayout;
	private LinearLayout progressLayout;
	private TextView tvProgressMsg;
	private ScrollView layout;
	private boolean flFirstTime;
	private Menu menu;
	private List<String> listParameters;
	private boolean isSendCommand;
	private String ack;
	private static final String PREFS_NAME = "Preferences";
	private AndroidDecoder decoder;
	private Handler mHandler;
	private Double landscapeKey;
	private Double portraitKey;
	private boolean isLandscape;
	private int width;
	private int height;
	private Thread loop;
	private TextView serviceName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smart_object_gui);
		Bundle b = getIntent().getExtras();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		this.idSOClicked = settings.getInt("idSOClicked", -1);
		if (this.idSOClicked == -1) {
			this.idSOClicked = b.getInt("idSOClicked", 0);
		}
		try {
			this.soList = new ApplicationFacade().loadSmartObjectListFromDisk(openFileInput("SOFILE"));
		} catch (FileNotFoundException e) {
			// do nothing
		} catch (IOException e) {
			// do nothing
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// this.soList = (SmartObjectList) b.getSerializable("SOList");
		this.so = soList.getList().get(idSOClicked);
		this.landscapeKey = so.getActualLandscapeKey() == null ? (so.getLandscapeGenotypes().isEmpty() ? null : so.getLandscapeGenotypes().lastKey()) : so.getActualLandscapeKey();
		this.portraitKey = so.getActualPortraitKey() == null ? (so.getPortraitGenotypes().isEmpty() ? null : so.getPortraitGenotypes().lastKey()) : so.getActualPortraitKey();
		setTitle(this.soList.getList().get(idSOClicked).getName());
		this.listView = (ListView) findViewById(R.id.smListView);
		this.soGUILayout = (ScrollView) findViewById(R.id.soGUILayout);
		this.progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
		this.tvProgressMsg = (TextView) findViewById(R.id.tvProgressMsg);
		this.tvProgressMsg.setText(getText(R.string.pmGeneratingGUI));
		this.facade = new ApplicationFacade();
		this.decoder = new AndroidDecoder();
		this.soGUILayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				soGUILayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				width = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getWidth();
				height = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
				isLandscape = width > height;
				getSlidingMenu().setBehindWidth((int) (width * .8));
				if (isLandscape && landscapeKey != null) {
					decode(so.getLandscapeGenotypes().get(landscapeKey));
				} else if (!isLandscape && portraitKey != null) {
					decode(so.getPortraitGenotypes().get(portraitKey));
				} else {
					generateGUI();
				}
			}
		});
		setSlidingMenu();
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					menu.findItem(R.id.refresh_option).setEnabled(true);
					verifyMenuItemsStatus();
					break;
				case 2:
					serviceName.setText((String) msg.obj);
					break;
				case 3:
					makeToast((String) msg.obj);
				default:
					break;
				}
			}
		};
	}

	private void setSlidingMenu() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.soList.getSONames());
		this.listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		this.listView.setAdapter(adapter);
		this.listView.setOnItemClickListener(this);
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(.6f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setMode(SlidingMenu.LEFT);
		sm.setBehindScrollScale(.0f);
		sm.setBehindCanvasTransformer(new CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
				canvas.scale(scale, scale, canvas.getWidth() / 2, canvas.getHeight() / 2);
			}
		});
		setSlidingActionBarEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (this.so != this.soList.getList().get(arg2)) {
			this.idSOClicked = arg2;
			this.so = this.soList.getList().get(this.idSOClicked);
			this.landscapeKey = so.getActualLandscapeKey() == null ? (so.getLandscapeGenotypes().isEmpty() ? null : so.getLandscapeGenotypes().lastKey()) : so.getActualLandscapeKey();
			this.portraitKey = so.getActualPortraitKey() == null ? (so.getPortraitGenotypes().isEmpty() ? null : so.getPortraitGenotypes().lastKey()) : so.getActualPortraitKey();
			setTitle(this.so.getName());
			if (isLandscape && landscapeKey != null) {
				decode(so.getLandscapeGenotypes().get(landscapeKey));
			} else if (!isLandscape && portraitKey != null) {
				decode(so.getPortraitGenotypes().get(portraitKey));
			} else {
				generateGUI();
			}
		}
		getSlidingMenu().toggle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.guimenu, menu);
		this.menu = menu;
		verifyMenuItemsStatus();

		return true;
	}

	private void verifyMenuItemsStatus() {
		if (isLandscape) {
			if (landscapeKey == null) {
				setEnabledMenuItem(R.id.previous_gui, R.drawable.ic_action_previous_item_disabled, false);
				setEnabledMenuItem(R.id.next_gui, R.drawable.ic_action_next_item_disabled, false);
			} else {
				if (so.getLandscapeGenotypes().lowerKey(landscapeKey) == null) {
					setEnabledMenuItem(R.id.next_gui, R.drawable.ic_action_next_item_disabled, false);
				} else {
					setEnabledMenuItem(R.id.next_gui, R.drawable.ic_action_next_item_enabled, true);
				}
				if (so.getLandscapeGenotypes().higherKey(landscapeKey) == null) {
					setEnabledMenuItem(R.id.previous_gui, R.drawable.ic_action_previous_item_disabled, false);
				} else {
					setEnabledMenuItem(R.id.previous_gui, R.drawable.ic_action_previous_item_enabled, true);
				}
			}
		} else {
			if (portraitKey == null) {
				setEnabledMenuItem(R.id.previous_gui, R.drawable.ic_action_previous_item_disabled, false);
				setEnabledMenuItem(R.id.next_gui, R.drawable.ic_action_next_item_disabled, false);
			} else {
				if (so.getPortraitGenotypes().lowerKey(portraitKey) == null) {
					setEnabledMenuItem(R.id.next_gui, R.drawable.ic_action_next_item_disabled, false);
				} else {
					setEnabledMenuItem(R.id.next_gui, R.drawable.ic_action_next_item_enabled, true);
				}
				if (so.getPortraitGenotypes().higherKey(portraitKey) == null) {
					setEnabledMenuItem(R.id.previous_gui, R.drawable.ic_action_previous_item_disabled, false);
				} else {
					setEnabledMenuItem(R.id.previous_gui, R.drawable.ic_action_previous_item_enabled, true);
				}
			}
		}
	}

	private void setEnabledMenuItem(int itemId, int iconId, boolean enabled) {
		this.menu.findItem(itemId).setEnabled(enabled);
		this.menu.findItem(itemId).setIcon(iconId);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.refresh_option) {
			generateGUI();
		} else if (item.getItemId() == R.id.next_gui) {
			if (isLandscape) {
				Double nextLandscapeKey = so.getLandscapeGenotypes().lowerKey(landscapeKey);
				landscapeKey = nextLandscapeKey;
				so.setActualLandscapeKey(nextLandscapeKey);
				decode(so.getLandscapeGenotypes().get(nextLandscapeKey));
			} else {
				Double nextPortraitKey = so.getPortraitGenotypes().lowerKey(portraitKey);
				portraitKey = nextPortraitKey;
				so.setActualPortraitKey(nextPortraitKey);
				decode(so.getPortraitGenotypes().get(nextPortraitKey));
			}
		} else if (item.getItemId() == R.id.previous_gui) {
			if (isLandscape) {
				Double previousLandscapeKey = so.getLandscapeGenotypes().higherKey(landscapeKey);
				landscapeKey = previousLandscapeKey;
				so.setActualLandscapeKey(previousLandscapeKey);
				decode(so.getLandscapeGenotypes().get(previousLandscapeKey));
			} else {
				Double previdousPortraitKey = so.getPortraitGenotypes().higherKey(portraitKey);
				portraitKey = previdousPortraitKey;
				so.setActualPortraitKey(previdousPortraitKey);
				decode(so.getPortraitGenotypes().get(previdousPortraitKey));
			}
		} else {
			getSlidingMenu().toggle();
		}

		return true;
	}

	@Override
	public void onDestroy() {
		super.onStop();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("idSOClicked", this.idSOClicked);
		editor.commit();
		if (loop != null) {
			loop.interrupt();
		}
		Thread save = new Thread(new Runnable() {
			public void run() {
				try {
					facade.saveSmartObjectListToDisk(soList, openFileOutput("SOFILE", Context.MODE_PRIVATE));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		save.start();
		try {
			save.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.idSOClicked = -1;
		if (loop != null) {
			loop.interrupt();
		}
	}

	public void generateGUI() {
		startTransaction(this);
	}

	private void decode(final Genotype genotype) {
		new Thread(new Runnable() {
			public void run() {
				AndroidPhenotype phenotype = (AndroidPhenotype) decoder.decode(genotype, SmartObjectGUIActivity.this);
				mHandler.obtainMessage(1, phenotype).sendToTarget();
			}
		}).start();
	}

	@Override
	public void preExecute() {
		// swapLayouts();
	}

	@Override
	public void execute() throws Exception {
		if (this.isSendCommand) {
			ack = facade.sendSmartObjectCommand(so.getUrl(), this.listParameters);
		} else {
			AndroidPhenotype phenotype = (AndroidPhenotype) this.facade.generateGUI(this.soList, this.idSOClicked, width, height, this, openFileOutput("SOFILE", Context.MODE_PRIVATE));
			this.layout = phenotype.getLayout();
			if (isLandscape) {
				landscapeKey = this.so.getLandscapeGenotypes().lastKey();
			} else {
				portraitKey = this.so.getPortraitGenotypes().lastKey();
			}
			ack = "Tela gerada com sucesso!";
		}
	}

	@Override
	public void postExecute() {
		if (this.isSendCommand) {
			this.isSendCommand = false;
		} else {
			setEnabledMenuItem(R.id.refresh_option, R.drawable.ic_action_refresh_enabled, true);
			verifyMenuItemsStatus();
			setLayout(this.layout);
			// swapLayouts();
		}
		Toast.makeText(this, ack, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void swapLayouts() {
		this.progressLayout.setVisibility(this.progressLayout.getVisibility() == LinearLayout.VISIBLE ? LinearLayout.INVISIBLE : LinearLayout.VISIBLE);
	}

	public void setLayout(final View layout) {
		ViewGroupUtils.replaceView(soGUILayout, layout);
		layout.invalidate();
		soGUILayout = (ScrollView) layout;
	}

	public void executeService(final View view) {
		final SOLinearLayout frame = (SOLinearLayout) view.getParent().getParent();
		serviceName = (TextView) ((LinearLayout) frame.getChildAt(0)).getChildAt(0);
		this.listParameters = new ArrayList<String>();
		this.listParameters.add("idSOModbus");
		this.listParameters.add(so.getIdSOModbus());
		this.listParameters.add("idServiceModbus");
		this.listParameters.add(so.getIdServiceModbus(String.valueOf(serviceName.getText())));
		this.listParameters.add("idRegisterModbus");
		this.listParameters.add(so.getIdRegisterModbus(serviceName.getText().toString()));
		// this.listParameters.add(serviceName.getText().toString());
		this.flFirstTime = true;
		createServiceURL(this.listParameters, frame);
		this.isSendCommand = true;
		serviceName = null;
		if (listParameters.get(3).equals("03")) {
			serviceName = getAckTextView();
		}
		new Thread(new Runnable() {
			public void run() {
				ack = facade.sendSmartObjectCommand(so.getUrl(), listParameters);
				if (serviceName == null) {
					mHandler.obtainMessage(3, ack).sendToTarget();
				} else {
					mHandler.obtainMessage(2, ack).sendToTarget();
				}
			}
		}).start();
		// startTransaction(this);
	}

	/**
	 * @return
	 */
	private TextView getAckTextView() {
		TextView tv = null;
		LinearLayout layout = (LinearLayout) soGUILayout.getChildAt(0);
		for (int i = 0; i < layout.getChildCount(); i++) {
			View view = layout.getChildAt(i);
			if (view instanceof SOLinearLayout && ((SOLinearLayout) view).isGetLayout()) {
				LinearLayout child = (LinearLayout) ((LinearLayout) view).getChildAt(1);
				tv = (TextView) child.getChildAt(1);
			}
		}

		return tv;
	}

	private void makeToast(String message) {
		Toast.makeText(SmartObjectGUIActivity.this, message, Toast.LENGTH_SHORT).show();
	}

	private void createServiceURL(List<String> list, View root) {
		int i = 0;
		if (this.flFirstTime) {
			i = 1;
			this.flFirstTime = false;
		}
		if (!this.flFirstTime && !(root instanceof LinearLayout)) {
			if (root instanceof CheckBox) {
				list.add(String.valueOf(((CheckBox) root).isChecked()));
			} else if (root instanceof EditText) {
				list.add(((EditText) root).getText().toString());
			} else if (root instanceof TextView) {
				String tv = ((TextView) root).getText().toString();
				if (tv.contains(":")) {
					list.add(tv.substring(0, tv.indexOf(" ")));
				} else {
					list.add(tv);
				}
			} else if (root instanceof Spinner) {
				list.add((String) ((Spinner) root).getSelectedItem());
			} else if (root instanceof NegativeSeekBar) {
				list.add(((NegativeSeekBar) root).getStringProgress());
			} else if (root instanceof RadioGroup) {
				RadioGroup rg = (RadioGroup) root;
				RadioButton rb = (RadioButton) rg.getChildAt(rg.getCheckedRadioButtonId());
				list.add(rb.getText().toString());
			}

			return;
		} else {
			for (; i < ((LinearLayout) root).getChildCount(); i++) {
				createServiceURL(list, ((LinearLayout) root).getChildAt(i));
			}

			return;
		}

	}
}
