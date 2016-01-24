package project.maybanhang.adapters;

import java.util.ArrayList;

import project.maybanhang.adapters.QuickKeyProductsAdapter.ViewHolder;
import project.maybanhang.entity.ItemInfo;
import project.maybanhang.ver1.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {

	private ArrayList<String> data;
	private Activity activity;
	private LayoutInflater layoutInflater;

	public MenuAdapter(Activity _activity, ArrayList<String> menus){
			this.activity = _activity;
			this.layoutInflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.data = menus;
		
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
		
		ViewHolder viewHolder=new ViewHolder();
		if(convertView == null){
			convertView=layoutInflater.inflate(R.layout.group_item_layout,null);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.text_group_name);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String temp=data.get(position);
		viewHolder.textView.setText(temp);			
		
		return convertView;
	}
	public static class ViewHolder{		
		TextView textView;		
	}


}
