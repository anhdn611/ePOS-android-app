package project.maybanhang.adapters;

import java.util.ArrayList;

import project.maybanhang.entity.ItemInfo;
import project.maybanhang.entity.QuickKeyProduct;
import project.maybanhang.ver1.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuickKeyProductsAdapter extends ArrayAdapter<QuickKeyProduct> {

	private ArrayList<QuickKeyProduct> data;
	private Context context;

	public QuickKeyProductsAdapter(Context _context,int resource, ArrayList<QuickKeyProduct> items) {
		super(_context, resource, items);
		this.context = _context;	
		this.data = items;
	}

	@Override
	public int getCount() {

		return data.size();
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
			LayoutInflater layoutInflater= (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.item_layout, null);
			viewHolder.textViewTitle = (TextView) convertView
					.findViewById(R.id.item_name);
//			viewHolder.textViewPrice = (TextView) convertView
//					.findViewById(R.id.item_price);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		QuickKeyProduct temp = data.get(position);
		viewHolder.textViewTitle.setText(temp.getTitle() + "");
		//viewHolder.textViewPrice.setText(temp.retail_price + "");
		return convertView;
	}

	public static class ViewHolder {
		TextView textViewTitle;
		//TextView textViewPrice;
		ImageView image;
	}

}
