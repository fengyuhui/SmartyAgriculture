package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.dao.autogenerate.FarmVisitOrderMapper;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrder;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderExample;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderView;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitsExample;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.model.FarmVisitOrderViewModel;
import cn.bupt.smartyagl.service.IFarmVisitOrderService;

/**
 * 参观农场订单管理业务逻辑
 * @author TMing
 *
 */

@Service
public class FarmVisitOrderServiceImpl implements IFarmVisitOrderService{
	@Autowired 
	private FarmVisitOrderMapper farmVisitOrderMapper;

	@Override
	public boolean createOrder(FarmVisitOrder farmVisitOrder) {
		int i = farmVisitOrderMapper.insert(farmVisitOrder);
		if (0 != i)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteOrder(int id) {
		FarmVisitOrder fmo = new FarmVisitOrder();
		fmo.setId(id);
		fmo.setIsDelete(true);
		return farmVisitOrderMapper.updateByPrimaryKeySelective(fmo) == 1;
	}

	@Override
	public FarmVisitOrder getOrderByID(int id) {
		try {
			// 订单详情搜索
			FarmVisitOrderExample ae = new FarmVisitOrderExample();
			cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderExample.Criteria cta = ae
					.createCriteria();
			cta.andIdEqualTo(id);
			FarmVisitOrder farmVisitOrder = farmVisitOrderMapper.selectByExample(ae).get(0);
			return farmVisitOrder;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public FarmVisitOrder getVisitOrderByID(int id) {
		try {
			// 订单详情搜索
			FarmVisitOrderExample ae = new FarmVisitOrderExample();
			cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderExample.Criteria cta = ae
					.createCriteria();
			cta.andIdEqualTo(id);
			FarmVisitOrder farmVisitOrder = farmVisitOrderMapper.selectByExample(ae).get(0);
			return farmVisitOrder;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<FarmVisitOrder> getOrderListByIdList(String[] orderIdArray) {
		List<FarmVisitOrder> orderList = new ArrayList<FarmVisitOrder>();
		if (orderIdArray.length == 0) {
			return null;
		} else {
			for (int i = 0; i < orderIdArray.length; i++) {
				FarmVisitOrder orderView = this.getVisitOrderByID(Integer
						.parseInt(orderIdArray[i]));
				if (orderView != null) {
					orderList.add(orderView);
				}
			}
		}
		return orderList;
	}
	
	@Override
	public List<FarmVisitOrder> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean payOrder(Integer orderId,String tradeNumber) {
		FarmVisitOrder fo = new FarmVisitOrder();
		fo.setId(orderId);
		fo.setTradeNumber(tradeNumber);
		fo.setOrderStatus(2);
		farmVisitOrderMapper.updateByPrimaryKeySelective(fo);
		return true;
	}

	@Override
	public boolean updateOrderStatus(FarmVisitOrder or) {
		return farmVisitOrderMapper.updateByPrimaryKeySelective(or) != 0;
	}

}
