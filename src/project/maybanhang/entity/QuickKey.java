package project.maybanhang.entity;

public class QuickKey {
	public QuickKey(int quickKeyId, String name, int shopId, int branchId,
			int active) {
		super();
		this.quickKeyId = quickKeyId;
		this.name = name;
		this.shopId = shopId;
		this.branchId = branchId;
		this.active = active;
	}

	// quickkey_id: "41",
	// name: "Default",
	// shop_id: "32",
	// branch_id: "44",
	// created_date: "1392278496",
	// active: "0"
	private int quickKeyId;
	private String name;
	private int shopId;
	private int branchId;
	private int active;

	public int getQuickKeyId() {
		return quickKeyId;
	}

	public void setQuickKeyId(int quickKeyId) {
		this.quickKeyId = quickKeyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

}
