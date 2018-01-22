package cn.bupt.smartyagl.controller.inf;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.bupt.smartyagl.dao.autogenerate.UserMapper;
import cn.bupt.smartyagl.entity.autogenerate.User;
import cn.bupt.smartyagl.service.IUserService;
import cn.bupt.smartyagl.util.IPUtil;
import cn.bupt.smartyagl.util.NetDataAccessUtil;

@Controller("UserinfoController")
@RequestMapping("interface/user")
public class UserinfoController {
	@Autowired
	IUserService userService;
	@Autowired
	UserMapper userMapper;
	
	/**
	 * 查看用户信息
	 */
	@RequestMapping(value = "/userInfo")
	@ResponseBody
	public Object userInfo(HttpServletRequest request) {
		Integer userId = (Integer) request.getAttribute("userId");
		// userId = 1;
		User user = userService.getUserInfoById(userId);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if (user == null) {
			nau.setContent(null);
			nau.setResult(0);
			nau.setResultDesp("该用户不存在");
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", user.getName());
			map.put("id", user.getId());
			map.put("phone", user.getPhone());
			if (user.getPortrait() == null) {
				// user.setPortrait("portrait/" + default_image);
				map.put("portrait", "http://" + IPUtil.getIpAddr(request) + ":"
						+ request.getLocalPort()
						+ "/SmartyAgriculture/data/upload/portrait/"
						+ "default_image/" + "ic_default_portrait.png");
			} else {
				map.put("portrait",
						"http://" + IPUtil.getIpAddr(request) + ":"
								+ request.getLocalPort()
								+ "/SmartyAgriculture/data/upload/"
								+ user.getPortrait());
			}
			map.put("token", request.getParameter("token"));
			map.put("money", user.getMoney());
			map.put("score", user.getScore());
			map.put("openId", user.getOpenId());
			map.put("deviceType", user.getDeviceType());
			map.put("deviceId", user.getDeviceId());
			map.put("lastLoginTime", user.getLastLoginTime());
			map.put("createTime", user.getCreateTime());
			nau.setContent(map);
			nau.setResult(1);
			nau.setResultDesp("查询用户成功");
		}
		return nau;
	}

	// 修改用户信息@zxy
	@RequestMapping(value = "/changeuserInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object changeuserinfo(HttpServletRequest request, User user)
			throws IllegalStateException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		NetDataAccessUtil nau = new NetDataAccessUtil();
		Integer userId = (Integer) request.getAttribute("userId");
			
		user.setId(userId);

		// 设置上下方文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		// 检查form是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {

				// 由CommonsMultipartFile继承而来,拥有上面的方法.
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					file.getName();
					
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyyMMddHHmmsss");// 设置日期格式
//					System.out.println(df.format(new Date()));// new
																// Date()为获取当前系统时间
					String fileName = df.format(new Date());
//					fileName.setHeaderEncoding("UTF-8"); 
					// 后缀名不为jpg、png、jpeg、则改为jpg
					if ((!fileName.endsWith(".jpg"))
							|| !(fileName.endsWith(".png"))
							|| !(fileName.endsWith(".jpeg"))) {
						if (fileName.indexOf(".") >= 0) {

							fileName = fileName.substring(0,
									fileName.lastIndexOf("."));
						}

						fileName = (fileName + ".jpg"); // 后缀名不符合规则，改名
					}
					String prepath = request.getSession().getServletContext()
							.getRealPath("/");
//					System.out.println("地址1" + prepath);
					String rearpath = "data/upload/portrait/" + fileName;
					String path = prepath + rearpath;
//					System.out.println("文件地址" + path);
					user.setPortrait("portrait/" + fileName);
//					System.out.println("portrait/" + fileName);
					File localFile = new File(path);
					file.transferTo(localFile);
					break;
				}
			}
		}
		boolean rst = userService.updateMyuserInfo(user);
		if (rst) {
//				map.put("phone", user.getPhone());
			user = userService.getUserInfoById(userId);	
			map.put("name", user.getName());
			map.put("portrait", "http://" + IPUtil.getIpAddr(request) + ":"
					+ request.getLocalPort()
					+ "/upload/" + user.getPortrait());
			map.put("openId", user.getOpenId());
			map.put("deviceType", user.getDeviceType());
			map.put("deviceId", user.getDeviceId());
			nau.setContent(map);
			nau.setResult(1);
			nau.setResultDesp("修改用户信息成功");
		} else {
			nau.setResult(0);
			nau.setResultDesp("修改用户信息失败");
		}		
		return nau;
	}


//修改手机号@zxy
	@RequestMapping(value = "/changePhone")
	@ResponseBody
	public Object changePhone(HttpServletRequest request, User user,String phone)
			throws IllegalStateException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		NetDataAccessUtil nau = new NetDataAccessUtil();
		
		boolean ret = userService.phoneExisted(phone);
		if (ret==true) {		
			nau.setContent(null);
			nau.setResult(-1);
			nau.setResultDesp("手机号已经注册！");
		}
		else{
			Integer userId = (Integer) request.getAttribute("userId");	
			user.setId(userId);
			userMapper.updateByPrimaryKeySelective(user);
			user.setPhone(phone);
			map.put("phone", user.getPhone());
			nau.setContent(map);
			nau.setResult(1);
			nau.setResultDesp("修改手机号成功");
		}
		return nau;
	}
	
	//获取用户搜索历史
	@RequestMapping(value = "/searchHistory")
	@ResponseBody
	public Object searchHistory(HttpServletRequest request, User user)
			throws IllegalStateException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		NetDataAccessUtil nau = new NetDataAccessUtil();
		
		String searchHistory = user.getSearchHistory();
		if (searchHistory==null) {		
			nau.setContent(null);
			nau.setResult(-1);
			nau.setResultDesp("获取搜索历史失败！");
		}
		else{
			Integer userId = (Integer) request.getAttribute("userId");	
			user.setId(userId);
			userMapper.updateByPrimaryKeySelective(user);
//			user.setPhone(phone);
			map.put("searchHistory", user.getSearchHistory());
			nau.setContent(map);
			nau.setResult(1);
			nau.setResultDesp("获取搜索历史成功");
		}
		return nau;
	}
}