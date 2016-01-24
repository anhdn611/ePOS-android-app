package project.maybanhang.entity;

import java.util.ArrayList;

public class GroupCustomer {
	private String Group_name;
	public String Id;
	
	public ArrayList<Customer> Customers=new ArrayList<Customer>();
	public String getGroup_name() {
		return Group_name;
	}
	public void setGroup_name(String group_name) {
		if(group_name == "null") group_name="";
		Group_name = group_name;
	}
}
