package project.maybanhang.adapters;

import java.util.ArrayList;

import project.maybanhang.DataSource;
import project.maybanhang.adapters.TableListAdapter.ViewHolder;
import project.maybanhang.entity.Table;
import project.maybanhang.ver1.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GopBanAdapter extends ArrayAdapter<Table> {

	private ArrayList<Table> data;
	private Context context;

	public GopBanAdapter(Context context, int resource, ArrayList<Table> tables) {
		super(context, resource, tables);
		this.context = context;

		this.data = tables;
		if (DataSource.sSelectedTable != null)
			DataSource.sSelectedTable.isGopBanSelected = true;
	}

	public Table getItem(int index) {
		return data.get(index);
	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.table_item_layout,
					null);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.table_name);
			viewHolder.layout_chef = (RelativeLayout) convertView
					.findViewById(R.id.lnlayout_table);
			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.table_image);
			viewHolder.textView.setText(data.get(position).name);

			viewHolder.ticker = (ImageView) convertView
					.findViewById(R.id.ticker);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Table temp = data.get(position);

		if (temp.isGopBanSelected) {

			viewHolder.ticker.setVisibility(ImageView.VISIBLE);
			viewHolder.layout_chef.setBackgroundColor(Color
					.parseColor("#ff2f4b"));
			viewHolder.image.setImageResource(R.drawable.icon_table);
			viewHolder.textView.setTextColor(Color.parseColor("#FFFFFF"));
		} else {

			viewHolder.ticker.setVisibility(ImageView.INVISIBLE);
			viewHolder.layout_chef.setBackgroundColor(Color
					.parseColor("#e4e4e4"));
			viewHolder.image.setImageResource(R.drawable.icon_table_dadat);
			viewHolder.textView.setTextColor(Color.parseColor("#000000"));
		}

		return convertView;
	}

	public static class ViewHolder {
		TextView textView;
		RelativeLayout layout_chef;
		// FrameLayout background;
		ImageView image;
		ImageView ticker;
	}

}
