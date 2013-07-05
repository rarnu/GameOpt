package com.bn.gameopt.android.classes;

import java.io.Serializable;

import org.json.JSONObject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class GameItem implements Serializable {

	private static final long serialVersionUID = 2078766849262310252L;
	public String name = null;
	public String packageName = null;
	public String extraData = null;
	public Drawable icon = null;
	public boolean installed = false;
	public String type = null;
	private Context context;

	public GameItem(Context context, String name, String packageName,
			String type) {
		this.context = context;
		this.name = name;
		this.packageName = packageName;
		this.type = type;
		init();
	}

	public GameItem(Context context, String name, String packageName,
			String type, String extraData) {
		this(context, name, packageName, type);
		this.extraData = extraData;
		init();
	}

	private void init() {
		// get icon, get installed
		PackageManager pm = context.getPackageManager();
		try {
			ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
			installed = (info != null);
			icon = pm.getApplicationIcon(info);
		} catch (Exception e) {

		}
	}

	public static GameItem fromJson(Context context, JSONObject json)
			throws Exception {
		GameItem item = new GameItem(context, json.getString("name"),
				json.getString("package_name"), json.getString("type"));
		return item;
	}

}
