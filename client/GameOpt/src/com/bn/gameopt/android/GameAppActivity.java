package com.bn.gameopt.android;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.bn.gameopt.android.adapter.GameAppAdapter;
import com.bn.gameopt.android.classes.GameItem;
import com.bn.gameopt.android.classes.GameListItem;
import com.rarnu.utils.UIUtils;

public class GameAppActivity extends Activity {

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
	}
}
