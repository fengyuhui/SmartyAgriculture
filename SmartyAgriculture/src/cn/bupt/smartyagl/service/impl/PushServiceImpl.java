package cn.bupt.smartyagl.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.GoodsListMapper;
import cn.bupt.smartyagl.dao.autogenerate.OrderViewMapper;
import cn.bupt.smartyagl.dao.autogenerate.MessageMapper;
import cn.bupt.smartyagl.dao.autogenerate.OrderMasterMapper;
import cn.bupt.smartyagl.entity.JpushBean;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.GoodsListExample;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.entity.autogenerate.OrderViewExample;
import cn.bupt.smartyagl.entity.autogenerate.Message;
import cn.bupt.smartyagl.entity.autogenerate.MessageExample;
import cn.bupt.smartyagl.entity.autogenerate.OrderMaster;
import cn.bupt.smartyagl.entity.autogenerate.OrderMasterExample;
import cn.bupt.smartyagl.entity.autogenerate.User;
import cn.bupt.smartyagl.service.IPushService;
import cn.bupt.smartyagl.service.IUserService;
import cn.bupt.smartyagl.util.JPushUtil;
import cn.jpush.api.push.PushResult;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-6-24 上午9:16:08 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Service
public class PushServiceImpl implements IPushService{
	@Autowired
	IUserService userService;
	
	@Autowired
	OrderMasterMapper orderMasterMapper;
	@Autowired
	MessageMapper messageMapper;
	@Autowired
	GoodsListMapper goodsListMapper;
	@Autowired
	OrderViewMapper orderViewMapper;
	
	@Override
	public boolean specalOffer(List<Integer> goodsIds,String sendTime) {
		GoodsListExample ge = new GoodsListExample();
		ge.createCriteria().andIdIn(goodsIds);
		List<GoodsList> goodsList = goodsListMapper.selectByExample(ge);
		if(goodsList.size() <= 0){
			return false;
		}
		String message = "有新的特价商品：";
		for(GoodsList goods : goodsList){
			message += goods.getName() + ",";
		}
		message = message.substring(0, message.length()-1 );
		return this.pushMessageToAll(message, "特价商品", ConstantsSql.PUSH_SPECAIL_OFFER, new HashMap<String, String>(),sendTime);
	}

	@Override
	public boolean flashSale(List<Integer> goodsIds,String sendTime) {
		GoodsListExample ge = new GoodsListExample();
		ge.createCriteria().andIdIn(goodsIds);
		List<GoodsList> goodsList = goodsListMapper.selectByExample(ge);
		if(goodsList.size() <= 0){
			return false;
		}
		String message = "有新的限时抢购商品：";
		for(GoodsList goods : goodsList){
			message += goods.getName() + ",";
		}
		message = message.substring(0, message.length()-1 );
		return this.pushMessageToAll(message, "限时抢购", ConstantsSql.PUSH_FlASH_SALE, new HashMap<String, String>(),sendTime);
	}

	@Override
	public boolean hotSale(List<Integer> goodsIds,String sendTime) {
		GoodsListExample ge = new GoodsListExample();
		ge.createCriteria().andIdIn(goodsIds);
		List<GoodsList> goodsList = goodsListMapper.selectByExample(ge);
		if(goodsList.size() <= 0){
			return false;
		}
		String message = "有新的热卖商品：";
		for(GoodsList goods : goodsList){
			message += goods.getName() + ",";
		}
		message = message.substring(0, message.length()-1 );
		return this.pushMessageToAll(message, "热卖商品", ConstantsSql.PUSH_HOTSALE, new HashMap<String, String>(),sendTime);
	}

	@Override
	public boolean newSale(List<Integer> goodsIds,String sendTime) {
		GoodsListExample ge = new GoodsListExample();
		ge.createCriteria().andIdIn(goodsIds);
		List<GoodsList> goodsList = goodsListMapper.selectByExample(ge);
		if(goodsList.size() <= 0){
			return false;
		}
		String message = "新品上市：";
		for(GoodsList goods : goodsList){
			message += goods.getName() + ",";
		}
		message = message.substring(0, message.length()-1 );
		return this.pushMessageToAll(message, "新品上市", ConstantsSql.PUSH_NEWSALE, new HashMap<String, String>(),sendTime);
	}

	@Override
	public boolean sendGoods(Integer orderId) {
		OrderViewExample oe = new OrderViewExample();
		cn.bupt.smartyagl.entity.autogenerate.OrderViewExample.Criteria criteria = oe.createCriteria();
		criteria.andIdEqualTo(orderId);
		List<OrderView> ovs = orderViewMapper.selectByExample( oe );
		if( ovs.size() > 0 ){
			return false;
		}
		OrderView ov = ovs.get(0);
		String message = "您的商品"+ov.getName()+"已发货";
		User user = userService.getUserInfoById( ov.getUserId() );
		Map<String,String> map = new HashMap<String, String>();
		map.put("orderId", ""+orderId);
		return this.pushMessageToOne(message, "商品已发货", ConstantsSql.PUSH_SENDGOODS, user.getId(),map,null);
	}

	@Override
	public boolean remoteLogin(Integer userId) {
		return this.pushMessageToOne("异地登录", "异地登录", ConstantsSql.PUSH_REMOTELOGIN, userId,new HashMap<String, String>(),null);
	}

	@Override
	public boolean returnGoodsSucess(String orderId) {
		OrderMasterExample oe = new OrderMasterExample();
		cn.bupt.smartyagl.entity.autogenerate.OrderMasterExample.Criteria criteria = oe.createCriteria();
		criteria.andOrderIdEqualTo(orderId);
		List<OrderMaster> ovs = orderMasterMapper.selectByExample( oe );
		if( ovs.size() > 0 ){
			return false;
		}
		OrderMaster ov = ovs.get(0);
		String message = "您的商品"+"已退货成功";
		User user = userService.getUserInfoById( ov.getUserId() );
		Map<String,String> map = new HashMap<String, String>();
		map.put("orderId", ""+orderId);
		return this.pushMessageToOne(message, "商品退货", ConstantsSql.PUSH_RETURNGOODS_Sucess, user.getId(),map,null);
	}

	@Override
	public boolean returnGoodsFail(String orderId) {
		OrderMasterExample oe = new OrderMasterExample();
		cn.bupt.smartyagl.entity.autogenerate.OrderMasterExample.Criteria criteria = oe.createCriteria();
		criteria.andOrderIdEqualTo(orderId);
		List<OrderMaster> ovs = orderMasterMapper.selectByExample( oe );
		if( ovs.size() > 0 ){
			return false;
		}
		OrderMaster ov = ovs.get(0);
		String message = "您的商品"+"退货失败";
		User user = userService.getUserInfoById( ov.getUserId() );
		Map<String,String> map = new HashMap<String, String>();
		map.put("orderId", ""+orderId);
		return this.pushMessageToOne(message, "商品退货", ConstantsSql.PUSH_RETURNGOODS_Sucess, user.getId(),map,null);
	}

	
	@Override
	public boolean pushMessageToOne(String content,String title, Integer type, Integer userId,Map<String,String> map,String sendTime) {
		User user = userService.getUserInfoById(userId);
		//推送信息包装
		JpushBean jb = new JpushBean();
		jb.setContent(content);
		jb.setType(type);
		jb.setTitle(title);
		ObjectMapper om = new ObjectMapper();
		String jsonMsg = "";
	    try {
	    	jsonMsg = om.writeValueAsString(jb);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return false;
		}
	    
	    if(user.getDeviceType().equals( ConstantsSql.DeviceType_Android )){//往Android设备推送信息
	    	PushResult pr = JPushUtil.pubshOneToAndroid(jsonMsg, title,sendTime, map, user.getDeviceId());
	    }else if( user.getDeviceId().equals( ConstantsSql.DeviceType_IOS ) ){//往IOS设备推送信息
	    	PushResult pr = JPushUtil.pubshOneToIOS(jsonMsg,title,sendTime , map, user.getDeviceId());
	    }
	    //消息列表添加消息
	    Message message = new Message();
		message.setMarkId(type);
		message.setMessage(content);
		message.setUserId( userId );
		message.setCreateTime(new Date());
		this.addMessage(message);
		return true;
	}

	@Override
	public boolean pushMessageToAll(String content, String title, Integer type,Map<String,String> map,String sendTime) {
		map.put("type", ""+type);
		PushResult pr = JPushUtil.buildPushObject_all_all_alert(content,map,title,sendTime);
		//消息列表添加消息
		Message message = new Message();
		message.setMarkId(type);
		message.setMessage(content);
		message.setUserId( ConstantsSql.AllUser );
		message.setCreateTime(new Date());
		this.addMessage(message);
		return true;
	}

	@Override
	public List getMessageList(Message message) {
		try{
			MessageExample me = new MessageExample();
			cn.bupt.smartyagl.entity.autogenerate.MessageExample.Criteria ct =  me.createCriteria();
			//用户筛选
			if(message.getId() == null ){
				return null;
			}
			ct.andUserIdEqualTo(message.getId());
			//消息类型获取
			if( message.getMarkId() != null && message.getMarkId() >0 ){//消息类型筛选
				ct.andMarkIdEqualTo( message.getMarkId() );
			}
			//未读判断
			if(message.getStatus() != null){
				ct.andStatusEqualTo(message.getStatus());
			}
			
			List<Message> messageList= messageMapper.selectByExample(me);
			return messageList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean addMessage(Message message) {
		if( messageMapper.insertSelective(message)>0  ){
			return true;
		}
		return false;
	}

	@Override
	public Integer getNoReadMessageCount(Message message) {
		MessageExample me = new MessageExample();
		cn.bupt.smartyagl.entity.autogenerate.MessageExample.Criteria ct =  me.createCriteria();
		ct.andUserIdEqualTo(message.getId());
		ct.andStatusEqualTo(ConstantsSql.MESSAGE_NO_READ);
		return messageMapper.countByExample(me);
	}

	@Override
	public boolean returnEvalute(Integer userId, Integer commentId) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("commentId", ""+commentId);
		return this.pushMessageToOne("您有商家回复您的评论，请注意查看", "评论", ConstantsSql.PUSH_COMMON, userId,map,null);
	}

	@Override
	public boolean update(Message message) {
		int i = messageMapper.updateByPrimaryKeySelective(message);
		if( i > 0){
			return true;
		}
		return false;
	}



}
