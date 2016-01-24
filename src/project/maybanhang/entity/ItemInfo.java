package project.maybanhang.entity;

public class ItemInfo {
	public String name;	
	public String product_id;
	public long retail_price;
	public String product_category_id;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Item: "+name+";price :" +retail_price;
	}

}
