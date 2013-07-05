package com.bn.gameopt.android.classes;

import java.util.List;

public class GameListItem {

	public List<GameItem> listGames = null;
	public String name;
	
	public GameListItem(String name) {
		this.name = name;
	}
	
	public GameListItem(String name, List<GameItem> listGames) {
		this(name);
		this.listGames = listGames;
	}
}
