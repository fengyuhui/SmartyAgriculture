package cn.bupt.smartyagl.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.bupt.smartyagl.dao.dto.OrderDTO;
import cn.bupt.smartyagl.dao.dto.OrderForm;
import cn.bupt.smartyagl.entity.autogenerate.OrderDetail;

public class OrderFormToOrderDTO {

	public static OrderDTO convert(OrderForm orderForm) {
		OrderDTO orderDTO = new OrderDTO();
		orderForm.setGoodsIds(orderForm.getGoodsIds().replaceAll("goodsID", "goodsId"));
		BeanUtils.copyProperties(orderForm, orderDTO);
		ArrayList<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		Gson gson = new Gson();
		orderDetailList = gson.fromJson(orderForm.getGoodsIds(),
				new TypeToken<List<OrderDetail>>() {}.getType());
		orderDTO.setOrderDetailList(orderDetailList);
		return orderDTO;
	}
}
