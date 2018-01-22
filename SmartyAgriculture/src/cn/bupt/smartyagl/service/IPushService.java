package cn.bupt.smartyagl.service;

import java.util.List;
import java.util.Map;

import cn.bupt.smartyagl.entity.autogenerate.Message;

/** 
 * 推送服务类
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-6-24 上午9:04:25 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface IPushService {
	
	/**
	 * 特价产品消息推送
	 * @param goodsIds
	 * @return
	 */
	public boolean specalOffer(List<Integer> goodsIds,String sendTime);
	
	/**
	 * 限时抢购
	 * @param goodsIds
	 * @return
	 */
	public boolean flashSale(List<Integer> goodsIds,String sendTime);

	/**
	 * 热卖商品
	 * @param goodsIds
	 * @return
	 */
	public boolean hotSale(List<Integer> goodsIds,String sendTime);
	

	/**
	 * 新品上市
	 * @param goodsIds
	 * @return
	 */
	public boolean newSale(List<Integer> goodsIds,String sendTime);
	
	/**
	 * 商品发货
	 * @param goodsIds
	 * @return
	 */
	public boolean sendGoods(Integer orderId);
	
	/**
	 * 异地登录
	 * @param userId
	 * @return
	 */
	public boolean remoteLogin(Integer userId);
	/**
	 * 退货成功
	 * @param goodsIds
	 * @return
	 */
	public boolean returnGoodsSucess(String orderId);
	
	/**
	 * 退货失败
	 * @param goodsIds
	 * @return
	 */
	public boolean returnGoodsFail(String orderId);
	
	/**
	 * 评价消息推送
	 * @param goodsIds
	 * @return
	 */
	public boolean returnEvalute(Integer userId,Integer commentId);
	
	/**
	 * 给用户推送指定信息
	 * @param content	推送内容
	 * @param type	推送消息类型
	 * @param userId	推送用户id
	 * @return
	 */
	public boolean pushMessageToOne(String content,String title,Integer type, Integer userId,Map<String,String> map,String sendTime);
	
	/**
	 * 推送信息给所有人
	 * @param content
	 * @param type
	 * @return
	 */
	public boolean pushMessageToAll(String content,String title, Integer type,Map<String,String> map,String sendTime);
	
	/**
	 * 获取消息记录列表
	 */
	public List getMessageList(Message message);
	
	/**
	 * 添加消息
	 */
	public boolean addMessage(Message message);
	
	/**
	 * 获得未读消息个数
	 */
	public Integer getNoReadMessageCount(Message message);
	
	/**
	 * 更新消息状态
	 * @param message
	 * @return
	 */
	public boolean update(Message message);

}
