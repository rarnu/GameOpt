package com.bn.gameopt.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bn.gameopt.android.R;
import com.bn.gameopt.android.classes.GameItem;
import com.rarnu.utils.UIUtils;

public class GameAppAdapter extends BaseAdapter {

	private List<GameItem> list;
	private LayoutInflater inflater;
	int gridSize;

	public GameAppAdapter(Context context, List<GameItem> list, int gridSize) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		this.gridSize = gridSize;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = inflater.inflate(R.layout.item_ag_game, parent, false);
		}
		UIUtils.setViewSizeX(v, gridSize);
		UIUtils.setViewSizeY(v, gridSize);
		GameAppHolder holder = (GameAppHolder) v.getTag();
		if (holder == null) {
			holder = new GameAppHolder();
			holder.imgIcon = (ImageView) v.findViewById(R.id.imgIcon);
			holder.tvName = (TextView) v.findViewById(R.id.tvName);
			v.setTag(holder);
		}
		GameItem item = list.get(position);
		if (item != null) {
			holder.tvName.setText(item.name);
			holder.imgIcon.setImageDrawable(item.icon);
		}
		return v;
	}

}
