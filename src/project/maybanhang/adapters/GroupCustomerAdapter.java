package project.maybanhang.adapters;

import java.util.ArrayList;

import project.maybanhang.entity.GroupCustomer;
import project.maybanhang.ver1.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GroupCustomerAdapter extends BaseAdapter {

	private ArrayList<GroupCustomer> data;
	private Activity activity;
	private LayoutInflater layoutInflater;

	public GroupCustomerAdapter(Activity _activity, ArrayList<GroupCustomer> groups){
			this.activity = _activity;
			this.layoutInflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.data = groups;
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
		
		ViewHolder viewHolder=new ViewHolder();
		if(convertView == null){
			convertView=layoutInflater.inflate(R.layout.groupcustomer_item_layout,null);
			viewHolder.textViewName = (TextView) convertView
					.findViewById(R.id.groupcustomer_name);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		GroupCustomer temp= data.get(position);
		Log.d(this.toString(), "Set Group" + temp.getGroup_name());
		viewHolder.textViewName.setText(temp.getGroup_name());			
		return convertView;
	}
	public static class ViewHolder{		
		TextView textViewName;		
	}

}
