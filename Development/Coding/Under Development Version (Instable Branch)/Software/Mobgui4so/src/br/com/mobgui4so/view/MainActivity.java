/**
 * 
 */
package br.com.mobgui4so.view;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import br.com.mobgui4so.R;
import br.com.mobgui4so.controller.ApplicationFacade;
import br.com.mobgui4so.model.pojo.SmartObjectList;

/**
 * @author Ercilio Nascimento
 */
public class MainActivity extends BaseActivity implements OnClickListener, OnFocusChangeListener, ITransaction {

	private ImageButton btSearch;
	private RadioGroup rgSearchType;
	private SmartObjectList list;
	private SmartObjectList listFromDisk;
	private LinearLayout searchLayout;
	private LinearLayout progressLayout;
	private EditText etUser;
	private EditText etPassword;
	private EditText etServer;
	private CheckBox ckSaveUser;
	private ApplicationFacade facade;
	private Handler mHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.facade = new ApplicationFacade();
		this.etUser = (EditText) findViewById(R.id.etUser);
		etUser.setText("ercilio");
		this.etPassword = (EditText) findViewById(R.id.etPassword);
		etPassword.setText("batatinha");
		this.etServer = (EditText) findViewById(R.id.etServer);
		etServer.setText("http://172.23.21.31:8080/SORepository");
		// this.ckSaveUser = (CheckBox) findViewById(R.id.ckSaveUser);
		this.searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
		this.progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
		// this.rgSearchType = (RadioGroup) findViewById(R.id.radioGroup1);
		this.btSearch = (ImageButton) findViewById(R.id.bSearch);
		this.btSearch.setOnClickListener(this);
		this.etUser.setOnFocusChangeListener(this);
		this.etPassword.setOnFocusChangeListener(this);
		this.etServer.setOnFocusChangeListener(this);
		try {
			this.listFromDisk = this.facade.loadSmartObjectListFromDisk(openFileInput("SOFILE"));
		} catch (FileNotFoundException e) {
			// do nothing
		} catch (IOException e) {
			// do nothing
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					Intent i = new Intent(MainActivity.this, ListSmartObjectActivity.class);
					i.putExtra("list", listFromDisk);
					startActivity(i);
					break;
				default:
					break;
				}
			}
		};
	}

	@Override
	public void preExecute() {
		this.swapLayouts();
	}

	@Override
	public void execute() throws IOException, ClassNotFoundException {
		String[] form = new String[4];
		form[0] = this.etUser.getText().toString();
		form[1] = this.etPassword.getText().toString();
		form[2] = this.etServer.getText().toString();
		// form[3] = String.valueOf(this.ckSaveUser.isChecked());
		this.list = this.facade.discovery(form, 1, getResources().getAssets().open("appConfig.properties"));
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (this.searchLayout.getVisibility() == LinearLayout.INVISIBLE)
			this.swapLayouts();
	}

	@Override
	public void postExecute() {
		if (this.list == null) {
			alert(R.string.noSOfound);
			this.swapLayouts();
		} else if (this.list.getErrorMsg() != null) {
			alert(R.string.invalidUser);
			this.swapLayouts();
		} else {
			listFromDisk = list.merge(listFromDisk);
			new Thread(new Runnable() {
				public void run() {
					try {
						facade.saveSmartObjectListToDisk(listFromDisk, openFileOutput("SOFILE", Context.MODE_PRIVATE));
						mHandler.obtainMessage(1).sendToTarget();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	@Override
	public void onClick(View v) {
		startTransaction(this);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		boolean has = ((EditText) v).getText().toString().equals(getString(R.string.user)) || ((EditText) v).getText().toString().equals(getString(R.string.password))
			|| ((EditText) v).getText().toString().equals(getString(R.string.server)) || ((EditText) v).getText().toString().equals("");
		if (hasFocus && has) {
			if (v.equals(this.etPassword)) {
				this.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}
			((EditText) v).setText("");
		} else if (has) {
			if (v.equals(this.etUser)) {
				this.etUser.setText(R.string.user);
			} else if (v.equals(this.etPassword)) {
				this.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
				this.etPassword.setText(R.string.password);
			} else {
				this.etServer.setText(R.string.server);
			}
		}
	}

	@Override
	public void onBackPressed() {
		if (this.searchLayout.getVisibility() == LinearLayout.INVISIBLE) {
			this.swapLayouts();
			finishTransaction();
		} else {
			finish();
		}
	}

	@Override
	public void swapLayouts() {
		this.searchLayout.setVisibility(this.searchLayout.getVisibility() == LinearLayout.VISIBLE ? LinearLayout.INVISIBLE : LinearLayout.VISIBLE);
		this.progressLayout.setVisibility(this.progressLayout.getVisibility() == LinearLayout.VISIBLE ? LinearLayout.INVISIBLE : LinearLayout.VISIBLE);
	}
}
