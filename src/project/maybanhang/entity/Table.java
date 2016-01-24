package project.maybanhang.entity;

public class Table {
	public int table_id;
	public int place_id;
	public String name;
	public int status;
	public String combine;
	public int order_id;
	public boolean isCurrentActive;
	public boolean isGopBanSelected;
	public boolean isBanCanChuyen;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id = "+table_id+", name = "+ name+", order_id= "+order_id;
	}
}
