package cn.bupt.smartyagl.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.dao.dto.OrderDTO;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrder;
import cn.bupt.smartyagl.entity.autogenerate.OrderDetail;
import cn.bupt.smartyagl.entity.autogenerate.OrderList;
import cn.bupt.smartyagl.entity.autogenerate.OrderMaster;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.model.OrderDetailModel;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-5-13 上午10:46:35 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface IOrderService {
	public Integer getSharerIdByOrderId(int id) ;
	
	public boolean addOrder(OrderDTO order);//添加订单
	
	public List<OrderMaster> getOrderList(OrderList orderList);//根据用户id获取订单列表
	
	public Map<String,Object> getOrderDetail(Integer orderId);//根据订单id获取订单详情
	
	public boolean updateOrder(OrderList order);//更改订单状态
	
	/**
	 * 获取各种订单数量，前端用
	 * @param orderId
	 * @return
	 */
	public Map<Integer,Integer> getOrderNums(Integer userId);
	
	/**
	 * 商品将退款订单钱退回商户（后台确认退款后用）
	 * @return
	 */
	public boolean rebackMondy( Integer orderId );
	
	/**
	 * 未登录购买时向用户手机邮箱发送订单信息
	 * orderList 订单号List 
	 * allPrice	总价格
	 */
	public boolean sendOrderMessage(String phone,String email,List<OrderList> orderLists,Double all_price);
	
	/**
	 * 获订单列表
	 * search:搜索条件
	 * @author wtt
	 */
	public void getOrderList(int userID,
			ModelAndView mav, int pageNumber, int pageSize, String orderBy,Date starttime,Date endtime, String orderId);
	/**
	 * 修改订单状态
	 * @param id
	 * @param status
	 * @param tracking 运单号
	 * @return
	 * @author waiting
	 */

	public OrderView getOrderById(Integer orderId);	//根据订单id获取订单详情
	
	public OrderMaster getOrderById(String orderId);
	
	public List<OrderView> getOrderListByIdList(String[] orderIdArray);//根据订单id列表获取订单列表
	
	public boolean updateOrdersByIdList(String[] orderIdArray);//批量更新订单状态从"未支付"为"待发货"
	
	public List<Integer> getGoodsIdList(String[] orderIdArray);//根据订单id列表获取goodsId列表

	public boolean exportExcelOrder(Date starttime,Date endtime,HttpServletResponse responce);//订单导出excel @wtt

	/**
	 * 根据物流的xml返回成map对象
	 * @param trackingNumber
	 * @return
	 * @author jm
	 */
	public Map getMap(String trackingNumber);
	
	/**
	 * 访问，返回物流xml
	 * @param trackingNumber
	 * @return
	 * @author jm
	 */
	public String getLogisticXml(String trackingNumber);
	/**
	 * 取消购物卡支付一半订单
	 * @param order
	 * @return
	 */
	public boolean cancleCardPaySomeOrder(String orderIds);
//	boolean addOrderWithoutLog(Integer num,Integer goodsId,Integer status);

	List<FarmVisitOrder> getVisitOrderListByIdList(String[] orderIdArray);

	FarmVisitOrder getVisitOrderById(Integer orderId);
	/**
	 * 根据输入的商品名搜索该用户购买的相关订单
	 * @param goodsName
	 * @return
	 */

	public List<OrderView> getOrderByName(String goodsName, Integer userId);

	public int deleteOrderById(Integer orderId);//删除订单
	
	public List<OrderList> getOrdersByUserId(Integer userId);//根据用户id获取其所有订单

	boolean updateOrderStatus(String id, int status, String tracking);

	public boolean updateOrderStatus(String id, Integer status);

	public List<OrderDetail> getOrderDetailList(String orderId);

	public Map<String, Object> getOrderMaster(String orderId);
	
	public List<OrderDetailModel> getOrderDetailByBlockManagerIdAndUnSolved(Integer blockManagerId);
	
	public List<OrderDetailModel> getOrderDetailByBlockManagerIdAndSolved(Integer blockManagerId);
}
