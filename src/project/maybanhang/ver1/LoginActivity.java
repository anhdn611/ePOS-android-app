package project.maybanhang.ver1;

import org.json.JSONArray;
import org.json.JSONObject;
import project.maybanhang.ApiDefiner;
import project.maybanhang.CustomHttpClient;
import project.maybanhang.DataSource;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	Button btnLogin;
	EditText usernameEditText;
	EditText EditTextPassword;
	TextView TextViewLoginMessage;
	Context context;
	Button btnTestShop;
	Button btnTestRestaurant;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		DataSource.ResetResource();
		btnTestShop = (Button) findViewById(R.id.btn_testshop);
		btnTestRestaurant = (Button) findViewById(R.id.btn_XepBan);
		btnLogin = (Button) findViewById(R.id.sign_in_button);
		usernameEditText = (EditText) findViewById(R.id.username);
		TextViewLoginMessage = (TextView) findViewById(R.id.login_status_message);
		EditTextPassword = (EditText) findViewById(R.id.password);
		
		context = this;
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				try {
					// Add your data
					String request_login = String.format(
							"%s?username=%s&password=%s", ApiDefiner.URL_LOGIN,
							usernameEditText.getText().toString(),
							EditTextPassword.getText().toString());
					LoginAsyncTask myAsynTask = new LoginAsyncTask();
					myAsynTask.execute(request_login);

				} catch (Exception ex) {
					TextViewLoginMessage.setText(ex.toString());
				}
			}
		});

	}
/*	
 * Send a request for Login*/
	private class LoginAsyncTask extends AsyncTask<String, String, String> {

		Intent intent;
		ProgressDialog waitDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			waitDialog = new ProgressDialog(getApplication());
			waitDialog.setCancelable(false);
			waitDialog.setTitle("Đang đăng nhập");
		}

		@Override
		protected String doInBackground(String... params) {
			String jsonResult = null;
			try {
				Log.d("Login Async Task", "Start Login");
				intent = new Intent();
				jsonResult = CustomHttpClient.executeHttpGet(params[0]);
				if (jsonResult.trim().equals(ApiDefiner.MESS_WRONG_USER_PASS))
					return ApiDefiner.MESS_WRONG_USER_PASS;
				if (jsonResult.equals(ApiDefiner.MESS_CONNECTION_ERROR))
					return ApiDefiner.MESS_CONNECTION_ERROR;
				JSONObject json_Personal_Info = new JSONObject(jsonResult);
				JSONArray jsArr = json_Personal_Info
						.getJSONArray(ApiDefiner.TAG_PERSON_INFO);

				JSONObject js = jsArr.getJSONObject(0).getJSONObject(
						ApiDefiner.TAG_USER);
				Bundle bundle = new Bundle();
				bundle.putString(ApiDefiner.TAG_USER_ID,
						js.getString(ApiDefiner.TAG_USER_ID));
				bundle.putString(ApiDefiner.TAG_FULL_NAME,
						js.getString(ApiDefiner.TAG_FULL_NAME));
				bundle.putString(ApiDefiner.TAG_GROUP_ID,
						js.getString(ApiDefiner.TAG_GROUP_ID));
				bundle.putString(ApiDefiner.TAG_SHOP_ID,
						js.getString(ApiDefiner.TAG_SHOP_ID));
				bundle.putString(ApiDefiner.TAG_SHOP_TYPE,
						js.getString(ApiDefiner.TAG_SHOP_TYPE));
				bundle.putString(ApiDefiner.TAG_BRANCH_ID,
						js.getString(ApiDefiner.TAG_BRANCH_ID));
				intent.putExtras(bundle);
				if (js.getString(ApiDefiner.TAG_SHOP_TYPE).equals(
						ApiDefiner.ID_SHOP)) {
					intent.setClass(context, ShopActivity.class);
				} else if (js.getString(ApiDefiner.TAG_SHOP_TYPE).equals(
						ApiDefiner.ID_COFFE)) {
					intent.setClass(context, RestaurantActivity.class);
				}

				Log.d("POS", "Login Async Task: OK");
				return "ok";
			} catch (Exception e) {
				//Log.e("Login:", e.getMessage());
				return ApiDefiner.MESS_CONNECTION_ERROR;
			}
		}

		protected void onPostExecute(String result) {
			if (result == "" || result.equals(ApiDefiner.MESS_WRONG_USER_PASS)
					|| result.equals(ApiDefiner.MESS_CONNECTION_ERROR))
				TextViewLoginMessage.setText(result);
			else {
				Log.d("POS", "Login Async Task: Start Main Activity");
				startActivity(intent);
			}
			waitDialog.dismiss();
		}

		protected void onProgressUpdate(String... progress) {
			TextViewLoginMessage.setText("Connecting..");
		}

	}
}
