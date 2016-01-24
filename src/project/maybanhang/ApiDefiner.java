package project.maybanhang;

public class ApiDefiner {
	// URL API
	public static final String URL_LOGIN = "http://ws.cnv.vn/SerShowUser.php";
	public static final String URL_SHOP_ITEMS = "http://ws.cnv.vn/SerShowproductsByshopId.php";
	public static final String URL_CATEGORIES = "http://ws.cnv.vn/SerShowProductCategoryByShop.php";
	public static final String URL_PRODUCT_BY_CATEGORIES = "http://ws.cnv.vn/SerShowproductsBycategoryId.php";
	public static final String URL_KHUVUC_BAN = "http://ws.cnv.vn/SerShowplace.php";
	public static final String URL_THONG_TIN_KH = "http://ws.cnv.vn/SerShowCustomerByShop.php";
	public static final String URL_TIMKIEM_SP = "http://ws.cnv.vn/SerFindproducts.php";
	public static final String URL_INSERT_HOADON = "http://ws.cnv.vn/SelectProductSentBill.php";
	public static final String URL_GET_TABLES_BY_ID = "http://ws.cnv.vn/SerShowTableByPlaceId.php";
	public static final String URL_UPDATE_STATUS_TABLE = "http://ws.cnv.vn/SerUpdatestatustable.php";
	public static final String URL_LOAD_ORDER = "http://ws.cnv.vn/SerLoadbillByid.php";
	// Parameter
	public static final String ID_SHOP = "0";
	public static final String ID_COFFE = "1";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	// LOGIN
	public static final String TAG_SHOP_ID = "shop_id";
	public static final String TAG_SHOP_TYPE = "shop_type";
	public static final String TAG_GROUP_ID = "shop_id";
	public static final String TAG_FULL_NAME = "full_name";
	public static final String TAG_USER = "User";
	public static final String TAG_USER_ID = "user_id";
	public static final String TAG_PERSON_INFO = "Personal information";
	public static final String TAG_USERNAME = "username";
	public static final String TAG_EMAIL = "email";
	public static final String TAG_FISRTNAME = "first_name";
	public static final String TAG_LASTNAME = "last_name";
	// RESTAURANT
	public static final String TAG_All_PLACES = "All places";
	public static final String TAG_PLACE_ID = "place_id";
	public static final String TAG_PLACE = "flace";
	public static final String TAG_NAME = "name";
	public static final String TAG_BRANCH_ID = "branch_id";
	public static final String TAG_TABLE_ID = "table_id";
	public static final String TAG_STATUS = "status";
	public static final String TAG_COMBINE = "combine";
	public static final String TAG_ORDER_ID = "order_id";
	public static final String TAG_ALL_TABLE = "All tables";
	public static final String TAG_TABLE = "table";
	// SERVER MESSAGE
	public static final String MESS_WRONG_USER_PASS = "username or password not invalid.";
	public static final String MESS_CONNECTION_ERROR = "no internet access";
	public static final String MESS_PRODUCT_NOT_FOUND = "Not find product.";
	public static final String MESS_NO_TABLE = "Not table.";
	// CUSTOMER
	public static final String TAG_GROUP_CUSTOMER_ID = "group_customer_id";
	public static final String TAG_GROUP_CUSTOMER_NAME = "groupname";
	public static final String TAG_CUSTOMER_ID = "customer_id";
	public static final String TAG_CUSTOMER_FIRSTNAME = "first_name";
	public static final String TAG_CUSTOMER_LASTNAME = "last_name";
	public static final String TAG_CUSTOMER_PHONE = "phone";
	public static final String TAG_CUSTOMER_LIST = "customer list";
	public static final String TAG_CUSTOMER = "customer";
	// PRODUCT
	public static final String TAG_PRODUCT_ID = "product_id";
	// SHOP
	public static final String TAG_CATEGROY_ID = "product_category_id";
	// BILL
	public static final String TAG_NOTE = "note";
	public static final String TAG_ORDER_DATE = "order_date";
	public static final String TAG_SUBTOTAL = "subtotal";
	public static final String TAG_TAX = "tax";
	public static final String TAG_DISCOUNT = "discount";
	public static final String TAG_TOTAL_PRICE = "total_price";
	public static final String TAG_TOPAY = "topay";
	public static final String TAG_ORDER_SYMBOL = "order_symbol";
	public static final String TAG_ORDER_NUMBER = "order_number";
	public static final String TAG_TAX_ID = "tax_id";
	public static final String TAG_BARCODE = "barcode";
	public static final String TAG_ALL_PRODUCT = "All product";
	public static final String TAG_PRODUCT = "product";
	public static final String TAG_PRODUCT_NAME = "product_name";
	public static final String TAG_NUMBER = "number";
	public static final String TAG_PRICE = "price";
	//
	public static final int OK = 1;
	public static final int FAIL = 0;
	public static final String NONE = "none.";
	public static final String TAG_RESULT = "result";
	public static final String URL_GETLIST_GIAODIEN = "http://ws.cnv.vn/SerLoadquickkeyByshop.php";
	public static final String QUICK_KEY = "quickkey";
	public static final String QUICK_KEY_LIST = "quickkey list";
	public static final String TAG_QUICKKEY_ID = "quickkey_id";
	public static final String TAG_ACTIVE = "active";
	public static final String URL_GET_QUICKKEY_SESSION = "http://ws.cnv.vn/SerQuickkeySessionByid.php";
	public static final String TAG_ALL_QUICKKEY_SESSION = "All quickkey session";
	public static final String TAG_QUICKKEY_SESSION = "quickkey_session";
	public static final String TAG_QUICKKEY_SESSION_ID = "quickkey_session_id";
	public static final String URL_GET_PRODUCT_QUICKKEY_SESSION = "http://ws.cnv.vn/SerLoadproductByquickkey.php";
	public static final String URL_TRANSFER = "http://ws.cnv.vn/SerTransferTable.php";
	public static final String TAG_TABLE_ID_OLD = "table_id_old";
	public static final String TAG_PLACE_ID_NEW = "place_id_new";
	public static final String TAG_TABLE_ID_NEW = "table_id_new";

}
