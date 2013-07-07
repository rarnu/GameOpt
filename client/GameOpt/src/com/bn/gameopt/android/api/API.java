package com.bn.gameopt.android.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.bn.gameopt.android.classes.GameItem;
import com.bn.gameopt.android.classes.GameListItem;
import com.rarnu.utils.HttpRequest;

public class API {

	private static final String HOST = "http://192.168.1.6:8888/";

	public static List<String> getProtectedPackages(final Context context) {
		// {"message":"succ","data":["com.android.systemui","com.cyanogenmod.trebuchet","com.android.settings"]}
		String ret = HttpRequest.get(HOST + "get_protected_packages.php", "",
				HTTP.UTF_8);
		Log.e("getProtectedPackages", ret);
		List<String> list = null;
		try {
			JSONObject json = new JSONObject(ret);
			if (json.getString("message").equals("succ")) {
				JSONArray jarrData = json.getJSONArray("data");
				if (jarrData != null && jarrData.length() != 0) {
					list = new ArrayList<String>();
					for (int i = 0; i < jarrData.length(); i++) {
						list.add(jarrData.getString(i));
					}
				}
			}
		} catch (Exception e) {

		}
		return list;
	}

	public static List<GameListItem> getGames(final Context context) {
		// {"message":"succ","data":[{"name":"大众点评","package_name":"com.dianping.v1","type":"软件"},{"name":"小鸟爆破","package_name":"com.enfeel.birzzle","type":"休闲游戏"},{"name":"ESFileExplorer","package_name":"com.estrongs.android.pop","type":"软件"},{"name":"Evernote","package_name":"com.evernote.world","type":"软件"},{"name":"ZENONIA5","package_name":"com.gamevil.zenonia5.global","type":"RPG"},{"name":"GitHub","package_name":"com.github.mobile","type":"软件"},{"name":"Google Plus","package_name":"com.google.android.apps.plus","type":"软件"},{"name":"Gmail","package_name":"com.google.android.gm","type":"软件"},{"name":"Keep","package_name":"com.google.android.keep","type":"软件"},{"name":"疯狂猜图","package_name":"com.hortor.pictoword","type":"休闲游戏"},]}
		String ret = HttpRequest.get(HOST + "get_games.php", "", HTTP.UTF_8);
		Log.e("getGames", ret);
		List<GameListItem> list = null;
		List<GameItem> listItems = null;
		try {
			JSONObject json = new JSONObject(ret);
			if (json.getString("message").equals("succ")) {
				JSONArray jarrData = json.getJSONArray("data");
				if (jarrData != null && jarrData.length() != 0) {
					listItems = new ArrayList<GameItem>();
					for (int i = 0; i < jarrData.length(); i++) {
						listItems.add(GameItem.fromJson(context,
								jarrData.getJSONObject(i)));
					}
					list = buildFolderedList(listItems);
				}
			}
		} catch (Exception e) {
			Log.e("getGames", e.getMessage());
		}
		return list;
	}

	private static List<GameListItem> buildFolderedList(List<GameItem> list) {
		List<GameListItem> listRet = null;
		if (list != null && list.size() != 0) {
			listRet = new ArrayList<GameListItem>();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).installed) {
					addItemToList(listRet, list.get(i));
				}
			}
		}
		return listRet;
	}

	private static void addItemToList(List<GameListItem> list, GameItem item) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).name.equals(item.type)) {
				list.get(i).listGames.add(item);
				return;
			}
		}
		list.add(new GameListItem(item.type));
		addItemToList(list, item);
	}
}
