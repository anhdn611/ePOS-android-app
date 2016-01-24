package project.maybanhang.fragment;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.maybanhang.ApiDefiner;
import project.maybanhang.CustomHttpClient;
import project.maybanhang.DataSource;
import project.maybanhang.adapters.QuickKeyProductsAdapter;
import project.maybanhang.adapters.PlacesAdapter;
import project.maybanhang.adapters.QuickKeySessionAdapter;
import project.maybanhang.adapters.TableListAdapter;
import project.maybanhang.entity.Bill;
import project.maybanhang.entity.Category;
import project.maybanhang.entity.Customer;
import project.maybanhang.entity.GroupCustomer;
import project.maybanhang.entity.ItemInfo;
import project.maybanhang.entity.OrderItem;
import project.maybanhang.entity.Place;
import project.maybanhang.entity.QuickKey;
import project.maybanhang.entity.QuickKeyProduct;
import project.maybanhang.entity.QuickKeySession;
import project.maybanhang.entity.Table;
import project.maybanhang.ver1.R;
import project.maybanhang.ver1.RestaurantActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.transition.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantFragment extends Fragment {

	ArrayList<Table> mTables = new ArrayList<Table>();
	ArrayList<ItemInfo> mAllItem = new ArrayList<ItemInfo>();
	ArrayList<QuickKeyProduct> mQKProduct = new ArrayList<QuickKeyProduct>();
	GridView gvTable;
	GridView gvItems;
	Button btn_refresh_table;
	Button btn_refresh_products;
	Button btn_AllPlace;
	Button btn_AllQuickKeySession;
	TextView mTvSelectedPlace;
	TextView mTvSelectedGiaoDien;
	public TableListAdapter tableAdapter;
	QuickKeyProductsAdapter itemAdapter;
	public PlaceAsyncTask mPlaceAsyncTask;
	public TableAsyncTask mTableAsyncTask;
	public ItemsAsyncTask mItemsAsyncTask;
	public GetGiaoDienAsyncTask mGiaoDienAsynTask;
	public GetProductAsyncTask mGetKeyProductAsyncTask;
	public GetQuickKeySessionAsyncTask mGetQuickKeySessionAsyncTask;
	PlacesAdapter placesAdapter;
	QuickKeySessionAdapter mSessionAdapter;
	private boolean mOnAdd;
	RestaurantActivity restaurantActivity;
	private ArrayList<QuickKey> mQuickKeys;
	private ArrayList<QuickKeySession> mKeySessions;
	private Dialog mChonSoLuong;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	public void RefreshPlace() {
		loadPlaces();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_restaurant, container,
				false);
		btn_refresh_products = (Button) view
				.findViewById(R.id.btn_refresh_products);
		btn_refresh_table = (Button) view.findViewById(R.id.btn_refresh_table);
		btn_AllPlace = (Button) view.findViewById(R.id.btn_tatca);
		btn_AllQuickKeySession = (Button) view
				.findViewById(R.id.btn_allQuickKeySession);
		mTvSelectedPlace = (TextView) view.findViewById(R.id.tv_selected_place);
		mTvSelectedGiaoDien = (TextView) view
				.findViewById(R.id.tv_selected_giaodien);

		restaurantActivity = (RestaurantActivity) getActivity();
		// Làm mới table
		btn_refresh_products.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.d("POS", "products refresh button clicked");
				// loadProductItems();
			}
		});
		// Làm mới Products
		btn_refresh_table.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.d("POS", "table refresh btn_clicked");
				// loadPlaces();

			}
		});

		// Chọn All
		btn_AllPlace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				placesAdapter = new PlacesAdapter(getActivity(),
						DataSource.sPlaces);
				gvTable.setAdapter(placesAdapter);
				mTvSelectedPlace.setVisibility(TextView.INVISIBLE);
				if (DataSource.sSelectedTable != null)
					DataSource.sSelectedTable.isCurrentActive = false;
				DataSource.sSelectedTable = null;
				DataSource.currentBill = null;
				restaurantActivity.orderItems.clear();
			}
		});

		btn_AllQuickKeySession.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.d("POS", "Sản phẩm btn clicked");
				gvItems.setAdapter(mSessionAdapter);
				mTvSelectedGiaoDien.setVisibility(TextView.INVISIBLE);
			}
		});
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		try {

			gvTable = (GridView) getView().findViewById(
					R.id.gridview_restaurant_table);
			gvItems = (GridView) getView().findViewById(
					R.id.gridview_restaurant_items);
			placesAdapter = new PlacesAdapter(getActivity(), DataSource.sPlaces);
			mKeySessions = new ArrayList<QuickKeySession>();
			mSessionAdapter = new QuickKeySessionAdapter(restaurantActivity,
					R.id.gridview_restaurant_items, mKeySessions);
			Bundle bundle = getArguments();

			if (DataSource.sPlaces.size() == 0) {
				loadPlaces();
			}

			gvItems.setAdapter(mSessionAdapter);
			gvTable.setAdapter(placesAdapter);

			// Choose table
			gvTable.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					try {
						// Check the current item is Place or table
						if (view.getTag().getClass()
								.equals(PlacesAdapter.ViewHolder.class)) {
							// isPlace
							tableAdapter = new TableListAdapter(getActivity(),
									R.layout.table_item_layout,
									DataSource.sPlaces.get(position).Tables);
							gvTable.setAdapter(tableAdapter);
							mTvSelectedPlace.setText(DataSource.sPlaces
									.get(position).Name);
							mTvSelectedPlace.setVisibility(TextView.VISIBLE);
						} else {
							// Is Table
							if (DataSource.sSelectedTable != null) {
								DataSource.sSelectedTable.isCurrentActive = false;
							}
							Table table = tableAdapter.getItem(position);

							table.isCurrentActive = true;
							DataSource.sSelectedTable = table;
							tableAdapter.notifyDataSetChanged();
							if (table.status == 0) {
								restaurantActivity.orderItems.clear();
								restaurantActivity.UpdateOrder();
								return;
							}
							DataSource.currentBill = null;
							Log.d("POS", "Chọn bàn thứ " + position);
							LoadOrderInfoAsynTask loadOrderInfoAsynTask = new LoadOrderInfoAsynTask();
							loadOrderInfoAsynTask.execute(mTables.get(position));
						}

					} catch (Exception exception) {
						Log.e("POS", "gvTable.setOnItemClickListener: "
								+ exception.getMessage());
					}
				}

			});
			// Choose Item
			gvItems.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					if (view.getTag().getClass()
							.equals(QuickKeySessionAdapter.ViewHolder.class)) {
						GetProductAsyncTask productAsyncTask = new GetProductAsyncTask();
						productAsyncTask.execute(mKeySessions.get(position)
								.getId());
						mTvSelectedGiaoDien.setText(mKeySessions.get(position)
								.getName());
						mTvSelectedGiaoDien.setVisibility(TextView.VISIBLE);
					} else if (DataSource.sSelectedTable != null) {
						if (!(DataSource.sSelectedTable.status == 0)) {
							if (mOnAdd)
								return;
							mOnAdd = true;
							AddToOrder(mQKProduct.get(position));
						} else {
							Toast.makeText(getActivity(),
									"bàn chưa được đặt hoặc xếp",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getActivity(), "chưa ch�?n bàn",
								Toast.LENGTH_SHORT).show();
					}
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadPlaces() {
		DataSource.sPlaces.clear();
		mTables.clear();
		mPlaceAsyncTask = new PlaceAsyncTask();
		mPlaceAsyncTask.execute(getArguments()
				.getString(ApiDefiner.TAG_SHOP_ID),
				getArguments().getString(ApiDefiner.TAG_BRANCH_ID));
	}

	@Override
	public void onStart() {
		super.onStart();
		if (DataSource.sCategories.size() == 0) {
			loadProductItems();
		}
	}

	private void loadProductItems() {
		this.mAllItem.clear();
		DataSource.sCategories.clear();
		// mItemsAsyncTask = new ItemsAsyncTask();
		// mItemsAsyncTask.execute(getArguments()
		// .getString(ApiDefiner.TAG_SHOP_ID),
		// getArguments().getString(ApiDefiner.TAG_BRANCH_ID));
		mGiaoDienAsynTask = new GetGiaoDienAsyncTask();
		mGiaoDienAsynTask.execute(
				getArguments().getString(ApiDefiner.TAG_SHOP_ID),
				getArguments().getString(ApiDefiner.TAG_BRANCH_ID));
	}

	TextView tvProductName;
	TextView tvPrice;
	TextView tvQuantityInStore;
	TextView tvNumberProduct;
	ImageButton btnBonus;
	ImageButton btnMinus;
	Button btnDongy;
	Button btnCancel;
	int productCount = 1;

	private void AddToOrder(final QuickKeyProduct item) {
		productCount = 1;
		mChonSoLuong = new Dialog(restaurantActivity);
		mChonSoLuong.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mChonSoLuong.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		mChonSoLuong
				.setContentView(R.layout.dialog_selection_number_product_layout);
		mChonSoLuong.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				mOnAdd = false;
			}
		});
		tvProductName = (TextView) mChonSoLuong
				.findViewById(R.id.tv_name_product);
		tvPrice = (TextView) mChonSoLuong.findViewById(R.id.tv_product_price);
		tvNumberProduct = (TextView) mChonSoLuong.findViewById(R.id.tv_quantiy);
		tvProductName.setText(item.getTitle());
		tvPrice.setText("" + item.getRetailPrice() * productCount);
		tvNumberProduct.setText("" + productCount);
		btnDongy = (Button) mChonSoLuong.findViewById(R.id.btn_dongy);
		btnCancel = (Button) mChonSoLuong.findViewById(R.id.btn_huy);
		btnBonus = (ImageButton) mChonSoLuong.findViewById(R.id.btn_bonus);
		btnMinus = (ImageButton) mChonSoLuong.findViewById(R.id.btn_minus);
		btnBonus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				productCount++;
				tvNumberProduct.setText("" + productCount);
				tvPrice.setText("" + item.getRetailPrice() * productCount);
			}
		});

		btnMinus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (productCount > 1) {
					productCount--;
					tvNumberProduct.setText("" + productCount);
					tvPrice.setText("" + item.getRetailPrice() * productCount);
				}
			}
		});
		btnDongy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				restaurantActivity.orderItems.add(new OrderItem(item
						.getProductId(), productCount, item.getRetailPrice(),
						item.getTitle()));
				restaurantActivity.UpdateOrder();
				mChonSoLuong.dismiss();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mChonSoLuong.dismiss();
			}
		});
		mChonSoLuong.show();

	}

	private class PlaceAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String jsonResult = null;
			try {
				String path = ApiDefiner.URL_KHUVUC_BAN + "?shop_id="
						+ params[0] + "&branch_id=" + params[1];
				Log.d("POS", "Lấy các Khu vực bàn: " + path);
				jsonResult = CustomHttpClient.executeHttpGet(path);

				JSONObject root = new JSONObject(jsonResult);

				JSONArray jsArr = root.getJSONArray(ApiDefiner.TAG_All_PLACES);
				for (int i = 0; i < jsArr.length(); i++) {
					JSONObject temp = jsArr.getJSONObject(i).getJSONObject(
							ApiDefiner.TAG_PLACE);
					Place tempPlace = new Place();
					tempPlace.Name = temp.getString(ApiDefiner.TAG_NAME);
					tempPlace.Place_id = temp.getInt(ApiDefiner.TAG_PLACE_ID);
					Log.d("POS", tempPlace.toString());
					DataSource.sPlaces.add(tempPlace);
				}
				return jsonResult;
			} catch (Exception e) {
				Log.e("POS", e.getMessage());
				return ApiDefiner.MESS_CONNECTION_ERROR;
			}
		}

		protected void onPostExecute(String result) {

			mTableAsyncTask = new TableAsyncTask();
			mTableAsyncTask.execute("");
			placesAdapter.notifyDataSetChanged();
		}

	}

	private class TableAsyncTask extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			String jsonResult = null;
			try {
				for (Place place : DataSource.sPlaces) {
					if (place.Place_id == -1)
						continue;

					String path = ApiDefiner.URL_GET_TABLES_BY_ID + "?"
							+ ApiDefiner.TAG_PLACE_ID + "=" + place.Place_id;
					Log.d("POS", "Lấy các bàn: " + path + " trong" + place.Name);
					jsonResult = CustomHttpClient.executeHttpGet(path);
					Log.d("POS", "Result: " + jsonResult + " trong" + params[0]);
					if (jsonResult.trim().equals(ApiDefiner.MESS_NO_TABLE)) {

						continue;
					}
					JSONObject root = new JSONObject(jsonResult);

					JSONArray jsArr = root
							.getJSONArray(ApiDefiner.TAG_ALL_TABLE);
					Log.d("POS", "Total table: " + jsArr.length());
					for (int i = 0; i < jsArr.length(); i++) {
						JSONObject temp = jsArr.getJSONObject(i).getJSONObject(
								ApiDefiner.TAG_TABLE);
						Table tempTable = new Table();
						tempTable.name = temp.getString(ApiDefiner.TAG_NAME);
						tempTable.table_id = temp
								.getInt(ApiDefiner.TAG_TABLE_ID);
						tempTable.status = temp.getInt(ApiDefiner.TAG_STATUS);
						tempTable.order_id = temp
								.getInt(ApiDefiner.TAG_ORDER_ID);
						Log.d("POS", tempTable.toString());
						mTables.add(tempTable);
						place.Tables.add(tempTable);
						// DataSource.sPlaces.get(0).Tables.add(tempTable);
					}
				}
			} catch (Exception e) {
				Log.e("POS", e.getMessage());
				return ApiDefiner.MESS_CONNECTION_ERROR;
			}
			return jsonResult;
		}

		protected void onPostExecute(String result) {
			// if (result == null) {
			//
			// } else {
			// if (mItemsAsyncTask.getStatus() == AsyncTask.Status.FINISHED) {
			// mProgressBar.dismiss();
			// }
			// // tableAdapter.notifyDataSetChanged();
			// }
			if (tableAdapter != null) {
				tableAdapter.notifyDataSetChanged();
			}
		}
	}

	private class ItemsAsyncTask extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String jsonResult = null;
			try {
				String path = ApiDefiner.URL_SHOP_ITEMS + "?"
						+ ApiDefiner.TAG_SHOP_ID + "=" + params[0]
						+ "&branch_id=" + params[1];

				Log.d("Restaurant. Async Task", "Sản phẩm " + path + " trong"
						+ params[0]);

				jsonResult = CustomHttpClient.executeHttpGet(path);
				if (jsonResult.trim().contains("Not find product"))
					return jsonResult;

				path = ApiDefiner.URL_CATEGORIES + "?" + ApiDefiner.TAG_SHOP_ID
						+ "=" + params[0];
				Log.d("POS", "Sản phẩm " + path);
				jsonResult = CustomHttpClient.executeHttpGet(path);
				Log.d("POS", "Sản phẩm: " + jsonResult);
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
					item.Shop_id = temp.getString(ApiDefiner.TAG_SHOP_ID);
					DataSource.sCategories.add(item);
					Log.d("anhdn", "Category" + item.Name);
				}

				for (Category cate : DataSource.sCategories) {
					path = ApiDefiner.URL_PRODUCT_BY_CATEGORIES + "?"
							+ ApiDefiner.TAG_SHOP_ID + "=" + cate.Shop_id + "&"
							+ ApiDefiner.TAG_CATEGROY_ID + "="
							+ cate.Product_category_id;
					Log.d("Shop Async Task", "Get item by category "
							+ cate.Product_category_id + ": " + path);
					String result = CustomHttpClient.executeHttpGet(path);

					root = new JSONObject(result);
					JSONArray jsProductArr = root.getJSONArray("All products");

					for (int i = 0; i < jsProductArr.length(); i++) {
						JSONObject tempProduct = jsProductArr.getJSONObject(i)
								.getJSONObject("product");
						ItemInfo item = new ItemInfo();
						item.name = tempProduct.getString("title");
						item.retail_price = Long.parseLong(tempProduct
								.getString("retail_price"));
						item.product_category_id = tempProduct
								.getString("product_category_id");
						item.product_id = tempProduct
								.getString(ApiDefiner.TAG_PRODUCT_ID);
						cate.Items.add(item);
						Log.d("Shop Async Task", "Add " + item.name + " to "
								+ cate.Name);
					}
					Log.d("Shop. Async Task",
							cate.Name + ": " + cate.Items.size() + " items");
				}
				DataSource.Refresh();
				Log.d("POS", "Total items: " + DataSource.getAllItem().size());
				return jsonResult;
			} catch (IOException e) {
				Log.e("POS", e.getMessage());
				return ApiDefiner.MESS_CONNECTION_ERROR;
			} catch (Exception e) {
				Log.e("POS", e.getMessage());
			}
			return "";
		}

		protected void onPostExecute(String result) {
			if (result.trim().contains("Not find product")) {
				Toast.makeText(RestaurantFragment.this.getActivity(),
						"Hiện tại chưa có sản phẩm nào.", Toast.LENGTH_LONG)
						.show();
			} else {

				itemAdapter.notifyDataSetChanged();
			}

		}
	}

	private class LoadOrderInfoAsynTask extends
			AsyncTask<Table, String, Integer> {

		@Override
		protected Integer doInBackground(Table... params) {
			Table table = params[0];
			NameValuePair order_id = new BasicNameValuePair(
					ApiDefiner.TAG_ORDER_ID, table.order_id + "");
			ArrayList<NameValuePair> paramesters = new ArrayList<NameValuePair>();
			paramesters.add(order_id);
			try {
				Log.d("Restaurat Fragment.LoadOrderAsynTask", "Load info of"
						+ table.name);
				String result = CustomHttpClient.executeHttpPost(
						ApiDefiner.URL_LOAD_ORDER, paramesters);
				JSONObject jsResult = new JSONObject(result);
				// Check result is error or not
				try {
					if (jsResult.getJSONObject(Bill.BILL_INFO)
							.getString(ApiDefiner.TAG_RESULT)
							.equals(ApiDefiner.NONE))
						restaurantActivity.orderItems.clear();
					DataSource.sSelectedCustomer = new Customer();
					return ApiDefiner.OK;
				} catch (JSONException jex) {

				}
				// Add order item to orders list
				Log.d("POS",
						"Restaurant Fragment.LoadOrderAsynTask: Load order result"
								+ result);
				Bill bill = new Bill(result);
				DataSource.currentBill = bill;
				restaurantActivity.orderItems.clear();
				restaurantActivity.orderItems.addAll(bill.getOrders());
				for (GroupCustomer groupcustomer : DataSource.sGroupsCustomer) {
					for (Customer customer : groupcustomer.Customers) {
						if (customer.Customer_id.equals(bill.getCustomer_id())) {
							DataSource.sSelectedCustomer = customer;
							break;
						}
					}
				}
				// restaurantActivity.isBookedTable = true;
				return ApiDefiner.OK;
			} catch (Exception e) {
				e.printStackTrace();
				return ApiDefiner.FAIL;
			}

		}

		@Override
		protected void onPostExecute(Integer result) {
			switch (result) {
			case ApiDefiner.OK:
				restaurantActivity.UpdateCustomerInfo();
				restaurantActivity.UpdateOrder();
				break;
			default:
				break;
			}

			super.onPostExecute(result);
		}
	}

	private class GetGiaoDienAsyncTask extends
			AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO
			ArrayList<NameValuePair> paramenters = new ArrayList<NameValuePair>();
			paramenters.add(new BasicNameValuePair(ApiDefiner.TAG_SHOP_ID,
					params[0]));
			paramenters.add(new BasicNameValuePair(ApiDefiner.TAG_BRANCH_ID,
					params[1]));
			try {
				String result = CustomHttpClient.executeHttpPost(
						ApiDefiner.URL_GETLIST_GIAODIEN, paramenters);
				mQuickKeys = new ArrayList<QuickKey>();
				JSONObject jsRoot = new JSONObject(result);
				JSONArray quickkeys = jsRoot
						.getJSONArray(ApiDefiner.QUICK_KEY_LIST);
				for (int i = 0; i < quickkeys.length(); i++) {
					JSONObject jquickkey = quickkeys.getJSONObject(i)
							.getJSONObject(ApiDefiner.QUICK_KEY);
					QuickKey quickey = new QuickKey(
							jquickkey.getInt(ApiDefiner.TAG_QUICKKEY_ID),
							jquickkey.getString(ApiDefiner.TAG_NAME),
							jquickkey.getInt(ApiDefiner.TAG_SHOP_ID),
							jquickkey.getInt(ApiDefiner.TAG_BRANCH_ID),
							jquickkey.getInt(ApiDefiner.TAG_ACTIVE));
					Log.d("POS", "Quick key: " + quickey.getName());
					mQuickKeys.add(quickey);
					if (quickey.getActive() == 1) {
						GetQuickKeySessionAsyncTask sessionAsyncTask = new GetQuickKeySessionAsyncTask();
						sessionAsyncTask.execute("" + quickey.getQuickKeyId());
					}
				}

			} catch (Exception e) {
				Log.e("POS", "GetGiaoDien: " + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);
		}
	}

	private class GetQuickKeySessionAsyncTask extends
			AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO get list of session by giao dien _Id

			try {
				String result = CustomHttpClient
						.executeHttpGet(ApiDefiner.URL_GET_QUICKKEY_SESSION
								+ "?" + ApiDefiner.TAG_QUICKKEY_ID + "="
								+ params[0]);
				mKeySessions = new ArrayList<QuickKeySession>();
				JSONArray quickkeys = new JSONObject(result)
						.getJSONArray(ApiDefiner.TAG_ALL_QUICKKEY_SESSION);
				for (int i = 0; i < quickkeys.length(); i++) {
					JSONObject jSession = quickkeys.getJSONObject(i)
							.getJSONObject(ApiDefiner.TAG_QUICKKEY_SESSION);
					QuickKeySession session = new QuickKeySession(
							jSession.getInt(ApiDefiner.TAG_QUICKKEY_SESSION_ID),
							jSession.getString(ApiDefiner.TAG_NAME), jSession
									.getString("sorting"), jSession
									.getString("created_date"));
					mKeySessions.add(session);
					Log.d("POS", "Quick key session: " + session.getName());
				}

			} catch (Exception e) {
				Log.e("POS", "Quick key session:" + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			mSessionAdapter = new QuickKeySessionAdapter(restaurantActivity,
					R.id.gridview_restaurant_items, mKeySessions);
			gvItems.setAdapter(mSessionAdapter);
			mSessionAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}
	}

	private class GetProductAsyncTask extends
			AsyncTask<Integer, String, String> {

		@Override
		protected String doInBackground(Integer... arg0) {
			try {
				String result = CustomHttpClient
						.executeHttpGet(ApiDefiner.URL_GET_PRODUCT_QUICKKEY_SESSION
								+ "?"
								+ ApiDefiner.TAG_QUICKKEY_SESSION_ID
								+ "=" + arg0[0]);
				Log.d("POS", "GetProductByQuickKey: " + result);
				mQKProduct.clear();
				JSONArray quickkeys = new JSONObject(result)
						.getJSONArray(ApiDefiner.TAG_ALL_PRODUCT);
				for (int i = 0; i < quickkeys.length(); i++) {
					JSONObject product = quickkeys.getJSONObject(i)
							.getJSONObject(ApiDefiner.TAG_PRODUCT);
					mQKProduct
							.add(new QuickKeyProduct(
									product.getInt(QuickKeyProduct.TAG_QK_PRODUCT_ID),
									product.getInt(QuickKeyProduct.TAG_QK_SESSION_ID),
									product.getInt(QuickKeyProduct.TAG_PRODUCT_ID),
									product.getString(QuickKeyProduct.TAG_ALIAS),
									product.getString(QuickKeyProduct.TAG_TITLE),
									product.getString(QuickKeyProduct.TAG_PRODUCT_CATEGORY_ID),
									product.getLong(QuickKeyProduct.TAG_RETAIL_PRICE),
									product.getString(QuickKeyProduct.TAG_TAX_ID),
									product.getInt(QuickKeyProduct.TAG_NEW)));
				}

			} catch (Exception e) {
				Log.e("POS", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			QuickKeyProductsAdapter productsAdapter = new QuickKeyProductsAdapter(
					restaurantActivity, R.id.gridview_restaurant_items,
					mQKProduct);
			gvItems.setAdapter(productsAdapter);
			super.onPostExecute(result);
		}
	}
}
