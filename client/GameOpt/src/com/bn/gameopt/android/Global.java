package com.bn.gameopt.android;

import java.util.ArrayList;
import java.util.List;

import com.bn.gameopt.android.classes.GameListItem;

public class Global {
	public static List<GameListItem> listGames = new ArrayList<GameListItem>();
	
	public static void release() {
		listGames.clear();
	}
}
