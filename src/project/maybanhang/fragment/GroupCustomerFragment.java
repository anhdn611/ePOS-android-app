package project.maybanhang.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import project.maybanhang.ApiDefiner;
import project.maybanhang.CustomHttpClient;
import project.maybanhang.DataSource;
import project.maybanhang.adapters.GroupCustomerAdapter;
import project.maybanhang.adapters.PlacesAdapter;
import project.maybanhang.entity.Customer;
import project.maybanhang.entity.GroupCustomer;
import project.maybanhang.entity.Place;
import project.maybanhang.ver1.R;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class GroupCustomerFragment extends Fragment {
	GridView gridViewCustomer;
	String shopID;
	public boolean dataGetAll;
	private ProgressDialog progressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_groupcustomer,
				container, false);
		dataGetAll = false;
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		GroupCustomerAdapter groupCustomerAdapter = new GroupCustomerAdapter(
				getActivity(), DataSource.sGroupsCustomer);
		gridViewCustomer.setAdapter(groupCustomerAdapter);
		if (DataSource.sGroupsCustomer.size() == 0) {
			Bundle bundle = getArguments();
			shopID = bundle.getString(ApiDefiner.TAG_SHOP_ID);
			CustomerAsyncTask customerAsyncTask = new CustomerAsyncTask();
			customerAsyncTask.execute(bundle.getString(ApiDefiner.TAG_SHOP_ID),
					bundle.getString(ApiDefiner.TAG_BRANCH_ID));
			progressBar = new ProgressDialog(getActivity());
			progressBar.setMessage("�?ang lấy dữ liệu");
			progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressBar.show();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		gridViewCustomer = (GridView) getView().findViewById(
				R.id.gridview_group_customer);
		gridViewCustomer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FragmentTransaction fragmentTransaction = getActivity()
						.getFragmentManager().beginTransaction();
				Bundle bundle = new Bundle();
				bundle.putString(ApiDefiner.TAG_GROUP_CUSTOMER_ID,
						DataSource.sGroupsCustomer.get(position).Id);

				CustomerFragment customerFragment = new CustomerFragment();
				customerFragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.container, customerFragment);
				fragmentTransaction.commit();
			}
		});
	}

	private class CustomerAsyncTask extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			String jsonResult = null;
			try {
				String path = ApiDefiner.URL_THONG_TIN_KH + "?shop_id="
						+ params[0];
				Log.d("Customer. Async Task", "Lấy danh sách khách hàng: "
						+ path);
				jsonResult = CustomHttpClient.executeHttpGet(path);
				JSONObject root = new JSONObject(jsonResult);
				JSONArray groups = root.getJSONArray("group list");

				for (int i = 0; i < groups.length(); i++) {
					JSONObject temp = groups.getJSONObject(i).getJSONObject(
							"group_customer");
					GroupCustomer group = new GroupCustomer();
					group.setGroup_name(temp
							.getString(ApiDefiner.TAG_NAME));
					group.Id = temp.getString(ApiDefiner.TAG_GROUP_CUSTOMER_ID);
					DataSource.sGroupsCustomer.add(group);
				}

				JSONArray jsArr = root
						.getJSONArray(ApiDefiner.TAG_CUSTOMER_LIST);

				for (int i = 0; i < jsArr.length(); i++) {
					JSONObject temp = jsArr.getJSONObject(i).getJSONObject(
							ApiDefiner.TAG_CUSTOMER);
					Customer customer = new Customer();
					customer.Customer_id = temp
							.getString(ApiDefiner.TAG_CUSTOMER_ID);
					customer.Phone = temp
							.getString(ApiDefiner.TAG_CUSTOMER_PHONE);
					customer.setLast_name(temp
							.getString(ApiDefiner.TAG_CUSTOMER_LASTNAME).replace("null",""));
					customer.setFirst_name(temp
							.getString(ApiDefiner.TAG_CUSTOMER_FIRSTNAME).replace("null",""));
					customer.Group_customer_id = temp
							.getString(ApiDefiner.TAG_GROUP_CUSTOMER_ID);
					customer.Groupname = temp
							.getString(ApiDefiner.TAG_GROUP_CUSTOMER_NAME);
					if (customer.Groupname.equals("null"))
						customer.Groupname = "";

					for (GroupCustomer item : DataSource.sGroupsCustomer) {
						if (item.getGroup_name().equals(customer.Groupname)) {
							item.Customers.add(customer);
						}
					}
				}
				
				Log.d("Customer. Async Task", "number of group: "
						+ DataSource.sGroupsCustomer.size());
				return jsonResult;
			} catch (Exception e) {
				Log.e("Customer. Async Task", e.getMessage());
				return ApiDefiner.MESS_CONNECTION_ERROR;
			}
		}

		protected void onPostExecute(String result) {
			GroupCustomerAdapter groupCustomerAdapter = new GroupCustomerAdapter(
					getActivity(), DataSource.sGroupsCustomer);
			gridViewCustomer.setAdapter(groupCustomerAdapter);
			progressBar.dismiss();
		}

		protected void onProgressUpdate(String... progress) {

		}
	}
}
