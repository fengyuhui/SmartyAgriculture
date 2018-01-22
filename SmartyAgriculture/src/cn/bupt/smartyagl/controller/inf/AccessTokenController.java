package cn.bupt.smartyagl.controller.inf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jmx.snmp.Timestamp;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.AccessToken;
import cn.bupt.smartyagl.entity.AccessTokenToLzx;
import cn.bupt.smartyagl.entity.JsTicket;
import cn.bupt.smartyagl.entity.UserInfoByToken;
import cn.bupt.smartyagl.service.IAccessTokenService;
import cn.bupt.smartyagl.util.CommonUtil;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.RedisUtil;
import cn.bupt.smartyagl.util.UserUtil;

@Controller
@RequestMapping("/accessToken")
public class AccessTokenController {

	@Autowired

	IAccessTokenService accessTokenService;
	String openid;
	
	//config接口注入权限验证
	
	/**
     * 方法名：Signature 主要检验jsTicket
     * @param request
      */
	@RequestMapping("/getSignatureByTicket")
    @ResponseBody
     public NetDataAccessUtil getSignature( HttpServletRequest request,String jsTicket,String myurl) {
    	 NetDataAccessUtil nau = new NetDataAccessUtil();
    	 Map<String, Object> ret = new HashMap<String, Object>();
         String appid = Constants.APPID; 
         String signature = "";
         String nonceStr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串  
		 String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳  
		    
         
         HttpServletRequest httpRequest=(HttpServletRequest)request;
//         http://www.zhongyuanfarm.cn/SmartAgricultureWechat/mine/addComment.html
         myurl = "http://www.zhongyuanfarm.cn/SmartAgricultureWechat/position/styleEdit.html";
         String requestUrl = myurl;

         //5、将参数排序并拼接字符串
		 String str = "jsapi_ticket="+jsTicket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+requestUrl;  
//		 String strmy = "jsapi_ticket=kgt8ON7yVITDhtdwci0qeX3hhveKRvbBjDqpHp_zcBWnFTE5T8iz4UnVNoQ5pNM69GbFWxX8kmP0EuAu4_xUKQ&noncestr=f59d7c88580b4b1c&timestamp=1496283138&url=http://+www.zhongyuanfarm.cn/SmartAgricultureWechat/mine/addComment.html";
	    //6、将字符串进行sha1加密 
	    signature =SHA1(str);
       
             
	    if (signature!=null) {
	    	ret.put("Ticket", jsTicket);
	    	ret.put("appId", appid);
	    	ret.put("timestamp", timestamp);
	    	ret.put("nonceStr", nonceStr);
	    	ret.put("signature", signature);
	    	nau.setContent(ret);
      	  	nau.setResult(1);
      	  	nau.setResultDesp("获取签名成功");
	    } else {
	    	nau.setContent(null);
	    	nau.setResult(0);
	    	nau.setResultDesp("获取签名失败");
	    }   
		return nau;
     }
	
	//获取jsapi_ticket
		@RequestMapping("/getTicketBy")
		@ResponseBody
		public NetDataAccessUtil getTicketByAccessToken(String accessToken){
			NetDataAccessUtil nau = new NetDataAccessUtil();
	   	 Map<String, Object> ret = new HashMap<String, Object>();
	        Jedis redis = RedisUtil.getJedis();
	        String jsTicket = "";
	        System.out.println(redis.get(accessToken)+"=k");
	        if (!redis.exists(accessToken)) {
	                    String jsonString = accessTokenService.sendGet(
	                    "https://api.weixin.qq.com/cgi-bin/ticket/getticket",
	                    "access_token="+ accessToken+"&type=jsapi");

//	            String  jsonString= "{\"access_token\":\"xu0roL6_zg3KjZ1CMATLk_mrfa0dHUNJuoIM-vbA-3qBZ9jykoYp3sNd2QhgwkN4Gla3oyq88gOGu4QOJQa7uGGvFuwzkl2zb8cULixbB6s\",\"expires_in\":7200, \"refresh_token\":\"Yc9dPKnrRQPMgqvtpqqivjmps2-yZvbeM5kMFAuiUiQsLqKljJlkBplGlMUCXz59Fls2tswgMfhPSur6SRQoEIw7TocumLmpk-mJn-lFDv4\",\"openid\":\"oA_rRw7pbflsX6NKx8gaGE-FU39A\",\"scope\":\"snsapi_userinfo\"}";
	            ObjectMapper mapper = new ObjectMapper();
	            try {
//	                 System.out.println(accessToken+"999999999");
	            	JsTicket jsTicketBean = mapper.readValue(jsonString,
	                        new TypeReference<JsTicket>() {
	                        });
	                // 获取jsonString中的access_token
	            	jsTicket = jsTicketBean.getTicket();
//	                openid=accessTokenBean.getOpenid();
	                RedisUtil.set(accessToken, jsTicket);
	                redis.expire(accessToken, 7200);
	                jsTicket = redis.get(accessToken);
	                // System.out.println(access_token+"8333");
	                if (jsTicket == null) {
	                    Map<String, Object> map = new HashMap<String, Object>();
	                    map.put("errcode", jsTicketBean.getErrcode());
	                    map.put("errmsg", jsTicketBean.getErrmsg());                 
	                    nau.setContent(map);
	                    nau.setResult(0);
	                    nau.setResultDesp("getTicket失败");
	                    return nau;
	                } else {
	                    System.out.println(jsTicket+"934346");
	                    Map<String, Object> map = new HashMap<String, Object>();
	                    map.put("Ticket", jsTicketBean.getTicket());
	                    map.put("expires_in", jsTicketBean.getExpires_in());
	                    nau.setContent(map);
	                    nau.setResult(1);
	                    nau.setResultDesp("getTicket成功");
	                    return nau;
	                }

	            } catch (JsonParseException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	                nau.setResult(0);
	                nau.setResultDesp("getTicket失败1");
	                return nau;
	            } catch (JsonMappingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	                nau.setResult(0);
	                nau.setResultDesp("accessToken错误，getTicket失败");
	                return nau;
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	                nau.setResult(0);
	                nau.setResultDesp("getTicket失败3");
	                return nau;
	            }

	            // 如果已有access_token,则发送 GET请求,直接从redis取出
	        } else {
	        	jsTicket = redis.get(accessToken);
		        
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("Ticket", redis.get(accessToken));
//	            map.put("openid", AccessToken.getOpenid());
	            nau.setContent(map);
	            nau.setResult(1);
	            nau.setResultDesp("get缓存Ticket成功");
	            return nau;
	        }
			
		}
		
	//签名算法
		public static String SHA1(String decript) {  
		    try {  
		        MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");  
		        digest.update(decript.getBytes());  
		        byte messageDigest[] = digest.digest();  
		        // Create Hex String  
		        StringBuffer hexString = new StringBuffer();  
		        // 字节数组转换为 十六进制 数  
		            for (int i = 0; i < messageDigest.length; i++) {  
		                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);  
		                if (shaHex.length() < 2) {  
		                    hexString.append(0);  
		                }  
		                hexString.append(shaHex);  
		            }  
		            return hexString.toString();  
		   
		        } catch (NoSuchAlgorithmException e) {  
		            e.printStackTrace();  
		        }  
		        return "";  
		}  
		
	//获取签名
		@RequestMapping("/getSig")
		@ResponseBody
		public static void getSig(HttpServletRequest request,String[] args) {  
			NetDataAccessUtil nau = new NetDataAccessUtil();
			//1、获取AccessToken 
			Jedis redis = RedisUtil.getJedis();
//			String accessToken = redis.get(Constants.USERAPPID);;  
			String accessToken = redis.get(Constants.APPID);
					
		    //2、获取Ticket  
//		    String jsapi_ticket = redis.get(accessToken);
//		    String jsTicket = redis.get(accessToken);
		    String jsapi_ticket = redis.get(accessToken);
		      
		    //3、时间戳和随机字符串  
		    String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串  
		    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳  
		      
		    System.out.println("accessToken:"+accessToken+"\njsapi_ticket:"+jsapi_ticket+"\n时间戳："+timestamp+"\n随机字符串："+noncestr);  
		      
		    //4、获取url  
		    String url="http:// www.zhongyuanfarm.cn/SmartAgricultureWechat/mine/addComment.html";  
		    /*根据JSSDK上面的规则进行计算，这里比较简单，我就手动写啦 
		    String[] ArrTmp = {"jsapi_ticket","timestamp","nonce","url"}; 
		    Arrays.sort(ArrTmp); 
		    StringBuffer sf = new StringBuffer(); 
		    for(int i=0;i<ArrTmp.length;i++){ 
		        sf.append(ArrTmp[i]); 
		    } 
		    */  
		      
		    //5、将参数排序并拼接字符串  
		    String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;  
		     
		    //6、将字符串进行sha1加密  
		    String signature =SHA1(str);  
		    System.out.println("参数："+str+"\n签名："+signature);  
		}  
	
	//后文所有 ：token、access_token、获取用户信息相关，
	//获取access_token
	@RequestMapping("/getToken")
	@ResponseBody
	public NetDataAccessUtil getToken() {
		String access_token= "";
		Jedis redis = RedisUtil.getJedis();
		NetDataAccessUtil nau = new NetDataAccessUtil();
		
		
		// System.out.println(access_token+"55555555555");
		// 先判断有没有access_token,如果没有access_token，则从url中获取
		
		/*
		 * if(redis.expire(Constants.APPID,120)); { redis.del(Constants.APPID);
		 * RedisUtil.returnResource(redis); }
		 */
//		String expires_in;
		if (!redis.exists(Constants.APPID)) {
			String jsonString = accessTokenService.sendGet(
					"https://api.weixin.qq.com/cgi-bin/token",
					"grant_type=client_credential&appid=" + Constants.APPID
							+ "&secret=" + Constants.APPSECERT);
			// String
			// jsonString="{\"access_token\":\"mh7tSltJ4MY74yMtLGaQ_E_1\",\"expires_in\":7200}";
			ObjectMapper mapper = new ObjectMapper();
			try {
				// System.out.println(access_token+"999999999");
				AccessToken accessTokenbean = mapper.readValue(jsonString,
						new TypeReference<AccessToken>() {
						});
				// 获取jsonString中的access_token
				access_token = accessTokenbean.getAccess_token();
				RedisUtil.set(Constants.APPID, access_token);
				redis.expire(Constants.APPID, 7200);
				access_token = redis.get(Constants.APPID);
				if (access_token == null) {
					nau.setContent(jsonString);
					nau.setResult(0);
					nau.setResultDesp("getToken失败");
				} else {
				    Map<String, Object> map = new HashMap<String, Object>();
		            map.put("expires_in", accessTokenbean.getExpires_in());
		            map.put("access_token", accessTokenbean.getAccess_token());
				    nau.setContent(map);
					nau.setResult(1);
					nau.setResultDesp("getToken成功");
				}

			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 如果已有access_token,则发送 GET请求,直接从redis取出
		} else {
			access_token = redis.get(Constants.APPID);
			nau.setContent(redis.get(Constants.APPID));
			nau.setResult(1);
			nau.setResultDesp("get缓存Token成功");
		}
		return nau;
	}
	
//	//都用appid而非userappid
//	public String getMToken() {
//		// String access_token= "";
//		Jedis redis = RedisUtil.getJedis();
//		// redis.set(access_token,Constants.APPID);
//		NetDataAccessUtil nau = new NetDataAccessUtil();
//		
//		String access_token = redis.get(Constants.APPID);
//		// 先判断有没有access_token,如果没有access_token，则从url中获取
//		// 如果时间超过两小时，则删除key
//		redis.expire(Constants.APPID, 600);
//		
//		if (!redis.exists(Constants.APPID)) {
//			String jsonString = accessTokenService.sendGet(
//					"https://api.weixin.qq.com/cgi-bin/token",
//					"grant_type=client_credential&appid=" + Constants.APPID
//							+ "&secret=" + Constants.APPSECERT);
//			ObjectMapper mapper = new ObjectMapper();
//			try {
//				// System.out.println(access_token+"999999999");
//				AccessToken accessTokenbean = mapper.readValue(jsonString,
//						new TypeReference<AccessToken>() {
//						});
//				// 获取jsonString中的access_token
//				access_token = accessTokenbean.getAccess_token();
//				RedisUtil.set(Constants.APPID, access_token);
//				access_token = redis.get(Constants.APPID);
//				// System.out.println(access_token+"8333");
//				if (access_token == null) {
//					nau.setContent(jsonString);
//					nau.setResult(0);
//					nau.setResultDesp("getToken失败");
//				} else {
//				    Map<String, Object> map = new HashMap<String, Object>();
//		            map.put("expires_in", accessTokenbean.getExpires_in());
//		            map.put("access_token", accessTokenbean.getAccess_token());
//				    nau.setContent(map);
////					nau.setContent(redis.get(Constants.USERAPPID));
//					nau.setResult(1);
//					nau.setResultDesp("getToken成功");
//				}
//
//			} catch (JsonParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (JsonMappingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			// 如果已有access_token,则发送 GET请求,直接从redis取出
//		} else {
////		    nau.setContent(accessTokenbean.getExpires_in());
//			nau.setContent(redis.get(Constants.APPID));
//			nau.setResult(1);
//			nau.setResultDesp("getToken成功");
//		}
//		return redis.get(Constants.APPID);
//	}
	
	
	@RequestMapping("/getTokenByCode")
    @ResponseBody
    public Object getTokenByCode(HttpServletRequest request, String code) {
        // String access_token= "";
        Jedis redis = RedisUtil.getJedis();
        // redis.set(access_token,Constants.APPID);
        NetDataAccessUtil nau = new NetDataAccessUtil();

        String accessToken = redis.get(code);
        redis.expire(code, 296);
        System.out.println("---"+redis.get(code));
        if (!redis.exists(code)) {
                    String jsonString = accessTokenService.sendGet(
                    "https://api.weixin.qq.com/sns/oauth2/access_token",
                    "appid="+ Constants.APPID+"&secret="
                    +Constants.APPSECERT+"&code="+code+"&grant_type=authorization_code");

          ObjectMapper mapper = new ObjectMapper();
            try {
                 System.out.println(accessToken+"999999999");
                AccessToken accessTokenBean = mapper.readValue(jsonString,
                        new TypeReference<AccessToken>() {
                        });
                // 获取jsonString中的access_token
                accessToken = accessTokenBean.getAccess_token();
//                openid=accessTokenBean.getOpenid();
                RedisUtil.set(code, accessToken);
                accessToken = redis.get(code);
                if (accessToken == null) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("errcode", accessTokenBean.getErrcode());
                    map.put("errmsg", accessTokenBean.getErrmsg());                 
                    nau.setContent(map);
                    nau.setResult(0);
                    nau.setResultDesp("getToken失败");
                } else {
                    // System.out.println(access_token+"934346");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("access_token", accessTokenBean.getAccess_token());
                    map.put("openid", accessTokenBean.getOpenid());
                    map.put("refresh_token", accessTokenBean.getRefresh_token());
                    map.put("expires_in", accessTokenBean.getExpires_in());
                    map.put("scope", accessTokenBean.getScope());
                    nau.setContent(map);
                    nau.setResult(1);
                    nau.setResultDesp("getToken成功");
                }

            } catch (JsonParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                nau.setResult(0);
                nau.setResultDesp("getToken失败1");
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                nau.setResult(0);
                nau.setResultDesp("code错误，getToken失败");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                nau.setResult(0);
                nau.setResultDesp("getToken失败3");
            }

            // 如果已有access_token,则发送 GET请求,直接从redis取出
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("access_token", redis.get(code));
//            map.put("openid", AccessToken.getOpenid());
            nau.setContent(map);
            nau.setResult(1);
            nau.setResultDesp("getToken成功");
        }
        return nau;
    }
	
	
	
	
	
	
	@RequestMapping("/getUserInfoByToken")
    @ResponseBody
    public Object getUserInfoByToken(HttpServletRequest request, String openid, String access_token) throws UnsupportedEncodingException {
//	    String code;
        Jedis redis = RedisUtil.getJedis();
        NetDataAccessUtil nau = new NetDataAccessUtil();
//        RedisUtil.set(Constants.APPID, "skdjfhksdhfsdfu");//测试
//        System.out.println(redis.get(code));
        if (access_token !=null) {
            
            String jsonString = accessTokenService.sendGet(
                    "https://api.weixin.qq.com/sns/userinfo",
                    "access_token="+access_token+"&openid="+openid+"&lang=zh_CN");
//            String  jsonString="{\"openid\":\"oA_rRw7pbflsX6NKx8gaGE-FU39A\",\"nickname\":\"陈佳明\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"海淀\",\"province\":\"北京\",\"country\":\"中国\",\"headimgurl\":\"http://wx.qlogo.cn/mmopen/Q3auHgzwzM7RPlbLOXujjl8riad8hXnjcC440pasVQTcaVwFRwtPfHS8CrDKqhAcZbVWFoq980JEOticpWgXpwHQ/0\",\"privilege\":[\"PRIVILEGE1\",\"PRIVILEGE2\"],\"unionid\": \"o6_bmasdasdsad6_2sgVt7hMZOPfL\"}";

            ObjectMapper mapper = new ObjectMapper();
//            System.out.println(redis.get(code));
            try {             
                UserInfoByToken userInfoByTokenBean = mapper.readValue(jsonString,
                        new TypeReference<UserInfoByToken>() {
                        });
//      ***          
//                if(redis.get(code)==null){
//                    System.out.println(userInfoByTokenBean.getNickname()+"sdgf");
//                }
//                userInfoByTokenBean.getNickname()="8475";
//                System.out.println(userInfoByTokenBean.getOpenid()+"888777");
                if(userInfoByTokenBean.getNickname()!=null){//redis.get(Constants.APPID)
                         System.out.println(access_token+"934346");
//                         System.out.println(redis.get(Constants.APPID)+"888777");
                         Map<String, Object> map = new HashMap<String, Object>();
                         map.put("openId", userInfoByTokenBean.getOpenid());
                         map.put("nickname", userInfoByTokenBean.getNickname());
                         map.put("sex", userInfoByTokenBean.getSex());
                         map.put("language", userInfoByTokenBean.getLanguage());
                         map.put("province", userInfoByTokenBean.getProvince());
                         map.put("city", userInfoByTokenBean.getCity());
                         map.put("country", userInfoByTokenBean.getCountry());
                         map.put("headimgurl", userInfoByTokenBean.getHeadimgurl());
                         map.put("privilege", userInfoByTokenBean.getPrivilege());
//                         expires_in=accessTokenBean.getExpires_in();
//                         openid = accessTokenBean.getOpenid();
                         nau.setContent(map);
                         nau.setResult(1);
                         nau.setResultDesp("获取用户信息成功");                        
                        
                }  else if(userInfoByTokenBean.getErrmsg()!=null) {                       
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("errcode", userInfoByTokenBean.getErrcode());
                        map.put("errmsg", userInfoByTokenBean.getErrmsg());   
//                        map.put("nickname", userInfoByTokenBean.getNickname());
                        nau.setContent(map);
                        nau.setResult(0);
                        nau.setResultDesp("获取用户信息失败");
                } else{
//                    nau.setContent(map);
                    nau.setResult(0);
                    nau.setResultDesp("获取用户信息失败");
                }

            } catch (JsonParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                nau.setResult(0);
                nau.setResultDesp("获取用户信息失败1");
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                nau.setResult(0);
                nau.setResultDesp("token或openid错误，获取用户信息失败");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                nau.setResult(0);
                nau.setResultDesp("获取用户信息失败3");
            }
        }
        else{
            nau.setContent("access_token与openid不能为空");
            nau.setResult(0);
            nau.setResultDesp("获取用户信息失败");
        }
            // 如果access_token已经失效
          
            //        } else {
//            Map<String, Object> map = new HashMap<String, Object>();
////            map.put("expires_in", expires_in);
////            map.put("access_token", redis.get(Constants.APPID));
//            map.put("openId", openid);
//            nau.setContent(map);
//            nau.setResult(1);
//            nau.setResultDesp("获取用户信息成功");
//        }
        return nau;
	}
	
	
	@RequestMapping("/getTokenByCodeForVote")
    @ResponseBody
    public Object getTokenByCodeForVote(HttpServletRequest request, String code) {
//        Jedis redis = RedisUtil.getJedis();
        NetDataAccessUtil nau = new NetDataAccessUtil();
        String accessToken = null;
//        redis.expire(code, 296);
        String jsonString = accessTokenService.sendGet(
        "https://api.weixin.qq.com/sns/oauth2/access_token",
        "appid="+ Constants.APPID+"&secret="
        +Constants.APPSECERT+"&code="+code+"&grant_type=authorization_code");
            ObjectMapper mapper = new ObjectMapper();
            try {
                AccessToken accessTokenBean = mapper.readValue(jsonString,
                        new TypeReference<AccessToken>() {
                        });
                // 获取jsonString中的access_token
                accessToken = accessTokenBean.getAccess_token();
                if (accessToken == null) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("errcode", accessTokenBean.getErrcode());
                    map.put("errmsg", accessTokenBean.getErrmsg());                 
                    nau.setContent(map);
                    nau.setResult(0);
                    nau.setResultDesp("getToken失败");
                } else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("access_token", accessTokenBean.getAccess_token());
                    map.put("openid", accessTokenBean.getOpenid());
                    map.put("refresh_token", accessTokenBean.getRefresh_token());
                    map.put("expires_in", accessTokenBean.getExpires_in());
                    map.put("scope", accessTokenBean.getScope());
                    nau.setContent(map);
                    nau.setResult(1);
                    nau.setResultDesp("getToken成功");
                }

            } catch (JsonParseException e) {
                e.printStackTrace();
                nau.setResult(0);
                nau.setResultDesp("getToken失败1");
            } catch (JsonMappingException e) {
                e.printStackTrace();
                nau.setResult(0);
                nau.setResultDesp("code错误，getToken失败");
            } catch (IOException e) {
                e.printStackTrace();
                nau.setResult(0);
                nau.setResultDesp("getToken失败3");
            }
            // 如果已有access_token,则发送 GET请求,直接从redis取出
        return nau;
    }
	/*
	 * toLZX
	 */
	@RequestMapping("/getTokentoLZX")
	@ResponseBody
	public NetDataAccessUtil getTokentoLZX(String url, String url3) {
		
		NetDataAccessUtil nau = new NetDataAccessUtil();
		String access_token;
		String jsonString = accessTokenService.sendGet(
				url,
				url3);
		ObjectMapper mapper = new ObjectMapper();
		try {
			AccessTokenToLzx accessTokenbean = mapper.readValue(jsonString,
					new TypeReference<AccessTokenToLzx>() {
					});
			// 获取jsonString中的access_token
			access_token = accessTokenbean.getAccess_token();
			
			if (access_token == null) {
				nau.setContent(jsonString);
				nau.setResult(0);
				nau.setResultDesp("getToken失败");
			} else {
			    Map<String, Object> map = new HashMap<String, Object>();
	            map.put("access_token", accessTokenbean.getAccess_token());
			    nau.setContent(map);
				nau.setResult(1);
				nau.setResultDesp("getToken成功");
			}

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nau;
	}
	
	public NetDataAccessUtil getSignaturebuaccessToken( HttpServletRequest request,String accessToken,String myurl) {
   	 NetDataAccessUtil nau = new NetDataAccessUtil();
   	 Map<String, Object> ret = new HashMap<String, Object>();
   	 Jedis redis = RedisUtil.getJedis();
//   	 accessToken = redis.get(Constants.USERAPPID);
        
        String appid = Constants.APPID; 
        String signature = "";
//        String timestamp = Long.toString(System.currentTimeMillis() / 1000); 
//        String nonceStr = UUID.randomUUID().toString();       
        String nonceStr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串  
		 String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳  
		    
        
        HttpServletRequest httpRequest=(HttpServletRequest)request;
//        http://www.zhongyuanfarm.cn/SmartAgricultureWechat/mine/addComment.html
   	 
        String requestUrl = myurl;
        String url = "http://" + request.getServerName() //服务器地址  
                + httpRequest.getContextPath()      //项目名称  
                + httpRequest.getServletPath()      //请求页面或其他地址  
            + "?" + (httpRequest.getQueryString()); //参数  
        
        System.out.println("url="+url);
        
//        String jsTicket = getTicketByAccessToken(accessToken);
        String jsTicket = redis.get(accessToken);
        redis.expire(accessToken, 600);
	     System.out.println(redis.get(accessToken)+"=k");
        //5、将参数排序并拼接字符串  
		 String str = "jsapi_ticket="+jsTicket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+requestUrl;  
//		 String strmy = "jsapi_ticket=kgt8ON7yVITDhtdwci0qeX3hhveKRvbBjDqpHp_zcBWnFTE5T8iz4UnVNoQ5pNM69GbFWxX8kmP0EuAu4_xUKQ&noncestr=f59d7c88580b4b1c&timestamp=1496283138&url=http://+www.zhongyuanfarm.cn/SmartAgricultureWechat/mine/addComment.html";
	    //6、将字符串进行sha1加密  
	    signature =SHA1(str); 
        
        if (!redis.exists(accessToken)) {
                    String jsonString = accessTokenService.sendGet(
                    "https://api.weixin.qq.com/cgi-bin/ticket/getticket",
                    "access_token="+ accessToken+"&type=jsapi");

            ObjectMapper mapper = new ObjectMapper();
            try {
//                 System.out.println(accessToken+"999999999");
            	JsTicket jsTicketBean = mapper.readValue(jsonString,
                        new TypeReference<JsTicket>() {
                        });
                // 获取jsonString中的access_token
            	jsTicket = jsTicketBean.getTicket();
//                openid=accessTokenBean.getOpenid();
                RedisUtil.set(accessToken, jsTicket);
                jsTicket = redis.get(accessToken);
                // System.out.println(access_token+"8333");
                if (accessToken == null) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("errcode", jsTicketBean.getErrcode());
                    map.put("errmsg", jsTicketBean.getErrmsg());         
                    nau.setContent(map);
                    nau.setResult(0);
                    nau.setResultDesp("get签名失败");
                } else {
                 // 注意这里参数名必须全部小写，且必须有序
                    String sign = "jsapi_ticket=" + jsTicket + "&noncestr=" + nonceStr+ "&timestamp=" + timestamp + "&url=" + requestUrl;
                    try {
                        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
                        crypt.reset();
                        crypt.update(sign.getBytes("UTF-8"));
//                      signature = CommonUtil.byteToHex(crypt.digest());
                        
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    System.out.println(jsTicket+"934346");
                    ret.put("Ticket", redis.get(accessToken));
                    ret.put("expires_in", jsTicketBean.getExpires_in());
                    ret.put("appId", appid);
                    ret.put("timestamp", timestamp);
                    ret.put("nonceStr", nonceStr);
                    ret.put("signature", signature);
                    nau.setContent(ret);
                	 nau.setResult(1);
                    nau.setResultDesp("get签名成功");
                }

            } catch (JsonParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                nau.setResult(0);
                nau.setResultDesp("get签名失败1");
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                nau.setResult(0);
                nau.setResultDesp("code错误，get签名失败");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                nau.setResult(0);
                nau.setResultDesp("get签名失败3");
            }

            // 如果已有jsTicket,则发送 GET请求,直接从redis取出
        } else {
//            	Map<String, Object> map = new HashMap<String, Object>();
        		jsTicket = redis.get(accessToken);
        	// 注意这里参数名必须全部小写，且必须有序
               String sign = "jsapi_ticket=" + jsTicket + "&noncestr=" + nonceStr+ "&timestamp=" + timestamp + "&url=" + requestUrl;
               try {
                   MessageDigest crypt = MessageDigest.getInstance("SHA-1");
                   crypt.reset();
                   crypt.update(sign.getBytes("UTF-8"));
//               	signature = CommonUtil.byteToHex(crypt.digest());
               	} catch (NoSuchAlgorithmException e) {
                   e.printStackTrace();
               } catch (UnsupportedEncodingException e) {
                   e.printStackTrace();
               }
               ret.put("Ticket", redis.get(accessToken));
               ret.put("appId", appid);
               ret.put("timestamp", timestamp);
               ret.put("nonceStr", nonceStr);
               ret.put("signature", signature);
               nau.setContent(ret);
           	nau.setResult(1);
               nau.setResultDesp("获取签名成功");
        }   
		return nau;
    }
}