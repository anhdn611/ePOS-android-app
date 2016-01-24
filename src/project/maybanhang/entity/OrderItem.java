package project.maybanhang.entity;

public class OrderItem {
	public OrderItem(int productId, int number, long price, String name) {
		super();
		this.productId = productId;
		this.number = number;
		this.price = price;
		this.name = name;
	}
	public OrderItem() {
		
	}
	private int productId;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private int number;
	private long price;
	private String name;
}
