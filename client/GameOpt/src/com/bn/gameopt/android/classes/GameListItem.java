package com.bn.gameopt.android.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameListItem implements Serializable {

	private static final long serialVersionUID = 6305838263098165994L;
	public List<GameItem> listGames = null;
	public String name;

	public GameListItem(String name) {
		this.name = name;
		this.listGames = new ArrayList<GameItem>();
	}

	public GameListItem(String name, List<GameItem> listGames) {
		this(name);
		this.listGames = listGames;
	}
}
