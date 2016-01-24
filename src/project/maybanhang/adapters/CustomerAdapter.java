package project.maybanhang.adapters;

import java.util.ArrayList;

import project.maybanhang.entity.Customer;
import project.maybanhang.ver1.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomerAdapter extends BaseAdapter {

	private ArrayList<Customer> data;
	private Activity activity;
	private LayoutInflater layoutInflater;

	public CustomerAdapter(Activity _activity, ArrayList<Customer> customers){
			this.activity = _activity;
			this.layoutInflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.data = customers;
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
			convertView=layoutInflater.inflate(R.layout.customer_item_layout,null);
			viewHolder.textViewName = (TextView) convertView
					.findViewById(R.id.customer_name);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Customer temp= data.get(position);
		Log.d(this.toString(), "KHÁCH HÀNG: " + temp.getLast_name()+" "+temp.getFirst_name());
		viewHolder.textViewName.setText(temp.getLast_name()+" "+temp.getFirst_name());			
		return convertView;
	}
	public static class ViewHolder{		
		TextView textViewName;		
	}

}
