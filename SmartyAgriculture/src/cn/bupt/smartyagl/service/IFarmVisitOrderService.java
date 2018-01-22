package cn.bupt.smartyagl.service;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrder;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderView;
import cn.bupt.smartyagl.model.FarmVisitOrderViewModel;

/**
 * 参观农场订单管理接口
 * @author TMing
 *
 */

public interface IFarmVisitOrderService {
	
	boolean createOrder(FarmVisitOrder farmVisitOrder);
	boolean deleteOrder(int id);
	FarmVisitOrder getOrderByID(int id);
	List<FarmVisitOrder> getAllOrders();
	
	/**
	 * 支付完成订单
	 */
	boolean payOrder( Integer orderId,String tradeNumber );
	
	boolean updateOrderStatus(FarmVisitOrder or);
	
	List<FarmVisitOrder> getOrderListByIdList(String[] orderIdArray);
	FarmVisitOrder getVisitOrderByID(int id);
	
}
