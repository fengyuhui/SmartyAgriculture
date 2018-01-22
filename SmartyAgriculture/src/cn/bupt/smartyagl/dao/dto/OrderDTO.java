package cn.bupt.smartyagl.dao.dto;

import java.util.ArrayList;

import cn.bupt.smartyagl.entity.autogenerate.OrderDetail;
import lombok.Data;


public class OrderDTO {
	
	private String orderId;
	
	private Integer userId;
	
	private Integer addressId;
	
	private String message;
	
	private Integer pickUp;
	
	private ArrayList<OrderDetail> orderDetailList ;

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public OrderDTO(String orderId, Integer userId, Integer addressId, String message, Integer pickUp,
			ArrayList<OrderDetail> orderDetailList) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.addressId = addressId;
		this.message = message;
		this.pickUp = pickUp;
		this.orderDetailList = orderDetailList;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getPickUp() {
		return pickUp;
	}

	public void setPickUp(Integer pickUp) {
		this.pickUp = pickUp;
	}

	public ArrayList<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(ArrayList<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer integer) {
		this.userId = integer;
	}

	public OrderDTO(Integer userId, Integer addressId, String message, Integer pickUp,
			ArrayList<OrderDetail> orderDetailList) {
		super();
		this.userId = userId;
		this.addressId = addressId;
		this.message = message;
		this.pickUp = pickUp;
		this.orderDetailList = orderDetailList;
	}

	public OrderDTO() {
		super();
	}
	
	

	
}
