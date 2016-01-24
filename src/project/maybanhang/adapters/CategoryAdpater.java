package project.maybanhang.adapters;

import java.util.ArrayList;

import project.maybanhang.entity.Category;
import project.maybanhang.ver1.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdpater extends BaseAdapter {

	private ArrayList<Category> data;
	private Activity activity;
	private LayoutInflater layoutInflater;

	public CategoryAdpater(Activity _activity, ArrayList<Category> items) {
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
			convertView = layoutInflater.inflate(R.layout.category_item_layout, null);
			viewHolder.textViewTitle = (TextView) convertView
					.findViewById(R.id.category_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Category temp = data.get(position);
		viewHolder.textViewTitle.setText(temp.Name + "");
		return convertView;
	}

	public static class ViewHolder {
		TextView textViewTitle;
		ImageView image;
	}

}
