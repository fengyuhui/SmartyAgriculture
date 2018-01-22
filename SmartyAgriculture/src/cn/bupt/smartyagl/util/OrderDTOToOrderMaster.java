package cn.bupt.smartyagl.util;

import org.springframework.beans.BeanUtils;

import cn.bupt.smartyagl.dao.dto.OrderDTO;
import cn.bupt.smartyagl.entity.autogenerate.OrderMaster;

import cn.bupt.smartyagl.dao.dto.OrderDTO;
import cn.bupt.smartyagl.entity.autogenerate.OrderMaster;

public class OrderDTOToOrderMaster {

	/**
	 * orderDTO对象转换成orderMaster
	 */
	
	public static OrderMaster convert(OrderDTO orderDTO) {
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		return orderMaster;
	}
}
