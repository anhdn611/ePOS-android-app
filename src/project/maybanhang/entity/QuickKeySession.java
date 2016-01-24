package project.maybanhang.entity;

public class QuickKeySession {
	/*
	 * 
	 * quickkey_session_id: "78", name: "IPHONE", quickkey_id: "41", sorting:
	 * "1", created_date: "1399868977"
	 */
	private int id;
	private String name;

	public QuickKeySession(int id, String name, String sorting,
			String dateString) {
		super();
		this.id = id;
		this.name = name;
		this.sorting = sorting;
		this.dateString = dateString;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSorting() {
		return sorting;
	}

	public void setSorting(String sorting) {
		this.sorting = sorting;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	private String sorting;
	private String dateString;
}
