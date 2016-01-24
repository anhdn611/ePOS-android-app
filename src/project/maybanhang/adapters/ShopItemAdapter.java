package project.maybanhang.adapters;

import java.util.ArrayList;

import project.maybanhang.adapters.CategoryAdpater.ViewHolder;
import project.maybanhang.entity.Category;
import project.maybanhang.entity.ItemInfo;
import project.maybanhang.ver1.R;
import android.app.Activity;
import android.content.Context;
import android.content.ClipData.Item;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopItemAdapter extends BaseAdapter {
	private ArrayList<ItemInfo> data;
	private Activity activity;
	private LayoutInflater layoutInflater;

	public ShopItemAdapter(Activity _activity, ArrayList<ItemInfo> items) {
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
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.shop_item_layout, null);
			viewHolder.textViewTitle = (TextView) convertView
					.findViewById(R.id.item_name);
			viewHolder.textViewPrice = (TextView) convertView
					.findViewById(R.id.item_price);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ItemInfo temp = data.get(position);
		viewHolder.textViewTitle.setText(temp.name + "");
		viewHolder.textViewPrice.setText(temp.retail_price + "");
		return convertView;
	}

	public static class ViewHolder {
		TextView textViewTitle;
		TextView textViewPrice;
		ImageView image;
	}
}