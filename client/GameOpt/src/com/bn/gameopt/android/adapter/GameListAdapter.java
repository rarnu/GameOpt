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
import com.bn.gameopt.android.classes.GameListItem;

public class GameListAdapter extends BaseAdapter {

	private List<GameListItem> list = null;
	private LayoutInflater inflater = null;

	public GameListAdapter(Context context, List<GameListItem> list) {
		inflater = LayoutInflater.from(context);
		this.list = list;
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
			v = inflater.inflate(R.layout.item_am_games, parent, false);
		}
		GameItemHolder holder = (GameItemHolder) v.getTag();
		if (holder == null) {
			holder = new GameItemHolder();
			holder.imgIcon = new ImageView[4];
			holder.imgIcon[0] = (ImageView) v.findViewById(R.id.imgIcon1);
			holder.imgIcon[1] = (ImageView) v.findViewById(R.id.imgIcon2);
			holder.imgIcon[2] = (ImageView) v.findViewById(R.id.imgIcon3);
			holder.imgIcon[3] = (ImageView) v.findViewById(R.id.imgIcon4);
			holder.tvName = (TextView) v.findViewById(R.id.tvName);
			v.setTag(holder);
		}
		GameListItem item = list.get(position);
		if (item != null) {
			holder.tvName.setText(item.name);
			for (int i = 0; i < holder.imgIcon.length; i++) {
				holder.imgIcon[i].setImageDrawable(null);
			}

			if (item.listGames != null) {
				for (int i = 0; i < 4; i++) {
					if (item.listGames.size() >= (i + 1)) {
						holder.imgIcon[i].setImageDrawable(item.listGames
								.get(i).icon);
					}
				}
			}
		}
		return v;
	}

}
