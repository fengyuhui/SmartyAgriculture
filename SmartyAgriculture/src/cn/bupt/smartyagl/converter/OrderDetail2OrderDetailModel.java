package cn.bupt.smartyagl.converter;

import cn.bupt.smartyagl.dao.autogenerate.GoodsMapper;
import cn.bupt.smartyagl.dao.autogenerate.OrderMasterMapper;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.OrderDetail;
import cn.bupt.smartyagl.entity.autogenerate.OrderMaster;
import cn.bupt.smartyagl.model.OrderDetailModel;
import cn.bupt.smartyagl.util.SpringContextUtil;

public class OrderDetail2OrderDetailModel {

	public static OrderDetailModel conventer(OrderDetail orderDetail) {
		
		OrderDetailModel orderDetailModel = new OrderDetailModel();

		System.out.println("orderDetailId: "+orderDetail.getDetailId());
		orderDetailModel.setOrderDetailId(orderDetail.getDetailId());
		System.out.println("price: "+orderDetail.getPrice());
		orderDetailModel.setPrice(orderDetail.getPrice());
		System.out.println("time: "+orderDetail.getBuyTime());
		orderDetailModel.setBuyTime(orderDetail.getBuyTime());
		System.out.println("num: "+orderDetail.getNum());
		orderDetailModel.setNum(orderDetail.getNum());
		System.out.println("goodsMapper: "+(GoodsMapper) SpringContextUtil.getBean("goodsMapper"));
		GoodsMapper goodsMapper = (GoodsMapper) SpringContextUtil.getBean("goodsMapper");
		System.out.println("goodsId: "+orderDetail.getGoodsId());
		Goods goods = goodsMapper.selectByPrimaryKey(orderDetail.getGoodsId());
		String goodName = goods.getName();
		orderDetailModel.setGoodName(goodName);
		System.out.println("bean: "+(OrderMasterMapper) SpringContextUtil.getBean("orderMasterMapper"));
		OrderMasterMapper orderMasterMapper = (OrderMasterMapper) SpringContextUtil.getBean("orderMasterMapper");
		System.out.println("orderId: "+orderDetail.getOrderId());
		System.out.println("master: "+orderMasterMapper.selectByPrimaryKey(orderDetail.getOrderId()));
		
		OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderDetail.getOrderId());
		orderDetailModel.setStatus(orderMaster.getStatus());
		//orderDetailModel.setStatus(4);
		
		return orderDetailModel;
		
	}
	
}
