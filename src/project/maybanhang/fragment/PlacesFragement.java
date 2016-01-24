package project.maybanhang.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import project.maybanhang.ApiDefiner;
import project.maybanhang.CustomHttpClient;
import project.maybanhang.adapters.PlacesAdapter;
import project.maybanhang.entity.Place;
import project.maybanhang.ver1.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class PlacesFragement extends Fragment {
	
	GridView gridViewPlaces;
	ArrayList<Place> places=new ArrayList<Place>();
	String shopID;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_places,
				container, false);
		Bundle bundle=getArguments();
		shopID=bundle.getString(ApiDefiner.TAG_SHOP_ID);
		if(places.size() == 0){
		PlaceAsyncTask placeAsyncTask= new PlaceAsyncTask();		
		placeAsyncTask.execute(bundle.getString(ApiDefiner.TAG_SHOP_ID),
				bundle.getString(ApiDefiner.TAG_BRANCH_ID));
		}
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		gridViewPlaces = (GridView)getView().findViewById(R.id.gridview_places);
		PlacesAdapter placesAdapter=new PlacesAdapter(getActivity(), places);
		gridViewPlaces.setAdapter(placesAdapter);
		
		gridViewPlaces.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				FragmentTransaction fragmentTransaction =getActivity().getFragmentManager().beginTransaction();
				Bundle bundle=new Bundle();
				bundle.putInt(ApiDefiner.TAG_PLACE_ID, places.get(position).Place_id);
				bundle.putString(ApiDefiner.TAG_NAME,places.get(position).Name);
				bundle.putString(ApiDefiner.TAG_SHOP_ID, shopID);	
				RestaurantFragment restaurantFragment=new RestaurantFragment();
				restaurantFragment.setArguments(bundle);
				fragmentTransaction.replace(R.id.container, restaurantFragment);
				fragmentTransaction.commit();
			}
		});
	}
	
	private class PlaceAsyncTask extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			String jsonResult = null;
			try {
				String path=ApiDefiner.URL_KHUVUC_BAN+"?shop_id="+params[0]+"&branch_id="+params[1];
				Log.d("Restaurant. Async Task", "Lấy các Khu vực bàn: "+path);
				jsonResult = CustomHttpClient.executeHttpGet(path);
				
				JSONObject root=new JSONObject(jsonResult);
				
				JSONArray jsArr = root.getJSONArray(ApiDefiner.TAG_All_PLACES);
				for (int i = 0; i < jsArr.length(); i++) {
					JSONObject temp=jsArr.getJSONObject(i).getJSONObject(ApiDefiner.TAG_PLACE);
					Place tempPlace=new Place();					
					tempPlace.Name = temp.getString(ApiDefiner.TAG_NAME);					
					tempPlace.Place_id = temp.getInt(ApiDefiner.TAG_PLACE_ID);
					Log.d("Restaurant. Async Task",tempPlace.toString());
					places.add(tempPlace);
				}
				return jsonResult;
			} catch (Exception e) {
				Log.e("Restaurant. Async Task", e.getMessage());
				return ApiDefiner.MESS_CONNECTION_ERROR;
			}
		}

		protected void onPostExecute(String result) {
			
		
		}

		protected void onProgressUpdate(String... progress) {

		}
	}

}
