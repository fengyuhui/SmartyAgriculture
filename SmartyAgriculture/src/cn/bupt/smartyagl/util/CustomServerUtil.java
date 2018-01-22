package cn.bupt.smartyagl.util;

import java.util.ArrayList;
import java.util.List;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.request.OpenimUsersGetRequest;
import com.taobao.api.response.OpenimUsersAddResponse;
import com.taobao.api.response.OpenimUsersGetResponse;

/** 
 * 牵牛客服服务类
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-7-20 上午10:20:49 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class CustomServerUtil {
	
	/**
	 * 创建用户
	 * @param userName
	 * @param passwd
	 */
	public static void createUser(String userName,String passwd){
		propertiesUtil.changePath("customService.properties");
		TaobaoClient client = new DefaultTaobaoClient(propertiesUtil.getValue("url"), propertiesUtil.getValue("appKey"), propertiesUtil.getValue("appSecret"));
		OpenimUsersAddRequest req = new OpenimUsersAddRequest();
		List<Userinfos> list2 = new ArrayList<Userinfos>();
		Userinfos obj3 = new Userinfos();
		list2.add(obj3);
		obj3.setUserid(userName);
		obj3.setPassword(passwd);
		req.setUserinfos(list2);
		OpenimUsersAddResponse rsp = null;
		try {
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据userIds获取用户信息
	 */
	public static void getUserMessage(String userIds){
		propertiesUtil.changePath("customService.properties");
		TaobaoClient client = new DefaultTaobaoClient(propertiesUtil.getValue("url"), propertiesUtil.getValue("appKey"), propertiesUtil.getValue("appSecret"));
//		//获取账户信息
		OpenimUsersGetRequest req = new OpenimUsersGetRequest();
		req.setUserids(userIds);
		OpenimUsersGetResponse rsp;
		try {
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws ApiException {
		//创建用户
		propertiesUtil.changePath("customService.properties");
		TaobaoClient client = new DefaultTaobaoClient(propertiesUtil.getValue("url"), propertiesUtil.getValue("appKey"), propertiesUtil.getValue("appSecret"));
		OpenimUsersAddRequest req = new OpenimUsersAddRequest();
		List<Userinfos> list2 = new ArrayList<Userinfos>();
		Userinfos obj3 = new Userinfos();
		list2.add(obj3);
		obj3.setUserid("123521");
		obj3.setPassword("123456");
		obj3.setIconUrl("http://img3.imgtn.bdimg.com/it/u=1302073421,728475412&fm=21&gp=0.jpg");
		req.setUserinfos(list2);
		OpenimUsersAddResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
//		//获取账户信息
//		OpenimUsersGetRequest req = new OpenimUsersGetRequest();
//		req.setUserids("test");
//		OpenimUsersGetResponse rsp = client.execute(req);
//		System.out.println(rsp.getBody());
	}
}
