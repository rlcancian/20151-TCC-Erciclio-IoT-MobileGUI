package br.com.mobgui4so.view;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.mobgui4so.R;
import br.com.mobgui4so.controller.ApplicationFacade;
import br.com.mobgui4so.model.pojo.SmartObjectList;

/**
 * @author Ercilio Nascimento
 */

public class ListSmartObjectActivity extends BaseActivity implements OnItemClickListener {

	private SmartObjectList listFromDisk;
	private ListView listView;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_list_smart_object);
		try {
			this.listFromDisk = new ApplicationFacade().loadSmartObjectListFromDisk(openFileInput("SOFILE"));
		} catch (FileNotFoundException e) {
			// do nothing
		} catch (IOException e) {
			// do nothing
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// this.soList = (SmartObjectList) getIntent().getSerializableExtra("list");
		this.listView = (ListView) findViewById(R.id.listViewSO);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.listFromDisk.getSONames());
		this.listView.setAdapter(adapter);
		this.listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent i = new Intent(this, SmartObjectGUIActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("SOList", this.listFromDisk);
		b.putInt("idSOClicked", arg2);
		i.putExtras(b);
		startActivity(i);
	}
}
