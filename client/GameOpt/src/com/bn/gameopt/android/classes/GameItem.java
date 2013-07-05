package com.bn.gameopt.android.classes;

import android.graphics.drawable.Drawable;

public class GameItem {

	public String name = null;
	public String packageName = null;
	public String extraData = null;
	public Drawable icon = null;

	public GameItem(String name, String packageName) {
		this.name = name;
		this.packageName = packageName;
		getPackageIcon();
	}

	public GameItem(String name, String packageName, String extraData) {
		this(name, packageName);
		this.extraData = extraData;
		getPackageIcon();
	}
	
	private void getPackageIcon() {
		
	}

}
