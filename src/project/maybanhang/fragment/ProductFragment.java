package project.maybanhang.fragment;

import java.util.ArrayList;

import project.maybanhang.ApiDefiner;
import project.maybanhang.DataSource;
import project.maybanhang.adapters.ShopItemAdapter;
import project.maybanhang.entity.Category;
import project.maybanhang.entity.ItemInfo;
import project.maybanhang.entity.OrderItem;
import project.maybanhang.entity.QuickKeyProduct;
import project.maybanhang.ver1.R;
import project.maybanhang.ver1.ShopActivity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductFragment extends Fragment {
	GridView gridViewProduct;
	String shopID;
	Button btn_all;
	
	TextView tvCategoryName;
	boolean onAdd;
	ArrayList<ItemInfo> items=new ArrayList<ItemInfo>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_product,
				container, false);
		gridViewProduct = (GridView) view.findViewById(
				R.id.gridview_products);
		btn_all =( Button) view.findViewById(R.id.btn_all);
		tvCategoryName=(TextView) view.findViewById(R.id.current_category_name);
		
		gridViewProduct.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {	
				if(onAdd) return;
				onAdd=true;
				//AddToOrder(m.get(position));
			}
			
		});
		
		
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		Bundle bundle=getArguments();
		
		for (Category cate : DataSource.sCategories) {
			if(cate.Product_category_id.equals(bundle.getString(ApiDefiner.TAG_CATEGROY_ID))){
				tvCategoryName.setText(cate.Name);
				items=cate.Items;
				ShopItemAdapter shopItemAdapterAdapter = new ShopItemAdapter(
						getActivity(), cate.Items);
				Log.d("Product Activity","Number of product "+ cate.Items.size());
				gridViewProduct.setAdapter(shopItemAdapterAdapter);
				return;
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		btn_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ShopActivity parent = (ShopActivity) getActivity();
				parent.fragmentTransaction= parent.fragmentManager.beginTransaction();
				parent.fragmentTransaction.replace(R.id.container,
						parent.categoryFragment);
				parent.fragmentTransaction.commit();
			}
		});
	}
	
	private void AddToOrder(final QuickKeyProduct item)
	{
		LayoutInflater li = LayoutInflater.from(getActivity());
		View inputView = li.inflate(R.layout.dialog_input_quantity, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

		// set input_quatity to alertdialog builder
		alertDialogBuilder.setView(inputView);

		final EditText userInput = (EditText) inputView
				.findViewById(R.id.editTextQuantity);

		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("�?ặt",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    	/// Check value of edit text
			    	boolean isValidInput=false;
					try{
						int number=Integer.parseInt(userInput.getText().toString());
						if(number>0)
							isValidInput=true;
					}catch (Exception e){
						isValidInput=false;
					}
					if(!isValidInput){
						Toast.makeText(getActivity(), "Nhập số lớn hơn 0", Toast.LENGTH_SHORT).show();
						onAdd = false;
						return;
					}
			    	OrderItem newOrder=new OrderItem();
			    	ShopActivity shopActivity=(ShopActivity)getActivity();
			    	boolean isInList=false;
			    	for (OrderItem element : shopActivity.order_items) {
						if(element.getName().equals(item.getAlias())){
							element.setNumber(element.getNumber() + Integer.parseInt(userInput.getText().toString()));
							element.setPrice(element.getNumber()* item.getRetailPrice());
							isInList=true;
							break;
						}
					}
			    	if(!isInList){
			    	newOrder.setName(item.getAlias());			    	
			    	newOrder.setProductId(item.getProductId());
			    	newOrder.setNumber(Integer.parseInt(userInput.getText().toString()));
			    	newOrder.setPrice(newOrder.getNumber()* item.getRetailPrice());
					shopActivity.order_items.add(newOrder);					
					
			    	}
			    	shopActivity.orderListAdapter.notifyDataSetChanged();
			    	shopActivity.UpdateOrder();
					Toast.makeText(shopActivity, "�?ã thêm " +userInput.getText().toString()+" "+ newOrder.getName()+"vào đơn hàng." , Toast.LENGTH_SHORT).show();
					onAdd=false;
			    }
			  })
			.setNegativeButton("Hủy",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.dismiss();
				onAdd=false;
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	
	}
}