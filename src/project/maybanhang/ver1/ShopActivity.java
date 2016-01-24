package project.maybanhang.ver1;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import project.maybanhang.ApiDefiner;
import project.maybanhang.CustomHttpClient;
import project.maybanhang.DataSource;
import project.maybanhang.adapters.QuickKeyProductsAdapter;
import project.maybanhang.adapters.MenuAdapter;
import project.maybanhang.adapters.OrderListAdapter;
import project.maybanhang.adapters.TableListAdapter;
import project.maybanhang.entity.Bill;
import project.maybanhang.entity.Customer;
import project.maybanhang.entity.ItemInfo;
import project.maybanhang.entity.OrderItem;
import project.maybanhang.entity.Table;
import project.maybanhang.entity.User;
import project.maybanhang.fragment.CategoryFragment;
import project.maybanhang.fragment.GroupCustomerFragment;
import project.maybanhang.fragment.PlacesFragement;
import project.maybanhang.fragment.ProductFragment;
import project.maybanhang.fragment.RestaurantFragment;
import project.maybanhang.ver1.RestaurantActivity.SendHoaDonAsynTask;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends Activity {

	public ListView list;
	public OrderListAdapter orderListAdapter;
	private User user;
	Button btn_ThanhToan;
	Button btn_ChonKhachHang;
	Button btn_Chon_sanpham;
	Button btn_luuTam;
	ImageButton btn_Logout;
	TextView tvTongHang;
	TextView tvGiamGia;
	TextView tvVAT;
	TextView tvThanhToan;
	TextView tvTotal;
	TextView tvCustomerName;
	TextView tvCustomerGroup;
	TextView tvCustomerPhone;
	MenuAdapter menuAdapter;
	ProgressDialog progress;
	int order_id = -1;
	public ArrayList<OrderItem> order_items = new ArrayList<OrderItem>();

	public CategoryFragment categoryFragment;
	public ProductFragment productFragment;
	public GroupCustomerFragment groupCustomerFragment;

	public String shop_id = "";
	public FragmentManager fragmentManager;
	public FragmentTransaction fragmentTransaction;
	Context context;
	Bundle bundle;
	private double tien_phaiTra;
	private Dialog dialog_thanhtoan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		context = this;
		btn_ChonKhachHang = (Button) findViewById(R.id.btn_khachhang);
		btn_Chon_sanpham = (Button) findViewById(R.id.btn_chonsp);
		btn_Logout = (ImageButton) findViewById(R.id.Logout);
		btn_luuTam = (Button) findViewById(R.id.btn_luutam);
		tvCustomerName = (TextView) findViewById(R.id.tv_customer_name);
		tvCustomerGroup = (TextView) findViewById(R.id.tv_customer_type);
		tvCustomerPhone = (TextView) findViewById(R.id.tv_phone_number);
		bundle = getIntent().getExtras();
		user = new User(bundle);
		progress = new ProgressDialog(context);
		progress.setCanceledOnTouchOutside(false);
		tvGiamGia = (TextView) findViewById(R.id.textViewGiam_gia);
		tvThanhToan = (TextView) findViewById(R.id.textViewTHANHTOAN);
		tvTongHang = (TextView) findViewById(R.id.textViewTongHang);
		tvTotal = (TextView) findViewById(R.id.tvTotal);
		tvVAT = (TextView) findViewById(R.id.textViewVAT);
		shop_id = bundle.getString(ApiDefiner.TAG_SHOP_ID);
		fragmentManager = getFragmentManager();
		categoryFragment = new CategoryFragment();
		productFragment = new ProductFragment();
		groupCustomerFragment = new GroupCustomerFragment();
		goChonSanpham();

		list = (ListView) findViewById(R.id.OrderlistView);
		btn_Logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		orderListAdapter = new OrderListAdapter(this, R.layout.order_list_item,
				this.order_items);

		list.setAdapter(orderListAdapter);

		btn_Chon_sanpham.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				goChonSanpham();
			}
		});

		btn_ChonKhachHang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (groupCustomerFragment.isVisible())
					return;
				fragmentTransaction = fragmentManager.beginTransaction();
				Bundle bundle = new Bundle();
				bundle.putString(ApiDefiner.TAG_SHOP_ID, shop_id);
				groupCustomerFragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.container,
						groupCustomerFragment);
				Log.d(this.toString(), "replace group customer fragment");
				fragmentTransaction.commit();
			}
		});

		btn_luuTam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SendHoaDonAsynTask sendHoaDonAsynTask = new SendHoaDonAsynTask();
				sendHoaDonAsynTask.execute(0, 1);
			}
		});
		dialog_thanhtoan = new Dialog(context);
		dialog_thanhtoan.setCanceledOnTouchOutside(false);
		dialog_thanhtoan.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_thanhtoan.setContentView(R.layout.dialog_thanhtoan);

		btn_ThanhToan = (Button) findViewById(R.id.btn_thanh_toan);
		btn_ThanhToan.setOnClickListener(new OnClickListener() {
			private TextView tv_TienThua;
			private TextView tv_TienKhachTra;
			private TextView tv_TienPhaiTra;
			Button btn_close;
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

			// Button btn_dot;

			@Override
			public void onClick(View arg0) {

				btn_close = (Button) dialog_thanhtoan
						.findViewById(R.id.btn_close_calculator);
				tv_TienPhaiTra = (TextView) dialog_thanhtoan
						.findViewById(R.id.tv_tien_phai_tra);
				tv_TienPhaiTra.setText(NumberFormat.getInstance().format(
						tien_phaiTra));
				tv_TienKhachTra = (TextView) dialog_thanhtoan
						.findViewById(R.id.tv_TienKhachTra);
				tv_TienThua = (TextView) dialog_thanhtoan
						.findViewById(R.id.tv_TienThua);
				btn_0 = (Button) dialog_thanhtoan
						.findViewById(R.id.btn_calcu_0);
				btn_1 = (Button) dialog_thanhtoan
						.findViewById(R.id.btn_calcu_1);
				btn_2 = (Button) dialog_thanhtoan
						.findViewById(R.id.btn_calcu_2);
				btn_3 = (Button) dialog_thanhtoan
						.findViewById(R.id.btn_calcu_3);
				btn_4 = (Button) dialog_thanhtoan
						.findViewById(R.id.btn_calcu_4);
				btn_5 = (Button) dialog_thanhtoan
						.findViewById(R.id.btn_calcu_5);
				btn_6 = (Button) dialog_thanhtoan
						.findViewById(R.id.btn_calcu_6);
				btn_7 = (Button) dialog_thanhtoan
						.findViewById(R.id.btn_calcu_7);
				btn_8 = (Button) dialog_thanhtoan
						.findViewById(R.id.btn_calcu_8);
				btn_9 = (Button) dialog_thanhtoan
						.findViewById(R.id.btn_calcu_9);
				btn_backSpace = (ImageButton) dialog_thanhtoan
						.findViewById(R.id.btn_calcu_delete);
				btn_enter = (Button) dialog_thanhtoan
						.findViewById(R.id.btn_calcu_enter);
				btn_ThanhToan = (Button) dialog_thanhtoan
						.findViewById(R.id.Btn_cal_thanh_toan);
				btn_ThanhToanIn = (Button) dialog_thanhtoan
						.findViewById(R.id.Btn_thanh_toan_in);
				btn_close.setOnClickListener(hanlde);
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
				dialog_thanhtoan.show();
			}

			OnClickListener hanlde = new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						SendHoaDonAsynTask sendHoaDonAsynTask;
						String temp = ((String) tv_TienKhachTra.getText())
								.replace(",", "");
						switch (v.getId()) {
						case R.id.btn_calcu_0:
							tv_TienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "0")));
							break;
						case R.id.btn_calcu_1:
							tv_TienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "1")));
							break;
						case R.id.btn_calcu_2:
							tv_TienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "2")));
							break;
						case R.id.btn_calcu_3:
							tv_TienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "3")));
							break;
						case R.id.btn_calcu_4:
							tv_TienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "4")));
							break;
						case R.id.btn_calcu_5:
							tv_TienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "5")));
							break;
						case R.id.btn_calcu_6:
							tv_TienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "6")));
							break;
						case R.id.btn_calcu_7:
							tv_TienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "7")));
							break;
						case R.id.btn_calcu_8:
							tv_TienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "8")));
							break;
						case R.id.btn_calcu_9:
							tv_TienKhachTra.setText(NumberFormat.getInstance()
									.format(Long.parseLong(temp + "9")));
							break;
						case R.id.btn_close_calculator:
							dialog_thanhtoan.cancel();
							break;
						case R.id.Btn_cal_thanh_toan:
							progress.setTitle("Đang gửi hóa đơn");
							progress.show();
							sendHoaDonAsynTask = new SendHoaDonAsynTask();
							sendHoaDonAsynTask.execute(1, 1);
							dialog_thanhtoan.dismiss();
							break;
						case R.id.Btn_thanh_toan_in:
							progress.setTitle("Đang gửi hóa đơn");
							progress.show();
							sendHoaDonAsynTask = new SendHoaDonAsynTask();
							sendHoaDonAsynTask.execute(1, 1);
							dialog_thanhtoan.dismiss();
							break;
						case R.id.btn_calcu_delete:
							if (temp.length() == 1)
								tv_TienKhachTra.setText(0);
							else
								tv_TienKhachTra.setText(NumberFormat
										.getInstance()
										.format(Integer.parseInt(temp
												.substring(0, temp.length() - 1))));
							break;
						case R.id.btn_calcu_enter:
							break;
						default:
							break;
						}
						temp = ((String) tv_TienKhachTra.getText()).replace(
								",", "");
						if (temp.equals(""))
							temp = "0";
						int tienKhachTra = Integer.parseInt(temp);
						if (tienKhachTra > tien_phaiTra) {
							tv_TienThua.setText(NumberFormat.getInstance()
									.format(tienKhachTra - tien_phaiTra));
						} else
							tv_TienThua.setText("0");

					} catch (Exception e) {
						Log.e("Shop Activity", e.getMessage());
					}

				}
			};
		});

	}

	private void goChonSanpham() {
		if (categoryFragment.isVisible())
			return;
		categoryFragment.setArguments(bundle);
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.container, categoryFragment);
		fragmentTransaction.commit();
	}

	public void UpdateOrder() {
		double sum = 0.0;
		for (OrderItem item : order_items) {
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

	public class SendHoaDonAsynTask extends AsyncTask<Integer, Void, Integer> {

		private static final int ORDER_CODE_ERROR = 1;
		private static final int ORDER_CODE_SUCCESS = 0;

		// status : Tình Trạng:0->Pending; 1->Finished; -1-> Void
		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				Bill bill = new Bill();
				if (params[2] != -1){
					order_id = params[2];
					bill.setOrder_id(order_id+"");
				}else{
					bill.setOrder_id("");	
				}
				bill.setBranch_id(user.getBranch_id());
				if (DataSource.sSelectedCustomer != null)
					bill.setCustomer_id(DataSource.sSelectedCustomer.Customer_id);
				bill.setDiscount(0d);
				bill.setNote("");
				Log.d("Send Order", "Note: " + bill.getNote());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				bill.setOrder_date(sdf.format(new Date()));
				Log.d("Send Order", "Order date: " + bill.getOrder_date());
				bill.setOrder_number(1);
				Log.d("Send Order", "Order number: " + bill.getOrder_number());
				bill.setOrder_symbol("demo");
				Log.d("Send Order", "Order symbol: " + bill.getOrder_symbol());
				bill.setShop_id(user.getShop_id());
				Log.d("Send Order", "shop id: " + bill.getShop_id());
				// biến đầu tiên status : Tình Trạng:0->Pending; 1->Finished;
				// -1-> Void
				bill.setStatus(params[0]);
				Log.d("Send Order", "Status: " + bill.getStatus());
				bill.setStatus_table(params[1]);
				Log.d("Send Order", "Status table: " + bill.getStatus_table());
				bill.setSubtotal(Double.parseDouble(tvTongHang.getText()
						.toString().replace(",", "")));
				Log.d("Send Order", "Subtotal: " + bill.getSubtotal());
				bill.setTax_id(0);
				Log.d("Send Order", "Tax id: " + bill.getTax());
				bill.setTopay(tien_phaiTra);
				Log.d("Send Order", "Topay: " + bill.getTopay());
				bill.setTotal_price(Double.parseDouble(tvTotal.getText()
						.toString().replace(",", "")));
				Log.d("Send Order", "Total price: " + bill.getTotal_price());
				bill.setUser_id(user.getUser_id());
				Log.d("Send Order", "User id: " + bill.getUser_id());
				ArrayList<String> orders = new ArrayList<String>();
				for (OrderItem order : order_items) {
					orders.add(String.format("%s-%s-%d#", order.getProductId(),
							order.getNumber(), (int) order.getPrice()));
				}
				bill.setAllProduct(orders);
				Log.d("Restaurant", "Tạo hóa đơn: " + params[0] + ","
						+ params[1]);
				String result = CustomHttpClient.executeHttpPost(
						ApiDefiner.URL_INSERT_HOADON, bill.GetParameter());
				JSONObject jsonResult = new JSONObject(result);
				Log.d("Restaurant", "Gửi hóa đơn, Result " + result);
				if (jsonResult.getJSONObject("SentBill").getString("code")
						.trim().equals("1")) {
					return ORDER_CODE_ERROR;
				} else
					return ORDER_CODE_SUCCESS;

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
				Toast.makeText(context, "Đã Thanh toán thành công",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "Thanh toán gặp lỗi",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
