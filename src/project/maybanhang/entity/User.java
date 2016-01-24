package project.maybanhang.entity;

import project.maybanhang.ApiDefiner;
import android.os.Bundle;
import android.util.Log;

public class User {
	String user_id;
	String group_id;
	String shop_id;
	String shop_type;
	String branch_id;
	String username;
	String email;
	String full_name;
	String first_name;
	String last_name;
	String password;
	String created_date;
	boolean enabled;
	String last_login_date;
	String security_question;
	String security_answer;
	String active_code;
	String active_expired_date;
	String forgot_password_code;
	String forgot_password_expired_date;
	String admin_note;
	String sorting;
	
	public User(Bundle bundle) {
		full_name=bundle.getString(ApiDefiner.TAG_FULL_NAME);
		user_id=bundle.getString(ApiDefiner.TAG_USER_ID);
		group_id=bundle.getString(ApiDefiner.TAG_GROUP_ID);
		shop_id=bundle.getString(ApiDefiner.TAG_SHOP_ID);
		shop_type=bundle.getString(ApiDefiner.TAG_SHOP_TYPE);
		branch_id=bundle.getString(ApiDefiner.TAG_BRANCH_ID);
		Log.d("Login", "user "+ full_name+" đã được khởi tạo");
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getShop_type() {
		return shop_type;
	}
	public void setShop_type(String shop_type) {
		this.shop_type = shop_type;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getLast_login_date() {
		return last_login_date;
	}
	public void setLast_login_date(String last_login_date) {
		this.last_login_date = last_login_date;
	}
	public String getSecurity_question() {
		return security_question;
	}
	public void setSecurity_question(String security_question) {
		this.security_question = security_question;
	}
	public String getSecurity_answer() {
		return security_answer;
	}
	public void setSecurity_answer(String security_answer) {
		this.security_answer = security_answer;
	}
	public String getActive_code() {
		return active_code;
	}
	public void setActive_code(String active_code) {
		this.active_code = active_code;
	}
	public String getActive_expired_date() {
		return active_expired_date;
	}
	public void setActive_expired_date(String active_expired_date) {
		this.active_expired_date = active_expired_date;
	}
	public String getForgot_password_code() {
		return forgot_password_code;
	}
	public void setForgot_password_code(String forgot_password_code) {
		this.forgot_password_code = forgot_password_code;
	}
	public String getForgot_password_expired_date() {
		return forgot_password_expired_date;
	}
	public void setForgot_password_expired_date(String forgot_password_expired_date) {
		this.forgot_password_expired_date = forgot_password_expired_date;
	}
	public String getAdmin_note() {
		return admin_note;
	}
	public void setAdmin_note(String admin_note) {
		this.admin_note = admin_note;
	}
	public String getSorting() {
		return sorting;
	}
	public void setSorting(String sorting) {
		this.sorting = sorting;
	}
	
}
