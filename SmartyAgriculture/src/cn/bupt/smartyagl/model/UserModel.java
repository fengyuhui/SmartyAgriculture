package cn.bupt.smartyagl.model;

import cn.bupt.smartyagl.entity.autogenerate.User;

public class UserModel extends User{

	private static  String newPasswd1;
    private static String newPasswd2;

    private String createTimeString;//注册的时间
    public User userInfo;//用户类
    
    
    public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	public static  String getNewPasswd1() {
		return newPasswd1;
	}

	public void setNewPasswd1(String newPasswd1) {
		UserModel.newPasswd1 = newPasswd1;
	}

	public  static String getNewPasswd2() {
		return newPasswd2;
	}

	public void setNewPasswd2(String newPasswd2) {
		UserModel.newPasswd2 = newPasswd2;
	}
}
