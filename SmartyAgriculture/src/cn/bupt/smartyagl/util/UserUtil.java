package cn.bupt.smartyagl.util;

import java.util.HashMap;
import java.util.Map;

import cn.bupt.smartyagl.entity.autogenerate.User;

public class UserUtil {
	/**
	 * 生成token值
	 * @param phone 电话号码
	 * @param lastLoginTime 最后登录时间
	 * @return
	 */
	public static String generateToken(String phone, String lastLoginTime) {
		String token =MD5Util.MD5(phone + lastLoginTime);
		return token;
	}
	/**
	 * 通过token值获取用户id
	 * @param phone
	 * @param lastLoginTime
	 * @return token存在，则返回用户id;否则，返回-1;
	 */
	public static int getUserIdByToken(String key) {
		boolean res = RedisUtil.exist(key);
		if(res){
			int userId = Integer.parseInt((String)RedisUtil.get(key));
			return userId;
		}else{
			return -1;
		}
	}
	/**
	 * 通过user设置接口返回用户数据的结构体
	 * @param user 用户对象
	 */
	public static Map<String,Object> constructUserResp(User User,String token){
        Map<String,Object> content = new HashMap<String,Object>();
        content.put("id", User.getId());
        content.put("token", token);
        content.put("name", User.getName());
        content.put("phone", User.getPhone());
        content.put("portrait",User.getPortrait());
        content.put("money", User.getMoney());
        content.put("score", User.getScore());
        content.put("openId", User.getOpenId());
        content.put("deviceId",User.getDeviceId());
        content.put("deviceType", User.getDeviceType());
        content.put("lastLoginTime",User.getLastLoginTime());
        content.put("createTime", User.getCreateTime());
        return content;
	}
}
