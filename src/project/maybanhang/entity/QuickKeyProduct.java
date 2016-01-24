package project.maybanhang.entity;

public class QuickKeyProduct {
	public QuickKeyProduct(int sessionProductId, int sessionId, int productId,
			String alias, String title, String categoryId, long retailPrice,
			String taxId, int isNew) {
		super();
		this.sessionProductId = sessionProductId;
		this.sessionId = sessionId;
		this.productId = productId;
		this.alias = alias;
		this.title = title;
		this.categoryId = categoryId;
		this.retailPrice = retailPrice;
		this.taxId = taxId;
		this.isNew = isNew;
	}

	// product: {
	public static final String TAG_QK_PRODUCT_ID = "quickkey_session_product_id";
	public static final String TAG_QK_SESSION_ID = "quickkey_session_id";
	public static final String TAG_SORTING = "sorting";
	public static final String TAG_COLOR = "color";
	public static final String TAG_PRODUCT_ID = "product_id";
	public static final String TAG_SHOP_ID = "shop_id";
	public static final String TAG_ALIAS = "alias";
	public static final String TAG_TITLE = "title";
	public static final String TAG_SKU = "sku";
	public static final String TAG_BARCODE = "barcode";
	public static final String TAG_DESCRIPTION = "description";
	public static final String TAG_PRODUCT_CATEGORY_ID = "product_category_id";
	public static final String TAG_SUPPLIER = "supplier";
	public static final String TAG_SUPPLY_PRICE = "supply_price";
	public static final String TAG_RETAIL_PRICE = "retail_price";
	public static final String TAG_TAX_ID = "tax_id";
	public static final String TAG_HAS_VARIANT = "has_variant";
	public static final String TAG_STOCK_TYPE = "stock_type";
	public static final String TAG_HAS_STOCK_TRACKING = "has_stock_tracking";
	public static final String TAG_ENABLE = "enable";
	public static final String TAG_CREATE_DATE = "created_date";
	public static final String TAG_IMAGES = "images";
	public static final String TAG_NEW = "new";
	public static final String TAG_TAX_INPUT = "tax_input";
	public static final String TAG_UNIT = "unit";

	private int sessionProductId;
	private int sessionId;
	private int sorting;
	private int color;
	private int productId;
	private int shopId;
	private String alias;
	private String title;
	private String sku;
	private String barcode;
	private String description;
	private String categoryId;
	private String supplier;
	private long supplyPrice;
	private long retailPrice;
	private String taxId;
	private int hasVariant;
	private int stockType;
	private int hasStockTracking;
	private int enabled;
	private String createDate;
	private String images;
	private int isNew;
	private String taxInput;
	private String unit;

	public QuickKeyProduct(int sessionProductId, int sessionId, int sorting,
			int color, int productId, int shopId, String alias, String title,
			String sku, String barcode, String description, String categoryId,
			String supplier, long supplyPrice, long retailPrice, String taxId,
			int hasVariant, int stockType, int hasStockTracking,
			int enabled, String createDate, String images, int isNew,
			String taxInput, String unit) {
		super();
		this.sessionProductId = sessionProductId;
		this.sessionId = sessionId;
		this.sorting = sorting;
		this.color = color;
		this.productId = productId;
		this.shopId = shopId;
		this.alias = alias;
		this.title = title;
		this.sku = sku;
		this.barcode = barcode;
		this.description = description;
		this.categoryId = categoryId;
		this.supplier = supplier;
		this.supplyPrice = supplyPrice;
		this.retailPrice = retailPrice;
		this.taxId = taxId;
		this.hasVariant = hasVariant;
		this.stockType = stockType;
		this.hasStockTracking = hasStockTracking;
		this.enabled = enabled;
		this.createDate = createDate;
		this.images = images;
		this.isNew = isNew;
		this.taxInput = taxInput;
		this.unit = unit;
	}

	public int getSessionProductId() {
		return sessionProductId;
	}

	public void setSessionProductId(int sessionProductId) {
		this.sessionProductId = sessionProductId;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public int getSorting() {
		return sorting;
	}

	public void setSorting(int sorting) {
		this.sorting = sorting;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public double getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(long supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public long getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(long retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public int getHasVariant() {
		return hasVariant;
	}

	public void setHasVariant(int hasVariant) {
		this.hasVariant = hasVariant;
	}

	public int getStockType() {
		return stockType;
	}

	public void setStockType(int stockType) {
		this.stockType = stockType;
	}

	public int isHasStockTracking() {
		return hasStockTracking;
	}

	public void setHasStockTracking(int hasStockTracking) {
		this.hasStockTracking = hasStockTracking;
	}

	public int isEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public int isNew() {
		return isNew;
	}

	public void setNew(int isNew) {
		this.isNew = isNew;
	}

	public String getTaxInput() {
		return taxInput;
	}

	public void setTaxInput(String taxInput) {
		this.taxInput = taxInput;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
