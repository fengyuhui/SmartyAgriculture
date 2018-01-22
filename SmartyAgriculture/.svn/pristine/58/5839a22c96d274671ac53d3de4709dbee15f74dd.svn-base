package cn.bupt.smartyagl.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.JpushBean;
import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.Audience.Builder;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.schedule.ScheduleClient;
import cn.jpush.api.schedule.ScheduleResult;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-6-16 上午10:33:05 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class JPushUtil {
	/**
	 * 推送对象
	 */
	private static JPushClient jpushClient;
	static{
		jpushClient = new JPushClient(Constants.JPUSH_SECRETE, Constants.JPUSH_APPKEY);
	}
	
	public static void main(String[] args) throws APIConnectionException, APIRequestException {
				//推送信息包装
				JpushBean jb = new JpushBean();
				jb.setContent("test");
				jb.setType(7);
				jb.setTitle("title");
				ObjectMapper om = new ObjectMapper();
				String jsonMsg = "";
				try {
					 jsonMsg = om.writeValueAsString(jb);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				Map<String,String> map = new HashMap<String, String>();
				map.put("type", "7");
				map.put("title", "title");
				jsonMsg ="ssss";
				PushResult pr = JPushUtil.buildPushObject_all_all_alert(jsonMsg,null,"title","");
				System.out.println("?");
	}
	
	/**
	 * 所有平台，所有设备，内容为 ALERT 的通知。
	 * @return
	 */
	public static PushResult buildPushObject_all_all_alert(String content,Map<String,String> map,String title,String sendTime) {
		try{
			PushPayload payload = PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.all())
	                .setNotification(Notification.android(content, title, map))
	                .build();
			//是否定时发送
			if(sendTime == null || sendTime.equals("")){
				 jpushClient.sendPush(payload);
			}else{
				ScheduleResult jc =  jpushClient.createSingleSchedule(title, sendTime, payload);
			}
			 PushPayload payload2 = PushPayload.newBuilder()
		                .setPlatform(Platform.all())
		                .setAudience(Audience.all())
		                .setNotification(Notification.ios(content,map))
		                .build();
			 	//是否定时发送
				if(sendTime == null || sendTime.equals("")){
					return jpushClient.sendPush(payload2);//
				}else{
					jpushClient.createSingleSchedule(title, sendTime, payload2);
					return null;
				}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 往android手机端推送通知
	 * @param content 推送内容
	 * @param aim 推送目标
	 * @param title 推送标题
	 * @return
	 */
	 public static PushResult pubshOneToAndroid(String content,String title,String sendTime,Map<String,String> map,String ...aim) {
		 try{
				cn.jpush.api.push.model.PushPayload.Builder builder = PushPayload.newBuilder()
	             .setPlatform(Platform.android());
	             if(aim.length<=0){
	            	 builder.setAudience(Audience.tag( aim ));
	             }else{
	            	 builder.setAudience(Audience.all());
	             }
	            
	             PushPayload payload = builder.setNotification(Notification.android(content, title, map))
	             .build();
	            //是否定时发送
				if(sendTime == null || sendTime.equals("")){
					return jpushClient.sendPush(payload);
				}else{
					jpushClient.createSingleSchedule(title, sendTime, payload);
					return null;
				}
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
	 }
	 
	 /**
		 * 往ios手机端推送通知
		 * @param content 推送内容
		 * @param aim 推送目标
		 * @param title 推送标题
		 * @return
		 */
		 public static PushResult pubshOneToIOS(String content,String title,String sendTime,Map<String,String> map,String ...aim) {
			 try{
				 cn.jpush.api.push.model.PushPayload.Builder builder = PushPayload.newBuilder()
			                .setPlatform(Platform.ios());
							 if(aim.length<=0){//如果目标数量为0，向所有用户发送消息
				            	 builder.setAudience(Audience.tag( aim ));
				             }else{
				            	 builder.setAudience(Audience.all());
				             }
							 PushPayload payload =  builder.setNotification(
			                        		Notification.ios(content, map)
			                        )
			                 .setMessage(Message.content(content))
			                 .setOptions(Options.newBuilder()
			                         .setApnsProduction(true)
			                         .build())
			                 .build();
							//是否定时发送
								if(sendTime == null || sendTime.equals("")){
									return jpushClient.sendPush(payload);
								}else{
									jpushClient.createSingleSchedule(title, sendTime, payload);
									return null;
								}
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
		 }
		 
		 /**
		  * 往IOS与Android设备推送消息
		  * @return
		  */
		 public static PushResult buildPushObject_ios_audienceMore_messageWithExtras(String content,String title,String sendTime,String ...aim) {
			 try{
					PushPayload payload = PushPayload.newBuilder()
			                .setPlatform(Platform.android_ios())
			                .setAudience(Audience.newBuilder()
			                        .addAudienceTarget(AudienceTarget.tag(aim))
			                        .build())
			                .setMessage(Message.newBuilder()
			                        .setMsgContent(content)
			                        .addExtra("from", "JPush")
			                        .build())
			                .build();
					//是否定时发送
					if(sendTime == null || sendTime.equals("")){
						return jpushClient.sendPush(payload);
					}else{
						jpushClient.createSingleSchedule(title, sendTime, payload);
						return null;
					}
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
		    }

	 
}
