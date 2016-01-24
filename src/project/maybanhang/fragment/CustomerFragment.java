package project.maybanhang.fragment;

import java.util.ArrayList;

import project.maybanhang.ApiDefiner;
import project.maybanhang.DataSource;
import project.maybanhang.adapters.CustomerAdapter;
import project.maybanhang.entity.Customer;
import project.maybanhang.entity.GroupCustomer;
import project.maybanhang.ver1.R;
import project.maybanhang.ver1.RestaurantActivity;
import project.maybanhang.ver1.ShopActivity;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class CustomerFragment extends Fragment {
	ArrayList<Customer> customers = new ArrayList<Customer>();
	GridView gridViewCustomer;
	String shopID;
	Button btn_all;
	TextView tvGroupCustomerName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_customer, container,
				false);
		gridViewCustomer = (GridView) view.findViewById(R.id.gridview_customer);
		btn_all = (Button) view.findViewById(R.id.btn_all);
		tvGroupCustomerName = (TextView) view
				.findViewById(R.id.current_group_customer_name);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		Bundle bundle = getArguments();
		for (GroupCustomer item : DataSource.sGroupsCustomer) {
			if (item.Id.equals(bundle
					.getString(ApiDefiner.TAG_GROUP_CUSTOMER_ID))) {
				tvGroupCustomerName.setText(item.getGroup_name());
				customers = item.Customers;
				CustomerAdapter customerAdapter = new CustomerAdapter(
						getActivity(), customers);
				Log.d(this.toString(),
						"Number of customer" + item.Customers.size());
				gridViewCustomer.setAdapter(customerAdapter);
				return;
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		gridViewCustomer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				DataSource.sSelectedCustomer = customers.get(position);
				if (getActivity() instanceof RestaurantActivity) {
					RestaurantActivity parent = (RestaurantActivity) getActivity();
					parent.UpdateCustomerInfo();
				} else {
					ShopActivity parent = (ShopActivity) getActivity();
					parent.UpdateCustomerInfo();
				}
			}
		});
		btn_all.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (getActivity() instanceof RestaurantActivity) {
					RestaurantActivity parent = (RestaurantActivity) getActivity();
					parent.fragmentTransaction = parent.fragmentManager
							.beginTransaction();
					parent.fragmentTransaction.replace(R.id.container,
							parent.groupCustomerFragment);
					parent.fragmentTransaction.commit();
				} else {
					ShopActivity parent = (ShopActivity) getActivity();
					parent.fragmentTransaction = parent.fragmentManager
							.beginTransaction();
					parent.fragmentTransaction.replace(R.id.container,
							parent.groupCustomerFragment);
					parent.fragmentTransaction.commit();
				}
			}
		});
	}

}
