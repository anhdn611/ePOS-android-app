package project.maybanhang.ver1;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import project.maybanhang.ApiDefiner;
import project.maybanhang.CustomHttpClient;
import project.maybanhang.DataSource;
import project.maybanhang.adapters.GopBanAdapter;
import project.maybanhang.adapters.PlacesAdapter;
import project.maybanhang.adapters.QuickKeyProductsAdapter;
import project.maybanhang.adapters.OrderListAdapter;
import project.maybanhang.adapters.TableListAdapter;
import project.maybanhang.entity.Bill;
import project.maybanhang.entity.Customer;
import project.maybanhang.entity.OrderItem;
import project.maybanhang.entity.Place;
import project.maybanhang.entity.Table;
import project.maybanhang.entity.User;
import project.maybanhang.fragment.GroupCustomerFragment;
import project.maybanhang.fragment.OrderFragment;
import project.maybanhang.fragment.RestaurantFragment;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantActivity extends Activity {

	ListView list;
	public OrderListAdapter orderListAdapter;
	private double tien_phaiTra;
	private User user;
	// public boolean isBookedTable;
	QuickKeyProductsAdapter quickKeyProductsAdapter;
	Button btnThanhToan;
	Button btnChonKhachHang;
	Button btnChon_sanpham;
	Button btnluuTam;
	Button btnDatban;
	Button btnXepBan;
	Button btnHoaDon;
	Button btn_ketphien;
	Button btn_gopBan;
	Button btn_chuyenban;
	ImageButton btnLogout;
	TextView tvTongHang;
	TextView tvGiamGia;
	TextView tvVAT;
	TextView tvThanhToan;
	TextView tvTotal;
	TextView tvCustomerName;
	TextView tvCustomerGroup;
	TextView tvCustomerPhone;
	ProgressDialog progress;

	Typeface tf;

	public ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();

	// public Fragment placeFragment;
	public Fragment tableFragment;
	public GroupCustomerFragment groupCustomerFragment;
	public OrderFragment orderFragment;
	public String shopId = "";
	public FragmentManager fragmentManager;
	public FragmentTransaction fragmentTransaction;
	Bundle bundle;
	Context context;
	private Dialog mThanhtoanDialog;
	private Dialog mGopBanDialog;
	int currentPlaceGopBanDialog = 0;// Only use when GopBan Dialog is shown

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant);
		tf = Typeface.createFromAsset(getAssets(), "fonts/DS-DIGI.TTF");
		context = this;
		progress = new ProgressDialog(context);
		progress.setCanceledOnTouchOutside(false);
		btnChonKhachHang = (Button) findViewById(R.id.btn_khachhang);
		btnChon_sanpham = (Button) findViewById(R.id.btn_chonsp);
		btnluuTam = (Button) findViewById(R.id.btn_luutam);
		btnDatban = (Button) findViewById(R.id.btn_datban);
		btnXepBan = (Button) findViewById(R.id.btn_XepBan);
		btnHoaDon = (Button) findViewById(R.id.btn_hoadon2);
		btn_ketphien = (Button) findViewById(R.id.btn_ketphien);
		btn_gopBan = (Button) findViewById(R.id.btn_gopBan);
		btn_chuyenban = (Button) findViewById(R.id.btn_chuyenban);

		tvGiamGia = (TextView) findViewById(R.id.textViewGiam_gia);
		tvThanhToan = (TextView) findViewById(R.id.textViewTHANHTOAN);
		tvTongHang = (TextView) findViewById(R.id.textViewTongHang);
		tvTotal = (TextView) findViewById(R.id.total);
		tvTotal.setTypeface(tf);
		tvVAT = (TextView) findViewById(R.id.textViewVAT);
		tvCustomerName = (TextView) findViewById(R.id.customer_name);
		tvCustomerGroup = (TextView) findViewById(R.id.customer_type);
		tvCustomerPhone = (TextView) findViewById(R.id.phone_number);
		btnLogout = (ImageButton) findViewById(R.id.Logout);
		bundle = getIntent().getExtras();
		user = new User(bundle);
		shopId = bundle.getString(ApiDefiner.TAG_SHOP_ID);
		fragmentManager = getFragmentManager();
		tableFragment = new RestaurantFragment();
		groupCustomerFragment = new GroupCustomerFragment();

		chonSanpham();

		/*
		 * Handle event user Tab on "Dat Ban" button Cập nhật trạng thái của bàn
		 * thành 2
		 */
		btnDatban.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// check user choose a table or not
				if (DataSource.sSelectedTable == null) {
					Toast.makeText(context, "chưa chọn bàn", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (DataSource.sSelectedTable.status == 0) {
					// Send request to server
					UpdateStatusTable updateStatusTable = new UpdateStatusTable();
					updateStatusTable.execute(
							DataSource.sSelectedTable.table_id, 2);
				} else if (DataSource.sSelectedTable.status == 2) {
					Toast.makeText(context, "Bàn đã được đặt",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (DataSource.sSelectedTable.status == 1) {
					Toast.makeText(context, "Bàn đang xếp", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		mGopBanDialog = new Dialog(RestaurantActivity.this);
		mGopBanDialog.setCanceledOnTouchOutside(false);
		mGopBanDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mGopBanDialog.setContentView(R.layout.dialog_gopban);
		mGopBanDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));

		/*
		 * Chuyen ban
		 */
		btn_chuyenban.setOnClickListener(new OnClickListener() {

			GridView gvTables;
			GridView gvPlaces;
			Button btnGopBan;
			Button btnHuy;
			Table selectedTable = new Table();
			Place currentPlace = new Place();

			@Override
			public void onClick(View v) {
				if (DataSource.sSelectedTable == null) {
					Toast.makeText(context, "Chọn bàn muốn chuyển trước",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (DataSource.sSelectedTable.status == 0) {
					Toast.makeText(context, "Chọn bàn đã đặt hoặc đang xếp",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (DataSource.sSelectedTable.order_id == 0) {
					Toast.makeText(context, "Lưu tạm hóa đơn trước khi chuyển",
							Toast.LENGTH_SHORT).show();
					return;

				}
				gvTables = (GridView) mGopBanDialog
						.findViewById(R.id.gv_gopban_list_table);
				gvPlaces = (GridView) mGopBanDialog
						.findViewById(R.id.gv_gopban_list_place);
				btnGopBan = (Button) mGopBanDialog
						.findViewById(R.id.btn_gopban_dialog);
				btnHuy = (Button) mGopBanDialog
						.findViewById(R.id.btn_gopban_huybo);
				PlacesAdapter placesAdapter = new PlacesAdapter(
						(Activity) context, DataSource.sPlaces);
				btnGopBan.setText("Chuyển");

				gvPlaces.setAdapter(placesAdapter);
				gvTables.setAdapter(null);
				// gvTables.setAdapter(gopBanAdapter);
				btnHuy.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						selectedTable.isGopBanSelected = false;

						mGopBanDialog.dismiss();
					}
				});

				btnGopBan.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String gopban = "";

						Log.d("POS", "Chuyển" + gopban);

						ChuyenBanAsyncTask task = new ChuyenBanAsyncTask();
						task.execute(currentPlace.Place_id,
								selectedTable.table_id,
								DataSource.sPlaces.indexOf(currentPlace),
								currentPlace.Tables.indexOf(selectedTable));
						DataSource.sSelectedTable.isGopBanSelected = false;
						selectedTable.isGopBanSelected = false;
						mGopBanDialog.dismiss();
					}
				});

				gvPlaces.setOnItemClickListener(new OnItemClickListener() {

					GopBanAdapter gopBanAdapter;

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						currentPlace = DataSource.sPlaces.get(position);
						gopBanAdapter = new GopBanAdapter(context,
								R.layout.table_item_layout, currentPlace.Tables);
						gvTables.setAdapter(gopBanAdapter);
						gvTables.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int position, long arg3) {
								Log.d("POS", "position: " + position
										+ ", View: " + arg0.getId());

								// check table is in array
								Table selected = gopBanAdapter
										.getItem(position);
								if (selected == DataSource.sSelectedTable) {
									return;
								}
								selectedTable.isGopBanSelected = false;
								selectedTable = selected;
								selectedTable.isGopBanSelected = true;
								gopBanAdapter.notifyDataSetChanged();
							}
						});
					}

				});
				mGopBanDialog.show();
			}
		});

		/*
		 * Gop ban
		 */
		btn_gopBan.setOnClickListener(new OnClickListener() {
			GridView gvTables;
			GridView gvPlaces;
			Button btnGopBan;
			Button btnHuy;
			ArrayList<Table> selectedTable;

			private void cleanSelectedCheck() {
				for (Table table : selectedTable) {
					table.isGopBanSelected = false;
				}
			}

			@Override
			public void onClick(View v) {
				gvTables = (GridView) mGopBanDialog
						.findViewById(R.id.gv_gopban_list_table);
				gvPlaces = (GridView) mGopBanDialog
						.findViewById(R.id.gv_gopban_list_place);
				btnGopBan = (Button) mGopBanDialog
						.findViewById(R.id.btn_gopban_dialog);
				btnHuy = (Button) mGopBanDialog
						.findViewById(R.id.btn_gopban_huybo);
				PlacesAdapter placesAdapter = new PlacesAdapter(
						(Activity) context, DataSource.sPlaces);
				btnGopBan.setText("Gộp bàn");
				gvPlaces.setAdapter(placesAdapter);
				gvTables.setAdapter(null);
				selectedTable = new ArrayList<Table>();
				btnHuy.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						cleanSelectedCheck();
						mGopBanDialog.dismiss();
					}
				});

				btnGopBan.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String gopban = "";
						for (int i = 0; i < selectedTable.size(); i++) {
							gopban += "-" + selectedTable.get(i).table_id;
						}
						Log.d("POS", "Gộp" + gopban);
						cleanSelectedCheck();
						mGopBanDialog.dismiss();
					}
				});

				gvPlaces.setOnItemClickListener(new OnItemClickListener() {

					GopBanAdapter gopBanAdapter;

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						gopBanAdapter = new GopBanAdapter(context,
								R.layout.table_item_layout, DataSource.sPlaces
										.get(position).Tables);
						gvTables.setAdapter(gopBanAdapter);
						gvTables.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int position, long arg3) {
								Log.d("POS", "position: " + position
										+ ", View: " + arg0.getId());
								// check table is in array
								Table clickedItem = gopBanAdapter
										.getItem(position);
								boolean isClicked = false;
								if (selectedTable.contains(clickedItem)) {
									selectedTable.remove(clickedItem);
									clickedItem.isGopBanSelected = false;

								} else {
									selectedTable.add(clickedItem);
									clickedItem.isGopBanSelected = true;
								}
								gopBanAdapter.notifyDataSetChanged();
							}
						});
					}

				});
				mGopBanDialog.show();
			}
		});
		/*
		 * XepBan *
		 */
		btnXepBan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (DataSource.GetSelectedTable() == null) {
					Toast.makeText(context, "bạn chưa chọn bàn",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (DataSource.sSelectedTable.status == 0
						|| DataSource.sSelectedTable.status == 2) {
					// Send request to server
					UpdateStatusTable updateStatusTable = new UpdateStatusTable();
					updateStatusTable.execute(
							DataSource.sSelectedTable.table_id, 1);
				} else {
					Toast.makeText(context, "Bàn đang xếp", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		/*
		 * Thêm món vào hóa đơn
		 */
		btnChon_sanpham.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				chonSanpham();
			}
		});

		/*
		 * Lưu tạm hóa đơn
		 */
		btnluuTam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					if(DataSource.sSelectedTable == null) return;
					SendHoaDonAsynTask sendHoaDonAsynTask = new SendHoaDonAsynTask();
					sendHoaDonAsynTask.execute(0,
							DataSource.sSelectedTable.status,
							DataSource.GetSelectedTable().order_id);
				} catch (Exception exception) {
					Log.e("POS", exception.getMessage());
				}
			}
		});
		/*
		 * Thoát / Logout
		 */
		btnLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});

		/*
		 * Thanh toán
		 */
		mThanhtoanDialog = new Dialog(RestaurantActivity.this);
		mThanhtoanDialog.setCanceledOnTouchOutside(false);
		mThanhtoanDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mThanhtoanDialog.setContentView(R.layout.dialog_thanhtoan);
		mThanhtoanDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		btnThanhToan = (Button) findViewById(R.id.btn_tt);
		btnThanhToan.setOnClickListener(new OnClickListener() {

			private TextView tvTienThua;
			private TextView tvTienKhachTra;
			private TextView tvTienPhaiTra;
			Button btnClose;
			Button btn_ThanhToan;
			Button btn_ThanhToanIn;
			Button btn_enter;
			ImageButton btn_backSpace;
			Button btn_1;
			Button btn_2;
			Button btn_3;
			Button btn_4;
			Button btn_5;
			Button btn_6;
			Button btn_7;
			Button btn_8;
			Button btn_9;
			Button btn_0;

			@Override
			public void onClick(View arg0) {

				if (DataSource.sSelectedTable == null)
					return;

				btnClose = (Button) mThanhtoanDialog
						.findViewById(R.id.btn_close_calculator);
				tvTienPhaiTra = (TextView) mThanhtoanDialog
						.findViewById(R.id.tv_tien_phai_tra);
				tvTienPhaiTra.setTypeface(tf);
				tvTienPhaiTra.setText(NumberFormat.getInstance().format(
						tien_phaiTra));
				tvTienKhachTra = (TextView) mThanhtoanDialog
						.findViewById(R.id.tv_TienKhachTra);
				tvTienKhachTra.setText("");
				tvTienKhachTra.setText("0");
				tvTienThua = (TextView) mThanhtoanDialog
						.findViewById(R.id.tv_TienThua);
				tvTienThua.setText("0");
				btn_0 = (Button) mThanhtoanDialog
						.findViewById(R.id.btn_calcu_0);
				btn_1 = (Button) mThanhtoanDialog
						.findViewById(R.id.btn_calcu_1);
				btn_2 = (Button) mThanhtoanDialog
						.findViewById(R.id.btn_calcu_2);
				btn_3 = (Button) mThanhtoanDialog
						.findViewById(R.id.btn_calcu_3);
				btn_4 = (Button) mThanhtoanDialog
						.findViewById(R.id.btn_calcu_4);
				btn_5 = (Button) mThanhtoanDialog
						.findViewById(R.id.btn_calcu_5);
				btn_6 = (Button) mThanhtoanDialog
						.findViewById(R.id.btn_calcu_6);
				btn_7 = (Button) mThanhtoanDialog
						.findViewById(R.id.btn_calcu_7);
				btn_8 = (Button) mThanhtoanDialog
						.findViewById(R.id.btn_calcu_8);
				btn_9 = (Button) mThanhtoanDialog
						.findViewById(R.id.btn_calcu_9);
				btn_backSpace = (ImageButton) mThanhtoanDialog
						.findViewById(R.id.btn_calcu_delete);
				btn_enter = (Button) mThanhtoanDialog
						.findViewById(R.id.btn_calcu_enter);
				btn_ThanhToan = (Button) mThanhtoanDialog
						.findViewById(R.id.Btn_cal_thanh_toan);
				btn_ThanhToanIn = (Button) mThanhtoanDialog
						.findViewById(R.id.Btn_thanh_toan_in);
				btnClose.setOnClickListener(hanlde);
				btn_0.setOnClickListener(hanlde);
				btn_1.setOnClickListener(hanlde);
				btn_2.setOnClickListener(hanlde);
				btn_3.setOnClickListener(hanlde);
				btn_4.setOnClickListener(hanlde);
				btn_5.setOnClickListener(hanlde);
				btn_6.setOnClickListener(hanlde);
				btn_7.setOnClickListener(hanlde);
				btn_8.setOnClickListener(hanlde);
				btn_9.setOnClickListener(hanlde);
				btn_backSpace.setOnClickListener(hanlde);
				btn_enter.setOnClickListener(hanlde);
				btn_ThanhToan.setOnClickListener(hanlde);
				btn_ThanhToanIn.setOnClickListener(hanlde);
				mThanhtoanDialog.show();
			}

			OnClickListener hanlde = new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {

						SendHoaDonAsynTask sendHoaDonAsynTask;
						String temp = ((String) tvTienKhachTra.getText())
								.replace(",", "");
						switch (v.getId()) {
						case R.id.btn_calcu_0:
							tvTienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "0")));
							break;
						case R.id.btn_calcu_1:
							tvTienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "1")));
							break;
						case R.id.btn_calcu_2:
							tvTienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "2")));
							break;
						case R.id.btn_calcu_3:
							tvTienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "3")));
							break;
						case R.id.btn_calcu_4:
							tvTienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "4")));
							break;
						case R.id.btn_calcu_5:
							tvTienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "5")));
							break;
						case R.id.btn_calcu_6:
							tvTienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "6")));
							break;
						case R.id.btn_calcu_7:
							tvTienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "7")));
							break;
						case R.id.btn_calcu_8:
							tvTienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "8")));
							break;
						case R.id.btn_calcu_9:
							tvTienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "9")));
							break;
						case R.id.btn_close_calculator:
							mThanhtoanDialog.cancel();
							break;
						case R.id.Btn_cal_thanh_toan:
							progress.setTitle("Đang gửi hóa đơn");
							progress.show();
							sendHoaDonAsynTask = new SendHoaDonAsynTask();
							sendHoaDonAsynTask.execute(1, 1,
									DataSource.GetSelectedTable().order_id);

							mThanhtoanDialog.dismiss();
							break;
						case R.id.Btn_thanh_toan_in:
							progress.setTitle("Đang gửi hóa đơn");
							progress.show();
							sendHoaDonAsynTask = new SendHoaDonAsynTask();
							sendHoaDonAsynTask.execute(1, 1,
									DataSource.GetSelectedTable().order_id);
							mThanhtoanDialog.dismiss();
							break;
						case R.id.btn_calcu_delete:
							if (temp.length() == 1)
								tvTienKhachTra.setText("0");
							else
								tvTienKhachTra.setText(NumberFormat
										.getInstance()
										.format(Integer.parseInt(temp
												.substring(0, temp.length() - 1))));
							break;
						case R.id.btn_calcu_enter:
							break;
						default:
							break;
						}
						temp = ((String) tvTienKhachTra.getText()).replace(",",
								"");
						if (temp.equals(""))
							temp = "0";
						int tienKhachTra = Integer.parseInt(temp);
						if (tienKhachTra > tien_phaiTra) {
							tvTienThua.setText(NumberFormat.getInstance()
									.format(tienKhachTra - tien_phaiTra));
						} else
							tvTienThua.setText("0");
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			};
		});

		// Initial OrderListView
		list = (ListView) findViewById(R.id.OrderlistView);
		orderListAdapter = new OrderListAdapter(this, R.layout.order_list_item,
				orderItems);
		list.setAdapter(orderListAdapter);
		// Chọn khách hàng
		btnChonKhachHang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (groupCustomerFragment.isVisible())
					return;
				fragmentTransaction = fragmentManager.beginTransaction();
				Bundle bundle = new Bundle();
				bundle.putString(ApiDefiner.TAG_SHOP_ID, shopId);
				groupCustomerFragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.container,
						groupCustomerFragment);
				Log.d(this.toString(), "replace group customer fragment");
				fragmentTransaction.commit();
			}
		});

		// Chọn hóa đơn
		btnHoaDon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (tableFragment.isVisible())
					return;
				tableFragment.setArguments(bundle);
				fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.container, tableFragment);
				fragmentTransaction.commit();
			}
		});
	}

	public void MessageUncomplete(View v) {
		Toast.makeText(context, "Chức năng đang hoàn thiện", Toast.LENGTH_SHORT)
				.show();
	}

	private void chonSanpham() {
		if (tableFragment.isVisible())
			return;
		tableFragment.setArguments(bundle);
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.container, tableFragment);
		fragmentTransaction.commit();
	}

	public void UpdateOrder() {
		double sum = 0;
		for (OrderItem item : orderItems) {
			sum += item.getPrice() * item.getNumber();
		}
		tien_phaiTra = sum;
		tvTotal.setText(NumberFormat.getInstance().format(sum));
		tvTongHang.setText(NumberFormat.getInstance().format(sum));
		tvThanhToan.setText(NumberFormat.getInstance().format(sum));
	}

	public void UpdateCustomerInfo() {
		Customer customer = DataSource.sSelectedCustomer;
		if (customer == null)
			return;
		tvCustomerName.setText(customer.getFirst_name() + " "
				+ customer.getLast_name());
		tvCustomerGroup.setText(customer.Groupname);
		tvCustomerPhone.setText(customer.Mobile);
	}

	public void CreateNewOrder() {
		orderItems.clear();
		DataSource.sSelectedCustomer = new Customer();
		tvCustomerName.setText("Khách lẻ");
		tvCustomerPhone.setText("");
		tvCustomerGroup.setText("");
		tvGiamGia.setText("");
		tvThanhToan.setText("");
		tvTongHang.setText("");
		tvTotal.setText("");
		tvVAT.setText("");
		tien_phaiTra = 0;

	}

	/*
	 * The first parameter is placeId, the second is tableId
	 */
	public class ChuyenBanAsyncTask extends AsyncTask<Integer, Void, String> {

		int orderId;
		int tableIdOld;
		int tableIdNew;
		int placeIdNew;
		int placeIndex;
		int tableIndex;

		@Override
		protected String doInBackground(Integer... params) {
			try {

				orderId = DataSource.sSelectedTable.order_id;
				tableIdOld = DataSource.sSelectedTable.table_id;
				placeIdNew = params[0];
				tableIdNew = params[1];
				placeIndex = params[2];
				tableIndex = params[3];

				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair(
						ApiDefiner.TAG_ORDER_ID, orderId + ""));
				postParameters.add(new BasicNameValuePair(
						ApiDefiner.TAG_TABLE_ID_OLD, tableIdOld + ""));
				postParameters.add(new BasicNameValuePair(
						ApiDefiner.TAG_PLACE_ID_NEW, placeIdNew + ""));
				postParameters.add(new BasicNameValuePair(
						ApiDefiner.TAG_TABLE_ID_NEW, tableIdNew + ""));
				String result = CustomHttpClient.executeHttpPost(
						ApiDefiner.URL_TRANSFER, postParameters);
				Log.d("POS", result);
				JSONObject jsResult = new JSONObject(result);
				// {"Transfer table":{"code":0,"result":"Transfer table success."}}

				JSONObject UpdateTable = jsResult
						.getJSONObject("Transfer table");
				if (UpdateTable.getString("result").equals(
						"Transfer table success.")) {
					Table destinate = DataSource.sPlaces.get(placeIndex).Tables
							.get(tableIndex);
					destinate.status = DataSource.sSelectedTable.status;
					destinate.order_id = DataSource.sSelectedTable.order_id;
					DataSource.sSelectedTable.status = 0;
					DataSource.sSelectedTable.order_id = 0;
					return "OK";
				} else
					return "error";

			} catch (Exception exception) {
				Log.e("POS", "Chuyển bàn" + exception.getMessage());
				return "error";
			}

		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("error")) {
				Toast.makeText(context, "Lỗi! Chuyển bàn không thành công",
						Toast.LENGTH_SHORT).show();

			} else {
				((RestaurantFragment) tableFragment).tableAdapter
						.notifyDataSetChanged();
				Toast.makeText(context, "Chuyển bàn thành công",
						Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}
	}

	public class UpdateStatusTable extends AsyncTask<Integer, Void, String> {

		int table_id;
		int statusUpdate;
		ProgressDialog progressDialog;
		final int DAT_BAN = 2;
		final int XEP_BAN = 1;
		final int EMPTY = 0;

		@Override
		protected String doInBackground(Integer... params) {
			table_id = params[0];
			statusUpdate = params[1];
			String link = ApiDefiner.URL_UPDATE_STATUS_TABLE + "?"
					+ ApiDefiner.TAG_TABLE_ID + "=" + table_id + "&"
					+ ApiDefiner.TAG_STATUS + "=" + statusUpdate;

			String result;
			try {
				result = CustomHttpClient.executeHttpGet(link);
				Log.d("POS", result);
				JSONObject jsResult = new JSONObject(result);
				JSONObject UpdateTable = jsResult.getJSONObject("update table");
				if (UpdateTable.getString("result").equals("successfully!")) {
					DataSource.sSelectedTable.status = statusUpdate;

					return "OK";
				} else
					return "error";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			if (result.equals("error")) {
				Toast.makeText(context, "Lỗi! Đặt bàn không thành công",
						Toast.LENGTH_SHORT).show();

			} else {
				switch (statusUpdate) {
				case DAT_BAN:
					Toast.makeText(
							context,
							String.format("Đã đặt %s thành công",
									DataSource.GetSelectedTable().name),
							Toast.LENGTH_SHORT).show();
					break;
				case XEP_BAN:
					Toast.makeText(
							context,
							String.format("Đã xếp %s thành công",
									DataSource.GetSelectedTable().name),
							Toast.LENGTH_SHORT).show();
					break;
				}

			}
			((RestaurantFragment) tableFragment).tableAdapter
					.notifyDataSetChanged();
			super.onPostExecute(result);
		}
	}

	public class SendHoaDonAsynTask extends AsyncTask<Integer, Void, Integer> {

		private static final int ORDER_CODE_ERROR = 1;
		private static final int ORDER_CODE_SUCCESS = 0;
		int status;
		int status_table;
		int order_id;

		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				// status : Tình Trạng:0 ->Pending; 1 ->Finished; -1 -> Void
				status = params[0];
				// Tình trạng bill cho bàn: 1->bàn sử dụng luôn; 2->bàn được đặt
				status_table = params[1];
				Bill bill = new Bill();
				if (params[2] != 0) {
					order_id = params[2];
					bill.setOrder_id(order_id + "");
				} else {
					bill.setOrder_id("");
				}
				if (DataSource.currentBill != null) {
					bill.setTax(DataSource.currentBill.getTax());
					bill.setTax_id(DataSource.currentBill.getTax_id());
				}
				Log.d("Send Order", "order id=" + order_id);
				bill.setBranch_id(user.getBranch_id());
				if (DataSource.sSelectedCustomer != null)
					bill.setCustomer_id(DataSource.sSelectedCustomer.Customer_id);
				bill.setDiscount(0d);
				// Table đang chon.
				Table table = DataSource.GetSelectedTable();
				// Tầng đang được chọn
				// bill.setFloor_id(table.place_id);
				Log.d("Send Order", "place id=" + table.place_id);
				bill.setNote("");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				bill.setOrder_date(sdf.format(new Date()));
				bill.setOrder_number(1);
				bill.setOrder_symbol("demo");
				bill.setShop_id(user.getShop_id());
				// biến đầu tiên status : Tình Trạng:0->Pending; 1->Finished;
				// -1-> Void
				bill.setStatus(status);
				bill.setStatus_table(status_table);
				bill.setSubtotal(Double.parseDouble(tvTongHang.getText()
						.toString().replace(",", "")));
				bill.setTable_id(table.table_id);
				Log.d("Send Order", "table id=" + table.table_id);

				bill.setTopay(tien_phaiTra);
				bill.setTotal_price(Double.parseDouble(tvTotal.getText()
						.toString().replace(",", "")));
				bill.setUser_id(user.getUser_id());
				ArrayList<String> orders = new ArrayList<String>();
				for (OrderItem order : orderItems) {
					orders.add(String.format("%s-%s-%d#", order.getProductId(),
							order.getNumber(), (int) order.getPrice()));
				}
				bill.setAllProduct(orders);
				Log.d("Restaurant", "Tạo hóa đơn,status: " + status
						+ ", trạng thái bàn: " + status_table);
				String result = CustomHttpClient.executeHttpPost(
						ApiDefiner.URL_INSERT_HOADON, bill.GetParameter());
				JSONObject jsonResult = new JSONObject(result);
				Log.d("Send Order", "Gửi hóa đơn, Result " + result);
				if (jsonResult.getJSONObject("SentBill").getString("code")
						.trim().equals("1")) {
					return ORDER_CODE_ERROR;
				} else {
					if (status == 1) {
						// Set table status is empty
						UpdateStatusTable updateStatusTable = new UpdateStatusTable();
						updateStatusTable.execute(bill.getTable_id(), 0);
					}
					if (bill.getOrder_id().equals(""))
						table.order_id = jsonResult.getJSONObject("SentBill")
								.getInt(ApiDefiner.TAG_RESULT);
					return ORDER_CODE_SUCCESS;
				}

			} catch (Exception e) {
				Log.e("Restaurant", e.getMessage());
				return 1;
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (result == ORDER_CODE_SUCCESS) {
				if (status == 1) {
					CreateNewOrder();
					Toast.makeText(context, "Đã thanh toán thành công",
							Toast.LENGTH_SHORT).show();

				}
				if (status == 0) {
					// CreateNewOrder();
					Toast.makeText(context, "Đã lưu thành công",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context, "Thanh toán gặp lỗi",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
