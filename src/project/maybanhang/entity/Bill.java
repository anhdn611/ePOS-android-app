package project.maybanhang.entity;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.maybanhang.ApiDefiner;

public class Bill {
	public static final String BILL_INFO = "Bill information";
	public static final String BILL = "Bill";

	public Bill(String json) {
		try {
			JSONObject root = new JSONObject(json);
			JSONArray BillInfomation = root.getJSONArray(BILL_INFO);
			JSONObject info = BillInfomation.getJSONObject(0).getJSONObject(
					BILL);
			if (!info.getString(ApiDefiner.TAG_ORDER_ID).equals("null"))
				order_id = info.getString(ApiDefiner.TAG_ORDER_ID);
			shop_id = info.getString(ApiDefiner.TAG_SHOP_ID);
			branch_id = info.getString(ApiDefiner.TAG_BRANCH_ID);
			user_id = info.getString(ApiDefiner.TAG_USER_ID);
			customer_id = info.getString(ApiDefiner.TAG_CUSTOMER_ID);
			note = info.getString(ApiDefiner.TAG_NOTE);
			order_date = info.getString(ApiDefiner.TAG_ORDER_DATE);
			if (!info.getString(ApiDefiner.TAG_SUBTOTAL).equals("null"))
				subtotal = info.getDouble(ApiDefiner.TAG_SUBTOTAL);
			if (!info.getString(ApiDefiner.TAG_TAX).equals("null"))
				tax = info.getInt(ApiDefiner.TAG_TAX);
			if (!info.getString(ApiDefiner.TAG_DISCOUNT).equals("null"))
				discount = info.getDouble(ApiDefiner.TAG_DISCOUNT);
			if (!info.getString(ApiDefiner.TAG_TOTAL_PRICE).equals("null"))
				total_price = info.getDouble(ApiDefiner.TAG_TOTAL_PRICE);
			if (!info.getString(ApiDefiner.TAG_TOPAY).equals("null"))
				topay = info.getDouble(ApiDefiner.TAG_TOPAY);
			status = info.getInt(ApiDefiner.TAG_STATUS);
			barcode = info.getString(ApiDefiner.TAG_BARCODE);
			if (!info.getString(ApiDefiner.TAG_PLACE_ID).equals("null"))
				place_id = info.getInt(ApiDefiner.TAG_PLACE_ID);
			if (!info.getString(ApiDefiner.TAG_TABLE_ID).equals("null"))
				table_id = info.getInt(ApiDefiner.TAG_TABLE_ID);
			order_symbol = info.getString(ApiDefiner.TAG_ORDER_SYMBOL);
			if (!info.getString(ApiDefiner.TAG_ORDER_NUMBER).equals("null"))
				order_number = info.getInt(ApiDefiner.TAG_ORDER_NUMBER);
			if (!info.getString(ApiDefiner.TAG_TAX_ID).equals("null"))
				tax_id = info.getInt(ApiDefiner.TAG_TAX_ID);
			orders = new ArrayList<OrderItem>();
			JSONArray allProduct = root
					.getJSONArray(ApiDefiner.TAG_ALL_PRODUCT);
			for (int i = 0; i < allProduct.length(); i++) {
				JSONObject product = allProduct.getJSONObject(i).getJSONObject(
						ApiDefiner.TAG_PRODUCT);
				OrderItem order = new OrderItem();
				order.setName(product.getString(ApiDefiner.TAG_PRODUCT_NAME));
				order.setNumber(product.getInt(ApiDefiner.TAG_NUMBER));
				order.setPrice(product.getLong(ApiDefiner.TAG_PRICE));
				order.setProductId(product.getInt(ApiDefiner.TAG_PRODUCT_ID));
				orders.add(order);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Bill() {
		orders = new ArrayList<OrderItem>();
	}

	public ArrayList<NameValuePair> GetParameter() {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("order_id", order_id + ""));
		pairs.add(new BasicNameValuePair("shop_id", getShop_id()));
		pairs.add(new BasicNameValuePair("branch_id", getBranch_id()));
		pairs.add(new BasicNameValuePair("user_id", getUser_id()));
		pairs.add(new BasicNameValuePair("customer_id", getCustomer_id()));
		pairs.add(new BasicNameValuePair("note", getNote()));
		pairs.add(new BasicNameValuePair("order_date", getOrder_date()));
		pairs.add(new BasicNameValuePair("subtotal", getSubtotal().toString()));
		pairs.add(new BasicNameValuePair("tax", tax + ""));
		pairs.add(new BasicNameValuePair("discount", getDiscount().toString()));
		pairs.add(new BasicNameValuePair("total_price", getTotal_price()
				.toString()));
		pairs.add(new BasicNameValuePair("topay", getTopay().toString()));
		pairs.add(new BasicNameValuePair("status", getStatus() + ""));
		pairs.add(new BasicNameValuePair("status_table", getStatus_table() + ""));
		pairs.add(new BasicNameValuePair("barcode", getBarcode() + ""));
		pairs.add(new BasicNameValuePair("place_id", getPlaceId() + ""));
		pairs.add(new BasicNameValuePair("table_id", getTable_id() + ""));
		pairs.add(new BasicNameValuePair("order_symbol", getOrder_symbol()));
		pairs.add(new BasicNameValuePair("order_number", getOrder_number() + ""));
		pairs.add(new BasicNameValuePair("tax_id", getTax_id() + ""));
		StringBuilder allProduct = new StringBuilder();
		for (String product : getAllProduct()) {
			allProduct.append(product);
		}
		String orders = allProduct.toString();
		if (orders.lastIndexOf("#") != -1) {
			pairs.add(new BasicNameValuePair("All_products", orders.substring(
					0, orders.lastIndexOf("#"))));
		} else {
			pairs.add(new BasicNameValuePair("All_products", orders));
		}
		return pairs;
	}

	private ArrayList<OrderItem> orders;

	public ArrayList<OrderItem> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<OrderItem> orders) {
		this.orders = orders;
	}

	private float tax;
	private String order_id;
	private String shop_id;
	private String branch_id;
	private String user_id;
	private String customer_id;
	private String note;
	private String order_date;
	private Double subtotal;
	private double discount;
	private Double total_price;
	private Double topay;
	private int status;
	private int status_table;
	private String barcode;
	private int place_id;
	private int table_id;
	private String order_symbol;
	private int order_number;
	private int tax_id;
	private ArrayList<String> allProduct;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public Double getTopay() {
		return topay;
	}

	public void setTopay(Double topay) {
		this.topay = topay;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus_table() {
		return status_table;
	}

	public void setStatus_table(int status_table) {
		this.status_table = status_table;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getPlaceId() {
		return place_id;
	}

	public void setPlaceId(int placeId) {
		this.place_id = placeId;
	}

	public int getTable_id() {
		return table_id;
	}

	public void setTable_id(int table_id) {
		this.table_id = table_id;
	}

	public String getOrder_symbol() {
		return order_symbol;
	}

	public void setOrder_symbol(String order_symbol) {
		this.order_symbol = order_symbol;
	}

	public int getOrder_number() {
		return order_number;
	}

	public void setOrder_number(int order_number) {
		this.order_number = order_number;
	}

	public int getTax_id() {
		return tax_id;
	}

	public void setTax_id(int tax_id) {
		this.tax_id = tax_id;
	}

	public ArrayList<String> getAllProduct() {
		return allProduct;
	}

	public void setAllProduct(ArrayList<String> allProduct) {
		this.allProduct = allProduct;
	}

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}
}
