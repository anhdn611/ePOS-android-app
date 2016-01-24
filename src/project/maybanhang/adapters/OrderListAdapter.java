package project.maybanhang.adapters;

import java.text.NumberFormat;
import java.util.List;

import project.maybanhang.entity.OrderItem;
import project.maybanhang.ver1.R;
import project.maybanhang.ver1.RestaurantActivity;
import project.maybanhang.ver1.ShopActivity;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class OrderListAdapter extends ArrayAdapter<OrderItem> {

	boolean onDelete;
	Context context;
	List<OrderItem> values;
	public OrderListAdapter(Context context, int resource, List<OrderItem> data) {
		super(context, resource, data);
		this.context = context;
	    this.values = data;
	}
	
	@Override
	public int getCount() {
		return values.size();
	}


	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			LayoutInflater inflater= (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi = inflater.inflate(R.layout.order_list_item,parent,false);
			viewHolder.TextView_name = (TextView) vi
					.findViewById(R.id.order_name);
			viewHolder.TextView_num1 = (TextView) vi
					.findViewById(R.id.order_num1);
			viewHolder.TextView_num2 = (TextView) vi
					.findViewById(R.id.order_num2);
			viewHolder.TextView_price = (TextView) vi
					.findViewById(R.id.order_total_price);
			viewHolder.Btn_delete = (Button) vi
					.findViewById(R.id.Btn_delete_order_item);
			viewHolder.Btn_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					try{
					Log.d("Order List","Delete " +view.getTag());
					int position=Integer.parseInt(view.getTag().toString());
					values.remove(position); 
					Activity parent=(Activity)context;
					if (parent instanceof RestaurantActivity) {
						((RestaurantActivity)parent).UpdateOrder();
					} else {
						((ShopActivity)parent).UpdateOrder();
					}
					Log.d("Order List","Size order list: "+ values.size());
					notifyDataSetChanged();
					}catch(Exception e){
						Log.e("Order List",e.getMessage());
					}
				}
			});
			vi.setTag(viewHolder);
			viewHolder.Btn_delete.setTag(position);			
		} else {
			viewHolder = (ViewHolder) vi.getTag();
		}
		OrderItem order=values.get(position);
		viewHolder.TextView_name.setText(order.getName());
		viewHolder.TextView_num2.setText(order.getNumber()+"");
		String price = "0";
		//if (order.getPrice() != null)
			price = NumberFormat.getInstance().format(order.getPrice() * order.getNumber());
		viewHolder.TextView_price.setText(price);
		
		return vi;
	}

	public static class ViewHolder {

		public TextView TextView_name;
		public TextView TextView_num1;
		public TextView TextView_num2;
		public TextView TextView_price;
		public Button Btn_delete;
	}
}
