package com.bn.gameopt.android;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.bn.gameopt.android.adapter.GameListAdapter;
import com.bn.gameopt.android.classes.GameListItem;
import com.bn.gameopt.android.loader.GameListLoader;
import com.rarnu.utils.UIUtils;

public class MainActivity extends Activity implements OnItemClickListener {

	GridView gvGames;
	GameListAdapter adapterGames;
	GameListLoader loaderGames;
	List<GameListItem> listGames;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		UIUtils.initDisplayMetrics(this, getWindowManager(), false);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gvGames = (GridView) findViewById(R.id.gvGames);
		gvGames.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		GameListItem item = listGames.get(position);
		// TODO: show sub folder
	}
}
