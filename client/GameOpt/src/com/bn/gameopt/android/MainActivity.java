package com.bn.gameopt.android;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.Loader;
import android.content.Loader.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.bn.gameopt.android.adapter.GameListAdapter;
import com.bn.gameopt.android.api.API;
import com.bn.gameopt.android.classes.GameListItem;
import com.bn.gameopt.android.loader.GameListLoader;
import com.rarnu.command.RootUtils;
import com.rarnu.utils.UIUtils;

public class MainActivity extends Activity implements OnItemClickListener,
		OnLoadCompleteListener<List<GameListItem>> {

	GridView gvGames;
	GameListAdapter adapterGames;
	GameListLoader loaderGames;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		UIUtils.initDisplayMetrics(this, getWindowManager(), false);
		RootUtils.init(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gvGames = (GridView) findViewById(R.id.gvGames);
		gvGames.setOnItemClickListener(this);

		int size = (UIUtils.getWidth() - UIUtils.dipToPx(32)) / 3;
		adapterGames = new GameListAdapter(this, Global.listGames, size);
		gvGames.setAdapter(adapterGames);

		loaderGames = new GameListLoader(this);
		loaderGames.registerListener(0, this);
		loaderGames.startLoading();

		loadProtectedListT();
		startService(new Intent(this, CleanService.class));
	}

	@Override
	protected void onDestroy() {
		stopService(new Intent(this, CleanService.class));
		Global.release();
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		startActivity(new Intent(this, GameAppActivity.class).putExtra("id",
				position));
	}

	@Override
	public void onLoadComplete(Loader<List<GameListItem>> loader,
			List<GameListItem> data) {
		Global.listGames.clear();
		if (data != null) {
			Global.listGames.addAll(data);
			adapterGames.setNewList(Global.listGames);
		}
	}

	private void loadProtectedListT() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Global.listIgnorePackages = API
						.getProtectedPackages(MainActivity.this);
			}
		}).start();
	}
}
