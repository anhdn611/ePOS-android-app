package project.maybanhang.entity;

public class Customer {
	public String Customer_id;
	public String Groupname;
	private String First_name;
	private String Last_name;
	public String Address;
	public String City;
	public String Country;
	public String Gender;
	public String Birthday;
	public String Phone;
	public String Fax;
	public String Mobile;
	public String Email;
	public String Website;
	public String Company;
	public String Code;
	public String Created_date;
	public String Tax_id;
	public String Bought;
	public String Group_customer_id;

	public Customer() {
		First_name = "Khách lẻ";
		Last_name = "";
	}

	public String getFirst_name() {
		return First_name;
	}

	public void setFirst_name(String first_name) {
		if (first_name.equals("null"))
			first_name = "";
		First_name = first_name;
	}

	public String getLast_name() {

		return Last_name;
	}

	public void setLast_name(String last_name) {
		if (last_name.equals("null"))
			last_name = "";
		Last_name = last_name;
	}

}
