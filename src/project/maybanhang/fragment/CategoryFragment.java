package project.maybanhang.fragment;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import project.maybanhang.ApiDefiner;
import project.maybanhang.CustomHttpClient;
import project.maybanhang.DataSource;
import project.maybanhang.adapters.CategoryAdpater;
import project.maybanhang.entity.Category;
import project.maybanhang.entity.ItemInfo;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CategoryFragment extends Fragment {
	GridView gridViewCategory;
	String shopID;
	public boolean dataGetAll;
	CategoryAdpater categoryAdapter;
	ProgressDialog progressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category, container,
				false);
		dataGetAll = false;
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		categoryAdapter = new CategoryAdpater(getActivity(),
				DataSource.sCategories);
		gridViewCategory.setAdapter(categoryAdapter);
		if (DataSource.sCategories.size() == 0) {
			Bundle bundle = getArguments();
			shopID = bundle.getString(ApiDefiner.TAG_SHOP_ID);
			ProductsAsyncTask productAsyncTask = new ProductsAsyncTask();
			productAsyncTask.execute(bundle.getString(ApiDefiner.TAG_SHOP_ID),
					bundle.getString(ApiDefiner.TAG_SHOP_ID));
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
		gridViewCategory = (GridView) getView().findViewById(
				R.id.gridview_categories);
		gridViewCategory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FragmentTransaction fragmentTransaction = getActivity()
						.getFragmentManager().beginTransaction();
				Bundle bundle = new Bundle();
				bundle.putString(ApiDefiner.TAG_CATEGROY_ID,
						DataSource.sCategories.get(position).Product_category_id);
				ProductFragment productFragment = new ProductFragment();
				productFragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.container, productFragment);
				fragmentTransaction.commit();
			}
		});
	}

	private class ProductsAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String jsonResult = null;
			try {

				// get Categories
				String path = ApiDefiner.URL_SHOP_ITEMS + "?"
						+ ApiDefiner.TAG_SHOP_ID + "=" + params[0] + "&"
						+ ApiDefiner.TAG_BRANCH_ID + "=" + params[1];
				Log.d("SHOP. Async Task", "PATH: " + path);
				jsonResult = CustomHttpClient.executeHttpGet(path);
				if (!jsonResult.trim().equals("Link Error.")) {
					Log.d("SHOP. Async Task", "Result: " + jsonResult
							+ " trong" + params[0]);
					JSONObject root = new JSONObject(jsonResult);
					// CATEGORIES
					JSONArray jsArr = root.getJSONArray("category list");
					for (int i = 0; i < jsArr.length(); i++) {
						JSONObject temp = jsArr.getJSONObject(i).getJSONObject(
								"category");
						Category item = new Category();
						item.Name = temp.getString("name");
						item.Product_category_id = temp
								.getString("product_category_id");
						DataSource.sCategories.add(item);
						Log.d("SHOP Async Task", "Add Category:  " + item.Name);
					}
					// PRODUCT
					JSONArray jsProductArr = root.getJSONArray("product list");

					for (int i = 0; i < jsProductArr.length(); i++) {
						JSONObject tempProduct = jsProductArr.getJSONObject(i).getJSONObject("product");
						ItemInfo item = new ItemInfo();
						item.name = tempProduct.getString("title");
						item.retail_price = Long.parseLong(tempProduct
								.getString("retail_price"));
						item.product_category_id = tempProduct
								.getString("product_category_id");
						for (Category category : DataSource.sCategories) {
							if (item.product_category_id
									.equals(category.Product_category_id)) {
								category.Items.add(item);
								Log.d("SHOP Async Task", "Add " + item.name
										+ " to " + category.Name);
							}
						}
						;
					}
				} else {
					path = ApiDefiner.URL_CATEGORIES + "?"
							+ ApiDefiner.TAG_SHOP_ID + "=" + params[0];
					Log.d("SHOP. Async Task", "PATH CATEGORY: " + path);
					jsonResult = CustomHttpClient.executeHttpGet(path);
					JSONObject root = new JSONObject(jsonResult);
					// CATEGORIES
					JSONArray jsArr = root.getJSONArray("All categorys");
					for (int i = 0; i < jsArr.length(); i++) {
						JSONObject temp = jsArr.getJSONObject(i).getJSONObject(
								"category");
						Category item = new Category();
						item.Name = temp.getString("name");
						item.Product_category_id = temp
								.getString("product_category_id");
						DataSource.sCategories.add(item);
						Log.d("SHOP Async Task", "Category" + item.Name);
					}

					for (Category cate : DataSource.sCategories) {
						path = ApiDefiner.URL_PRODUCT_BY_CATEGORIES + "?"
								+ ApiDefiner.TAG_CATEGROY_ID + "="
								+ cate.Product_category_id;
						Log.d("SHOP. Async Task", "PATH: " + path);
						String result = CustomHttpClient.executeHttpGet(path);

						root = new JSONObject(result);
						JSONArray jsProductArr = root
								.getJSONArray("All products");

						for (int i = 0; i < jsProductArr.length(); i++) {
							JSONObject tempProduct = jsProductArr
									.getJSONObject(i).getJSONObject("product");
							ItemInfo item = new ItemInfo();
							item.name = tempProduct.getString("title");
							item.retail_price = Long.parseLong(tempProduct
									.getString("retail_price"));
							item.product_category_id = tempProduct
									.getString("product_category_id");
							cate.Items.add(item);
							Log.d("SHOP Async Task", "Add " + item.name
									+ " to " + cate.Name);
						}
						Log.d("SHOP Async Task",
								cate.Name + ": " + cate.Items.size() + " items");
					}
				}

				Log.d("SHOP Async Task", "Total items: "
						+ DataSource.sCategories.size());
				return jsonResult;
			} catch (IOException e) {
				Log.e("SHOP Async Task", e.getMessage());
				return ApiDefiner.MESS_CONNECTION_ERROR;
			} catch (Exception e) {
				return jsonResult;
			}
		}

		protected void onPostExecute(String result) {
			CategoryFragment.this.categoryAdapter.notifyDataSetChanged();
			if (result.equals(ApiDefiner.MESS_CONNECTION_ERROR)) {
				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
			}
			progressBar.dismiss();
		}

		protected void onProgressUpdate(String... progress) {

		}
	}
}
