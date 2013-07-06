package com.bn.gameopt.android;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.bn.gameopt.android.adapter.GameAppAdapter;
import com.bn.gameopt.android.classes.GameItem;
import com.bn.gameopt.android.classes.GameListItem;
import com.rarnu.utils.UIUtils;

public class GameAppActivity extends Activity implements OnItemClickListener {

	GridView gvGameApps;
	GameAppAdapter adapterGameApps;
	List<GameItem> listGameApps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int id = getIntent().getIntExtra("id", 0);
		GameListItem item = Global.listGames.get(id);
		setTitle(item.name);

		setContentView(R.layout.activity_gameapp);
		gvGameApps = (GridView) findViewById(R.id.gvGameApps);
		listGameApps = item.listGames;
		int size = (UIUtils.getWidth() - UIUtils.dipToPx(80)) / 3;
		adapterGameApps = new GameAppAdapter(this, listGameApps, size);
		gvGameApps.setAdapter(adapterGameApps);
		gvGameApps.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		finish();
		GameItem item = listGameApps.get(position);
		new AlertDialog.Builder(this).setTitle(item.name)
				.setMessage(item.packageName).setPositiveButton("OK", null)
				.show();

	}
}
