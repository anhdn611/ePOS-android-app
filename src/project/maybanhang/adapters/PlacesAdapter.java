package project.maybanhang.adapters;

import java.util.ArrayList;

import project.maybanhang.entity.Place;
import project.maybanhang.ver1.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlacesAdapter extends BaseAdapter {

	private ArrayList<Place> data;
	private Activity activity;
	private LayoutInflater layoutInflater;

	public PlacesAdapter(Activity _activity, ArrayList<Place> items) {
		this.activity = _activity;
		this.layoutInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.data = items;

	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public Object getItem(int arg0) {

		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.place_item_layout,
					null);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.tv_place_name);
			viewHolder.textView.setText(data.get(position).Name);
			convertView.setTag(viewHolder);
			//convertView.setTag(1, "PlaceItem");
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}		
		return convertView;
	}

	public static class ViewHolder {
		TextView textView;
		LinearLayout background;
		String type="Place";
	}

}
