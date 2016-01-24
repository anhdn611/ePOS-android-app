package project.maybanhang;

import java.util.ArrayList;

import project.maybanhang.entity.Bill;
import project.maybanhang.entity.Category;
import project.maybanhang.entity.Customer;
import project.maybanhang.entity.GroupCustomer;
import project.maybanhang.entity.ItemInfo;
import project.maybanhang.entity.OrderItem;
import project.maybanhang.entity.Place;
import project.maybanhang.entity.QuickKey;
import project.maybanhang.entity.Table;

public class DataSource {
	
	public static ArrayList<GroupCustomer> sGroupsCustomer = new ArrayList<GroupCustomer>();
	public static ArrayList<Category> sCategories = new ArrayList<Category>();
	private static ArrayList<ItemInfo> sAllItemInfo = new ArrayList<ItemInfo>();
	public static Bill currentBill = null;
	public static void Refresh() {
		sAllItemInfo.clear();
		for (Category cate : sCategories) {
			sAllItemInfo.addAll(cate.Items);
		}
	}

	public static ArrayList<ItemInfo> getAllItem() {
		return sAllItemInfo;
	}

	public static Table GetSelectedTable() {
		
		for (Place itemPlace : sPlaces) {
			for (Table table_ement : itemPlace.Tables) {
				if (table_ement.isCurrentActive) {
					return table_ement;
				}
			}
		}
		
		return null;
	}
	
	
	public static Table sSelectedTable;

	public static ArrayList<Place> sPlaces = new ArrayList<Place>();
	// Vị trí của tầng đang xem
	public static int IndexCurrentPlace = 0;
	// Khách hàng được chọn
	public static Customer sSelectedCustomer = new Customer();

	public static void ResetResource() {
		sGroupsCustomer = new ArrayList<GroupCustomer>();
		sCategories = new ArrayList<Category>();
		sAllItemInfo = new ArrayList<ItemInfo>();
		sPlaces = new ArrayList<Place>();
		IndexCurrentPlace = 0;
		sSelectedCustomer = new Customer();
	}
	public static void ClearAllPlace(){
		sPlaces=new ArrayList<Place>();
	}
	public static OrderItem sCurrentOrder=new OrderItem();
}
