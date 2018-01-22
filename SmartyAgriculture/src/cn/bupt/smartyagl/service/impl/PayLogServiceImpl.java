package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.PayLogMapper;
import cn.bupt.smartyagl.dao.autogenerate.OrderListMapper;
import cn.bupt.smartyagl.entity.autogenerate.PayLog;
import cn.bupt.smartyagl.entity.autogenerate.PayLogExample;
import cn.bupt.smartyagl.entity.autogenerate.OrderList;
import cn.bupt.smartyagl.entity.autogenerate.OrderListExample;
import cn.bupt.smartyagl.entity.autogenerate.PayLogExample.Criteria;
import cn.bupt.smartyagl.service.IPayLogService;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-7-14 下午3:52:14 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Service
public class PayLogServiceImpl implements IPayLogService{

	@Autowired
	PayLogMapper payLogMapper;
	@Autowired
	OrderListMapper orderListMapper;
	
	@Override
	public boolean addPayLog(PayLog payLog) {
		payLog.setCreateTime(new Date());
		int rs = payLogMapper.insert(payLog);
		if(rs>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean updatePayLog(PayLog payLog) {
		payLog.setCreateTime(new Date());
		PayLogExample pe = new PayLogExample();
		pe.createCriteria().andTradeNoEqualTo(payLog.getTradeNo());
		int rs = payLogMapper.updateByExampleSelective(payLog, pe);
		if(rs>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateOrderByTradeNo(String tradeNo) {
		PayLogExample pe = new PayLogExample();
		pe.createCriteria().andTradeNoEqualTo(tradeNo);
		PayLog payLog = payLogMapper.selectByExample(pe).get(0);
		String orderIds = payLog.getOrderIds();
		//整理订单id格式
		List<Integer> list = new ArrayList<Integer>();
		String[] tmpList = orderIds.split(",");
		for(String tmp : tmpList){
			if(!tmp.equals("")){
				list.add(Integer.parseInt(tmp));
			}
		}
		//批量修改为已付款
		OrderListExample oe = new OrderListExample();
		oe.createCriteria().andIdIn(list);
		OrderList changeObj = new OrderList();
		changeObj.setStatus(ConstantsSql.SatusNoSend);
		orderListMapper.updateByExampleSelective(changeObj, oe);
//		List<orderList> lists = orderListMapper.selectByExample(oe);
//		for(orderList or:lists) {
//			or.setStatus(ConstantsSql.SatusNoSend);
//			orderListMapper.updateByPrimaryKeySelective(or);
//			System.out.println(new Date().toLocaleString()+"修改订单状态成功");
//		}
		//改为未发货
//		changeObj.setStatus(ConstantsSql.SatusNoSend);
//		
//		oe.createCriteria().andIdIn(list);
//		orderListMapper.updateByExampleSelective(changeObj, oe);
		return true;
	}
	
	@Override
	public int getTradeOrderStauts(String tradeNo) {
		// TODO Auto-generated method stub
		PayLogExample pe = new PayLogExample();
		Criteria cta = pe.createCriteria();
		cta.andTradeNoEqualTo(tradeNo);
		PayLog payLog = payLogMapper.selectByExample(pe).get(0);
		if(payLog==null){
			return 1;
		}else{
			if(payLog.getStatus()==0){
				return 0;
			}else{
				return 2;
			}
		}
	}

	@Override
	public boolean isExistTradeNo(String tradeNo) {
		PayLogExample pe = new PayLogExample();
		pe.createCriteria().andTradeNoEqualTo(tradeNo);
		List<PayLog> rs = payLogMapper.selectByExample(pe);
		if(rs.size()>0){
			return true;
		}
		return false;
	}

}
