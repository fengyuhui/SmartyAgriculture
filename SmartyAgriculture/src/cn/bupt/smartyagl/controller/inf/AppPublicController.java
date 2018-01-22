package cn.bupt.smartyagl.controller.inf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.User;
import cn.bupt.smartyagl.entity.autogenerate.UserExample;
import cn.bupt.smartyagl.entity.autogenerate.UserExample.Criteria;
import cn.bupt.smartyagl.model.UserModel;
import cn.bupt.smartyagl.service.IUserService;
import cn.bupt.smartyagl.util.CustomServerUtil;
import cn.bupt.smartyagl.util.IPUtil;
import cn.bupt.smartyagl.util.MD5Util;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.RedisUtil;
import cn.bupt.smartyagl.util.SslUtils;
import cn.bupt.smartyagl.util.UserUtil;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/publicApp")
public class AppPublicController extends AppBaseController {
	
	@Autowired
	IUserService userService;


	@RequestMapping("/login")
	@ResponseBody
	public Object login(HttpServletRequest request,User User) {

		NetDataAccessUtil netDataAccessUtil = new NetDataAccessUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式   
		if ("GET".equals(request.getMethod()) || null == User) {
			netDataAccessUtil.setContent(null);
			netDataAccessUtil.setResult(-3);
			netDataAccessUtil.setResultDesp("请求错误！");
		} else 
		{
			UserExample userEx=new UserExample();
			Criteria criteria=userEx.createCriteria();
			criteria.andPhoneEqualTo(User.getPhone());
			criteria.andPhoneIsNotNull();
			List<User> userList = userService.getUserByUserExample(userEx);
			if (userList == null || 0 == userList.size()) {
				//构造返回json对象
				netDataAccessUtil.setContent(null);
				netDataAccessUtil.setResult(-1);
				netDataAccessUtil.setResultDesp("该用户不存在！");
				
			} else 
			{
				if (!userList.get(0).getPasswd()
						.equals(MD5Util.MD5(User.getPasswd()))) {
					//构造返回json对象
					netDataAccessUtil.setContent(null);
					netDataAccessUtil.setResult(-2);
					netDataAccessUtil.setResultDesp("密码错误！");
				} else {
					//登录成功，清除之前的token信息
					Jedis redis = RedisUtil.getJedis();
					String lastLoginTime = df.format(userList.get(0).getLastLoginTime());
			        String key = UserUtil.generateToken(userList.get(0).getPhone(),lastLoginTime);
			        if( redis.exists(key)){
				        redis.del(key);
				        RedisUtil.returnResource(redis);
			        }
			        // 登录时间
			        Date loginTime = new Date();
			        String loginTimeStr = df.format(loginTime);
			        //更新登录时间到用户表中
			        userService.updateLoginTime(userList.get(0).getId(),loginTime);
			        //生成token,存token到redis
			        String token =UserUtil.generateToken(userList.get(0).getPhone(),loginTimeStr);
			        RedisUtil.set(token,userList.get(0).getId());
			        //拼接用户图像的完整路劲
			        userList.get(0).setPortrait("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+userList.get(0).getPortrait());
			        //返回信息
					netDataAccessUtil.setContent(UserUtil.constructUserResp(userList.get(0),token));
					netDataAccessUtil.setResult(1);
			        netDataAccessUtil.setResultDesp("登录成功");
			        }
				}
			}
		return netDataAccessUtil;
	}	  	
	
	/**
	 * 通过微信或QQ平台注册	 
	 * @author zxy 	 
	 * @param UserService  	 
	 * @param registBy	 
	 * @return 	
	 * @throws Exception 
	 */
	
	@RequestMapping(value="/registBy", method = RequestMethod.POST)
	@ResponseBody
	public Object registBy(HttpServletRequest request,User User,String nickName, String imgurl)
			throws Exception{

		NetDataAccessUtil netDataAccessUtil = new NetDataAccessUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式   
		UserExample userEx=new UserExample();
		Criteria criteria=userEx.createCriteria();
//		criteria.andPhoneEqualTo(User.getPhone());
//		criteria.andPhoneIsNotNull();
		criteria.andOpenIdEqualTo(User.getOpenId());
		criteria.andOpenIdIsNotNull();
		criteria.andOpenidTypeEqualTo(User.getOpenidType());
		criteria.andOpenidTypeIsNotNull();
		List<User> userList = userService.getUserByUserExample(userEx);
		if ("GET".equals(request.getMethod()) || null == User) {
			netDataAccessUtil.setContent(null);
			netDataAccessUtil.setResult(-3);//密码不是必填项
			netDataAccessUtil.setResultDesp("请求错误！");
		} if(User.getOpenidType()==null){
		    netDataAccessUtil.setContent(null);
            netDataAccessUtil.setResult(-2);
            netDataAccessUtil.setResultDesp("请选择第三方登录类型！");
		} 
		else {
			if (userList == null || 0 == userList.size()) {
				//app端传入昵称
				User.setName(nickName);
//				User.setPhone(phone);
				SimpleDateFormat dfe = new SimpleDateFormat("yyyyMMddHHmmsss");// 设置日期格式
////            System.out.println(df.format(new Date()));// new
//              bi = dfe.format(new Date())+ bi.getOriginalFilename();
//				String prepath = request.getSession().getServletContext()
//                        .getRealPath("");
//                String rearpath = "upload/portrait/" ;
                String path = request.getSession().getServletContext().getRealPath("")+"/../"
							+ Constants.FILE_PATH;
                
	            URL url = null;
//	            if("https".equalsIgnoreCase(url.getProtocol())){
//	                SslUtils.ignoreSsl();
//	            }
	            SslUtils.ignoreSsl();
	            try {
	                url = new URL(imgurl);
	            } catch (MalformedURLException e2) {
	                e2.printStackTrace();
	            }
	            InputStream is = null;
	            try {
	                is = url.openStream();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	            OutputStream os = null;
	            
	            File f = new File(path);
	            f.mkdirs();
	            try{
	                
	              System.out.println("文件地址=:" + path);
	                os = new FileOutputStream(path+dfe.format(new Date())+ ".jpg");
//	                portraitName = (portraitName + ".jpg"); // 后缀名不符合规则，改名
	             
	              User.setPortrait("portrait/" + dfe.format(new Date())+ ".jpg");
//	            System.out.println("portrait/" + fileName);
//	              File localFile = new File(path);
//	              ((MultipartFile) f).transferTo(localFile);
	                int bytesRead = 0;
	                byte[] buffer = new byte[8192];
	                while((bytesRead = is.read(buffer,0,8192))!=-1){
	                    os.write(buffer,0,bytesRead);}
	                }catch(FileNotFoundException e){ 
	                    
	                }catch (IOException e) {
	                  e.printStackTrace();
	                  }
				
				//设置用户类别
				User.setUserType(1);
				//用户积分
				User.setScore(0);
				//用户金额
				User.setMoney(0.0);;
				//用户状态
				User.setStatus(0);
				//登录时间
				Date loginTimeTemp = new Date();
		        User.setLastLoginTime(loginTimeTemp);
		        User.setCreateTime(loginTimeTemp);
		        //给密码加密
		        User.setPasswd(MD5Util.MD5(User.getPasswd()));
		        //向数据库中插入一条用户记录
				int res = userService.insertUser(User);
				if(1==res){
					if(User.getName()==null){
						User.setName(User.getOpenId());
//						User.setOpenidType(User.getOpenidType());
					}
					//获取该用户的id
					User newUser = userService.getUserByUserExample(userEx).get(0);
					int userId = newUser.getId();
			        String loginTime = df.format(loginTimeTemp);
			        //生成token,存token到redis
			        String token =UserUtil.generateToken(User.getPhone(),loginTime);
			        RedisUtil.set(token,userId);
			        //返回消息
			      //拼接用户图像的完整路劲
			        newUser.setPortrait("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+newUser.getPortrait());					
					netDataAccessUtil.setContent(UserUtil.constructUserResp(newUser,token));
					netDataAccessUtil.setResult(1);
					netDataAccessUtil.setResultDesp("第三方注册成功！");
				}else{
					 //返回消息
					netDataAccessUtil.setContent(null);
					netDataAccessUtil.setResult(-2);
					netDataAccessUtil.setResultDesp("第三方注册失败！");
				}
			} else {
				netDataAccessUtil.setContent(0);
				netDataAccessUtil.setResult(0);
		        netDataAccessUtil.setResultDesp("该openId已经注册，请选择登录");
				
		        }
			}
				
		return netDataAccessUtil;
				
			
	}
	
	/**
	 * 通过微信或QQ平台注册	 
	 * @author zxy 	 
	 * @param UserService  	 
	 * @param registBy	 
	 * @return 	
	 * @throws Exception 
	 */
	
	@RequestMapping(value="/registByIos", method = RequestMethod.POST)
	@ResponseBody
	public Object registByIos(HttpServletRequest request,User User,String nickName, String imgurl)
			throws Exception{

		NetDataAccessUtil netDataAccessUtil = new NetDataAccessUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式   
		UserExample userEx=new UserExample();
		Criteria criteria=userEx.createCriteria();
//		criteria.andPhoneEqualTo(User.getPhone());
//		criteria.andPhoneIsNotNull();
		criteria.andOpenIdEqualTo(User.getOpenId());
		criteria.andOpenIdIsNotNull();
		criteria.andOpenidTypeEqualTo(User.getOpenidType());
		criteria.andOpenidTypeIsNotNull();
		List<User> userList = userService.getUserByUserExample(userEx);
		if ("GET".equals(request.getMethod()) || null == User) {
			netDataAccessUtil.setContent(null);
			netDataAccessUtil.setResult(-3);//密码不是必填项
			netDataAccessUtil.setResultDesp("请求错误！");
		} if(User.getOpenidType()==null){
		    netDataAccessUtil.setContent(null);
            netDataAccessUtil.setResult(-2);
            netDataAccessUtil.setResultDesp("请选择第三方登录类型！");
		} 
		else {
			if (userList == null || 0 == userList.size()) {
				//app端传入昵称
				User.setName(nickName);
//				User.setPhone(phone);
				SimpleDateFormat dfe = new SimpleDateFormat("yyyyMMddHHmmsss");// 设置日期格式
////            System.out.println(df.format(new Date()));// new
//              bi = dfe.format(new Date())+ bi.getOriginalFilename();
//				String prepath = request.getSession().getServletContext()
//                        .getRealPath("");
//                String rearpath = "upload/portrait/" ;
                String path = request.getSession().getServletContext().getRealPath("")+"/../"
							+ Constants.FILE_PATH;
                
	            URL url = null;
//	            if("https".equalsIgnoreCase(url.getProtocol())){
//	                SslUtils.ignoreSsl();
//	            }
	            SslUtils.ignoreSsl();
	            try {
	                url = new URL(imgurl);
	            } catch (MalformedURLException e2) {
	                e2.printStackTrace();
	            }
	            InputStream is = null;
	            try {
	                is = url.openStream();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	            OutputStream os = null;
	            
	            File f = new File(path);
	            f.mkdirs();
	            try{
	                
	              System.out.println("文件地址=:" + path);
	                os = new FileOutputStream(path+dfe.format(new Date())+ ".jpg");
//	                portraitName = (portraitName + ".jpg"); // 后缀名不符合规则，改名
	             
	              User.setPortrait("portrait/" + dfe.format(new Date())+ ".jpg");
//	            System.out.println("portrait/" + fileName);
//	              File localFile = new File(path);
//	              ((MultipartFile) f).transferTo(localFile);
	                int bytesRead = 0;
	                byte[] buffer = new byte[8192];
	                while((bytesRead = is.read(buffer,0,8192))!=-1){
	                    os.write(buffer,0,bytesRead);}
	                }catch(FileNotFoundException e){ 
	                    
	                }catch (IOException e) {
	                  e.printStackTrace();
	                  }
				
				//设置用户类别
				User.setUserType(1);
				//用户积分
				User.setScore(0);
				//用户金额
				User.setMoney(0.0);;
				//用户状态
				User.setStatus(0);
				//登录时间
				Date loginTimeTemp = new Date();
		        User.setLastLoginTime(loginTimeTemp);
		        User.setCreateTime(loginTimeTemp);
		        //给密码加密
		        User.setPasswd(MD5Util.MD5(User.getPasswd()));
		        //向数据库中插入一条用户记录
				int res = userService.insertUser(User);
				if(1==res){
					if(User.getName()==null){
						User.setName(User.getOpenId());
//						User.setOpenidType(User.getOpenidType());
					}
					//获取该用户的id
					User newUser = userService.getUserByUserExample(userEx).get(0);
					int userId = newUser.getId();
			        String loginTime = df.format(loginTimeTemp);
			        //生成token,存token到redis
			        String token =UserUtil.generateToken(User.getPhone(),loginTime);
			        RedisUtil.set(token,userId);
			        //返回消息
			      //拼接用户图像的完整路劲
			        newUser.setPortrait("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+newUser.getPortrait());					
					netDataAccessUtil.setContent(UserUtil.constructUserResp(newUser,token));
					netDataAccessUtil.setResult(1);
					netDataAccessUtil.setResultDesp("第三方注册成功！");
				}else{
					 //返回消息
					netDataAccessUtil.setContent(null);
					netDataAccessUtil.setResult(-2);
					netDataAccessUtil.setResultDesp("第三方注册失败！");
				}
			} else {
//				netDataAccessUtil.setContent(0);
//				netDataAccessUtil.setResult(0);
//		        netDataAccessUtil.setResultDesp("该openId已经注册，请选择登录");
				//登录成功，清除之前的token信息
				Jedis redis = RedisUtil.getJedis();
				String lastLoginTime = df.format(userList.get(0).getLastLoginTime());
		        String key = UserUtil.generateToken(userList.get(0).getPhone(),lastLoginTime);//没有手机号没有token
		        if( redis.exists(key)){
			        redis.del(key);
			        RedisUtil.returnResource(redis);
		        }
		        // 登录时间
		        Date loginTime = new Date();
		        String loginTimeStr = df.format(loginTime);
		        //更新登录时间到用户表中
		        userService.updateLoginTime(userList.get(0).getId(),loginTime);
		        //生成token,存token到redis
		        String token =UserUtil.generateToken(userList.get(0).getPhone(),loginTimeStr);
		        RedisUtil.set(token,userList.get(0).getId());
		        //拼接用户图像的完整路劲
		        userList.get(0).setPortrait("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+userList.get(0).getPortrait());
		        //返回信息
		        netDataAccessUtil.setContent(UserUtil.constructUserResp(userList.get(0),token));
				netDataAccessUtil.setResult(1);
		        netDataAccessUtil.setResultDesp("登录成功");
		        }
			}
				
		return netDataAccessUtil;
				
			
	}
	
	@RequestMapping(value="/registByOther", method = RequestMethod.POST)
	@ResponseBody
	public Object registByOther(HttpServletRequest request,User User,String nickName, String imgurl)
			throws Exception{

		NetDataAccessUtil netDataAccessUtil = new NetDataAccessUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式   
		UserExample userEx=new UserExample();
		Criteria criteria=userEx.createCriteria();
		criteria.andOpenIdEqualTo(User.getOpenId());
		criteria.andOpenIdIsNotNull();
		criteria.andOpenidTypeEqualTo(User.getOpenidType());
		criteria.andOpenidTypeIsNotNull();
		List<User> userList = userService.getUserByUserExample(userEx);
		if ("GET".equals(request.getMethod()) || null == User) {
			netDataAccessUtil.setContent(null);
			netDataAccessUtil.setResult(-3);//密码不是必填项
			netDataAccessUtil.setResultDesp("请求错误！");
		} if(User.getOpenidType()==null){
		    netDataAccessUtil.setContent(null);
            netDataAccessUtil.setResult(-2);
            netDataAccessUtil.setResultDesp("请选择第三方登录类型！");
		} 
		else {
			if (userList == null || 0 == userList.size()) {
				//app端传入昵称
				User.setName(nickName);
				SimpleDateFormat dfe = new SimpleDateFormat("yyyyMMddHHmmsss");// 设置日期格式
                String path = request.getSession().getServletContext().getRealPath("")+"/../"
							+ Constants.FILE_PATH;
                
	            URL url = null;
	            SslUtils.ignoreSsl();
	            try {
	                url = new URL(imgurl);
	            } catch (MalformedURLException e2) {
	                e2.printStackTrace();
	            }
	            InputStream is = null;
	            try {
	                is = url.openStream();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	            OutputStream os = null;
	            
	            File f = new File(path);
	            f.mkdirs();
	            try{
	                
	              System.out.println("文件地址=:" + path);
	                os = new FileOutputStream(path+dfe.format(new Date())+ ".jpg");
	             
	              User.setPortrait("portrait/" + dfe.format(new Date())+ ".jpg");
	                int bytesRead = 0;
	                byte[] buffer = new byte[8192];
	                while((bytesRead = is.read(buffer,0,8192))!=-1){
	                    os.write(buffer,0,bytesRead);}
	                }catch(FileNotFoundException e){ 
	                    
	                }catch (IOException e) {
	                  e.printStackTrace();
	                  }
				
				//设置用户类别
				User.setUserType(1);
				//用户积分
				User.setScore(0);
				//用户金额
				User.setMoney(0.0);;
				//用户状态
				User.setStatus(0);
				//登录时间
				Date loginTimeTemp = new Date();
		        User.setLastLoginTime(loginTimeTemp);
		        User.setCreateTime(loginTimeTemp);
		        //给密码加密
		        User.setPasswd(MD5Util.MD5(User.getPasswd()));
		        //向数据库中插入一条用户记录
				int res = userService.insertUser(User);
				if(1==res){
					if(User.getName()==null){
						User.setName(User.getOpenId());
//						User.setOpenidType(User.getOpenidType());
					}
					//获取该用户的id
					User newUser = userService.getUserByUserExample(userEx).get(0);
					int userId = newUser.getId();
			        String loginTime = df.format(loginTimeTemp);
			        //生成token,存token到redis
			        String token =UserUtil.generateToken(User.getPhone(),loginTime);
			        RedisUtil.set(token,userId);
			        //返回消息
			      //拼接用户图像的完整路劲
			        newUser.setPortrait("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+newUser.getPortrait());					
					netDataAccessUtil.setContent(UserUtil.constructUserResp(newUser,token));
					netDataAccessUtil.setResult(1);
					netDataAccessUtil.setResultDesp("第三方注册成功！");
				}else{
					 //返回消息
					netDataAccessUtil.setContent(null);
					netDataAccessUtil.setResult(-2);
					netDataAccessUtil.setResultDesp("第三方注册失败！");
				}
			} else {
				//登录成功，清除之前的token信息
				Jedis redis = RedisUtil.getJedis();
				String lastLoginTime = df.format(userList.get(0).getLastLoginTime());
		        String key = UserUtil.generateToken(userList.get(0).getPhone(),lastLoginTime);//没有手机号没有token
		        if( redis.exists(key)){
			        redis.del(key);
			        RedisUtil.returnResource(redis);
		        }
		        // 登录时间
		        Date loginTime = new Date();
		        String loginTimeStr = df.format(loginTime);
		        //更新登录时间到用户表中
		        userService.updateLoginTime(userList.get(0).getId(),loginTime);
		        //生成token,存token到redis
		        String token =UserUtil.generateToken(userList.get(0).getPhone(),loginTimeStr);
		        RedisUtil.set(token,userList.get(0).getId());
		        //拼接用户图像的完整路劲
		        userList.get(0).setPortrait("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+userList.get(0).getPortrait());
		        //返回信息
		        netDataAccessUtil.setContent(UserUtil.constructUserResp(userList.get(0),token));
				netDataAccessUtil.setResult(1);
		        netDataAccessUtil.setResultDesp("登录成功");
		        }
			}
				
		return netDataAccessUtil;
				
			
	}	
	
	/**
	 * 通过微信或QQ平台登陆	 
	 * @author zxy 	 
	 * @param UserService  	 
	 * @param LoginBy 	 
	 * @return 	
	 * @throws Exception 
	 */
	
	@RequestMapping(value="/loginBy", method = RequestMethod.POST)
	@ResponseBody
	public Object loginBy(HttpServletRequest request,User User)
			throws Exception{

		NetDataAccessUtil netDataAccessUtil = new NetDataAccessUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		if ("GET".equals(request.getMethod()) || null == User) {
			netDataAccessUtil.setContent(null);
			netDataAccessUtil.setResult(-3);
			netDataAccessUtil.setResultDesp("请求错误！");
		} if(User.getOpenidType()==null){
		    netDataAccessUtil.setContent(null);
            netDataAccessUtil.setResult(-2);
            netDataAccessUtil.setResultDesp("请选择第三方登录类型！");
		}
		else {
			UserExample userEx=new UserExample();
			Criteria criteria=userEx.createCriteria();
			criteria.andOpenIdEqualTo(User.getOpenId());
			criteria.andOpenidTypeEqualTo(User.getOpenidType());
			criteria.andOpenIdIsNotNull();
			
			criteria.andOpenidTypeIsNotNull();
			List<User> userList = userService.getUserByUserExample(userEx);
			if (userList == null || 0 == userList.size()) {
					netDataAccessUtil.setContent(0);
					netDataAccessUtil.setResult(0);
					netDataAccessUtil.setResultDesp("该openId未注册，请选择注册！");
				
			} else {
				//登录成功，清除之前的token信息
				Jedis redis = RedisUtil.getJedis();
				String lastLoginTime = df.format(userList.get(0).getLastLoginTime());
		        String key = UserUtil.generateToken(userList.get(0).getPhone(),lastLoginTime);//没有手机号没有token
		        if( redis.exists(key)){
			        redis.del(key);
			        RedisUtil.returnResource(redis);
		        }
		        // 登录时间
		        Date loginTime = new Date();
		        String loginTimeStr = df.format(loginTime);
		        //更新登录时间到用户表中
		        userService.updateLoginTime(userList.get(0).getId(),loginTime);
		        //生成token,存token到redis
		        String token =UserUtil.generateToken(userList.get(0).getPhone(),loginTimeStr);
		        RedisUtil.set(token,userList.get(0).getId());
		        //拼接用户图像的完整路劲
		        userList.get(0).setPortrait("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+userList.get(0).getPortrait());
		        //返回信息
		        netDataAccessUtil.setContent(UserUtil.constructUserResp(userList.get(0),token));
				netDataAccessUtil.setResult(1);
		        netDataAccessUtil.setResultDesp("登录成功");
		        }
			}
				
		return netDataAccessUtil;
				
			
	}
	
	
	
	@RequestMapping("/register")
	@ResponseBody
	public Object register(HttpServletRequest request,User User) {

		NetDataAccessUtil netDataAccessUtil = new NetDataAccessUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式   
		UserExample userEx=new UserExample();
		Criteria criteria=userEx.createCriteria();
		criteria.andPhoneEqualTo(User.getPhone());
		criteria.andPhoneIsNotNull();
		List<User> userList = userService.getUserByUserExample(userEx);
		if(0!=userList.size()){
			//该手机号已经存在
			netDataAccessUtil.setContent(null);
			netDataAccessUtil.setResult(-1);
			netDataAccessUtil.setResultDesp("该手机号已经注册！");
		}else{
			//如果用户名为空，默认使用手机号为用户名
			if(User.getName()==null){
				boolean flag = true;
				String nickName="";
				//生成默认昵称，如yy_手机号码前6位+6位随机号
				do{
					nickName = userService.createNickName(User.getPhone());
					//查询数据库中是否已存在该昵称
					boolean ret = userService.checkName(nickName);
					if(ret==true){
						//不存在相同的昵称
						flag = false;
					}
				}while(flag==true);
				
				User.setName(nickName);
			}
			//设置用户类别
			User.setUserType(1);
			//用户积分
			User.setScore(0);
			//用户金额
			User.setMoney(0.0);
			//用户状态
			User.setStatus(0);
			//登录时间
			Date loginTimeTemp = new Date();
	        User.setLastLoginTime(loginTimeTemp);
	        User.setCreateTime(loginTimeTemp);
	        //给密码加密
	        User.setPasswd(MD5Util.MD5(User.getPasswd()));
	        //设置默认图像
	        String portraitPaht = "portrait/default_image/ic_default_portrait.png" ;
	        User.setPortrait(portraitPaht);
	        //添加牵牛客服
	        CustomServerUtil.createUser(User.getPhone(), User.getPasswd());
	        User.setCustomServiceName(User.getPhone());
	        User.setCustomServicePasswd(User.getPasswd());
	        //向数据库中插入一条用户记录
			int res = userService.insertUser(User);
			if(1==res){
				//获取该用户的id
				User newUser = userService.getUserByUserExample(userEx).get(0);
				int userId = newUser.getId();
		        String loginTime = df.format(loginTimeTemp);
		        //生成token,存token到redis
		        String token =UserUtil.generateToken(User.getPhone(),loginTime);
		        RedisUtil.set(token,userId);
		        //返回消息
		        //拼接用户图像的完整路劲
		        newUser.setPortrait("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+newUser.getPortrait());
				netDataAccessUtil.setContent(UserUtil.constructUserResp(newUser,token));
				netDataAccessUtil.setResult(1);
				netDataAccessUtil.setResultDesp("注册成功！");
			}else{
				 //返回消息
				netDataAccessUtil.setContent(null);
				netDataAccessUtil.setResult(-2);
				netDataAccessUtil.setResultDesp("注册失败！");
			}
		}
		return netDataAccessUtil;
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public Object logout(HttpServletRequest request) {

		NetDataAccessUtil netDataAccessUtil = new NetDataAccessUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式   
		//获取用户的id
		Integer userId = (Integer) request.getAttribute("userId");
		User userInfo = userService.getUserInfoById(userId);
		//生成当前登录生成的token的key值
		String lastLoginTime = df.format(userInfo.getLastLoginTime());
        String key = UserUtil.generateToken(userInfo.getPhone(),lastLoginTime);
		//退出登录，则清除当前登录的token信息
		Jedis redis = RedisUtil.getJedis();
        if( redis.exists(key)){
	        redis.del(key);
	        RedisUtil.returnResource(redis);
        }
        //返回信息
		netDataAccessUtil.setContent(null);
		netDataAccessUtil.setResult(1);
        netDataAccessUtil.setResultDesp("登出成功");
		return netDataAccessUtil;
	}
	
	@RequestMapping("/checkpasswd")
	@ResponseBody
	public Object checkpasswd(HttpServletRequest request,User User) {

		NetDataAccessUtil netDataAccessUtil = new NetDataAccessUtil();		
			UserExample userEx=new UserExample();
			Criteria criteria=userEx.createCriteria();
			criteria.andPhoneEqualTo(User.getPhone());
			criteria.andPhoneIsNotNull();
			List<User> userList = userService.getUserByUserExample(userEx);
			if (userList == null || 0 == userList.size()) {
				//构造返回json对象
				netDataAccessUtil.setContent(null);
				netDataAccessUtil.setResult(-2);
				netDataAccessUtil.setResultDesp("该用户不存在！");
				
			} else {	
			if (!userList.get(0).getPasswd()
						.equals(MD5Util.MD5(User.getPasswd()))) {
					//构造返回json对象
					netDataAccessUtil.setContent(null);
					netDataAccessUtil.setResult(-1);
					netDataAccessUtil.setResultDesp("密码错误！");
				} else {
			        //返回信息
					netDataAccessUtil.setResult(1);
			        netDataAccessUtil.setResultDesp("密码正确");
			        }
				}
		
		return netDataAccessUtil;
	}
	//忘记密码中修改密码@zxy
		@RequestMapping("/changepasswd")
		@ResponseBody
		public Object changepasswd(HttpServletRequest request,UserModel userModel){
					
			UserExample userEx=new UserExample();
			Criteria criteria=userEx.createCriteria();
			criteria.andPhoneEqualTo(userModel.getPhone());
			criteria.andPhoneIsNotNull();
//			List<user> userList = userService.getUserByUserExample(userEx);
			
			boolean rst =  userService.changepasswd(userModel);
			NetDataAccessUtil nau = new NetDataAccessUtil();

			if (rst) {		
					nau.setResult(1);
					nau.setResultDesp("修改成功");
					}
			else{
				nau.setResult(0 );
				nau.setResultDesp("修改失败（请确认两次密码输入是否相同或者手机号是否正确）");
				}		
//			}
			return nau;
		}
	
		/**
		 * 判断手机号是否已存在
		 * @param request
		 * @param userModel
		 * @return
		 */
		@RequestMapping("/checkPhoneExist")
		@ResponseBody
		public Object checkPhoneExist(HttpServletRequest request,String phone){
					
			NetDataAccessUtil nau = new NetDataAccessUtil();
			boolean ret = userService.phoneExisted(phone);
			if (ret==true) {		
				nau.setContent(null);
				nau.setResult(1);
				nau.setResultDesp("手机号存在！");
			}
			else{
				nau.setContent(null);
				nau.setResult(-1);
				nau.setResultDesp("手机号不存在！");
			}
			return nau;
		}
}    

    