package com.bn.gameopt.android.loader;

import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.bn.gameopt.android.classes.GameListItem;

public class GameListLoader extends AsyncTaskLoader<List<GameListItem>> {

	public GameListLoader(Context context) {
		super(context);
	}

	@Override
	public List<GameListItem> loadInBackground() {
		return null;
	}

	@Override
	protected void onStartLoading() {
		forceLoad();
	}

	@Override
	public void onCanceled(List<GameListItem> data) {
		super.onCanceled(data);
	}

	@Override
	protected void onStopLoading() {
		cancelLoad();
	}

	@Override
	protected void onReset() {
		stopLoading();

	}

}
